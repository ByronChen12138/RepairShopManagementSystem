package ca.mcgill.ecse321.repairshopmanagementsystem;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Map<String, String> exceptionHandler(Exception e) {
        Map<String, String> errorMessage = new HashMap<>();
        System.out.println("Error occurred: " + e.getMessage());
        errorMessage.put("error", e.getMessage());
        errorMessage.put("hasError", "true");
        return errorMessage;
    }

}
