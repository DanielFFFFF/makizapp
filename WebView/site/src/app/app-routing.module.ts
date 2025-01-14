import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {AdminComponent} from "./admin/admin.component";
import {ClientComponent} from "./client/client.component";
import {AuthGuard} from "./Auth/auth.guard";
import {LoginComponent} from "./login/login.component"
import {RegisterComponent} from "./register/register.component";

const routes: Routes = [
    {path: "login", component: LoginComponent},
    {path: "register", component: RegisterComponent},
    {path: "admin", component: AdminComponent, canActivate: [AuthGuard]},
    {path: ":projectId", component: ClientComponent},
    {path: "", redirectTo: '/login', pathMatch: 'full'},
];

@NgModule({
    imports: [RouterModule.forRoot(routes, {useHash: true})],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
