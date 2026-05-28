package com.sunbeam;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
	 // test endpoint
    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    // /login endpoint
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> credentials) {
        String user = credentials.get("user");
        String passwd = credentials.get("passwd");

        if (user != null && user.equals(passwd)) {
            return Map.of(
                "status", "success",
                "message", "Login successful"
            );
        } else {
            return Map.of(
                "status", "failed",
                "message", "Login failed"
            );
        }
    }
}
