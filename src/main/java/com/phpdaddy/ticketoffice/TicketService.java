package com.phpdaddy.ticketoffice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class TicketService {

    @Autowired
    private LinkedList<Ticket> tickets;

    public LinkedList<Ticket> getTickets() {
        return tickets;
    }

    public void add(Ticket ticket) {
        tickets.add(ticket);
    }

    public Ticket pollFirst() {
        return tickets.pollFirst();
    }

    public Ticket pollLast() {
        return tickets.pollLast();
    }

    /**
     * Update priorities according to the queue positions
     */
    public void updatePriorities() {
        for (int i = 0; i < tickets.size(); i++) {
            tickets.get(i).setPriority(i);
        }
    }
}
