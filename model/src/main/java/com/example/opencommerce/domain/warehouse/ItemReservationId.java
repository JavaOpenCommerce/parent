package com.example.opencommerce.domain.warehouse;

import java.util.Objects;

public class ItemReservationId {

    private final Long id;

    private ItemReservationId(Long id) {
        this.id = id;
    }

    public static ItemReservationId of(Long id) {
        return new ItemReservationId(id);
    }

    static ItemReservationId empty() {
        return new ItemReservationId(null);
    }

    public Long asLong() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemReservationId that = (ItemReservationId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
