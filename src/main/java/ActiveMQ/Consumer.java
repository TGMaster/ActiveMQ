/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActiveMQ;

import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author TGMaster
 */
public class Consumer {
    // URL of the JMS server
    private static String url = "tcp://18.233.148.58:61616";
 
    // Name of the queue we will receive messages from
    private static String subject = "TESTQUEUE";
 
    public static void main(String[] args) throws JMSException {
        // Getting JMS connection from the server
        ActiveMQConnectionFactory jmsConnectionFactory = new ActiveMQConnectionFactory(url);
        jmsConnectionFactory.setUserName("admin");
        jmsConnectionFactory.setPassword("admin");
 
        ConnectionFactory connectionFactory = jmsConnectionFactory;
        Connection connection = connectionFactory.createConnection();
        connection.start();
 
        // Creating session for seding messages
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
 
        // Getting the queue 'TESTQUEUE’
        Destination destination = session.createQueue(subject);
 
        // Tạo một MessageConsumer để lắng nghe từ Queue trên
        MessageConsumer consumer = session.createConsumer(destination);
        // Tạo một vòng lặp vô hạn
        //while (true) {
            Message message = consumer.receive();
            // There are many types of Message and TextMessage
            // is just one of them. Producer sent us a TextMessage
            // so we must cast to it to get access to its .getText()
            // method.
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                System.out.println("Received message '" + textMessage.getText() + "'");
            }
        //}
        // Do vòng lặp trên là vòng lặp vô hạn nên ta không cần phải đóng connection
        connection.close();
    }
}