package com.kaliente.pos.application.responses.order;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CompleteOrderResponse {
    private UUID completedOrderId;
}