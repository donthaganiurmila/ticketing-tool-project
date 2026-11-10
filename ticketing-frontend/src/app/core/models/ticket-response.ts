import { TicketStatus, TicketPriority } from './ticket-request';

export interface TicketResponse {
  id: number;
  title: string;
  description: string | null;
  status: TicketStatus;
  priority: TicketPriority;
  assigneeId: number | null;
  assigneeName: string | null;
  dueDate: string | null;
  createdAt: string;
  updatedAt: string;
}
