
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { TicketRequest, TicketStatus } from '../models/ticket-request';
import { TicketResponse } from '../models/ticket-response';

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  private apiUrl = 'http://localhost:8080/api/tickets';

  constructor(private http: HttpClient) {}

  // Get all tickets
  getAllTickets(): Observable<TicketResponse[]> {
    return this.http.get<TicketResponse[]>(this.apiUrl);
  }

  // Get ticket by ID
  getTicketById(id: number): Observable<TicketResponse> {
    return this.http.get<TicketResponse>(`${this.apiUrl}/${id}`);
  }

  // Create ticket
  createTicket(data: TicketRequest): Observable<TicketResponse> {
    return this.http.post<TicketResponse>(this.apiUrl, data);
  }

  // Update ticket
  updateTicket(id: number, data: TicketRequest): Observable<TicketResponse> {
    return this.http.put<TicketResponse>(`${this.apiUrl}/${id}`, data);
  }

  // Change ticket status
  updateTicketStatus(
    id: number,
    status: TicketStatus
  ): Observable<TicketResponse> {
    return this.http.patch<TicketResponse>(
      `${this.apiUrl}/${id}/status`,
      { status }
    );
  }

  // Delete ticket
  deleteTicket(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
