/**
 * Declare modules dependencies here
 */
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';

/**
 * Declare components dependencies here
 */
import { AppComponent } from './app.component';
import { RegisterComponent } from './auth/register/component/register.component';
import { ContentLayoutComponent } from './layout/content-layout/content-layout.component';

/**
 * Declare services dependencies here
 */
import { RegisterService } from './services/register.service';



@NgModule({

  /**
   * Declare the components to use
   */
  declarations: [
    AppComponent,
    RegisterComponent,
    ContentLayoutComponent
  ],

  /**
   * Declare the modules to import
   */
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],

  /**
   * Declare the providers here, like a service or something like these
   */
  providers: [
    RegisterService
  ],

  bootstrap: [AppComponent]
})
export class AppModule { }
