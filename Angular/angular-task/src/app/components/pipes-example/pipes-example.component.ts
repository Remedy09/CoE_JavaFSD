import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReverseTextPipe } from '../../pipes/reverse-text.pipe';

@Component({
  selector: 'app-pipes-example',
  standalone: true,
  imports: [CommonModule, ReverseTextPipe], // Import CommonModule & Custom Pipe
  templateUrl: './pipes-example.component.html',
  styleUrls: ['./pipes-example.component.css'],
})
export class PipesExampleComponent {
  title: string = 'Angular Pipes Example';
  today: Date = new Date();
  amount: number = 1234.56;
  percentage: number = 0.75;
}
