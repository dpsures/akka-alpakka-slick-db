package org.streaming.etl.app;

import akka.Done;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Sink;
import org.streaming.etl.source.CustomerSource;
import org.streaming.etl.vo.Customer;

import java.util.concurrent.CompletionStage;

public class RunEtl {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("db-processing-etl");

        ActorMaterializer materializer = ActorMaterializer.create(system);

        CustomerSource source = new CustomerSource();
        final CompletionStage<Done> done = source.customerSource()
                .log("customer")
                .runForeach(cust -> System.out.println(cust.getName()), materializer);
                //.runWith(Sink.ignore(), materializer);

        done.whenComplete((value, exception) -> {
            system.terminate();
            System.out.println("etl process done ...");
        });

    }
}
