package com.dub.sharewood.config;

import com.dub.sharewood.site.DefaultAccessTokenServices;
import com.dub.sharewood.site.DefaultOAuthNonceServices;
import com.dub.sharewood.site.OAuthSigningTokenAuthenticationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;

import javax.inject.Inject;

	
/**
 * ResourceServerConfiguration used by Spring OAuth2 
 * */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration 
				extends ResourceServerConfigurerAdapter 
{
	private static final String RESOURCE_ID = "SUPPORT";
 		
	@Inject 
	private OAuth2AccessDeniedHandler oauthAccessDeniedHandler;
	
	@Inject
	private OAuth2WebSecurityExpressionHandler webSecurityExpressionHandler;
	
	@Inject 
	private DefaultOAuthNonceServices nonceServices;
	
	@Inject 
	private DefaultAccessTokenServices tokenServices;
		
	@Bean
	public OAuthSigningTokenAuthenticationFilter resourceServerFilter() {	
		OAuthSigningTokenAuthenticationFilter filter 
					= new OAuthSigningTokenAuthenticationFilter();
		filter.setAuthenticationEntryPoint(new OAuth2AuthenticationEntryPoint());			
		filter.setNonceServices(nonceServices);
		filter.setTokenServices(tokenServices);
		filter.setResourceId(RESOURCE_ID);
		System.out.println("resourceServerFilter created");
		return filter;		
	}
	
	

	@Override
	public void configure(HttpSecurity http) 
									throws Exception 
	{		
		http.antMatcher("/services/**").authorizeRequests()	
			.antMatchers(HttpMethod.GET, "/services/Rest/photos/**")
			.access("#oauth2.hasScope('READ') and hasAuthority('ROLE_USER')")
			.antMatchers(HttpMethod.POST, "/services/Rest/photos/**")
			.access("#oauth2.hasScope('READ') and #oauth2.hasScope('WRITE') and hasAuthority('ROLE_USER')")
			.antMatchers(HttpMethod.DELETE, "/services/Rest/photos/**")
			.access("#oauth2.hasScope('READ') and #oauth2.hasScope('WRITE') and hasAuthority('ROLE_USER')");
		http.addFilterBefore(
					resourceServerFilter(), 
					SecurityContextHolderAwareRequestFilter.class);			
	}
	
	
	@Override
 	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(RESOURCE_ID);
		resources.accessDeniedHandler(oauthAccessDeniedHandler);
		resources.expressionHandler(webSecurityExpressionHandler);
		
	}
	
}
	
	
