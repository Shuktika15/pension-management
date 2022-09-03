import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from "../../../environments/environment";
import {JwtService} from "../../service/jwt/jwt.service";

@Injectable()
export class AuthorizationHeaderInterceptor implements HttpInterceptor {
  constructor(private jwtService: JwtService) {
  }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const apiUrl = environment.API_URL;
    const url = request.url;
    const endpoint = url.split(apiUrl)[1];

    if (endpoint !== '/authorization/login') {
      const token = this.jwtService.getToken();
      request = request.clone({
        setHeaders: {
          'Authorization': token
        }
      });
    }

    return next.handle(request);
  }
}
