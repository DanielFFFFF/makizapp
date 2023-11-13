import { NgModule } from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {AdminComponent} from "./admin/admin.component";
import {UserComponent} from "./user/user.component";
import {LoginComponent} from "./login/login.component";
import {authGuard} from "./Auth/auth.guard";

const routes: Routes = [
  {path: "login", component: LoginComponent},
  {path: "admin", component: AdminComponent, canActivate:[authGuard]},
  {path: "user/:id", component: UserComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
