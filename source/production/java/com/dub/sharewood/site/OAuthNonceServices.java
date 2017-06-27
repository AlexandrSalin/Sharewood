package com.dub.sharewood.site;

/** 
 * Interface used by Spring OAuth2 
 * */
public interface OAuthNonceServices
{
    void recordNonceOrFailIfDuplicate(String nonce, long timestamp);
}
