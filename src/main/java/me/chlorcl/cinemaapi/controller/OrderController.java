package me.chlorcl.cinemaapi.controller;

import me.chlorcl.cinemaapi.model.order.Order;
import me.chlorcl.cinemaapi.model.order.OrderItem;
import me.chlorcl.cinemaapi.model.seat.Seat;
import me.chlorcl.cinemaapi.model.seat.SeatStatus;
import me.chlorcl.cinemaapi.model.screening.Screening;
import me.chlorcl.cinemaapi.model.ticket.Ticket;
import me.chlorcl.cinemaapi.model.ticket.TicketPriceConfig;
import me.chlorcl.cinemaapi.model.ticket.TicketStatus;
import me.chlorcl.cinemaapi.model.ticket.TicketType;
import me.chlorcl.cinemaapi.model.user.User;
import me.chlorcl.cinemaapi.repository.*;
import me.chlorcl.cinemaapi.security.annotation.AdminAuthorization;
import me.chlorcl.cinemaapi.security.annotation.EmployeeAuthorization;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class OrderController {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ScreeningRepository screeningRepository;
    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final TicketPriceConfigRepository ticketPriceConfigRepository;

    public OrderController(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
                          ScreeningRepository screeningRepository, SeatRepository seatRepository,
                          TicketRepository ticketRepository, UserRepository userRepository,
                          TicketPriceConfigRepository ticketPriceConfigRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.screeningRepository = screeningRepository;
        this.seatRepository = seatRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.ticketPriceConfigRepository = ticketPriceConfigRepository;
    }

    @QueryMapping
    public List<Order> userOrders() {
        User currentUser = getCurrentUser();
        return orderRepository.findByUserOrderByOrderDateDesc(currentUser);
    }

    @QueryMapping
    public Order order(@Argument Integer id) {
        User currentUser = getCurrentUser();
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        if (!order.getUser().getId().equals(currentUser.getId()) && 
            !currentUser.getRole().toString().contains("ADMIN") && 
            !currentUser.getRole().toString().contains("EMPLOYEE")) {
            throw new IllegalArgumentException("You don't have permission to view this order");
        }
        
        return order;
    }

    @MutationMapping
    public Order createOrder(@Argument Integer screeningId, @Argument List<Integer> seatIds, @Argument List<TicketType> ticketTypes) {
        if (seatIds.size() != ticketTypes.size()) {
            throw new IllegalArgumentException("Number of seats and ticket types must match");
        }

        User currentUser = getCurrentUser();
        Screening screening = screeningRepository.findById(screeningId)
                .orElseThrow(() -> new IllegalArgumentException("Screening not found"));

        Order order = new Order(currentUser, LocalDateTime.now(), 0.0f, "PENDING");
        order = orderRepository.save(order);

        float totalAmount = 0.0f;
        List<OrderItem> orderItems = new ArrayList<>();

        for (int i = 0; i < seatIds.size(); i++) {
            Integer seatId = seatIds.get(i);
            TicketType ticketType = ticketTypes.get(i);

            Seat seat = seatRepository.findById(seatId)
                    .orElseThrow(() -> new IllegalArgumentException("Seat not found: " + seatId));


            if (seat.getStatus() != SeatStatus.AVAILABLE) {
                orderRepository.delete(order);
                throw new IllegalArgumentException("Seat is not available: " + seatId);
            }

            TicketPriceConfig priceConfig = ticketPriceConfigRepository
                    .findBySeatTypeAndTicketTypeAndIsActiveTrue(seat.getType(), ticketType)
                    .orElseThrow(() -> new IllegalArgumentException("No price configuration found for seat type " + seat.getType() + " and ticket type " + ticketType));

            LocalDate screeningDate = LocalDate.parse(screening.getDate(), DateTimeFormatter.ISO_DATE);
            boolean isWeekend = screeningDate.getDayOfWeek() == DayOfWeek.SATURDAY || screeningDate.getDayOfWeek() == DayOfWeek.SUNDAY;
            

            float ticketPrice = priceConfig.calculatePrice(isWeekend, false);


            Ticket ticket = new Ticket(
                    seat,
                    screening,
                    ticketPrice,
                    0.0f,
                    ticketPrice,
                    TicketStatus.SOLD,
                    ticketType,
                    LocalDateTime.now().toString(),
                    false,
                    UUID.randomUUID().toString()
            );
            ticket = ticketRepository.save(ticket);

            seat.setStatus(SeatStatus.SOLD);
            seatRepository.save(seat);

            OrderItem orderItem = new OrderItem(order, ticket, ticketPrice);
            orderItem = orderItemRepository.save(orderItem);
            orderItems.add(orderItem);

            totalAmount += ticketPrice;
        }

        order.setTotalAmount(totalAmount);
        order.setStatus("COMPLETED");
        order = orderRepository.save(order);

        return order;
    }

    @MutationMapping
    public Order cancelOrder(@Argument Integer orderId) {
        User currentUser = getCurrentUser();
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        if (!order.getUser().getId().equals(currentUser.getId())) {
            throw new IllegalArgumentException("You don't have permission to cancel this order");
        }

        order.setStatus("CANCELLED");

        for (OrderItem item : order.getItems()) {
            Ticket ticket = item.getTicket();
            ticket.setStatus(TicketStatus.AVAILABLE);
            ticketRepository.save(ticket);

            Seat seat = ticket.getSeat();
            seat.setStatus(SeatStatus.AVAILABLE);
            seatRepository.save(seat);
        }

        return orderRepository.save(order);
    }

    @EmployeeAuthorization
    @QueryMapping
    public List<Order> allOrders() {
        return orderRepository.findAll();
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found"));
    }
}