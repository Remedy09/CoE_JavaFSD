import { Component } from '@angular/core';

@Component({
  selector: 'app-local-storage-example',
  templateUrl: './local-storage-example.component.html',
  styleUrls: ['./local-storage-example.component.css'],
  standalone: true,
})
export class LocalStorageExampleComponent {
  counter: number = 0;

  constructor() {
    this.loadData(); // Load saved counter when component initializes
  }

  increment() {
    this.counter++;
  }

  decrement() {
    if (this.counter > 0) {
      this.counter--;
    }
  }

  saveData() {
    localStorage.setItem('counter', this.counter.toString());
  }

  loadData() {
    const storedCounter = localStorage.getItem('counter');
    this.counter = storedCounter ? parseInt(storedCounter, 10) : 0;
  }

  deleteData() {
    localStorage.removeItem('counter');
    this.counter = 0; // Reset to 0
  }
}
