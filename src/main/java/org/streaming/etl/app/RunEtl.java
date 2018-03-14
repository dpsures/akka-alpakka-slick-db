package org.streaming.etl.app;

import akka.Done;
import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import org.streaming.etl.sink.CustomerSink;
import org.streaming.etl.source.CustomerSource;
import org.streaming.etl.vo.Customer;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class RunEtl {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("db-processing-etl");

        ActorMaterializer materializer = ActorMaterializer.create(system);

        CustomerSource source = new CustomerSource();
        final Source<Customer, NotUsed> customerSource = source.customerSource();

        CustomerSink sink = new CustomerSink();
        final Sink<Customer, CompletionStage<Done>> customerSink = sink.loadToSinkDb();

        final CompletionStage<Done> done = customerSource.runWith(customerSink, materializer);

        done.whenComplete((value, exception) -> {
            system.terminate();
            System.out.println("etl process done ...");
        });

    }
}
