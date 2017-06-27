package com.dub.sharewood.config;

import com.dub.sharewood.site.DefaultAccessTokenServices;
import com.dub.sharewood.site.DefaultWebServiceClientService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;

import javax.inject.Inject;

	
/**
 * AuthorizationServerConfiguration used by Spring OAuth2 
 * */
@Configuration
@EnableWebSecurity
@EnableAuthorizationServer
public class AuthorizationServerConfiguration 
			extends AuthorizationServerConfigurerAdapter 
{	
	@Inject 
	private DefaultAccessTokenServices tokenServices;

	@Inject 
	private DefaultWebServiceClientService webServiceClientService;
	
	@Bean
	public OAuth2AccessDeniedHandler oauthAccessDeniedHandler() {
		return new OAuth2AccessDeniedHandler();
	}     
	
	@Bean
	public DefaultOAuth2RequestFactory requestFactory() {
		return new DefaultOAuth2RequestFactory(webServiceClientService);
	}
				
	@Bean 	
	public ClientDetailsUserDetailsService clientDetailsUserService() {		
		return new ClientDetailsUserDetailsService(webServiceClientService);
	}
			
	private AuthenticationManager oauthClientAuthenticationManager() throws Exception {
	    OAuth2AuthenticationManager authenticationManager 
	    							= new OAuth2AuthenticationManager();
	    authenticationManager.setClientDetailsService(webServiceClientService);
	    return authenticationManager;
	}
	
		
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {			
		endpoints.authenticationManager(oauthClientAuthenticationManager());
	    endpoints.pathMapping("/oauth/confirm_access", "/oauth/myApproval");
	    endpoints.tokenServices(tokenServices);
	    endpoints.setClientDetailsService(webServiceClientService);      
	}
		
		
	@Override	
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) 
													throws Exception {			
		
		oauthServer.accessDeniedHandler(oauthAccessDeniedHandler());
			
		oauthServer.passwordEncoder(new BCryptPasswordEncoder());
		
		System.out.println("oauthServer: " + oauthServer.getClass());
		
		System.out.println("AuthorizationServerConfiguration completed");

	}
		
		
	@Override	
	public void configure(ClientDetailsServiceConfigurer clients) 
												throws Exception {
		
		clients.withClientDetails(webServiceClientService);
		
	}

}