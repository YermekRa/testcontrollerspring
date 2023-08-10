package com.example.testcontroller.service;

import org.springframework.http.ResponseEntity;

public interface ICheckMethod {
    ResponseEntity checkController(String ip);
}
