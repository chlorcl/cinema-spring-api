package me.chlorcl.cinemaapi.repository;

import me.chlorcl.cinemaapi.model.seat.SeatType;
import me.chlorcl.cinemaapi.model.ticket.TicketPriceConfig;
import me.chlorcl.cinemaapi.model.ticket.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketPriceConfigRepository extends JpaRepository<TicketPriceConfig, Integer> {
    List<TicketPriceConfig> findByIsActiveTrue();
    Optional<TicketPriceConfig> findBySeatTypeAndTicketTypeAndIsActiveTrue(SeatType seatType, TicketType ticketType);
}