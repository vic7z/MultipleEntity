package com.vi.multipleentity.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "_user")
@Data
@NoArgsConstructor
public class User {
    @Id
    private Integer userId;
    private String username;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Roles> Roles=new HashSet<>();
}
