package com.dub.sharewood.site;

import com.dub.sharewood.site.entities.Nonce;
import com.dub.sharewood.site.repositories.NonceRepository;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/** 
 * Helper class used by Spring OAuth2 
 * */
@Service
public class DefaultOAuthNonceServices implements OAuthNonceServices
{
    @Inject NonceRepository nonceRepository;

    @Override
    @Transactional
    public void recordNonceOrFailIfDuplicate(String nonce, long timestamp)
    {
        if(this.nonceRepository.getByValueAndTimestamp(nonce, timestamp) != null)
            throw new InvalidTokenException("Duplicate nonce value [" + nonce +
                    "," + timestamp + "]");

        this.nonceRepository.save(new Nonce(nonce, timestamp));
    }

    @Transactional
    @Scheduled(fixedDelay = 60_000L)
    public void deleteOldNonces()
    {
        this.nonceRepository.deleteWhereTimestampLessThan(
                (System.currentTimeMillis() - 120_000L) / 1_000L
        );
    }
}
