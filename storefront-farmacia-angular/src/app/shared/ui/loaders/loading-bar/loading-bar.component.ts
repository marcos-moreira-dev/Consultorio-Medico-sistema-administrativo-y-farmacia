import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-loading-bar',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './loading-bar.component.html',
  styleUrls: ['./loading-bar.component.css'],
})
export class LoadingBarComponent {
  @Input() visible = false;
}
