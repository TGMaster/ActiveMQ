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
public class Main {

    public static int a, b;

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     * @throws javax.jms.JMSException
     */
    public static void main(String[] args) throws InterruptedException, JMSException {
        // Initialize
        Scanner input = new Scanner(System.in);

        // Enter number of threads and random numbers
        System.out.println("Input number of threads: ");
        a = input.nextInt();
        System.out.println("Input number of random numbers: ");
        b = input.nextInt();

        ClientModule cModule = new ClientModule();
        FactorialModule fModule = new FactorialModule();
    }

}
