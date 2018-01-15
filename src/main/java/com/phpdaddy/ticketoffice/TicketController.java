package com.phpdaddy.ticketoffice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Date;

@RestController
@RequestMapping(path = "/",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class TicketController {
    private static Integer idCounter = 0;

    @Autowired
    private TicketService ticketService;

    /**
     * Retrieve a list of tickets
     *
     * @return ResponseEntity
     */
    @RequestMapping(method = RequestMethod.GET)
    private ResponseEntity<?> getTickets() {
        return new ResponseEntity<>(ticketService.getTickets(), HttpStatus.OK);
    }

    /**
     * Generate a new ticket
     *
     * @return ResponseEntity
     */
    @RequestMapping(path = "/generate", method = RequestMethod.GET)
    private ResponseEntity<?> generateTicket() {
        idCounter++;
        Ticket ticket = new Ticket();
        ticket.setId(idCounter);
        ticket.setDate(Date.from(Instant.now()));
        ticket.setPriority(ticketService.getTickets().size());
        ticketService.add(ticket);
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    /**
     * Use active ticket, so it is removed after
     *
     * @return ResponseEntity
     */
    @RequestMapping(path = "/useActive", method = RequestMethod.GET)
    private ResponseEntity<?> useActiveTicket() {
        Ticket first = ticketService.pollFirst();
        ticketService.updatePriorities();
        if (first == null)
            return new ResponseEntity<>("List is empty", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(first, HttpStatus.OK);
    }


    /**
     * Remove last ticket from queue
     *
     * @return ResponseEntity
     */
    @RequestMapping(path = "/removeLast", method = RequestMethod.GET)
    private ResponseEntity<?> removeLastTicket() {
        Ticket last = ticketService.pollLast();
        ticketService.updatePriorities();
        if (last == null)
            return new ResponseEntity<>("List is empty", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(last, HttpStatus.OK);
    }
}
