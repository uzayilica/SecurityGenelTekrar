package com.uzay.securitygeneltekrarr.authentication;

import com.uzay.securitygeneltekrarr.jwt.JwtService;
import com.uzay.securitygeneltekrarr.security.MyAuthenticationProvider;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginAuthentication {

    private final MyAuthenticationProvider myAuthenticationProvider;
    private final JwtService jwtService;

    public LoginAuthentication(MyAuthenticationProvider myAuthenticationProvider, JwtService jwtService) {
        this.myAuthenticationProvider = myAuthenticationProvider;
        this.jwtService = jwtService;
    }

    @PostMapping("/authentication-login")
    public ResponseEntity<?>login(@RequestParam String username, @RequestParam String password){
        UsernamePasswordAuthenticationToken authenticate = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticated = myAuthenticationProvider.authenticate(authenticate);
        if (authenticated != null) {
            String token = jwtService.generateToken2(authenticated);
            return ResponseEntity.ok().body(token);
        }
        return  ResponseEntity.badRequest().body("bir hata oluştu");
    }

}