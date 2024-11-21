package com.uzay.securitygeneltekrarr.authentication;

import com.uzay.securitygeneltekrarr.dto.UserRequestDto;
import com.uzay.securitygeneltekrarr.dto.UserResponseDto;
import com.uzay.securitygeneltekrarr.entity.Role;
import com.uzay.securitygeneltekrarr.entity.Roles;
import com.uzay.securitygeneltekrarr.entity.User;
import com.uzay.securitygeneltekrarr.jwt.JwtService;
import com.uzay.securitygeneltekrarr.repository.RoleRepository;
import com.uzay.securitygeneltekrarr.repository.UserRepository;
import com.uzay.securitygeneltekrarr.security.MyUserDetailsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;
@Validated
@RestController
public class Register {

    private final JwtService jwtService;
    private final MyUserDetailsService myUserDetailsService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public Register(JwtService jwtService, MyUserDetailsService myUserDetailsService, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.myUserDetailsService = myUserDetailsService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?>Register(@Valid @RequestBody UserRequestDto user) {

        try {
            myUserDetailsService.loadUserByUsername(user.getUsername());
            return ResponseEntity.ok().body("kullanıcı zaten kayıtlı");
        }
        catch (UsernameNotFoundException e) {
            // Önce "ROLE_USER" rolünü oluştur
            Role roleUser = new Role();
            roleUser.setName(Roles.ROLE_USER);
            roleUser.setDescription("Default User Role");
            roleUser = roleRepository.save(roleUser);


            User newUser = new User();
            newUser.setUsername(user.getUsername());
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            newUser.setEmail(user.getEmail());



            // Kullanıcının rol kümesine "ROLE_USER" rolünü ekle
            Set<Role> roles = new HashSet<>();
            roles.add(roleUser);
            newUser.setRole(roles);

            // Kullanıcıyı kaydet
            User savedUser = userRepository.save( newUser);

            // Roller için kullanıcı bağlantısını ayarla
            roleUser.setUser(savedUser);
            roleRepository.save(roleUser);

            return ResponseEntity.ok().body("eklendi");

        }





    }


}
