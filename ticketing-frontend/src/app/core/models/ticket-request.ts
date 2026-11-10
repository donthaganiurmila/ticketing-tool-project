export type TicketStatus = 'TO_DO' | 'IN_PROGRESS' | 'DONE';

export type TicketPriority = 'LOW' | 'MEDIUM' | 'HIGH';

export interface TicketRequest {
  title: string;
  description?: string;
  status: TicketStatus;
  priority: TicketPriority;
  assigneeId?: number | null;
  dueDate?: string | null;
}
