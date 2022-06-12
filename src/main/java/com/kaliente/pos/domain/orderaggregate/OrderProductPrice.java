package com.kaliente.pos.domain.orderaggregate;

import com.kaliente.pos.domain.seedwork.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "order_product_prices")
@Table
public class OrderProductPrice extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_product_id", nullable = false)
    private OrderProduct belongingProduct;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "currencyTitle", column = @Column(name = "currency_title")),
            @AttributeOverride( name = "currencyDate", column = @Column(name = "currency_date")),
            @AttributeOverride( name = "baseCrossRate", column = @Column(name = "base_cross_rate"))
    })
    private OrderCurrency currency;

    @Column
    private double amount;

}