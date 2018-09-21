import { NgModule } from '@angular/core';
import { Routes,  RouterModule} from '@angular/router';

import { AddcompanyComponent } from "./addcompany/addcompany.component";
import { AdmincompanyComponent } from "./admincompany/admincompany.component";

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'add-company',
        component: AddcompanyComponent,
        data: {
          title: 'Add Company'
        }
      },
      {
        path: 'admin-company',
        component: AdmincompanyComponent,
        data: {
          title: 'Admin Company'
        }
      }
    ]
  }
];
@NgModule ({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdmincompanyRoutingModule { }
