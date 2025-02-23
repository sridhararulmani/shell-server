package com.shell.webapplication.auth.dto;

import com.shell.webapplication.auth.custom.annotation.MobileNumberValidator;
import com.shell.webapplication.auth.custom.annotation.UniqueEmail;
import com.shell.webapplication.auth.custom.annotation.UniqueUserName;
import com.shell.webapplication.constent.AppConstant;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RegisterUserDto {

    @NotBlank(message = "Username is Required")
    @UniqueUserName(message = "Entered Username not permitted")
    @Size(min = 3, max = 20, message = "User name should be between 3 to 20 characters")
    private String userName;

    @NotBlank(message = "Email is Required")
    @UniqueEmail(message = "Entered email already have an account")
    @Email(regexp = AppConstant.EMAIL_REGEX, message = "Email should be valid")
    private String userEmail;

    @NotBlank(message = "Password is Required")
//    @Pattern(regexp = AppConstant.PASSWORD_REGEX, message = "Invalid password. "
//            + "1. Password should contains at lest 8 alphabetic characters,"
//            + "2. Password should contains at lest 1 upper camel case,"
//            + "3. Password should contains at lest 1 special character,"
//            + "4. Password should contains at lest 3 numbers")
    @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least 1 upper case letter.")
    @Pattern(regexp = ".*[a-z].*", message = "Password must contain at least 1 lower case letter.")
    @Pattern(regexp = ".*\\d.*", message = "Password must contain at least 1 digit.")
    @Pattern(regexp = ".*[@$!%*?&].*", message = "Password must contain at least 1 special character.")
    @Pattern(regexp = ".*[A-Za-z].*[A-Za-z].*[A-Za-z].*", message = "Password must contain at least 3 letters.")
    @Size(min = 8, max = 16, message = "User Password should be between 8 to 16 characters.")
    private String userPassword;

    //    @MobileNumberValidator(message = "Mobile Number should be valid")
    @Pattern(regexp = AppConstant.MOBILE_NUMBER_REGEX, message = "Mobile Number should be valid")
    private String mobileNumber;

    private MultipartFile userProfileImage;

}