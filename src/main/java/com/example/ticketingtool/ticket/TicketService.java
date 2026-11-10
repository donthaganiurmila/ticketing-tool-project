
package com.example.ticketingtool.ticket;

import com.example.ticketingtool.ticket.dto.TicketRequest;
import com.example.ticketingtool.ticket.dto.TicketResponse;
import com.example.ticketingtool.user.User;
import com.example.ticketingtool.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TicketService(TicketRepository ticketRepository,
                         UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    public Ticket createTicket(TicketRequest request) {
        Ticket ticket = new Ticket();
        mapRequestToTicket(request, ticket);
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Optional<Ticket> getTicketById(Long id) {
        return ticketRepository.findById(id);
    }

    public List<Ticket> getTicketsByStatus(TicketStatus status) {
        return ticketRepository.findByStatus(status);
    }
    private TicketResponse mapToResponse(Ticket ticket) {

        TicketResponse response = new TicketResponse();

        response.setId(ticket.getId());
        response.setTitle(ticket.getTitle());
        response.setDescription(ticket.getDescription());
        response.setStatus(ticket.getStatus());
        response.setPriority(ticket.getPriority());
        response.setCreatedAt(ticket.getCreatedAt());
        response.setUpdatedAt(ticket.getUpdatedAt());

        if (ticket.getAssignee() != null) {
            response.setAssigneeId(ticket.getAssignee().getId());
            response.setAssigneeName(ticket.getAssignee().getName());
        }

        return response;
    }

    public List<Ticket> getTicketsByPriority(TicketPriority priority) {
        return ticketRepository.findByPriority(priority);
    }

    public List<Ticket> getTicketsByAssignee(Long assigneeId) {
        return ticketRepository.findByAssignee_Id(assigneeId);
    }

    public Ticket updateTicket(Long id, TicketRequest request) {
        Ticket existingTicket = ticketRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Ticket not found"));

        mapRequestToTicket(request, existingTicket);
        return ticketRepository.save(existingTicket);
    }

    public void deleteTicket(Long id) {
        if (!ticketRepository.existsById(id)) {
            throw new IllegalArgumentException("Ticket not found");
        }

        ticketRepository.deleteById(id);
    }
    public List<TicketResponse> getAllTicketResponses() {
        return ticketRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private void mapRequestToTicket(TicketRequest request, Ticket ticket) {
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setStatus(request.getStatus());
        ticket.setPriority(request.getPriority());

        if (request.getAssigneeId() != null) {
            User assignee = userRepository.findById(request.getAssigneeId())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Assignee not found"));

            ticket.setAssignee(assignee);
        } else {
            ticket.setAssignee(null);
        }
    }

    public TicketResponse getTicketResponse(Ticket ticket) {
        return mapToResponse(ticket);
    }
}
