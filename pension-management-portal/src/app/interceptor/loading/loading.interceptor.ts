import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {finalize, Observable} from 'rxjs';
import {LoadingService} from "../../service/loading/loading.service";

@Injectable()
export class LoadingInterceptor implements HttpInterceptor {
  constructor(
    private loadingService: LoadingService
  ) {
  }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    this.loadingService.isLoading.next(true);

    return next.handle(request)
      .pipe(
        finalize(() => {
          this.loadingService.isLoading.next(false);
        })
      );
  }
}
