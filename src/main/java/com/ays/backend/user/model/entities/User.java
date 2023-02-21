package com.ays.backend.user.model.entities;

import com.ays.backend.user.model.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, columnDefinition = "varchar(60)")
    private String userUUID;

    @OneToOne(cascade=CascadeType.ALL)
    private PhoneNumber phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, unique = true, nullable = false)
    private UserStatus status;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Set<DeviceType> types = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

}
