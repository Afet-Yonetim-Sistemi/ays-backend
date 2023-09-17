package com.ays.auth.model;

import com.ays.auth.model.enums.AysUserType;
import com.ays.user.model.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * Represents an authenticated user in the AYS System.
 */
@Getter
@Setter
public class AysUser {

    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private AysUserType type;
    private Set<UserRole> roles;

}
