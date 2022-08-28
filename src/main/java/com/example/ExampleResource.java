package com.example;

import io.smallrye.mutiny.Multi;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.Duration;
import java.util.Random;
import java.util.concurrent.CompletionStage;


@Path("/hello")
public class ExampleResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Order hello() {

        System.out.println("Sending a message..");
        Order order = new Order();
        order.setId(1001);
        order.setItem("big motor");

        String message = "Hello from RESTEasy Reactive";

        return order;
    }

    private final Random random = new Random();

    @Inject
    @Channel("my-topic")
    Emitter<Order> priceEmitter;

    @POST
    //@Produces(MediaType.TEXT_PLAIN)
    public void send(Order message) {

        System.out.println("Sending a message..");
        CompletionStage<Void> ack = priceEmitter.send(message);
    }
}