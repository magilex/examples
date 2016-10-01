package com.magilex.examples.java8.sort;

/**
 * Created by mcamacho on 9/30/16.
 */
public class Helper {

    public static void sleep (int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
