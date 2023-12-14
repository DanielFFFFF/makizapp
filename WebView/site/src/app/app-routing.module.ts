import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {AdminComponent} from "./admin/admin.component";
import {ClientComponent} from "./client/client.component";
import {authGuard} from "./Auth/auth.guard";

const routes: Routes = [
  {path: "admin", component: AdminComponent, canActivate:[authGuard]},
  {path: ":projectID", component: ClientComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
