import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HighlightDirective } from '../../directive/highlight.directive';

@Component({
  selector: 'app-home-child',
  standalone: true,
  imports: [CommonModule, HighlightDirective],
  template: `
    <div appHighlight="lightblue" class="child-card">
      <h3>{{ title }}</h3>
      <p>{{ description }}</p>
    </div>
  `,
  styles: [`
    .child-card {
      padding: 16px;
      border: 1px solid #ccc;
      margin-bottom: 12px;
      border-radius: 4px;
      transition: background-color 0.3s ease;
    }
  `]
})
export class HomeChildComponent {
  @Input() title: string = '';
  @Input() description: string = '';
}
