package com.dub.sharewood.photos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

import com.dub.sharewood.site.entities.Photo;

/**
 * Main service interface with methods protected by @PreAuthorize annotation
 * */
public interface PhotoServices 
{	
	@PreAuthorize("authentication.principal.username.equals(#username) and " +
					"hasAuthority('ROLE_USER')")
	InputStream loadPhoto(long id, String username) 
									throws FileNotFoundException;
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	InputStream loadPhoto(long id) throws FileNotFoundException;
		
	@PreAuthorize("authentication.principal.username.equals(#username) and " +
			"hasAuthority('ROLE_USER')")
	List<Photo> getPhotosForCurrentUser(String username);
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	List<Photo> getAllPhotos();
	
	@PreAuthorize("hasAuthority('ROLE_USER')")
	List<Photo> getSharedPhotos();
	
	@PreAuthorize("hasAuthority('ROLE_USER')")
	long createPhoto(
			MultipartFile uploadedFileRef, 
			String username,
			String title,
			boolean shared) throws IOException ;
	
	@PreAuthorize("authentication.principal.username.equals(#username) and " +
			"hasAuthority('ROLE_USER')")
	void deletePhoto(long id, @P("username")String username);
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	void deletePhoto(long id);
	
	@PreAuthorize("authentication.principal.username.equals(#username) and " +
			"hasAuthority('ROLE_USER')")
	Photo getPhoto(long id, String username);
		
	@PreAuthorize("authentication.principal.username.equals(#username) and " +
			"hasAuthority('ROLE_USER')")
	void updatePhoto(Photo photo, @P("username")String username);
}
