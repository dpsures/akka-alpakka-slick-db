package org.streaming.etl.source;

import akka.NotUsed;
import akka.stream.alpakka.slick.javadsl.Slick;
import akka.stream.alpakka.slick.javadsl.SlickRow;
import akka.stream.alpakka.slick.javadsl.SlickSession;
import akka.stream.javadsl.Source;
import org.streaming.etl.utils.SlickSessionUtils;
import org.streaming.etl.vo.Customer;

public class CustomerSource {

    public Source<Customer, NotUsed> customerSource(){
        Source<Customer, NotUsed> custSource = null;
        SlickSession session = SlickSessionUtils.getSourceSlickSession();

        try{
            custSource = Slick
                    .source(
                            SlickSessionUtils.getSourceSlickSession(),
                            "select patient_id, registered_on, dob, patient_name from rcp_patient_head",
                            (SlickRow row) -> new Customer(row.nextString(), row.nextDate(), row.nextDate(), row.nextString())
                    );
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return custSource;
    }
}
