package com.dub.sharewood.site;

import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import com.dub.sharewood.site.entities.SigningAccessToken;


/** main interface used by Spring OAuth2 */
public interface SigningAccessTokenServices
				extends AuthorizationServerTokenServices, ResourceServerTokenServices 
{
	SigningAccessToken getAccessToken(String key);
}
