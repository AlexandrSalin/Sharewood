package com.dub.sharewood.users;

import com.dub.sharewood.site.entities.UserPrincipal;
import com.dub.sharewood.site.repositories.UserRepository;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

/** Default implementation of interface UserService */
@Service
public class DefaultUserService implements UserService
{
    private static final SecureRandom RANDOM;
    private static final int HASHING_ROUNDS = 10;

    static
    {
        try
        {
            RANDOM = SecureRandom.getInstanceStrong();
        }
        catch(NoSuchAlgorithmException e)
        {
            throw new IllegalStateException(e);
        }
    }

    @Inject UserRepository userRepository;

    @Override
    @Transactional
    public UserPrincipal loadUserByUsername(String username)
    {
        UserPrincipal principal = userRepository.getByUsername(username);
        // make sure the authorities and password are loaded
        principal.getAuthorities().size();
        principal.getPassword();
        return principal;
    }

    @Override
    @Transactional
    public void saveUser(UserPrincipal principal, String newPassword)
    {
        if(newPassword != null && newPassword.length() > 0)
        {
            String salt = BCrypt.gensalt(HASHING_ROUNDS, RANDOM);
            principal.setHashedPassword(
                    BCrypt.hashpw(newPassword, salt).getBytes()
            );
        }

        this.userRepository.save(principal);
    }

	@Override
	public List<UserPrincipal> allUsers() {
		// TODO Auto-generated method stub
		List<UserPrincipal> list = this.userRepository.findAll();
				//new ArrayList<UserPrincipal>();
		System.out.println("allUsers: " + list.size());
		
		return list;
	}
}
