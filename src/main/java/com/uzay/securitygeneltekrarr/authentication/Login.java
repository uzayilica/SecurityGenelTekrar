package com.uzay.securitygeneltekrarr.authentication;

import com.uzay.securitygeneltekrarr.dto.UserRequestDto;
import com.uzay.securitygeneltekrarr.dto.UserResponseDto;
import com.uzay.securitygeneltekrarr.jwt.JwtService;
import com.uzay.securitygeneltekrarr.security.MyUserDetailsService;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class Login {
    private final MyUserDetailsService myUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public Login(MyUserDetailsService myUserDetailsService, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.myUserDetailsService = myUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?>Login(@RequestBody UserRequestDto userRequestDto) {

        try {
            UserDetails userDetails = myUserDetailsService.loadUserByUsername(userRequestDto.getUsername());
            if(passwordEncoder.matches(userRequestDto.getPassword(),userDetails.getPassword())){
                String token = jwtService.generateToken(userDetails);
                return ResponseEntity.ok().body(Map.of("bilgi" ,"Giriş Başarılı Oldu","token",token));

            }
            else {

                return new ResponseEntity<>("hatalı şifre",HttpStatus.BAD_REQUEST);

            }
        }
        catch (UsernameNotFoundException e) {
            return  ResponseEntity.badRequest().body("Kullanıcı bulunamadı Kayıt Ol");
        }


    }




}
