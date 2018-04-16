/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factorial;

import java.util.*;
import javax.jms.JMSException;

/**
 *
 * @author TGMaster
 */
public class ClientModule {

    int a = Main.a;
    int b = Main.b;
    List<String> listOfResult = new ArrayList<>();

    public ClientModule() throws InterruptedException, JMSException {
        Functions f = new Functions();
        List<String> listOfString = new ArrayList<>();
        ActiveMQ active = new ActiveMQ();

        // Create 'a' threads
        for (int i = 0; i < a; i++) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    List<Integer> listOfRandom = f.GenerateRandom(b);
                    // Create 'b' random numbers
                    for (int i = 0; i < listOfRandom.size(); i++) {
                        Number number = new Number();
                        number.setNumber(listOfRandom.get(i));
                        listOfString.add(f.Send(number));
                    }

                }
            };
            
            // Run thread
            thread.start();
            thread.join();
        }
        // Send request messages
        active.Producer(listOfString, "FACT_REQUEST");

        // Wait for the result
        Thread waitThread = new Thread() {
            @Override
            public void run() {
                try {
                    listOfResult = active.Consumer(true, "FACT_RESULT");
                } catch (JMSException ex) {
                    ex.printStackTrace();
                }
            }
        };

        // Run thread
        waitThread.start();
        waitThread.join();
    }
}
