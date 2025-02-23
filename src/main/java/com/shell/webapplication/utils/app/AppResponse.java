package com.shell.webapplication.utils.app;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Scope(value = "prototype")
@Component
public class AppResponse implements Serializable {

    private String path;
    private Integer status;
    private String message;
    private LocalDateTime localDateTime;

}
