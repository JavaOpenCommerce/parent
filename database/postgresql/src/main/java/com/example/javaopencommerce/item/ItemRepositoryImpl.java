package com.example.javaopencommerce.item;

import static java.util.stream.Collectors.toUnmodifiableList;

import io.smallrye.mutiny.Uni;
import java.util.ArrayList;
import java.util.List;

class ItemRepositoryImpl implements ItemRepository {

    private final PsqlItemRepository repository;

    ItemRepositoryImpl(PsqlItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public Uni<List<Item>> getAllItems() {
        Uni<List<ItemEntity>> items = repository.getAllItems();
        Uni<List<ItemDetailsEntity>> details = repository.getAllItemDetails();

        return Uni.combine().all().unis(items, details)
            .combinedWith(this::convertToItemModelList);
    }

    private List<Item> convertToItemModelList(List<ItemEntity> items,
        List<ItemDetailsEntity> itemDetails) {

        List<Item> itemModels = new ArrayList<>();
        for (ItemEntity item : items) {
            List<ItemDetails> itemDetailsFiltered = itemDetails.stream()
                .filter(id -> id.getItemId().equals(item.getId()))
                .map(ItemDetailsEntity::toItemDetailsModel)
                .collect(toUnmodifiableList());

            Item itemModel = item.toItemModel();
            itemModel.addDetails(itemDetailsFiltered);

            itemModels
                .add(itemModel);
        }
        return itemModels;
    }

    @Override
    public Uni<Item> getItemById(Long id) {
        Uni<List<ItemDetailsEntity>> details = repository.getItemDetailsListByItemId(id);
        Uni<ItemEntity> item = repository.getItemById(id);
        return Uni.combine().all()
            .unis(item, details)
            .combinedWith((it, det) -> {
                it.setDetails(det);
                return it.toItemModel();
            });
    }

    @Override
    public Uni<List<Item>> getItemsListByIdList(List<Long> ids) {
        Uni<List<ItemDetailsEntity>> details = repository.getItemDetailsListByIdList(ids);
        Uni<List<ItemEntity>> items = repository.getItemsListByIdList(ids);

        return Uni.combine().all().unis(items, details)
            .combinedWith(this::convertToItemModelList);
    }

    @Override
    public Uni<Item> saveItem(Item item) {
        return repository.saveItem(ItemEntity.fromSnapshot(item.getSnapshot()))
            .map(ItemEntity::toItemModel);
    }

    @Override
    public Uni<ItemDetails> saveItemDetails(Item itemDetails) {
        return null;
    }

    @Override
    public Uni<Integer> getItemStock(Long id) {
        return repository.getItemStock(id);
    }

    @Override
    public Uni<Integer> changeItemStock(Long id, int stock) {
        return repository.changeItemStock(id, stock);
    }
}
