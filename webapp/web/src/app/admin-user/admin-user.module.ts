import { NgModule } from '@angular/core';
import { CommonModule } from "@angular/common";

import { AgmCoreModule } from '@agm/core';
import { AdminuserRoutingModule } from "./adminuser-routing.module";

import { AdduserPageComponent } from "./adduser-page/adduser-page.component";
import { AdminuserPageComponent } from "./adminuser-page/adminuser-page.component";

@NgModule({
  imports: [
    CommonModule,
    AdminuserRoutingModule,
    AgmCoreModule
  ],
  declarations: [
    AdduserPageComponent,
    AdminuserPageComponent
  ]
})
export class AdminUserModule { }
