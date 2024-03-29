package com.example.opencommerce.app.pricing.commands;

import com.example.opencommerce.domain.ItemId;
import com.example.opencommerce.domain.Value;

import java.time.Instant;

public record ApplyDiscountCommand(ItemId id, Value discountValue, Instant executionDate) {
}
