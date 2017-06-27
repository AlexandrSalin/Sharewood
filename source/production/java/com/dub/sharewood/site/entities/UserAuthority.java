package com.dub.sharewood.site.entities;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Embeddable;

/**
 * UserAuthority encapsulates a User Authority
 * It is used by basic Spring Security
 * */
@Embeddable
public class UserAuthority implements GrantedAuthority
{
    /**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	
	private String authority;

    public UserAuthority() { }

    public UserAuthority(String authority)
    {
        this.authority = authority;
    }

    @Override
    public String getAuthority()
    {
        return this.authority;
    }

    public void setAuthority(String authority)
    {
        this.authority = authority;
    }
    
    public String toString() {
    	return authority;
    }
}
