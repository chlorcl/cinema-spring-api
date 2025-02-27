package me.chlorcl.cinemaapi.controller;

import me.chlorcl.cinemaapi.model.ticket.Ticket;
import me.chlorcl.cinemaapi.model.ticket.TicketStatus;
import me.chlorcl.cinemaapi.model.ticket.TicketType;
import me.chlorcl.cinemaapi.model.seat.Seat;
import me.chlorcl.cinemaapi.model.screening.Screening;
import me.chlorcl.cinemaapi.repository.TicketRepository;
import me.chlorcl.cinemaapi.repository.SeatRepository;
import me.chlorcl.cinemaapi.repository.ScreeningRepository;
import me.chlorcl.cinemaapi.security.annotation.AdminAuthorization;
import me.chlorcl.cinemaapi.security.annotation.EmployeeAuthorization;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
public class TicketController {
    private final TicketRepository ticketRepository;
    private final SeatRepository seatRepository;
    private final ScreeningRepository screeningRepository;

    public TicketController(TicketRepository ticketRepository, SeatRepository seatRepository, ScreeningRepository screeningRepository) {
        this.ticketRepository = ticketRepository;
        this.seatRepository = seatRepository;
        this.screeningRepository = screeningRepository;
    }

    @EmployeeAuthorization
    @QueryMapping
    public List<Ticket> tickets() {
        return ticketRepository.findAll();
    }

    @QueryMapping
    public Ticket ticket(@Argument Integer id) {
        return ticketRepository.findById(id).orElse(null);
    }

    @EmployeeAuthorization
    @MutationMapping
    public Ticket createTicket(@Argument Integer seatId, @Argument Integer screeningId, @Argument Float price, @Argument Float discount, @Argument Float total, @Argument TicketStatus status, @Argument TicketType type, @Argument String purchaseDate, @Argument Boolean isValidate) {
        Seat seat = seatRepository.findById(seatId).orElseThrow();
        Screening screening = screeningRepository.findById(screeningId).orElseThrow();
        Ticket ticket = new Ticket(seat, screening, price, discount, total, status, type, purchaseDate, isValidate);
        return ticketRepository.save(ticket);
    }

    @EmployeeAuthorization
    @MutationMapping
    public Ticket updateTicket(@Argument Integer id, @Argument Integer seatId, @Argument Integer screeningId, @Argument Float price, @Argument Float discount, @Argument Float total, @Argument TicketStatus status, @Argument TicketType type, @Argument String purchaseDate, @Argument Boolean isValidate) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow();
        Seat seat = seatRepository.findById(seatId).orElseThrow();
        Screening screening = screeningRepository.findById(screeningId).orElseThrow();
        ticket.setSeat(seat);
        ticket.setScreening(screening);
        ticket.setPrice(price);
        ticket.setDiscount(discount);
        ticket.setTotal(total);
        ticket.setStatus(status);
        ticket.setType(type);
        ticket.setPurchaseDate(purchaseDate);
        ticket.setIsValidate(isValidate);
        return ticketRepository.save(ticket);
    }

    @EmployeeAuthorization
    @MutationMapping
    public Ticket deleteTicket(@Argument Integer id) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow();
        ticketRepository.delete(ticket);
        return ticket;
    }
}