import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {AdminComponent} from "./admin/admin.component";
import {ClientComponent} from "./client/client.component";
import {AuthGuard} from "./Auth/auth.guard";
import {LoginComponent} from "./login/login.component"

const routes: Routes = [
  {path: 'login', component: LoginComponent },
  {path: "admin", component: AdminComponent, canActivate:[AuthGuard]},
  {path: ":projectID", component: ClientComponent},
  {path: "", redirectTo: '/login', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
