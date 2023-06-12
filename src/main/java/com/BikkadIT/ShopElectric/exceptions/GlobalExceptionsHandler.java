package com.BikkadIT.ShopElectric.exceptions;

import com.BikkadIT.ShopElectric.payloads.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionsHandler {
    // handle resource not found exception

    private Logger logger= LoggerFactory.getLogger(GlobalExceptionsHandler.class);
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundException(ResourceNotFoundException ex){
        logger.info("Exception Handler invoked  ... ");
        ApiResponse responce = ApiResponse.builder().message(ex.getMessage()).success(true).build();

        return new ResponseEntity<>(responce,HttpStatus.NOT_FOUND);
    }

    // methodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex){

       List<ObjectError> allErrors =  ex.getBindingResult().getAllErrors();
       Map<String,Object> responce=new HashMap<>();
       allErrors.stream().forEach(objectError -> {
           String message = objectError.getDefaultMessage();
           String field = ((FieldError) objectError).getField();
           responce.put(field,message);
       });

        return new ResponseEntity<>(responce,HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(BadApiRequest.class)
    public ResponseEntity<ApiResponse> resourceNotFoundException(BadApiRequest ex){
        logger.info("Exception Handler invoked  ... ");
        ApiResponse responce = ApiResponse.builder().message(ex.getMessage()).success(true).build();

        return new ResponseEntity<>(responce,HttpStatus.BAD_REQUEST);
    }

}
