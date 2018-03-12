package org.streaming.etl.utils;

import akka.stream.alpakka.slick.javadsl.SlickSession;

public class SlickSessionUtils {

    public static SlickSession getSlickSession(){
        return SlickSession.forConfig("slick-postgres");
    }
}
