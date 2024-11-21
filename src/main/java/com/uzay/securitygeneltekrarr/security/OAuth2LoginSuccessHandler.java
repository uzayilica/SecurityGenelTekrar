package com.uzay.securitygeneltekrarr.security;

import com.uzay.securitygeneltekrarr.entity.User;
import com.uzay.securitygeneltekrarr.jwt.JwtService;
import com.uzay.securitygeneltekrarr.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Map;

@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final MyUserDetailsService myUserDetailsService;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public OAuth2LoginSuccessHandler(MyUserDetailsService myUserDetailsService,
                                     UserRepository userRepository,
                                     JwtService jwtService) {
        this.myUserDetailsService = myUserDetailsService;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        if(authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
            if(token.getAuthorizedClientRegistrationId().equals("github")) {
                OAuth2User oAuth2User = token.getPrincipal();
                Map<String, Object> attributes = oAuth2User.getAttributes();

                // GitHub login kontrolü
                if(!attributes.containsKey("login") || attributes.get("login") == null) {
                    throw new IllegalArgumentException("GitHub login bilgisi bulunamadı");
                }

                String login = attributes.get("login").toString();
                MyUserDetails myUserDetails;

                try {
                    myUserDetails = (MyUserDetails) myUserDetailsService.loadUserByUsername(login);
                } catch (UsernameNotFoundException e) {
                    User newUser = new User();
                    newUser.setUsername(login);
                    userRepository.save(newUser);
                    myUserDetails = new MyUserDetails(newUser);
                }

                // JWT token oluştur
                String jwtToken = jwtService.generateToken(myUserDetails);


                //!1.yol
//                Cookie cookie = new Cookie("jwtToken", jwtToken);
//                cookie.setHttpOnly(true);  // JavaScript'ten erişilemez
//                cookie.setSecure(true);     // Sadece HTTPS üzerinden gönderilsin
//                cookie.setMaxAge(3600);    // Token 1 saat geçerli
//                cookie.setPath("/");       // Tüm domain için geçerli
//                response.addCookie(cookie);
//
//
//     // Yönlendirme URL'si belirle (varsayılan olarak ana sayfa)
//     String targetUrl = "/home";  // Varsayılan yönlendirme URL'si
//
//// Eğer daha önce kaydedilmiş bir istek varsa, ona yönlendir
// if (request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST") != null) {
//      // Kaydedilen URL'yi al
//      targetUrl = (String) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
//                }
//
//// Kullanıcıyı yönlendirin
//                response.sendRedirect(targetUrl);  // Yönlendirme
//
//



                //!2.yol
//                // Authorization header'ına token ekleyin
//                response.setHeader("Authorization", "Bearer " + jwtToken);
//
//                // Yönlendirme URL'sini ayarla
//                String targetUrl = "/home";  // Ana sayfaya yönlendirme
//
//                // Yönlendirme
//                response.sendRedirect(targetUrl);



   //!3.yol
//                // Frontend'e yönlendirme URL'i oluştur
//                String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:5173/oauth2/callback/github")
//                        .queryParam("jwtToken", jwtToken)
//                        .build()
//                        .toUriString();
//
//                // Yönlendirme URL'ini ayarla
//                this.setDefaultTargetUrl(targetUrl);
            }
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}