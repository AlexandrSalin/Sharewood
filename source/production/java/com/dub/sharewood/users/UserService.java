package com.dub.sharewood.users;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;

import com.dub.sharewood.site.entities.UserPrincipal;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/** main User interface */
@Validated
public interface UserService extends UserDetailsService
{
    @Override
    UserPrincipal loadUserByUsername(String username);

    void saveUser(
            @NotNull(message = "{validate.authenticate.saveUser}") @Valid
                UserPrincipal principal,
            String newPassword
    );
    
    List<UserPrincipal> allUsers();
}
