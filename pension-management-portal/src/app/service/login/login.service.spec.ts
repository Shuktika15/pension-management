import {TestBed} from '@angular/core/testing';

import {LoginService} from './login.service';

describe('LoginService', () => {
  let service: LoginService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoginService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch the access token', () => {
    service.login({aadharNumber: "234151234234", password: "Jhinuk@151"})
      .subscribe((value) => {
        expect(value).toBeNull();
      })
  })
});
