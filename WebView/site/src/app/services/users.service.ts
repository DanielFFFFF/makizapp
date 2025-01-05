import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Utilisateur} from "../commons/Utilisateur";

@Injectable({
    providedIn: 'root'
})
export class UsersService {
    usersApiURL = '/api/user'

    constructor(private http: HttpClient) {
    }

    getUsers() {
        return this.http.get<Utilisateur[]>(this.usersApiURL + '/all');
    }

    enableUser(id: number) {
        return this.http.put<{message: string}>(this.usersApiURL + '/enable/' + id, {});
    }

    disableUser(id: number) {
        return this.http.put<{message: string}>(this.usersApiURL + '/disable/' + id, {});
    }
}
