import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {catchError, map, Observable, throwError} from "rxjs";
import {environment} from "../../../environments/environment";
import {JWTToken} from "../../model/jwttoken";
import {LoginRequest} from "../../model/login-request";
import Logger from "js-logger";
import {JwtService} from "../jwt/jwt.service";

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  constructor(
    private httpClient: HttpClient,
    private jwtService: JwtService
  ) {
  }

  login(loginRequest: LoginRequest): Observable<JWTToken> {
    let body = new URLSearchParams();
    body.set('aadharNumber', loginRequest.aadharNumber);
    body.set('password', loginRequest.password);
    let options = {
      headers: new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded')
    };

    return this.httpClient.post<JWTToken>(
      `${environment.API_URL}/authorization/login`,
      body.toString(),
      options
    ).pipe(
      catchError((err) => {
        Logger.error(`LoginService.login(LoginRequest): `, err);
        return throwError(err);
      }),
      map((value) => {
        this.jwtService.storeToken(value.access_token);
        return value;
      })
    );
  }
}
