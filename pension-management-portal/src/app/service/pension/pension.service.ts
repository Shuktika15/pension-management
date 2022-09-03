import {Injectable} from '@angular/core';
import {catchError, Observable, throwError} from "rxjs";
import {ProcessPensionResponse} from "../../model/process-pension-response";
import {ProcessPensionRequest} from "../../model/process-pension-request";
import {JwtService} from "../jwt/jwt.service";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import Logger from "js-logger";
import {AuthenticateService} from "../authenticate/authenticate.service";

@Injectable({
  providedIn: 'root'
})
export class PensionService {
  constructor(
    private httpClient: HttpClient,
    private jwtService: JwtService,
    private authenticationService: AuthenticateService
  ) {
  }

  processPension(processPensionRequest: ProcessPensionRequest): Observable<ProcessPensionResponse> {
    const token = this.jwtService.getToken();

    return this.httpClient.post<ProcessPensionResponse>(
      `${environment.API_URL}/process-pension`,
      processPensionRequest,
    ).pipe(
      catchError(err => {
        Logger.error('PensionService.processPension(ProcessPensionRequest): ', err);
        this.authenticationService.logout();
        return throwError(err);
      })
    );
  }
}
