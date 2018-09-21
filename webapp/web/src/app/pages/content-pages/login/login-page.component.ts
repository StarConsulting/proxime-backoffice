import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router, ActivatedRoute } from "@angular/router";
import { Dashboard1Component } from '../../../dashboard/dashboard1/dashboard1.component';

@Component({
    selector: 'app-login-page',
    templateUrl: './login-page.component.html',
    styleUrls: ['./login-page.component.scss']
})

export class LoginPageComponent {

  public email: string;
  public pass: string;

    @ViewChild('f') loginForm: NgForm;

    constructor(private router: Router,
        private route: ActivatedRoute) { }

    onSubmit() {
      if(this.email === 'admin' || this.pass === 'admin') {
        this.router.navigate(['/dashboard/dashboard1']);
      } else {
        alert('Usuario Incorrecto');
      }
    }
    // On Forgot password link click
    onForgotPassword() {
        this.router.navigate(['forgotpassword'], { relativeTo: this.route.parent });
    }
    // On registration link click
    onRegister() {
        this.router.navigate(['register'], { relativeTo: this.route.parent });
    }
}
