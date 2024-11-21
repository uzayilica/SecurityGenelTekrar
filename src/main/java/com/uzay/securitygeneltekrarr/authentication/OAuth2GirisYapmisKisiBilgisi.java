package com.uzay.securitygeneltekrarr.authentication;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuth2GirisYapmisKisiBilgisi {

    @GetMapping("/oauth2-giris-bilgisi")
    public ResponseEntity<?> getOauth2GirisBilgisi(@AuthenticationPrincipal OAuth2User oauth2User) {
        return ResponseEntity.ok(oauth2User);
    }

}
