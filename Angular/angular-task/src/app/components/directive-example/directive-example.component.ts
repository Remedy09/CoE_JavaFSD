import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeChildComponent } from '../home-child/home-child.component';

@Component({
  selector: 'app-directive-example',
  standalone: true,
  imports: [CommonModule, HomeChildComponent], // Import HomeChildComponent
  template: `
    <h2>Directive & Parent-Child Example</h2>
    <app-home-child title="Angular Directives" description="This is an example of a directive usage."></app-home-child>
    <app-home-child title="Reusable Component" description="This child component can be used multiple times."></app-home-child>
  `,
  styles: [`
/* Import Google Font */
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap');

/* Apply the font globally */
body, h2, p, button, app-home-child {
  font-family: 'Poppins', sans-serif !important;
}

/* Title Styling */
h2 {
  text-align: center;
  font-size: 28px;
  font-weight: 600;
  color: #333;
  margin-bottom: 20px;
}

/* Container for Child Components */
div {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 15px;
}

/* Child Component Styling */
app-home-child {
  width: 90%;
  max-width: 500px;
  padding: 16px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease-in-out;
  font-family: 'Poppins', sans-serif; /* Ensuring font applies */
}

/* Hover Effect */
app-home-child:hover {
  transform: scale(1.02);
}

  `]
})
export class DirectiveExampleComponent { }
