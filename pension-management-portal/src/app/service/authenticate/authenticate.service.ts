import {Injectable} from '@angular/core';
import {catchError, map, Observable, of} from "rxjs";
import {Pensioner} from "../../model/pensioner";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {JwtService} from "../jwt/jwt.service";
import Logger from "js-logger";
import {Router} from "@angular/router";
import {SnackbarService} from "../snackbar/snackbar.service";

@Injectable({
  providedIn: 'root'
})
export class AuthenticateService {
  private _pensioner?: Pensioner;

  constructor(
    private httpClient: HttpClient,
    private jwtService: JwtService,
    private router: Router,
    private _snackBarService: SnackbarService
  ) {
  }

  get pensioner(): Pensioner | undefined {
    return this._pensioner;
  }

  authenticate(): Observable<boolean> {
    this._pensioner = undefined;
    try {
      const token = this.jwtService.getToken();

      return this.httpClient.get<Pensioner>(
        `${environment.API_URL}/authorization/authenticate`,
      ).pipe(
        catchError((err) => {
          Logger.error('AuthenticationService.authenticate(): ', err);
          return of(false);
        }),
        map((value) => {
          if (typeof value === "object") {
            this._pensioner = value;
            return true;
          }

          return false;
        })
      );
    } catch (e) {
      Logger.error('AuthenticationService.authenticate(): ', e);
      return of(false);
    }
  }

  logout() {
    this._pensioner = undefined;
    this.jwtService.clearToken();
    this.router.navigate(['login']);
    this._snackBarService.showSnackbar("You're logged out");
  }
}
