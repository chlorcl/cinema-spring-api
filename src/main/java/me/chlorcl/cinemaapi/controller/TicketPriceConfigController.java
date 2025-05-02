package me.chlorcl.cinemaapi.controller;

import me.chlorcl.cinemaapi.model.seat.SeatType;
import me.chlorcl.cinemaapi.model.ticket.TicketPriceConfig;
import me.chlorcl.cinemaapi.model.ticket.TicketType;
import me.chlorcl.cinemaapi.repository.TicketPriceConfigRepository;
import me.chlorcl.cinemaapi.security.annotation.AdminAuthorization;
import me.chlorcl.cinemaapi.security.annotation.EmployeeAuthorization;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class TicketPriceConfigController {
    private final TicketPriceConfigRepository ticketPriceConfigRepository;

    public TicketPriceConfigController(TicketPriceConfigRepository ticketPriceConfigRepository) {
        this.ticketPriceConfigRepository = ticketPriceConfigRepository;
    }

    @QueryMapping
    public List<TicketPriceConfig> ticketPriceConfigs() {
        return ticketPriceConfigRepository.findByIsActiveTrue();
    }

    @QueryMapping
    public TicketPriceConfig ticketPriceConfig(@Argument Integer id) {
        return ticketPriceConfigRepository.findById(id).orElse(null);
    }

    @QueryMapping
    public TicketPriceConfig getTicketPrice(@Argument SeatType seatType, @Argument TicketType ticketType) {
        return ticketPriceConfigRepository.findBySeatTypeAndTicketTypeAndIsActiveTrue(seatType, ticketType)
                .orElseThrow(() -> new IllegalArgumentException("No active price configuration found for the given seat type and ticket type"));
    }

    @AdminAuthorization
    @MutationMapping
    public TicketPriceConfig createTicketPriceConfig(
            @Argument SeatType seatType,
            @Argument TicketType ticketType,
            @Argument Float basePrice,
            @Argument Float weekendSurcharge,
            @Argument Float holidaySurcharge,
            @Argument String description,
            @Argument Boolean isActive) {
        TicketPriceConfig config = new TicketPriceConfig(
                seatType, ticketType, basePrice, weekendSurcharge, holidaySurcharge, description, isActive);
        return ticketPriceConfigRepository.save(config);
    }

    @AdminAuthorization
    @MutationMapping
    public TicketPriceConfig updateTicketPriceConfig(
            @Argument Integer id,
            @Argument SeatType seatType,
            @Argument TicketType ticketType,
            @Argument Float basePrice,
            @Argument Float weekendSurcharge,
            @Argument Float holidaySurcharge,
            @Argument String description,
            @Argument Boolean isActive) {
        TicketPriceConfig config = ticketPriceConfigRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ticket price configuration not found"));
        
        config.setSeatType(seatType);
        config.setTicketType(ticketType);
        config.setBasePrice(basePrice);
        config.setWeekendSurcharge(weekendSurcharge);
        config.setHolidaySurcharge(holidaySurcharge);
        config.setDescription(description);
        config.setIsActive(isActive);
        
        return ticketPriceConfigRepository.save(config);
    }

    @AdminAuthorization
    @MutationMapping
    public TicketPriceConfig deleteTicketPriceConfig(@Argument Integer id) {
        TicketPriceConfig config = ticketPriceConfigRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ticket price configuration not found"));
        ticketPriceConfigRepository.delete(config);
        return config;
    }
}