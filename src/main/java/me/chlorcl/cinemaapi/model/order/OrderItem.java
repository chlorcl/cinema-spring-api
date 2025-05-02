package me.chlorcl.cinemaapi.model.order;

import jakarta.persistence.*;
import me.chlorcl.cinemaapi.model.ticket.Ticket;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    private Float price;

    public OrderItem() {
    }

    public OrderItem(Order order, Ticket ticket, Float price) {
        this.order = order;
        this.ticket = ticket;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}