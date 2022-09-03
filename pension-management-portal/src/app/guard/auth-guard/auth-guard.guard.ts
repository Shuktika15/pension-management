import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree} from '@angular/router';
import {map, Observable} from 'rxjs';
import {AuthenticateService} from "../../service/authenticate/authenticate.service";

@Injectable({
  providedIn: 'root'
})
export class AuthGuardGuard implements CanActivate {
  constructor(
    private authenticationService: AuthenticateService,
  ) {
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.authenticationService.authenticate()
      .pipe(
        map(value => {
          if (!value) {
            this.authenticationService.logout();
          }

          return value;
        })
      );
  }
}
