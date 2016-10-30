package com.magilex.examples.ui.console;

import static java.lang.System.out;

/**
 * Created by mcamacho on 10/28/16.
 */
public class ConsoleHelper {

    public static void removeLine() {
        // As per ANSI/VT100 Terminal Control Escape Sequences
        out.print("\033[2K"); // erase Line
        out.print("\033[300D"); // move cursor all the way to the left (backwards n positions)
    }

    public static void moveCursorUp() {
        out.print("\033[1A");
    }
}
