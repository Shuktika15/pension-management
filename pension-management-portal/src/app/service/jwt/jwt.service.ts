import {Injectable} from '@angular/core';
import {Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class JwtService {
  jwtSubject = new Subject<string>();

  storeToken(token: string) {
    localStorage.setItem('jwt', token);
  }

  getToken(): string {
    const token = localStorage.getItem('jwt');
    if (token === null) throw new Error("JWT token does not exist");
    return `Bearer ${token}`;
  }

  clearToken() {
    localStorage.removeItem('jwt');
  }
}
