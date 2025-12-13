package com.library.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseApp<T>{
    private int code;
    private String message;
    private T data;

    public static <T> ApiResponseApp<T> success(T data){
        return new ApiResponseApp<>(200, "ok", data);
    }

    public static <T> ApiResponseApp<T> error(String message){
        return new ApiResponseApp<>(400, message, null);
    }
}
