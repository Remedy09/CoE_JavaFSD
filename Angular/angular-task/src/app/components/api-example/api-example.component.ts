import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-api-example',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './api-example.component.html',
  styleUrls: ['./api-example.component.css']
})
export class ApiExampleComponent {
  posts: any[] = [];

  constructor(private apiService: ApiService) {}

  fetchData() {
    this.apiService.fetchPosts().subscribe((data) => {
      this.posts = data;
    });
  }
}
