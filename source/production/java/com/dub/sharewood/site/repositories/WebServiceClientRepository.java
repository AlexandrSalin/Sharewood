package com.dub.sharewood.site.repositories;


import org.springframework.data.repository.CrudRepository;

import com.dub.sharewood.site.entities.WebServiceClient;

public interface WebServiceClientRepository  extends CrudRepository<WebServiceClient, Long>
{
	
    WebServiceClient getByClientId(String clientId);
}
