package com.example.project.Logic.DatabaseClasses;


import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int OrderDetails_ID;

    private BigDecimal codePrice;

    @OneToOne
    @JoinColumn(name = "GameCode_ID")
    private GameCode gameCode;

    @ManyToOne
    @JoinColumn(name="order_id")
    private PlatformOrder order;

    @Version
    @Column(name = "version", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private int version;



    public OrderDetails() {
    }

    public OrderDetails(BigDecimal codePrice, PlatformOrder order, GameCode gameCode) {
        this.codePrice=codePrice;
        this.gameCode=gameCode;
        gameCode.setOrderDetails(this);
        this.order = order;
        this.order.addDetailsToOrder(this);
        this.version=0;
    }

    public int getOrderDetails_ID() {
        return OrderDetails_ID;
    }

    public BigDecimal getCodePrice() {
        return codePrice;
    }

    public GameCode getGameCode() {
        return gameCode;
    }

    public PlatformOrder getOrder() {
        return order;
    }
}
