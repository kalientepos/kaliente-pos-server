package com.kaliente.pos.api.controllers;

import com.kaliente.pos.application.models.base.BaseResponse;
import com.kaliente.pos.application.models.dtos.order.OrderDetailsDto;
import com.kaliente.pos.application.requests.order.*;
import com.kaliente.pos.application.responses.order.*;
import com.kaliente.pos.application.services.OrderService;
import com.kaliente.pos.domain.orderaggregate.Order;
import com.kaliente.pos.sharedkernel.Constants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ModelMapper mapper;

    @PreAuthorize("hasAnyRole('ROLE_PERSONNEL')")
    @GetMapping("/getAll")
    public ResponseEntity<BaseResponse<GetOrdersResponse>> getAll() {

        List<Order> orders = orderService.getAll();

        GetOrdersResponse response = null;

        if(orders != null) {
            response = new GetOrdersResponse(
                    orders.stream().map(o -> mapper.map(o, OrderDetailsDto.class)).collect(Collectors.toList())
            );
        }

        return new ResponseEntity<>(new BaseResponse<>(response, Constants.OPERATION_SUCCESS_MESSAGE), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PERSONNEL')")
    @GetMapping("/getById/{id}")
    public ResponseEntity<BaseResponse<GetOrderByIdResponse>> getOrderById(@PathVariable("id") UUID id) {

        Order order = orderService.getOrderById(id);

        GetOrderByIdResponse response = null;

        if(order != null) {
            response = new GetOrderByIdResponse(
                    mapper.map(order, OrderDetailsDto.class)
            );
        }

        return new ResponseEntity<>(new BaseResponse<>(response, Constants.OPERATION_SUCCESS_MESSAGE), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PERSONNEL')")
    @GetMapping("/getByCustomer/{customerId}")
    public ResponseEntity<BaseResponse<GetOrderByCustomerResponse>> getOrderByCustomerId(@PathVariable("customerId") UUID customerId) {

        Order order = orderService.getOrderByCustomerId(customerId);

        GetOrderByCustomerResponse response = null;

        if(order != null) {
            response = new GetOrderByCustomerResponse(
                    mapper.map(order, OrderDetailsDto.class)
            );
        }

        return new ResponseEntity<>(new BaseResponse<>(response, Constants.OPERATION_SUCCESS_MESSAGE), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PERSONNEL')")
    @PostMapping("/create")
    public ResponseEntity<BaseResponse<CreateOrderResponse>> createOrder(@RequestBody CreateOrderRequest request) {
        Order createdOrder = orderService.createOrder(request);

        return new ResponseEntity<>(new BaseResponse<>(new CreateOrderResponse(createdOrder.getId()), Constants.OPERATION_SUCCESS_MESSAGE), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PERSONNEL')")
    @PostMapping("/cancel")
    public ResponseEntity<BaseResponse<CancelOrderResponse>> cancelOrder(@RequestBody CancelOrderRequest request) {

        // TODO: Developing the CancelOrder service.

        return new ResponseEntity<>(new BaseResponse<>(null, Constants.OPERATION_SUCCESS_MESSAGE), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PERSONNEL')")
    @PostMapping("/complete")
    public ResponseEntity<BaseResponse<CompleteOrderResponse>> completeOrder(@RequestBody CompleteOrderRequest request) {

        // TODO: Developing the CancelOrder service.

        return new ResponseEntity<>(new BaseResponse<>(null, Constants.OPERATION_SUCCESS_MESSAGE), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PERSONNEL')")
    @PostMapping("{orderId}/makePayment")
    public ResponseEntity<BaseResponse<MakePaymentForOrderResponse>> completeOrder(@PathVariable("orderId") UUID orderId, @RequestBody MakePaymentForOrderRequest request) {

        // TODO: Developing the MakePaymentForOrder service.

        return new ResponseEntity<>(new BaseResponse<>(null, Constants.OPERATION_SUCCESS_MESSAGE), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PERSONNEL')")
    @PostMapping("{orderId}/update/customer-info")
    public ResponseEntity<BaseResponse<UpdateCustomerInformationResponse>> updateCustomerInformation(@PathVariable("orderId") UUID orderId, @RequestBody UpdateCustomerInformationRequest request) {

        // TODO: Developing the UpdateCustomerInformationForOrder service.

        return new ResponseEntity<>(new BaseResponse<>(null, Constants.OPERATION_SUCCESS_MESSAGE), HttpStatus.OK);
    }

}
