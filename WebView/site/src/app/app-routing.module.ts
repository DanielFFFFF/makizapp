import { NgModule } from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {AdminComponent} from "./admin/admin.component";
import {UserComponent} from "./user/user.component";
import {authGuard} from "./Auth/auth.guard";

const routes: Routes = [
  {path: "", redirectTo: "admin", pathMatch: 'full'},
  {path: "admin", component: AdminComponent, canActivate:[authGuard]},
  {path: "user/:name", component: UserComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
