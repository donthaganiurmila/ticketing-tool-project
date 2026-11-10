
package com.example.ticketingtool.ticket.dto;

import com.example.ticketingtool.ticket.TicketPriority;
import com.example.ticketingtool.ticket.TicketStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TicketRequest {

    @NotBlank(message = "Ticket title is required")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;

    private String description;

    @NotNull(message = "Ticket status is required")
    private TicketStatus status;

    @NotNull(message = "Ticket priority is required")
    private TicketPriority priority;

    private Long assigneeId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public TicketPriority getPriority() {
        return priority;
    }

    public void setPriority(TicketPriority priority) {
        this.priority = priority;
    }

    public Long getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(Long assigneeId) {
        this.assigneeId = assigneeId;
    }
}
