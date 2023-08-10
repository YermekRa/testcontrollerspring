package com.example.testcontroller.controller;

import com.example.testcontroller.service.impl.CheckMethod;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "check", method = RequestMethod.GET)
public class TestOk {

    @Autowired(required=true)
    private CheckMethod checkMethod;

    @GetMapping(value = "/")
    public @ResponseBody ResponseEntity getResponse(HttpServletRequest request) {
        return checkMethod.checkController(request.getRemoteAddr());
    }
}
