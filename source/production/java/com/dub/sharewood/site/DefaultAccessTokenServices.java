package com.dub.sharewood.site;

import com.dub.sharewood.site.entities.SigningAccessToken;
import com.dub.sharewood.site.repositories.SigningAccessTokenRepository;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import java.util.Date;
import java.util.UUID;

/** This class implements the interface SigningAccessTokenServices 
 * It is used by Spring OAuth2 for REST requests
 * */
@Service
public class DefaultAccessTokenServices implements SigningAccessTokenServices
{
    AuthenticationKeyGenerator authenticationKeyGenerator =
            new DefaultAuthenticationKeyGenerator();// oauth2 class

    @Inject SigningAccessTokenRepository repository;
    
    private OAuth2Authentication localOAuth2Authentication;
     
    @Override
    @Transactional
    public OAuth2AccessToken createAccessToken(OAuth2Authentication auth)
            throws AuthenticationException
    {	  	
    	// save context
    	this.setLocalOAuth2Authentication(auth);
    		
    	// behind the scene
        String key = this.authenticationKeyGenerator.extractKey(auth);
        
        // fails here
        SigningAccessToken token = this.repository.getByKey(key);
        
        /**
         * initially the table OauthAccessToken is empty 
         */
        
        if(token != null)
        {
            if(token.isExpired())
            {
                this.repository.delete(token);
                this.repository.flush();
            }
            else
            {
                token.setAuthentication(auth); // in case authorities changed
                this.repository.save(token);
                return token;
            }
        }
  
        token = new SigningAccessToken(
                key,
                UUID.randomUUID().toString(),
                new Date(System.currentTimeMillis() + 86_400_000L), // one day
                auth.getOAuth2Request().getScope(),
                auth
        );

        this.repository.save(token);

        return token;
    }

    @Override
    @Transactional
    public OAuth2AccessToken getAccessToken(OAuth2Authentication auth)
    {
        return this.repository.getByKey(
                this.authenticationKeyGenerator.extractKey(auth)
        );
    }

    @Override
    @Transactional
    public SigningAccessToken getAccessToken(String key)
    {
        return this.repository.getByKey(key);
    }

    @Override
    @Transactional
    public OAuth2AccessToken readAccessToken(String tokenValue)
    {
        return this.repository.getByValue(tokenValue);
    }

    @Override
    @Transactional
    public OAuth2Authentication loadAuthentication(String tokenValue)
            throws AuthenticationException
    {
        SigningAccessToken token = this.repository.getByValue(tokenValue);
        if(token == null)
            throw new InvalidTokenException("Invalid token " + tokenValue + ".");

        if(token.isExpired())
        {
            this.repository.delete(token);
            throw new InvalidTokenException("Expired token " + tokenValue + ".");
        }

        return token.getAuthentication();
    }

	@Override
	public OAuth2AccessToken refreshAccessToken(String arg0, TokenRequest arg1) throws AuthenticationException {
		// TODO Auto-generated method stub
		return null;
	}

	public OAuth2Authentication getLocalOAuth2Authentication() {
		return localOAuth2Authentication;
	}

	public void setLocalOAuth2Authentication(OAuth2Authentication localOAuth2Authentication) {
		this.localOAuth2Authentication = localOAuth2Authentication;
	}

	
	
	
}
