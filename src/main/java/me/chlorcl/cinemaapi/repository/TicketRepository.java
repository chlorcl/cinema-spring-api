package me.chlorcl.cinemaapi.repository;

import me.chlorcl.cinemaapi.model.ticket.Ticket;
import me.chlorcl.cinemaapi.security.annotation.EmployeeAuthorization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@EmployeeAuthorization
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}