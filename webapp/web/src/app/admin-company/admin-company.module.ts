import { NgModule } from '@angular/core';
import { CommonModule } from "@angular/common";

import { AgmCoreModule } from '@agm/core';
import { AdmincompanyRoutingModule } from "./admincompany-routing.module";

import { AddcompanyComponent} from "./addcompany/addcompany.component";
import { AdmincompanyComponent } from "./admincompany/admincompany.component";

@NgModule ({
  imports: [
    CommonModule,
    AdmincompanyRoutingModule,
    AgmCoreModule
  ],
  declarations: [
    AddcompanyComponent,
    AdmincompanyComponent
  ]
})
export class AdminCompanyModule {
}

