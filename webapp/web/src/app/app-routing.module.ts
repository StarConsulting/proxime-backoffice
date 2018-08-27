import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterComponent } from './auth/register/component/register.component';


const routes: Routes = [
  /*
    here you can add routes for your app.
    You should specify the uri path, the component to use
    and import the component.
  */ 
  {
    path:'register',
    component: RegisterComponent
  }
];


/*
  You don't need touch this for declare new routes
*/

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ],
})
export class AppRoutingModule { }
