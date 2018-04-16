/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factorial;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author TGMaster
 */
public class Functions {

    public String Send(Number number) {

        ObjectMapper mapper = new ObjectMapper();
        String string = "";
        try {
            //Convert object to JSON string
            string = mapper.writeValueAsString(number);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return string;
    }

    public Number Receive(String string) {

        ObjectMapper mapper = new ObjectMapper();
        Number number = null;
        try {
            // Convert JSON string to Object
            number = mapper.readValue(string, Number.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return number;
    }

    public List<Integer> GenerateRandom(int a) {
        List<Integer> listOfNumber = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < a; i++) {
            int number = rand.nextInt(10) + 1;
            listOfNumber.add(number);
        }
        return listOfNumber;
    }
    
    static class numberObj {public Number number;}
}
