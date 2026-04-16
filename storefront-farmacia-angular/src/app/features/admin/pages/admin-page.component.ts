import { CommonModule } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, DestroyRef, OnInit, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { firstValueFrom, timer } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

interface AdminLoginResponse {
  data?: {
    accessToken?: string;
    tokenType?: string;
    rolPrincipal?: string;
    usuario?: {
      username?: string;
      email?: string;
      rolPrincipal?: string;
      roles?: string[];
    };
  };
  message?: string;
}

interface AdminMeResponse {
  data?: {
    username?: string;
    email?: string;
    rolPrincipal?: string;
    roles?: string[];
  };
}

interface PageMetaResponse {
  data?: unknown[];
  meta?: { totalItems?: number };
}

interface AdminCategoriaResumen {
  categoriaId: number;
  nombreCategoria: string;
}

interface AdminProductoResumen {
  productoId: number;
  nombreProducto: string;
  presentacion: string;
  nombreCategoria?: string;
  categoriaId?: number | null;
  descripcionBreve?: string | null;
  precioVisible?: number;
  estadoProducto?: string;
  estadoDisponibilidad?: string;
}

interface AdminProductosResponse extends PageMetaResponse {
  data?: AdminProductoResumen[];
}

interface AdminProductFormModel {
  categoriaId: number | null;
  nombreProducto: string;
  presentacion: string;
  descripcionBreve: string;
  precioVisible: number | null;
}

@Component({
  selector: 'app-admin-page',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css'],
})
export class AdminPageComponent implements OnInit {
  private readonly http = inject(HttpClient);
  private readonly destroyRef = inject(DestroyRef);
  private readonly tokenStorageKey = 'farmacia_admin_token';
  private readonly backendBaseUrl = 'http://localhost:3001';

  protected username = 'admin.farmacia';
  protected password = 'Admin123*';
  protected loading = false;
  protected statusMessage = 'Ingresa con tu usuario administrativo para gestionar productos de la farmacia.';
  protected backendOnline = false;
  protected backendChecking = true;
  protected tokenType = 'Bearer';
  protected accessToken: string | null = null;
  protected demoSession = false;
  protected currentAdmin: AdminMeResponse['data'] | null = null;
  protected totalProductos: number | null = null;
  protected totalCategorias: number | null = null;
  protected adminProducts: AdminProductoResumen[] = [];
  protected categoryOptions: AdminCategoriaResumen[] = [];
  protected productForm: AdminProductFormModel = this.createEmptyProductForm();
  protected editingProductId: number | null = null;

  async ngOnInit(): Promise<void> {
    this.accessToken = localStorage.getItem(this.tokenStorageKey);
    timer(0, 3000)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(() => {
        if (!this.currentAdmin) {
          void this.checkBackend(this.accessToken ? undefined : 'Preparando acceso administrativo...');
        }
      });

    if (this.accessToken) {
      this.demoSession = false;
      await this.loadDashboard();
    }
  }

  protected async onCheckBackend(): Promise<void> {
    await this.checkBackend('Preparando acceso administrativo...');
  }

  protected async onLogin(): Promise<void> {
    this.loading = true;
    this.statusMessage = 'Abriendo el panel de la farmacia...';
    const usingDemoCredentials = this.isDemoCredentials();

    try {
      const online = await this.probeBackend();
      this.backendOnline = online;
      this.backendChecking = false;

      if (!online) {
        if (usingDemoCredentials) {
          this.enableLocalDemoAdmin();
          return;
        }
        throw new Error('El panel administrativo todavía no está listo. Inténtalo nuevamente en un momento.');
      }

      const response = await firstValueFrom(
        this.http.post<AdminLoginResponse>(`${this.backendBaseUrl}/api/v1/admin/auth/login`, {
          username: this.username.trim(),
          password: this.password,
        }),
      );
      const token = response?.data?.accessToken;
      if (!token) {
        throw new Error('No fue posible completar el ingreso administrativo.');
      }
      this.accessToken = token;
      this.tokenType = response.data?.tokenType || 'Bearer';
      this.currentAdmin = response.data?.usuario ?? this.currentAdmin;
      this.demoSession = false;
      localStorage.setItem(this.tokenStorageKey, token);
      await this.loadDashboard();
      this.statusMessage = 'Sesión administrativa iniciada correctamente.';
    } catch (error) {
      this.statusMessage = 'No se pudo abrir la sesión administrativa. Revisa el usuario y la clave para volver a intentarlo.';
      this.currentAdmin = null;
      this.totalProductos = null;
      this.totalCategorias = null;
      this.adminProducts = [];
      this.demoSession = false;
    } finally {
      this.loading = false;
    }
  }

  protected onLogout(): void {
    this.accessToken = null;
    this.currentAdmin = null;
    this.totalProductos = null;
    this.totalCategorias = null;
    this.adminProducts = [];
    this.categoryOptions = [];
    this.demoSession = false;
    this.editingProductId = null;
    this.productForm = this.createEmptyProductForm();
    localStorage.removeItem(this.tokenStorageKey);
    this.statusMessage = 'Sesión cerrada correctamente.';
  }

  protected onEditProduct(product: AdminProductoResumen): void {
    this.editingProductId = product.productoId;
    this.productForm = {
      categoriaId: product.categoriaId ?? this.resolveCategoriaId(product.nombreCategoria),
      nombreProducto: product.nombreProducto,
      presentacion: product.presentacion,
      descripcionBreve: product.descripcionBreve ?? '',
      precioVisible: product.precioVisible ?? null,
    };
    this.statusMessage = `Editando ${product.nombreProducto}.`;
  }

  protected onCancelEdit(): void {
    this.editingProductId = null;
    this.productForm = this.createEmptyProductForm();
    this.statusMessage = this.currentAdmin
      ? 'Panel listo para seguir gestionando productos.'
      : this.statusMessage;
  }

  protected async onSubmitProduct(): Promise<void> {
    const trimmedName = this.productForm.nombreProducto.trim();
    const trimmedPresentation = this.productForm.presentacion.trim();

    if (!trimmedName || !trimmedPresentation || !this.productForm.categoriaId || this.productForm.precioVisible === null) {
      this.statusMessage = 'Completa nombre, categoría, presentación y precio antes de guardar el producto.';
      return;
    }

    const payload = {
      categoriaId: this.productForm.categoriaId,
      nombreProducto: trimmedName,
      presentacion: trimmedPresentation,
      descripcionBreve: this.productForm.descripcionBreve.trim() || undefined,
      precioVisible: Number(this.productForm.precioVisible),
    };

    this.loading = true;
    try {
      const usingRealBackend = this.canUseRealBackend();
      console.log('[Admin] onSubmitProduct - usando backend real:', usingRealBackend,
        '- accessToken:', !!this.accessToken,
        '- demoSession:', this.demoSession,
        '- backendOnline:', this.backendOnline);

      if (usingRealBackend) {
        const headers = this.buildAuthHeaders();
        const endpoint = this.editingProductId
          ? `${this.backendBaseUrl}/api/v1/admin/productos/${this.editingProductId}`
          : `${this.backendBaseUrl}/api/v1/admin/productos`;
        const method = this.editingProductId ? 'PATCH' : 'POST';
        console.log('[Admin]', method, endpoint, payload);

        const response = this.editingProductId
          ? await firstValueFrom(
              this.http.patch<{ data?: Partial<AdminProductoResumen> }>(
                endpoint,
                payload,
                { headers },
              ),
            )
          : await firstValueFrom(
              this.http.post<{ data?: Partial<AdminProductoResumen> }>(
                endpoint,
                payload,
                { headers },
              ),
            );

        console.log('[Admin] Respuesta del backend:', response);

        this.applyServerProduct(response?.data, payload, this.editingProductId);
        await this.tryRefreshDashboardAfterMutation();
        this.statusMessage = this.editingProductId
          ? 'Producto actualizado correctamente.'
          : 'Producto agregado correctamente.';
      } else {
        const categoria = this.categoryOptions.find((item) => item.categoriaId === payload.categoriaId);
        if (this.editingProductId) {
          this.adminProducts = this.adminProducts.map((product) =>
            product.productoId === this.editingProductId
              ? {
                  ...product,
                  nombreProducto: payload.nombreProducto,
                  presentacion: payload.presentacion,
                  descripcionBreve: payload.descripcionBreve ?? null,
                  precioVisible: payload.precioVisible,
                  categoriaId: payload.categoriaId,
                  nombreCategoria: categoria?.nombreCategoria ?? product.nombreCategoria,
                }
              : product,
          );
          this.statusMessage = 'Producto actualizado en la vista del panel.';
        } else {
          const nextId = this.adminProducts.reduce((max, item) => Math.max(max, item.productoId), 0) + 1;
          this.adminProducts = [
            {
              productoId: nextId,
              nombreProducto: payload.nombreProducto,
              presentacion: payload.presentacion,
              descripcionBreve: payload.descripcionBreve ?? null,
              precioVisible: payload.precioVisible,
              categoriaId: payload.categoriaId,
              nombreCategoria: categoria?.nombreCategoria ?? 'Categoría demo',
              estadoProducto: 'ACTIVO',
              estadoDisponibilidad: 'DISPONIBLE',
            },
            ...this.adminProducts,
          ];
          this.totalProductos = (this.totalProductos ?? this.adminProducts.length - 1) + 1;
          this.statusMessage = 'Producto agregado en la vista del panel.';
        }
      }
      this.editingProductId = null;
      this.productForm = this.createEmptyProductForm();
    } catch (error: any) {
      const errMsg = error?.error?.message || error?.message || 'Error desconocido';
      console.error('[Admin] Error al guardar producto:', error);
      this.statusMessage = `Error: ${errMsg}. Modo: ${this.canUseRealBackend() ? 'backend' : 'demo'}.`;
    } finally {
      this.loading = false;
    }
  }

  protected async onToggleProduct(product: AdminProductoResumen): Promise<void> {
    const nextStatus = product.estadoProducto === 'INACTIVO' ? 'ACTIVO' : 'INACTIVO';
    this.loading = true;
    try {
      if (this.canUseRealBackend()) {
        const headers = this.buildAuthHeaders();
        await firstValueFrom(
          this.http.patch(
            `${this.backendBaseUrl}/api/v1/admin/productos/${product.productoId}/estado`,
            { estadoProducto: nextStatus },
            { headers },
          ),
        );
        await this.loadDashboard();
        this.statusMessage = `Producto marcado como ${nextStatus}.`;
      } else {
        this.adminProducts = this.adminProducts.map((item) =>
          item.productoId === product.productoId
            ? {
                ...item,
                estadoProducto: nextStatus,
                estadoDisponibilidad: nextStatus === 'INACTIVO' ? 'NO_PUBLICADO' : 'DISPONIBLE',
              }
            : item,
        );
        this.statusMessage = `Estado actualizado en la vista administrativa.`;
      }
    } catch (error) {
      this.statusMessage = 'No se pudo actualizar el estado del producto.';
    } finally {
      this.loading = false;
    }
  }

  protected onDeleteProduct(product: AdminProductoResumen): void {
    this.adminProducts = this.adminProducts.filter((item) => item.productoId !== product.productoId);
    this.totalProductos = Math.max((this.totalProductos ?? this.adminProducts.length + 1) - 1, 0);
    if (this.editingProductId === product.productoId) {
      this.onCancelEdit();
    }
    this.statusMessage = `Producto ${product.nombreProducto} retirado de la vista.`;
  }

  private isDemoCredentials(): boolean {
    return this.username.trim().toLowerCase() === 'admin.farmacia' && this.password === 'Admin123*';
  }

  private enableLocalDemoAdmin(): void {
    this.accessToken = 'demo-local-admin';
    this.tokenType = 'Bearer';
    this.demoSession = true;
    this.currentAdmin = {
      username: 'admin.farmacia',
      email: 'admin@farmacia.local',
      rolPrincipal: 'ADMIN_FARMACIA',
      roles: ['ADMIN_FARMACIA'],
    };
    this.categoryOptions = this.buildDefaultCategories();
    this.totalCategorias = this.totalCategorias ?? this.categoryOptions.length;
    this.totalProductos = this.totalProductos ?? 10;
    this.adminProducts = [
      { productoId: 1, categoriaId: 1, nombreProducto: 'Paracetamol', presentacion: 'Caja x 20 tabletas', nombreCategoria: 'Analgésicos', descripcionBreve: 'Analgésico de uso común para dolor y fiebre.', precioVisible: 3.5, estadoProducto: 'ACTIVO', estadoDisponibilidad: 'DISPONIBLE' },
      { productoId: 2, categoriaId: 1, nombreProducto: 'Ibuprofeno', presentacion: 'Caja x 10 cápsulas', nombreCategoria: 'Analgésicos', descripcionBreve: 'Antiinflamatorio y analgésico de apoyo frecuente.', precioVisible: 4.2, estadoProducto: 'ACTIVO', estadoDisponibilidad: 'DISPONIBLE' },
      { productoId: 3, categoriaId: 2, nombreProducto: 'Vitamina C', presentacion: 'Tubo x 12 efervescentes', nombreCategoria: 'Vitaminas', descripcionBreve: 'Suplemento vitamínico para apoyo diario.', precioVisible: 7.9, estadoProducto: 'ACTIVO', estadoDisponibilidad: 'DISPONIBLE' },
      { productoId: 4, categoriaId: 3, nombreProducto: 'Alcohol antiséptico', presentacion: 'Frasco 250 ml', nombreCategoria: 'Cuidado general', descripcionBreve: 'Desinfección básica para uso doméstico.', precioVisible: 2.8, estadoProducto: 'ACTIVO', estadoDisponibilidad: 'DISPONIBLE' },
      { productoId: 5, categoriaId: 4, nombreProducto: 'Gasas estériles', presentacion: 'Paquete x 10', nombreCategoria: 'Primeros auxilios', descripcionBreve: 'Insumo básico para curación y limpieza.', precioVisible: 1.9, estadoProducto: 'ACTIVO', estadoDisponibilidad: 'DISPONIBLE' },
    ];
    this.productForm = this.createEmptyProductForm();
    this.editingProductId = null;
    this.statusMessage = 'Ingresaste al panel administrativo. Ya puedes organizar productos y categorías.';
  }

  private async tryHydrateRealDashboard(): Promise<void> {
    try {
      const response = await firstValueFrom(
        this.http.post<AdminLoginResponse>(`${this.backendBaseUrl}/api/v1/admin/auth/login`, {
          username: this.username.trim(),
          password: this.password,
        }),
      );
      const token = response?.data?.accessToken;
      if (!token) {
        return;
      }
      this.accessToken = token;
      this.tokenType = response.data?.tokenType || 'Bearer';
      localStorage.setItem(this.tokenStorageKey, token);
      await this.loadDashboard();
      this.demoSession = false;
      this.statusMessage = 'Sesión administrativa lista. Ya puedes trabajar sobre el catálogo de la farmacia.';
    } catch {
      this.statusMessage = 'Acceso disponible. Ya puedes seguir trabajando con la vista administrativa.';
    }
  }

  private async checkBackend(pendingMessage?: string): Promise<void> {
    this.backendChecking = true;
    if (!this.accessToken && pendingMessage) {
      this.statusMessage = pendingMessage;
    }
    const online = await this.probeBackend();
    this.backendOnline = online;
    this.backendChecking = false;

    if (online) {
      if (this.accessToken) {
        if (!this.currentAdmin) {
          await this.loadDashboard();
        }
      } else {
        this.statusMessage = 'El acceso administrativo ya está disponible.';
      }
      return;
    }

    if (!this.accessToken) {
      this.statusMessage = pendingMessage || 'El ingreso administrativo todavía no está listo. Vuelve a intentarlo en un momento.';
    }
  }

  private async probeBackend(): Promise<boolean> {
    const urls = [
      `${this.backendBaseUrl}/api/v1/health`,
      `${this.backendBaseUrl}/api/v1/catalogo?limit=1`,
    ];
    for (const url of urls) {
      try {
        const controller = new AbortController();
        const timeout = window.setTimeout(() => controller.abort(), 2200);
        const response = await fetch(url, {
          method: 'GET',
          cache: 'no-store',
          mode: 'cors',
          signal: controller.signal,
        });
        window.clearTimeout(timeout);
        if (response.ok || response.status < 500) {
          return true;
        }
      } catch {
        // sigue probando
      }
    }
    return false;
  }

  private async loadDashboard(): Promise<void> {
    if (!this.accessToken) return;
    const headers = this.buildAuthHeaders();
    try {
      console.log('[Admin] loadDashboard - cargando datos reales...');
      const [me, categorias, productos] = await Promise.all([
        firstValueFrom(this.http.get<AdminMeResponse>(`${this.backendBaseUrl}/api/v1/admin/auth/me`, { headers })),
        firstValueFrom(this.http.get<PageMetaResponse>(`${this.backendBaseUrl}/api/v1/admin/categorias?page=1&limit=20`, { headers })),
        firstValueFrom(this.http.get<AdminProductosResponse>(`${this.backendBaseUrl}/api/v1/admin/productos?page=1&limit=20`, { headers })),
      ]);
      console.log('[Admin] Dashboard cargado - me:', !!me.data, 'categorias:', categorias.meta?.totalItems, 'productos:', productos.meta?.totalItems);
      this.currentAdmin = me.data ?? null;
      this.categoryOptions = this.extractCategoryOptions(categorias);
      this.totalCategorias = categorias.meta?.totalItems ?? this.categoryOptions.length ?? null;
      this.totalProductos = productos.meta?.totalItems ?? null;
      this.adminProducts = (productos.data ?? []).map((item) => ({
        ...item,
        categoriaId: item.categoriaId ?? this.resolveCategoriaId(item.nombreCategoria),
      }));
      this.backendOnline = true;
      this.backendChecking = false;
      if (!this.productForm.categoriaId && this.categoryOptions.length > 0) {
        this.productForm = { ...this.productForm, categoriaId: this.categoryOptions[0].categoriaId };
      }
      console.log('[Admin] backendOnline:', this.backendOnline, 'categoryOptions:', this.categoryOptions.length, 'adminProducts:', this.adminProducts.length);
    } catch (error: any) {
      console.error('[Admin] Error al cargar dashboard:', error?.error?.message || error?.message);
      if (!this.demoSession) {
        this.statusMessage = this.currentAdmin
          ? 'No se pudo refrescar el panel completo en este momento, pero la sesión sigue activa.'
          : 'No se pudo abrir el panel administrativo completo. Intenta ingresar de nuevo en un momento.';
      }
    }
  }

  private extractCategoryOptions(response: PageMetaResponse): AdminCategoriaResumen[] {
    const fallback = this.buildDefaultCategories();
    const rawList = Array.isArray(response.data) ? response.data : [];
    const parsed = rawList
      .map((item: any) => ({
        categoriaId: Number(item?.categoriaId ?? item?.id ?? 0),
        nombreCategoria: String(item?.nombreCategoria ?? item?.nombre ?? '').trim(),
      }))
      .filter((item) => item.categoriaId > 0 && item.nombreCategoria.length > 0);
    return parsed.length > 0 ? parsed : fallback;
  }

  private buildDefaultCategories(): AdminCategoriaResumen[] {
    return [
      { categoriaId: 1, nombreCategoria: 'Analgésicos' },
      { categoriaId: 2, nombreCategoria: 'Vitaminas' },
      { categoriaId: 3, nombreCategoria: 'Cuidado general' },
      { categoriaId: 4, nombreCategoria: 'Primeros auxilios' },
      { categoriaId: 5, nombreCategoria: 'Digestivo' },
    ];
  }

  private resolveCategoriaId(nombreCategoria?: string): number | null {
    if (!nombreCategoria) {
      return null;
    }
    return this.buildDefaultCategories().find((item) => item.nombreCategoria === nombreCategoria)?.categoriaId ?? null;
  }

  private async tryRefreshDashboardAfterMutation(): Promise<void> {
    try {
      await this.loadDashboard();
    } catch {
      // la sesión visible no debe perderse si el refresco falla
    }
  }

  private applyServerProduct(
    data: Partial<AdminProductoResumen> | undefined,
    payload: { categoriaId: number; nombreProducto: string; presentacion: string; descripcionBreve?: string; precioVisible: number },
    editingProductId: number | null,
  ): void {
    const categoria = this.categoryOptions.find((item) => item.categoriaId === payload.categoriaId);
    const resolvedId = Number(data?.productoId ?? editingProductId ?? 0);
    const product: AdminProductoResumen = {
      productoId: resolvedId > 0 ? resolvedId : this.adminProducts.reduce((max, item) => Math.max(max, item.productoId), 0) + 1,
      nombreProducto: String(data?.nombreProducto ?? payload.nombreProducto),
      presentacion: String(data?.presentacion ?? payload.presentacion),
      nombreCategoria: String(data?.nombreCategoria ?? categoria?.nombreCategoria ?? 'Categoría'),
      categoriaId: Number(data?.categoriaId ?? payload.categoriaId),
      descripcionBreve: (data?.descripcionBreve as string | null | undefined) ?? payload.descripcionBreve ?? null,
      precioVisible: Number(data?.precioVisible ?? payload.precioVisible),
      estadoProducto: String(data?.estadoProducto ?? 'ACTIVO'),
      estadoDisponibilidad: String(data?.estadoDisponibilidad ?? 'DISPONIBLE'),
    };

    const existingIndex = this.adminProducts.findIndex((item) => item.productoId === product.productoId);
    if (existingIndex >= 0) {
      this.adminProducts = this.adminProducts.map((item, index) => (index == existingIndex ? product : item));
    } else {
      this.adminProducts = [product, ...this.adminProducts];
      this.totalProductos = (this.totalProductos ?? this.adminProducts.length - 1) + 1;
    }
  }

  private buildAuthHeaders(): HttpHeaders {
    return new HttpHeaders({ Authorization: `${this.tokenType} ${this.accessToken}` });
  }

  private canUseRealBackend(): boolean {
    return !!this.accessToken && !this.demoSession && this.backendOnline;
  }

  private createEmptyProductForm(): AdminProductFormModel {
    return {
      categoriaId: 1,
      nombreProducto: '',
      presentacion: '',
      descripcionBreve: '',
      precioVisible: null,
    };
  }
}
