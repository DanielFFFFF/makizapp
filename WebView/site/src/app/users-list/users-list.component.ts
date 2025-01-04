import { Component } from '@angular/core';
import {Utilisateur} from "../commons/Utilisateur";
import {UsersService} from "../services/users.service";

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.css']
})
export class UsersListComponent {
    users!: Utilisateur[];

    constructor(private userService: UsersService) {
        this.userService.getUsers()
            .subscribe((users: Utilisateur[]) => this.users = users);
    }

    performUserOperation(id: number, operation: string) {
        if (operation === 'enable')
            this.userService.enableUser(id).subscribe(() =>
                this.userService.getUsers()
                    .subscribe((users: Utilisateur[]) => this.users = users));

        else if (operation === 'disable')
            this.userService.disableUser(id).subscribe(() =>
                this.userService.getUsers()
                    .subscribe((users: Utilisateur[]) => this.users = users));
    }
}
