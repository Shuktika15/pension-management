import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {LoginRequest} from "../../model/login-request";
import {LoginService} from "../../service/login/login.service";
import {Router} from "@angular/router";
import {LoadingService} from "../../service/loading/loading.service";
import {Observable} from "rxjs";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm?: FormGroup;
  loginError: string = '';
  isLoading?: Observable<boolean>;

  constructor(
    private formBuilder: FormBuilder,
    private loginService: LoginService,
    private loadingService: LoadingService,
    private router: Router
  ) {
    this.loginForm = formBuilder.group({
      aadharNumber: ['', [
        Validators.required,
        Validators.minLength(12),
        Validators.maxLength(12)
      ]],
      password: ['', [
        Validators.required
      ]]
    });
    this.isLoading = this.loadingService.isLoading;
  }

  ngOnInit(): void {
  }

  get aadharNumber() {
    return this.loginForm?.get('aadharNumber');
  }

  get password() {
    return this.loginForm?.get('password');
  }

  login() {
    this.loginError = '';

    const loginRequest: LoginRequest = {
      aadharNumber: this.aadharNumber!.value,
      password: this.password!.value
    };

    this.loginService.login(loginRequest)
      .subscribe({
        error: err => {
          if (err.status.toString().startsWith("4")) {
            this.loginError = 'Invalid credentials';
          } else if (err.status.toString().startsWith("5")) {
            this.loginError = 'Service unavailable';
          } else {
            this.loginError = 'Error code: ' + err.status;
          }
        },
        complete: () => this.router.navigate([''])
      });
  }
}
