package me.chlorcl.cinemaapi.model.ticket;

import jakarta.persistence.*;
import me.chlorcl.cinemaapi.model.screening.Screening;
import me.chlorcl.cinemaapi.model.seat.Seat;

@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Seat seat;
    @ManyToOne
    private Screening screening;
    private Float price;
    private Float discount;
    private Float total;
    @Enumerated(EnumType.STRING)
    private TicketStatus status;
    @Enumerated(EnumType.STRING)
    private TicketType type;
    private String purchaseDate;
    private Boolean isValidate;

    public Ticket(Seat seat, Screening screening, Float price, Float discount, Float total, TicketStatus status, TicketType type, String purchaseDate, Boolean isValidate) {
        this.seat = seat;
        this.screening = screening;
        this.price = price;
        this.discount = discount;
        this.total = total;
        this.status = status;
        this.type = type;
        this.purchaseDate = purchaseDate;
        this.isValidate = isValidate;
    }

    public Ticket() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Screening getScreening() {
        return screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Boolean getIsValidate() {
        return isValidate;
    }

    public void setIsValidate(Boolean isValidate) {
        this.isValidate = isValidate;
    }
}
