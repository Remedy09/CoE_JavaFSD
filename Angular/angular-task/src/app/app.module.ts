import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { AuthService } from './services/auth.service';
import { AuthGuard } from './guard/auth.guard';
import { HighlightDirective } from './directive/highlight.directive';
import { DirectiveExampleComponent } from './components/directive-example/directive-example.component';
import { LocalStorageExampleComponent } from './components/local-storage-example/local-storage-example.component';
import { HttpClientModule } from '@angular/common/http';
import { PipesExampleComponent } from './components/pipes-example/pipes-example.component';
import { ReverseTextPipe } from './pipes/reverse-text.pipe';

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    PipesExampleComponent,
    HttpClientModule,
    HomeComponent,    // Standalone component imported here
    DirectiveExampleComponent,
    LocalStorageExampleComponent,
    LoginComponent,   // Standalone component imported here
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HighlightDirective, // Non-standalone directive declared here
    ReverseTextPipe
  ],
  providers: [AuthService, AuthGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
