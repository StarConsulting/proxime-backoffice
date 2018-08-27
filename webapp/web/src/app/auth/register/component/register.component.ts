import { Component, ViewChild, NgModule } from '@angular/core';
import { NgForm } from '@angular/forms';
import { RegisterService } from '../../../services/register.service';
import { User } from '../../../models/User';

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss']
})

export class RegisterComponent {

    private user:User = new User(
        'register',
        'test',
        'test',
        'user11',
        'user11@correo.com',
        '000000',
        '+56676086865'
    );

    constructor(private registerService: RegisterService){}
    
    @ViewChild('f') registerForm: NgForm;

    onSubmit() { 

        this.registerService.register(this.user);
    }
}