import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot} from "@angular/router";
import {LoginService} from "../login/login.service";
import {loggedUser} from "../reducers/loggedUser";

@Injectable()
export class AuthGuard implements CanActivate {

  constructor(private loginService: LoginService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean>|boolean {
    // return this.loginService.getLoggedUser().map(
    //   (loggedUser) => {
    //     if (loggedUser === null) {
    //       this.router.navigate(['login']);
    //       return false;
    //     }
    //     return true;
    //   });

    if (this.loginService.isLogged) {
      return true;
    }
    this.router.navigate(['login']);
    return false;
  }

}
