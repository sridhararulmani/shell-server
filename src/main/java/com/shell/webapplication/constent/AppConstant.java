package com.shell.webapplication.constent;

import org.springframework.http.HttpStatus;

import java.util.regex.Pattern;

public interface AppConstant {

    public String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

    public String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    public String IMAGE_FORMAT_PATTERN_REGEX = ".*\\.(jpeg|png|gif)$";

    public String MOBILE_NUMBER_REGEX = "^([6-9]\\d{9})$";

    public Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public String USER_NOT_FOUND = "User " + HttpStatus.NOT_FOUND.getReasonPhrase();

    public String INVALID_USER_NAME_OR_PASSWORD = "Invalid Username or Password";

    public String INVALID_TOKEN = "Invalid Token";

    public String ACCESS_TOKEN_EXPIRED = "Access Token has been Expired";

}
