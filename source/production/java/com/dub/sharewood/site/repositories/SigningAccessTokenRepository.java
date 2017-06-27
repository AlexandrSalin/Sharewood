package com.dub.sharewood.site.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.dub.sharewood.site.entities.SigningAccessToken;


public interface SigningAccessTokenRepository
        extends JpaRepository<SigningAccessToken, Long>
{
    SigningAccessToken getByKey(String key);
    SigningAccessToken getByValue(String value);
}
