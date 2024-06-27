package ui;

import model.Event;
import model.EventLog;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            //new Game();
            new GraphicsGame();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }

//        for (Event e : EventLog.getInstance()) {
//            System.out.println(e.toString());
//        }
//
//        EventLog.getInstance().clear();
    }
}
