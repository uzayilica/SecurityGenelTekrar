package com.uzay.securitygeneltekrarr.service;

import com.uzay.securitygeneltekrarr.entity.Role;
import com.uzay.securitygeneltekrarr.entity.Roles;
import com.uzay.securitygeneltekrarr.entity.User;
import com.uzay.securitygeneltekrarr.exception.UserNotFoundException;
import com.uzay.securitygeneltekrarr.repository.RoleRepository;
import com.uzay.securitygeneltekrarr.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<User> getUsers() {
        List<User> all = userRepository.findAll();
        return all;
    }

    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı"));
        return optionalUser.get();
    }
    public User addUser(User user) {
        // Önce "ROLE_USER" rolünü oluştur
        Role roleUser = new Role();
        roleUser.setName(Roles.ROLE_USER);
        roleUser.setDescription("Default User Role");
        roleUser = roleRepository.save(roleUser);

        // Kullanıcının rol kümesine "ROLE_USER" rolünü ekle
        Set<Role> roles = new HashSet<>();
        roles.add(roleUser);
        user.setRole(roles);

        // Kullanıcıyı kaydet
        User savedUser = userRepository.save(user);

        // Roller için kullanıcı bağlantısını ayarla
        roleUser.setUser(savedUser);
        roleRepository.save(roleUser);

        return savedUser;
    }


    public boolean deleteUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            return false;
        }
        userRepository.deleteById(id);
        return true;

    }

    public User updateUser(Long id, User user) {
        // Kullanıcıyı veritabanında bul
        User userFound = userRepository.findById(id).orElse(null);

            // Sadece null olmayan değerleri güncelle
            if (user.getEmail() != null) {
                userFound.setEmail(user.getEmail());
            }
            if (user.getPassword() != null) {
                userFound.setPassword(user.getPassword());
            }
            if (user.getUsername() != null) {
                userFound.setUsername(user.getUsername());
            }

            // Güncellenmiş kullanıcıyı kaydet
            return userRepository.save(userFound);


        // Kullanıcı bulunamadıysa veya güncellenmek istenen bilgiler yoksa null döndür
    }
}
