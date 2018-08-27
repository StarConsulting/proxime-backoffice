import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { User } from '../models/User';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  public headers = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };


  /**
   * Api url
   */
  registerUrl : string = 'https://ventydhk62.execute-api.us-east-2.amazonaws.com/dev/auth/signup';

  
  constructor(public httpClient: HttpClient) { }



  register(user:User){
  
    console.log('Registrando usuario '+ user.username);

    return this.httpClient.post(this.registerUrl, user, this.headers).pipe(
      
    );
  }
}
