
package com.example.ticketingtool.dashboard;

import com.example.ticketingtool.ticket.TicketPriority;
import com.example.ticketingtool.ticket.TicketRepository;
import com.example.ticketingtool.ticket.TicketStatus;

import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DashboardService {

    private final TicketRepository ticketRepository;

    public DashboardService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Map<String, Long> getDashboardStatistics() {

        Map<String, Long> statistics = new LinkedHashMap<>();

        statistics.put("totalTickets",
                ticketRepository.count());

        statistics.put("openTickets",
                ticketRepository.countByStatus(TicketStatus.OPEN));

        statistics.put("inProgressTickets",
                ticketRepository.countByStatus(TicketStatus.IN_PROGRESS));

        statistics.put("resolvedTickets",
                ticketRepository.countByStatus(TicketStatus.RESOLVED));

        statistics.put("highPriorityTickets",
                ticketRepository.countByPriority(TicketPriority.HIGH));

        statistics.put("criticalTickets",
                ticketRepository.countByPriority(TicketPriority.CRITICAL));

        return statistics;
    }
}
