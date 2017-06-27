package com.dub.sharewood.site;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dub.sharewood.config.annotations.RestEndpointAdvice;
import com.dub.sharewood.exceptions.PhotoNotFoundException;
import com.dub.sharewood.exceptions.ResourceNotFoundException;
import com.dub.sharewood.exceptions.UnauthorizedException;

import org.springframework.web.bind.MethodArgumentNotValidException;

/** 
 * Helper class used to intercept all REST request exceptions 
 * and handle them in a consistent way
 * */
@RestEndpointAdvice
public class RestExceptionHandler
{
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException e)
    {
   
        ErrorResponse errors = new ErrorResponse();
        
        for(FieldError violation : e.getBindingResult().getFieldErrors())
        {
            ErrorItem error = new ErrorItem();
            error.setCode("400");
            String message = violation.getField() + " " + violation.getCode();
                       
            error.setMessage(message);
            System.out.println(violation.getCode());
            
            errors.addError(error);
        }
        
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    
    
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFileNotFound(FileNotFoundException e)
    {
    	System.out.println("FileNotFoundException");
    	    	
        ErrorResponse errors = new ErrorResponse();
        
        ErrorItem error = new ErrorItem();
        error.setCode("400");
        error.setMessage("Photo file missing");
            
        errors.addError(error);
                
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(UnauthorizedException e)
    {
    	System.out.println("from Handler UnauthorizedException");
    	
        ErrorResponse errors = new ErrorResponse();
        
        ErrorItem error = new ErrorItem();
        error.setCode("401");
        error.setMessage(e.getMessage());
          
        errors.addError(error);
        
    	System.out.println("from Handler returning ResponseEntity");
        return new ResponseEntity<>(errors, HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException e)
    {
    	System.out.println("Handler ResourceNotFoundException");
    	
        ErrorResponse errors = new ErrorResponse();
        
        ErrorItem error = new ErrorItem();
        error.setCode("404");
        error.setMessage("Resource not found");
          
        errors.addError(error);
        
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(PhotoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePhotoNotFound(PhotoNotFoundException e)
    {
    	System.out.println("Handler PhotoNotFoundException");
    	
        ErrorResponse errors = new ErrorResponse();
        
        ErrorItem error = new ErrorItem();
        error.setCode("404");
        error.setMessage("Resource not found");
          
        errors.addError(error);
        
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }
    
    // catch all handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleCatchAll(Exception e)
    {
    	System.out.println("Catch All Handler begin " + e);
    	e.printStackTrace();
    	    	
    	
        ErrorResponse errors = new ErrorResponse();
        
        ErrorItem error = new ErrorItem();
        error.setCode("500");
        error.setMessage("unexpected server error " + e.getMessage());
          
        errors.addError(error);
        
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
 
    public static class ErrorItem
    {
        private String code;
        private String message;

        @XmlAttribute
        public String getCode()
        {
            return code;
        }

        public void setCode(String code)
        {
            this.code = code;
        }

        @XmlValue
        public String getMessage()
        {
            return message;
        }

        public void setMessage(String message)
        {
            this.message = message;
        }
    }

    @XmlRootElement(name = "errors")
    public static class ErrorResponse
    {
        private List<ErrorItem> errors = new ArrayList<>();

        @XmlElement(name = "error")
        public List<ErrorItem> getErrors()
        {
            return errors;
        }

        public void setErrors(List<ErrorItem> errors)
        {
            this.errors = errors;
        }

        public void addError(ErrorItem error)
        {
            this.errors.add(error);
        }
    }
}
