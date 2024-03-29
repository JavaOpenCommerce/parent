package com.example.opencommerce.domain.order.exceptions.ordervalidation;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class OrderValidationException extends OrderException {

    private List<OrderValidationException> derivativeExceptions = new ArrayList<>();

    private OrderValidationException() {
    }

    public OrderValidationException(String message) {
        super(format("OrderValidationException: %s", message));
    }

    public OrderValidationException(List<OrderValidationException> orderInaccuracies) {
        derivativeExceptions = orderInaccuracies;
    }

    public List<OrderValidationException> getDerivativeExceptions() {
        return new ArrayList<>(derivativeExceptions);
    }
}
