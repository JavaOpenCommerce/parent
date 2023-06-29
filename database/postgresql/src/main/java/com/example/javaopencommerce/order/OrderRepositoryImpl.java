package com.example.javaopencommerce.order;

import static java.util.stream.Collectors.toList;

import com.example.javaopencommerce.order.Order.OrderItem;
import com.example.javaopencommerce.order.OrderPrincipal.OrderPrincipalSnapshot;
import com.example.javaopencommerce.statics.JsonConverter;

class OrderRepositoryImpl implements OrderRepository {

  private final PsqlOrderRepository psqlOrderRepository;

  OrderRepositoryImpl(PsqlOrderRepository psqlOrderRepository) {
    this.psqlOrderRepository = psqlOrderRepository;
  }

  @Override
  public Order findOrderById(OrderId id) {
    return psqlOrderRepository.findOrderById(id.id()).toOrderModel();
  }

  @Override
  public Order saveOrder(Order order) {
    return psqlOrderRepository.saveOrder(toOrderEntity(order)).toOrderModel();
  }

  private OrderEntity toOrderEntity(Order order) {
    OrderSnapshot orderSnapshot = order.getSnapshot();
    OrderPrincipalSnapshot orderPrincipal = orderSnapshot.getOrderPrincipal();
    return OrderEntity.builder().id(orderSnapshot.getId().id()).userId(orderPrincipal.id())
        .shippingAddressId(orderPrincipal.addressId())
        .paymentMethod(orderSnapshot.getOrderPrincipal().paymentMethod().name())
        .createdAt(orderSnapshot.getCreationDate()).status(orderSnapshot.getOrderStatus().name())
        .paymentStatus(orderSnapshot.getPaymentStatus().name())
        .valueGross(orderSnapshot.getOrderValueGross().asDecimal())
        .valueNett(orderSnapshot.getOrderValueNett().asDecimal()).simpleProductsJson(
            JsonConverter.convertToJson(
                orderSnapshot.getOrderBody().stream().map(this::toSimpleProductEntity)
                    .collect(toList()))).build();
  }

  private SimpleProductEntity toSimpleProductEntity(OrderItem orderItem) {
    return SimpleProductEntity.builder().itemId(orderItem.getItemId().id())
        .amount(orderItem.getAmount().asInteger()).name(orderItem.getName())
        .valueGross(orderItem.getValueGross().asDecimal()).vat(orderItem.getVat().asDouble())
        .build();
  }
}
