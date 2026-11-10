import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface DashboardStatistics {
  totalTickets: number;
  openTickets: number;
  inProgressTickets: number;
  resolvedTickets: number;
  highPriorityTickets: number;
  criticalTickets: number;
}

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  private apiUrl = 'http://localhost:8080/api/dashboard/statistics';

  constructor(private http: HttpClient) {}

  getStatistics(): Observable<DashboardStatistics> {
    return this.http.get<DashboardStatistics>(this.apiUrl);
  }
}
