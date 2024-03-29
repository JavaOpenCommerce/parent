package com.example.opencommerce.infra.commonexceptionmappers;

import com.example.opencommerce.app.BaseAppException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CommonAppExceptionMapper implements ExceptionMapper<BaseAppException> {
    @Override
    public Response toResponse(BaseAppException exception) {

        BaseExceptionDto exceptionDto = BaseExceptionDto.builder()
                .message(exception.getMessage())
                .type(exception.getClass()
                        .getSimpleName())
                .build();

        return Response.status(Response.Status.FORBIDDEN)
                .entity(exceptionDto)
                .build();
    }
}