package org.streaming.etl.utils;

import akka.stream.alpakka.slick.javadsl.SlickSession;

public class SlickSessionUtils {

    public static SlickSession getSourceSlickSession(){
        return SlickSession.forConfig("slick-postgres-source");
    }

    public static SlickSession getSinkSlickSession(){
        return SlickSession.forConfig("slick-postgres-sink");
    }
}
