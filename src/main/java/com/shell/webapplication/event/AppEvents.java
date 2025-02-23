package com.shell.webapplication.event;

import org.springframework.stereotype.Service;

@Service
public class AppEvents {

    public void logoutEvents() {
        System.out.println("Logout Event is Executing...!");
    }

}
