package com.dub.sharewood.site;

import org.springframework.security.oauth2.provider.ClientDetailsService;

import com.dub.sharewood.site.entities.WebServiceClient;

/** interface used by Spring OAuth2 */
public interface WebServiceClientService extends ClientDetailsService
{
    @Override
    WebServiceClient loadClientByClientId(String clientId);
}
