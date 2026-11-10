package com.example.ticketingtool.ticket;

import com.example.ticketingtool.ticket.dto.TicketRequest;
import com.example.ticketingtool.ticket.dto.TicketResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<TicketResponse> createTicket(
            @Valid @RequestBody TicketRequest request) {

        Ticket ticket = ticketService.createTicket(request);

        return ResponseEntity.ok(
                ticketService.getTicketResponse(ticket)
        );
    }

    @GetMapping
    public ResponseEntity<List<TicketResponse>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTicketResponses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        return ticketService.getTicketById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Ticket>> getTicketsByStatus(
            @PathVariable TicketStatus status) {
        return ResponseEntity.ok(ticketService.getTicketsByStatus(status));
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<Ticket>> getTicketsByPriority(
            @PathVariable TicketPriority priority) {
        return ResponseEntity.ok(ticketService.getTicketsByPriority(priority));
    }

    @GetMapping("/assignee/{assigneeId}")
    public ResponseEntity<List<Ticket>> getTicketsByAssignee(
            @PathVariable Long assigneeId) {
        return ResponseEntity.ok(ticketService.getTicketsByAssignee(assigneeId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(
            @PathVariable Long id,
            @Valid @RequestBody TicketRequest request) {
        try {
            return ResponseEntity.ok(ticketService.updateTicket(id, request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        try {
            ticketService.deleteTicket(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}