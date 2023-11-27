package com.auth.security;

import com.auth.service.dto.EnvUserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserSecurity extends User  {


    private com.auth.service.dto.EnvUserDto envUser;

    public UserSecurity(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public UserSecurity(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
    public UserSecurity(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, com.auth.service.dto.EnvUserDto envUser) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.setEnvUser(envUser);
    }

    public com.auth.service.dto.EnvUserDto getEnvUser() {
        return envUser;
    }

    public void setEnvUser(EnvUserDto envUser) {
        this.envUser = envUser;
    }
}
