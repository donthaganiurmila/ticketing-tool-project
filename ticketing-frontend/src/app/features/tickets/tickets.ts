import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { TicketService } from '../../core/services/ticket';
import { TicketRequest } from '../../core/models/ticket-request';
import { TicketResponse } from '../../core/models/ticket-response';

@Component({
  selector: 'app-tickets',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './tickets.html',
  styleUrl: './tickets.scss'
})
export class Tickets implements OnInit {

  tickets: TicketResponse[] = [];

  loading = false;
  errorMessage = '';
  successMessage = '';

  // Search and filter properties
  searchText = '';
  selectedStatus = 'ALL';
  selectedPriority = 'ALL';

  // Edit property
  editingTicketId: number | null = null;

  ticket: TicketRequest = this.emptyTicket();

  constructor(private ticketService: TicketService) {}

  ngOnInit(): void {
    this.loadTickets();
  }

  // Create an empty ticket with default values
  emptyTicket(): TicketRequest {
    return {
      title: '',
      description: '',
      status: 'TO_DO',
      priority: 'MEDIUM'
    };
  }

  // Load all tickets
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

  // Search and filter tickets
  get filteredTickets(): TicketResponse[] {
    return this.tickets.filter(ticket => {

      const search = this.searchText.toLowerCase();

      const matchesSearch =
        ticket.title.toLowerCase().includes(search) ||
        (ticket.description || '').toLowerCase().includes(search);

      const matchesStatus =
        this.selectedStatus === 'ALL' ||
        ticket.status === this.selectedStatus;

      const matchesPriority =
        this.selectedPriority === 'ALL' ||
        ticket.priority === this.selectedPriority;

      return matchesSearch && matchesStatus && matchesPriority;
    });
  }

  // Create ticket
  createTicket(): void {
    this.errorMessage = '';
    this.successMessage = '';

    this.ticketService.createTicket(this.ticket).subscribe({
      next: () => {
        this.successMessage = 'Ticket created successfully!';
        this.ticket = this.emptyTicket();
        this.loadTickets();
      },
      error: () => {
        this.errorMessage = 'Unable to create ticket.';
      }
    });
  }

  // Edit ticket
  editTicket(item: TicketResponse): void {
    this.editingTicketId = item.id;

    this.ticket = {
      title: item.title,
      description: item.description || '',
      status: item.status,
      priority: item.priority,
      assigneeId: item.assigneeId ?? undefined,
      dueDate: item.dueDate ?? undefined
    };

    this.successMessage = '';
    this.errorMessage = '';
  }

  // Update ticket
  updateTicket(): void {
    if (this.editingTicketId === null) {
      return;
    }

    this.ticketService.updateTicket(
      this.editingTicketId,
      this.ticket
    ).subscribe({
      next: () => {
        this.successMessage = 'Ticket updated successfully!';
        this.editingTicketId = null;
        this.ticket = this.emptyTicket();
        this.loadTickets();
      },
      error: () => {
        this.errorMessage = 'Unable to update ticket.';
      }
    });
  }

  // Cancel editing
  cancelEdit(): void {
    this.editingTicketId = null;
    this.ticket = this.emptyTicket();
    this.errorMessage = '';
    this.successMessage = '';
  }

  // Delete ticket
  deleteTicket(id: number): void {
    this.errorMessage = '';
    this.successMessage = '';

    if (!confirm('Are you sure you want to delete this ticket?')) {
      return;
    }

    this.ticketService.deleteTicket(id).subscribe({
      next: () => {
        this.successMessage = 'Ticket deleted successfully!';
        this.loadTickets();
      },
      error: () => {
        this.errorMessage = 'Unable to delete ticket.';
      }
    });
  }
}
