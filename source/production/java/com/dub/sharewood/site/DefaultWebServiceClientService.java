package com.dub.sharewood.site;

import com.dub.sharewood.site.entities.WebServiceClient;
import com.dub.sharewood.site.repositories.WebServiceClientRepository;

import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/** 
 * Helper class used by Spring OAuth2
 * */
@Service
public class DefaultWebServiceClientService implements WebServiceClientService
{
    @Inject WebServiceClientRepository clientRepository;
    
    public DefaultWebServiceClientService() {
    }

    @Override
    @Transactional
    public WebServiceClient loadClientByClientId(String clientId)
    {
    	System.out.println("loadClientByClientId " + clientId);
        WebServiceClient client = this.clientRepository.getByClientId(clientId);
        if(client == null)
            throw new ClientRegistrationException("Client not found");
        return client;
    }
}
