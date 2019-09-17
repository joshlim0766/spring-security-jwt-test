package com.pentasecurity.isignplus.systemservice.authentication.controller;

import com.pentasecurity.isignplus.systemservice.authentication.controller.dto.AuthenticationRequestDTO;
import com.pentasecurity.isignplus.systemservice.authentication.controller.dto.AuthenticationTokenDTO;
import com.pentasecurity.isignplus.systemservice.authentication.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(
            value = "/login",
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}
    )
    public AuthenticationTokenDTO login (
            @RequestBody AuthenticationRequestDTO authenticationRequest, HttpSession session) {
        return authenticationService.login(authenticationRequest, session);
    }

    @GetMapping(
            value = "/logout/postprocess"
    )
    public ResponseEntity logoutPostProcess () {
        return ResponseEntity.ok().build();
    }

    @GetMapping(
            value = "/test",
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}
    )
    public String test () {
        authenticationService.findUser("admin");
        return "ddd";
    }
}
