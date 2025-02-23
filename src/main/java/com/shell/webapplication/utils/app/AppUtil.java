package com.shell.webapplication.utils.app;

import com.shell.webapplication.constent.AppConstant;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class AppUtil {

    public static boolean isEmail(String value) {
        return Objects.nonNull(value) && AppConstant.EMAIL_PATTERN.matcher(value).matches();
    }

    //Converting Minutes into Milliseconds
    public static long getMillisecondsByMinutes(Long minutes) {
        return TimeUnit.MILLISECONDS.toMinutes(minutes);
    }

    public static void setRegisteredSuccessResponse(AppResponse appResponse) {
        appResponse.setPath(appResponse.getPath());
        appResponse.setStatus(Objects.nonNull(appResponse.getStatus()) ? appResponse.getStatus() : HttpStatus.CREATED.value());
        appResponse.setMessage("Sign Up Successful");
        appResponse.setLocalDateTime(LocalDateTime.now());
    }

    public static void setLoginSuccessResponse(AppResponse appResponse) {
        appResponse.setPath(appResponse.getPath());
        appResponse.setStatus(Objects.nonNull(appResponse.getStatus()) ? appResponse.getStatus() : HttpStatus.OK.value());
        appResponse.setMessage("Sign In Successful");
        appResponse.setLocalDateTime(LocalDateTime.now());
    }


    public static void setLogOutSuccessResponse(AppResponse appResponse) {
        appResponse.setPath(appResponse.getPath());
        appResponse.setStatus(Objects.nonNull(appResponse.getStatus()) ? appResponse.getStatus() : HttpStatus.OK.value());
        appResponse.setMessage("Sign Out Successful");
        appResponse.setLocalDateTime(LocalDateTime.now());}

    public static void setSuccessResponse(AppResponse appResponse) {
        appResponse.setPath(appResponse.getPath());
        appResponse.setStatus(Objects.nonNull(appResponse.getStatus()) ? appResponse.getStatus() : HttpStatus.OK.value());
        appResponse.setMessage("Process Success");
        appResponse.setLocalDateTime(LocalDateTime.now());
    }

    public static void setErrorResponse(AppResponse appResponse, Exception exception) {
        appResponse.setPath(appResponse.getPath());
        appResponse.setStatus(HttpStatus.SWITCHING_PROTOCOLS.value());
        appResponse.setMessage(exception.getMessage());
        appResponse.setLocalDateTime(LocalDateTime.now());
    }

}
