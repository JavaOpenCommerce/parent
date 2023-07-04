package com.example.javaopencommerce.catalog;

import com.example.javaopencommerce.PageDto;
import com.example.javaopencommerce.catalog.dtos.FullItemDto;
import com.example.javaopencommerce.catalog.dtos.ItemDto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemController {

    private final ItemQueryRepository queryRepository;
    private final ItemQueryFacade queryFacade;

    ItemController(ItemQueryRepository queryRepository,
                   ItemQueryFacade queryFacade) {
        this.queryRepository = queryRepository;
        this.queryFacade = queryFacade;
    }

    @GET
    @Path("/{id}")
    public FullItemDto getItemById(@PathParam("id") Long id) {
        return this.queryRepository.getItemById(id);
    }

    @POST
    public PageDto<ItemDto> search(SearchRequest request) {
        return this.queryFacade.getFilteredItems(request);
    }
}
