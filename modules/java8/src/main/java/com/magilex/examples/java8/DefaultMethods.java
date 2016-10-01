package com.magilex.examples.java8;

import static java.lang.System.*;

/**
 * Created by mcamacho on 10/14/15.
 */
interface IDefaultMethods {

    String doZ(String x);

    default String doX(String x) {
        return x + " {default}";
    }

    static String doY(String x) {
        return x + " {static}";
    }
}

public class DefaultMethods implements IDefaultMethods {

    @Override
    public String doZ(String x) {
        return x + " {implemented}";
    }

    public static void main(String[] args) {
        String input = "input";

        IDefaultMethods dm = new DefaultMethods();
        out.println(dm.doZ(input));
        out.println(dm.doX(input));
        out.println(IDefaultMethods.doY(input));
    }
}
