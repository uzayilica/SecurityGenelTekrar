package com.uzay.securitygeneltekrarr.authentication;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CsrfTokenController {

        @GetMapping("/csrf-token")
        public CsrfToken getCsrfToken(HttpServletRequest request) {
            return (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        }
    }
