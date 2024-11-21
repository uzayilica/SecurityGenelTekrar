package com.uzay.securitygeneltekrarr.controller;

import com.uzay.securitygeneltekrarr.entity.Role;
import com.uzay.securitygeneltekrarr.repository.RoleRepository;
import com.uzay.securitygeneltekrarr.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RoleController {


    private final RoleRepository roleRepository;

    public RoleController( RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostMapping("/add-role")
    public ResponseEntity<?> addRole(@RequestBody  Role role){
        Role save = roleRepository.save(role);
        return ResponseEntity.ok(save);
    }

    @GetMapping("/get-role/{id}")
    public ResponseEntity<?> getRole(Long id){
        Optional<Role> byId = roleRepository.findById(id);
        if(byId.isPresent()){
            return ResponseEntity.ok(byId.get());
        }
        return ResponseEntity.notFound().build();

    }

    @GetMapping("/get-roles")
    public ResponseEntity<?> getRoles(){
        List<Role> all = roleRepository.findAll();
        if (all.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(all);
    }

    @DeleteMapping("/delete-role/{id}")
    public ResponseEntity<?> deleteRole(Long id){
        Optional<Role> byId = roleRepository.findById(id);
        if(byId.isPresent()){
            roleRepository.delete(byId.get());
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-role/{id}")
    public ResponseEntity<?> updateRole(@PathVariable  Integer id ,@RequestBody  Role role){
        Optional<Role> byId = roleRepository.findById(role.getId());
        if(byId.isPresent()){
            Role role1 = byId.get();
            role1.setName(role.getName());
            role1.setDescription(role.getDescription());
            role1.setUser(role.getUser());
            roleRepository.save(role1);
            return ResponseEntity.ok(role1);


        }
        return ResponseEntity.notFound().build();
    }



}
