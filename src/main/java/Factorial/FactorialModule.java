/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factorial;

import javax.jms.JMSException;
import java.util.*;

/**
 *
 * @author TGMaster
 */
public class FactorialModule {

    //Initialize
    Functions f = new Functions();
    ActiveMQ active = new ActiveMQ();

    public FactorialModule() throws JMSException {

        // Receive the request messages
        List<String> listOfRequest = active.Consumer(false, "FACT_REQUEST");
        List<String> listOfResult = new ArrayList<>();

        for (String string : listOfRequest) {
            Number number = f.Receive(string);
            number.setResult(factorial(number.getNumber()));
            listOfResult.add(f.Send(number));
        }
        
        // Send the result messages
        active.Producer(listOfResult, "FACT_RESULT");
    }

    private static int factorial(int n) {
        if (n == 0) {
            return 1;
        } else {
            return (n * factorial(n - 1));
        }
    }

}
