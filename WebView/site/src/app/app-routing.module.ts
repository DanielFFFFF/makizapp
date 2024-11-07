import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {AdminComponent} from "./admin/admin.component";
import {ClientComponent} from "./client/client.component";
import {authGuard} from "./Auth/auth.guard";

const routes: Routes = [
  {path: "admin", component: AdminComponent, canActivate:[authGuard]},
  {path: ":projectId", component: ClientComponent},

  {path: "", redirectTo: '/admin', pathMatch: 'full'},  // Default path redirects to admin
  {path: "**", redirectTo: '/admin'}                    // Page path not found
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
