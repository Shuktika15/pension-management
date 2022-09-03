import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class LoadingService {
  constructor() {
  }

  private _isLoading: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  get isLoading(): BehaviorSubject<boolean> {
    return this._isLoading;
  }
}
