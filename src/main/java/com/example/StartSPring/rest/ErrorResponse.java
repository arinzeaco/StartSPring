package com.example.StartSPring.rest;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResponse
{

  //General error message about nature of error
  private String message;
 
  //Specific errors in API request processing
  private List<String> details;
 
  //Getter and setters
}