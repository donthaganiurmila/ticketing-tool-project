
package com.example.ticketingtool.ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByStatus(TicketStatus status);

    List<Ticket> findByPriority(TicketPriority priority);

    List<Ticket> findByAssignee_Id(Long assigneeId);

    long countByStatus(TicketStatus status);

    long countByPriority(TicketPriority priority);

}
