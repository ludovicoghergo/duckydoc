package com.duckydoc.appunti.rabbitmq;

import com.duckydoc.appunti.model.User;
import com.duckydoc.appunti.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @Autowired
    UserRepository userRepository;

    public void receiveMessage(String message) {
        System.out.println("Received by appunti Receiver <" + message + ">");

        int pos = message.indexOf("-", 0);
        /*System.out.println(message.substring(0, pos));
        System.out.println(message.substring(pos+1));*/
        try{
            int id = Integer.parseInt(message.substring(0, pos));
            User user = new User(id, message.substring(pos+1));
            userRepository.save(user);
            System.out.println("User added...");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
