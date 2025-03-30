import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';
import { DirectiveExampleComponent } from '../directive-example/directive-example.component';
import { LocalStorageExampleComponent } from '../local-storage-example/local-storage-example.component';
import { ApiExampleComponent } from '../api-example/api-example.component';
import { PipesExampleComponent } from '../pipes-example/pipes-example.component';

@Component({
  selector: 'app-home',
  imports: [CommonModule,
    DirectiveExampleComponent,
    LocalStorageExampleComponent,
    ApiExampleComponent,
    PipesExampleComponent],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  standalone: true
})
export class HomeComponent {
  constructor(private authService: AuthService, private router: Router) {}
  activeContent: string = '';

  // Called when a button is clicked
  showContent(section: string) {
    this.activeContent = section;
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']); // Redirect to login
  }
}
