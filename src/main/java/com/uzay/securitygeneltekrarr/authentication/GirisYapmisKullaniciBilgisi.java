package com.uzay.securitygeneltekrarr.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class GirisYapmisKullaniciBilgisi {

    private static final Logger log = LoggerFactory.getLogger(GirisYapmisKullaniciBilgisi.class);

    @GetMapping("/get-giris-yapmis-kullanici")
    public ResponseEntity<?> getGirisYapmisKullaniciBilgisi(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        String password = userDetails.getPassword();
        List<? extends GrantedAuthority> authorities = userDetails.getAuthorities().stream().toList();

        List<String> authority = authorities.stream().map(var -> var.getAuthority()).toList();
 log.info("s");

        return ResponseEntity.ok().body(
                Map.of("username",username,"password",password,"authorities", authorities,"authority",authority)
        );

    }




}
