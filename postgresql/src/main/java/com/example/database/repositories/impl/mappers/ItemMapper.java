package com.example.database.repositories.impl.mappers;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

import com.example.javaopencommerce.image.Image;
import com.example.javaopencommerce.item.Item;
import com.example.javaopencommerce.item.ItemDetails;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ItemMapper {

    private static final String ID = "id";
    private static final String ITEM_ID = "item_id";
    private static final String IMAGE_ID = "image_id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String LANG = "lang";
    private static final String ALT = "alt";
    private static final String URL = "url";
    private static final String STOCK = "stock";
    private static final String VAT = "vat";
    private static final String GROSS = "valuegross";


    public List<Item> getItems(RowSet<Row> rs) {
        if (rs == null) {
            return emptyList();
        }

        Map<Long, Item> items = new HashMap<>();
        for (Row row : rs) {
            Item item = rowToItem(row);
            items.put(item.getId(), item);
        }
        return new ArrayList<>(items.values());
    }

    public Item rowToItem(Row row) {
        if (row == null) {
            return Item.builder().build();
        }

        Long imageId = row.getLong(IMAGE_ID);

        Image image = ofNullable(imageId)
                .map(id -> Image.builder()
                        .id(imageId)
                        .alt(row.getString(ALT))
                        .url(row.getString(URL))
                        .build())
                .orElse(Image.builder().build());

        return Item.builder()
                .stock(row.getInteger(STOCK))
                .valueGross(BigDecimal.valueOf(row.getDouble(GROSS)))
                .vat(row.getDouble(VAT))
                .id(row.getLong(ID))
                .image(image)
                .build();
    }

    public List<ItemDetails> getItemDetails(RowSet<Row> rs) {
        if (rs == null) {
            return emptyList();
        }

        return stream(rs.spliterator(), false)
                .map(this::rowToItemDetails)
                .collect(toList());
    }

    public ItemDetails rowToItemDetails(Row row) {
        if (row == null) {
            return ItemDetails.builder().build();
        }

        return ItemDetails.builder()
                .id(row.getLong(ID))
                .description(row.getString(DESCRIPTION))
                .lang(Locale.forLanguageTag(row.getString(LANG)))
                .name(row.getString(NAME))
                .itemId(row.getLong(ITEM_ID))
                .build();
    }
}
