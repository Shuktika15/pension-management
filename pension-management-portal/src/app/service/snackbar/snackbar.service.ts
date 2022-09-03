import {Injectable} from '@angular/core';
import {MatSnackBar} from "@angular/material/snack-bar";

@Injectable({
  providedIn: 'root'
})
export class SnackbarService {
  constructor(
    private _snackBar: MatSnackBar
  ) {
  }

  showSnackbar(message: string) {
    this._snackBar.open(
      message,
      "Dismiss",
      {
        duration: 3000
      }
    )
    ;
  }
}
