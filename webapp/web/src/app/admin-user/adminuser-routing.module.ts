import { NgModule } from '@angular/core';
import { Routes,  RouterModule} from '@angular/router';
import { AdduserPageComponent} from './adduser-page/adduser-page.component';
import { AdminuserPageComponent} from './adminuser-page/adminuser-page.component';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'adduser',
        component: AdduserPageComponent,
        data: {
          title: 'Add User'
        }
      },
      {
        path: 'adminuser',
        component: AdminuserPageComponent,
        data: {
          title: 'Admin User'
        }
      }
      ]
  }
];
    @NgModule ({
      imports: [RouterModule.forChild(routes)],
      exports: [RouterModule],
    })
export class AdminuserRoutingModule { }
