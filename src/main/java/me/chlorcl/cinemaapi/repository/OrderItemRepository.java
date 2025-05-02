package me.chlorcl.cinemaapi.repository;

import me.chlorcl.cinemaapi.model.order.Order;
import me.chlorcl.cinemaapi.model.order.OrderItem;
import me.chlorcl.cinemaapi.model.ticket.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByOrder(Order order);
    Optional<OrderItem> findByTicket(Ticket ticket);
    void deleteByTicket(Ticket ticket);

    @Modifying
    @Query("DELETE FROM OrderItem oi WHERE oi.ticket.id = :ticketId")
    void deleteByTicketId(Integer ticketId);

    @Modifying
    @Query("DELETE FROM OrderItem oi WHERE oi.ticket.id IN (SELECT t.id FROM Ticket t WHERE t.screening.id = :screeningId)")
    void deleteByScreeningId(Integer screeningId);
}
