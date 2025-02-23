package com.shell.webapplication.auth.session;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class UserSession {

    public void setUserId(HttpSession session, Long userId) {
        session.setAttribute("userId", userId);
    }

    public Long getUserId(HttpSession session) {
        return (Long) session.getAttribute("userId");
    }

    public void clearSession(HttpSession session) {
        session.removeAttribute("userId");
    }

}
