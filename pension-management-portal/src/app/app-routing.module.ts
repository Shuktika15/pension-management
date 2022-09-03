import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./pages/home/home.component";
import {LoginComponent} from "./pages/login/login.component";
import {AuthGuardGuard} from "./guard/auth-guard/auth-guard.guard";

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    canActivate: [AuthGuardGuard],
    data: {
      animation: 'home',
    }
  },
  {
    path: 'login',
    component: LoginComponent,
    data: {
      animation: 'login',
    }
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
