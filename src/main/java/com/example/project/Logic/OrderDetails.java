package com.example.project.Logic;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int OrderDetails_ID;

    private double pricePerCode;

    @OneToMany(mappedBy = "orderDetails",cascade = CascadeType.MERGE)
    private List<GameCode> codes;

    @ManyToOne
    @JoinColumn(name="order_id")
    private PlatformOrder order;

    @ManyToOne
    @JoinColumn(name="game_id")
    private Game game;

    public OrderDetails() {
    }

    public OrderDetails(double pricePerCode, PlatformOrder order, Game game) {
        this.pricePerCode = pricePerCode;
        this.codes = new ArrayList<GameCode>();
        this.order = order;
        this.order.addDetailsToOrder(this);
        this.game = game;
    }

    public double getPricePerCode() {
        return pricePerCode;
    }

    public List<GameCode> getCodes() {
        return codes;
    }

    public PlatformOrder getOrder() {
        return order;
    }

    public Game getGame() {
        return game;
    }

    public void addCode(List<GameCode> codes){
        for(GameCode code:codes){
            this.codes.add(code);
            code.setOrderDetails(this);
        }
    }

    public int getOrderDetails_ID() {
        return OrderDetails_ID;
    }
}
