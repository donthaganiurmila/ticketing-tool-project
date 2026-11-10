
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TicketService } from '../../core/services/ticket';
import { TicketStatus } from '../../core/models/ticket-request';
import { TicketResponse } from '../../core/models/ticket-response';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-board',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './board.html',
  styleUrl: './board.scss',
})
export class Board implements OnInit {

  tickets: TicketResponse[] = [];

  loading = true;
  errorMessage = '';

  constructor(private ticketService: TicketService) {}

  ngOnInit(): void {
    this.loadTickets();
  }

  // Get all tickets from backend
  loadTickets(): void {
    this.loading = true;
    this.errorMessage = '';

    this.ticketService.getAllTickets().subscribe({
      next: (data) => {
        this.tickets = data;
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'Unable to load tickets.';
        this.loading = false;
      }
    });
  }

  // Filter tickets according to status
  getTicketsByStatus(status: TicketStatus): TicketResponse[] {
    return this.tickets.filter(ticket => ticket.status === status);
  }

  // Change ticket status and save it in database
  changeStatus(ticket: TicketResponse, newStatus: TicketStatus): void {

    const previousStatus = ticket.status;

    // Update the UI temporarily
    ticket.status = newStatus;

    // Send only the new status to backend
    this.ticketService.updateTicketStatus(
      ticket.id,
      newStatus
    ).subscribe({
      next: (updatedTicket) => {
        const index = this.tickets.findIndex(
          t => t.id === updatedTicket.id
        );

        if (index !== -1) {
          this.tickets[index] = updatedTicket;
        }
      },
      error: () => {
        // Restore previous status if backend update fails
        ticket.status = previousStatus;
        this.errorMessage = 'Unable to update ticket status.';
      }
    });
  }
}
