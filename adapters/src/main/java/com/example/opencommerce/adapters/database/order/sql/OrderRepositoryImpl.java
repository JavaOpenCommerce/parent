package com.example.opencommerce.adapters.database.order.sql;

import com.example.opencommerce.statics.JsonConverter;
import com.example.opencommerce.domain.OrderId;
import com.example.opencommerce.domain.order.Order;
import com.example.opencommerce.domain.order.Order.OrderItem;
import com.example.opencommerce.domain.order.OrderPrincipal.OrderPrincipalSnapshot;
import com.example.opencommerce.domain.order.OrderRepository;
import com.example.opencommerce.domain.order.OrderSnapshot;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import static java.util.stream.Collectors.toList;

@ApplicationScoped
class OrderRepositoryImpl implements OrderRepository {

    private final PsqlOrderRepository psqlOrderRepository;

    OrderRepositoryImpl(PsqlOrderRepository psqlOrderRepository) {
        this.psqlOrderRepository = psqlOrderRepository;
    }

    @Override
    public Order findOrderById(OrderId id) {
        return psqlOrderRepository.findOrderById(id.asUUID())
                .toOrderModel();
    }

    @Override
    @Transactional
    public Order saveOrder(Order order) {
        return psqlOrderRepository.saveOrder(toOrderEntity(order))
                .toOrderModel();
    }

    private OrderEntity toOrderEntity(Order order) {
        OrderSnapshot orderSnapshot = order.getSnapshot();
        OrderPrincipalSnapshot orderPrincipal = orderSnapshot.getOrderPrincipal();
        return OrderEntity.builder()
                .id(orderSnapshot.getId()
                        .asUUID())
                .userId(orderPrincipal.id())
                .shippingAddressId(orderPrincipal.addressId())
                .paymentMethod(orderSnapshot.getOrderPrincipal()
                        .paymentMethod()
                        .name())
                .createdAt(orderSnapshot.getCreationDate())
                .status(orderSnapshot.getOrderStatus()
                        .name())
                .paymentStatus(orderSnapshot.getPaymentStatus()
                        .name())
                .valueGross(orderSnapshot.getOrderValueGross()
                        .asDecimal())
                .valueNett(orderSnapshot.getOrderValueNett()
                        .asDecimal())
                .simpleProductsJson(
                        JsonConverter.convertToJson(
                                orderSnapshot.getOrderBody()
                                        .stream()
                                        .map(this::toSimpleProductEntity)
                                        .collect(toList())))
                .build();
    }

    private SimpleProductEntity toSimpleProductEntity(OrderItem orderItem) {
        return SimpleProductEntity.builder()
                .itemId(orderItem.getId()
                        .asLong())
                .amount(orderItem.getAmount()
                        .asInteger())
                .name(orderItem.getName())
                .valueGross(orderItem.getValueGross()
                        .asDecimal())
                .vat(orderItem.getVat()
                        .asDouble())
                .build();
    }
}
