package org.streaming.etl.sink;

import akka.Done;
import akka.stream.alpakka.slick.javadsl.Slick;
import akka.stream.alpakka.slick.javadsl.SlickSession;
import akka.stream.javadsl.Sink;
import org.streaming.etl.utils.SlickSessionUtils;
import org.streaming.etl.vo.Customer;

import java.util.concurrent.CompletionStage;

public class CustomerSink {

    public Sink<Customer, CompletionStage<Done>> loadToSinkDb(){
        Sink<Customer, CompletionStage<Done>> sink = null;
        try {
             sink = Slick.<Customer>sink(SlickSessionUtils.getSinkSlickSession(),
                    (customer) -> "insert into rcp_patient_head values ("+customer.getPatientId()+", '"+customer.getVisitDate()+"' , '"+customer.getDateOfBirth()+"' , '"+customer.getName()+"')");
        }catch (Exception e){
            e.printStackTrace();
        }
        return sink;
    }
}
