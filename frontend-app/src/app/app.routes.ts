import { Routes } from '@angular/router';
import { LoginComponent } from './auth-module/login/login.component';
import { SingupComponent } from './auth-module/singup/singup.component';

export const routes: Routes = [
    {path: '', component: LoginComponent},
];
