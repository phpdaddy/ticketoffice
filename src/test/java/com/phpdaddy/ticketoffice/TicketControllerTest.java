package com.phpdaddy.ticketoffice;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.LinkedList;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TicketControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TicketController ticketController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(ticketController)
                .build();
    }


    @Test
    public void shouldReturnTickets() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setId(0);
        ticket.setPriority(0);
        ticket.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2018-01-15 10:52"));
        LinkedList<Ticket> tickets = new LinkedList<>();
        tickets.add(ticket);

        given(ticketService.getTickets()).willReturn(tickets);
        this.mockMvc.perform(get("/")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(0)));

        verify(ticketService, times(1)).getTickets();
        verifyZeroInteractions(ticketService);
    }

    @Test
    public void shouldGenerateTicket() throws Exception {

        this.mockMvc.perform(get("/generate")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));

        verify(ticketService, times(1)).add(Matchers.any());
        verifyZeroInteractions(ticketService);
    }


    @Test
    public void shouldRemoveLastTicket() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setId(0);
        ticket.setPriority(0);
        ticket.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2018-01-15 10:52"));

        given(ticketService.pollLast()).willReturn(ticket);

        this.mockMvc.perform(get("/removeLast")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(0)));

        verify(ticketService, times(1)).updatePriorities();
        verify(ticketService, times(1)).pollLast();
        verifyZeroInteractions(ticketService);
    }


    @Test
    public void shouldRemoveLastTicket_Error() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setId(0);
        ticket.setPriority(0);
        ticket.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2018-01-15 10:52"));

        given(ticketService.pollLast()).willReturn(null);

        this.mockMvc.perform(get("/removeLast")).andDo(print())
                .andExpect(status().isBadRequest());

        verify(ticketService, times(1)).updatePriorities();
        verify(ticketService, times(1)).pollLast();
        verifyZeroInteractions(ticketService);
    }

    @Test
    public void shouldUseActiveTicket() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setId(0);
        ticket.setPriority(0);
        ticket.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2018-01-15 10:52"));

        given(ticketService.pollFirst()).willReturn(ticket);

        this.mockMvc.perform(get("/useActive")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(0)));

        verify(ticketService, times(1)).updatePriorities();
        verify(ticketService, times(1)).pollFirst();
        verifyZeroInteractions(ticketService);
    }
}
