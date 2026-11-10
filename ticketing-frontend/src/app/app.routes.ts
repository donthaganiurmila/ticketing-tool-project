import { Routes } from '@angular/router';

import { Login } from './features/auth/login/login';
import { Register } from './features/auth/register/register';
import { Dashboard } from './features/dashboard/dashboard';
import { Tickets } from './features/tickets/tickets';
import { Board } from './features/board/board';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: Login },
  { path: 'register', component: Register },
  { path: 'dashboard', component: Dashboard },
  { path: 'tickets', component: Tickets },
  { path: 'board', component: Board },
  { path: '**', redirectTo: 'login' }
];
