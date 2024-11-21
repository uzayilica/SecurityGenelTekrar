package com.uzay.securitygeneltekrarr.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Roles name;


    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn( name = "userId",referencedColumnName = "id")
    @JsonBackReference("user-role")
    private User user;
}
