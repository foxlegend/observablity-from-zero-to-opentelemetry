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
import io.opentelemetry.api.trace.Tracer;

@Path("/api/basket")
public class BasketResource {

    static final Tracer tracer = GlobalOpenTelemetry.getTracer("basket.traces");

    @Inject BasketService service;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBasket(@Context UriInfo uriInfo) {
        var span = tracer.spanBuilder("BasketResource::createBasket").startSpan();
        span.setAttribute("http.method", "GET");
        span.setAttribute("http.url", uriInfo.getRequestUri().toString());
        try (var scope = span.makeCurrent()) {
            var basket = service.createBasket(Basket.randomBasket());
            UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(Long.toString(basket.id));
            span.setAttribute("basket", basket.toString());
            return Response.created(builder.build()).entity(basket).build();
        } finally {
            span.end();
        }
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
