export interface BreadcrumbItemModel {
  label: string;
  routerLink?: string | readonly unknown[];
  queryParams?: Record<string, string | number | boolean | undefined>;
  current?: boolean;
}
