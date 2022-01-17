package com.github.foxlegend.otel;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.github.foxlegend.otel.models.Basket;
import com.github.foxlegend.otel.services.BasketService;

import org.jboss.resteasy.reactive.RestPath;

import io.opentelemetry.api.GlobalOpenTelemetry;

@Path("/api/basket")
public class BasketResource {
    @Inject BasketService service;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
        var basket = service.createBasket(Basket.randomBasket());
        UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(Long.toString(basket.id));
        return Response.created(builder.build()).entity(basket).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getBasket(@RestPath Long id) {
        var basket = service.getBasketById(id);
        if (basket != null) {
            return Response.ok(basket).build();
        } else {
            return Response.noContent().build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/checkout/{id}")
    public Response checkoutBasket(@RestPath Long id) {
        var basket = service.checkoutBasket(id);
        if (basket != null) {
            return Response.ok(basket).build();
        } else {
            return Response.noContent().build();
        }
    }


}
