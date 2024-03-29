package com.kaliente.pos.application.responses.order;

import com.kaliente.pos.application.models.order.OrderDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class GetOrderByIdResponse {
    private OrderDetailsDto foundOrder;
}
