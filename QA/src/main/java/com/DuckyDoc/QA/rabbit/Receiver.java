package com.DuckyDoc.QA.rabbit;

import com.DuckyDoc.QA.Model.User;
import com.DuckyDoc.QA.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @Autowired
    UserRepository userRepository;

    public void receiveMessage(String message) {
        System.out.println("Received by Object Receiver <" + message + ">");

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
