package com.dub.sharewood.photos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dub.sharewood.exceptions.NoUploadFileException;
import com.dub.sharewood.exceptions.PhotoNotFoundException;
import com.dub.sharewood.exceptions.PhotoUploadException;
import com.dub.sharewood.exceptions.UnauthorizedException;
import com.dub.sharewood.site.entities.Photo;
import com.dub.sharewood.site.repositories.PhotoRepository;

@Service
public class DefaultPhotoServices implements PhotoServices {
	
	private String baseDirPath;
	
	@Resource 
	private PhotoRepository photoRepository;
	
	
	@Override
	public List<Photo> getPhotosForCurrentUser(String username) {
		return photoRepository.findPhotosByUsername(username);
	}

	
	@Override
	public List<Photo> getAllPhotos() {
		return (List<Photo>)photoRepository.findAll();
	}

	@Override
	public void deletePhoto(long id) {
		// delete row in database
		String filename = "photo" + id + ".jpg";
			
		try {
			photoRepository.delete(id);
			// delete actual photo file			
			Path path = FileSystems.getDefault().getPath(baseDirPath, filename);
    	
    		Files.deleteIfExists(path);
		} catch (EmptyResultDataAccessException e) {
			throw new PhotoNotFoundException();
    	} catch (IOException e) {
    		System.out.println("delete unsuccesful " + e);   
    		e.printStackTrace();
    	} catch (Exception e) {
    		System.out.println("delete unsuccesful " + e);   
    	
    	}	  
	}
	
	
	@Override
	public void deletePhoto(long id, String username) {
		Photo photo = photoRepository.findOne(id);
		if (photo == null) {
			throw new PhotoNotFoundException();
		}
		String user = photo.getUsername();
		
		if (!user.equals(username)) {
			System.out.println("throwing UnauthorizedException");
			throw new UnauthorizedException();
		}
			
		deletePhoto(id);	
	}
	

	@Override
	public synchronized InputStream loadPhoto(long id, String username) 
							throws FileNotFoundException
	{	
		Photo photo = photoRepository.findOne(id);
		if (photo == null) {
			throw new PhotoNotFoundException();
		}
	
		String path = baseDirPath + "photo" + photo.getId() + ".jpg";
				
		if (photo.isShared() || username.equals(photo.getUsername())) {			
			return new FileInputStream(path);// throws FileNotFoundException
		} else {
			throw new UnauthorizedException();// caught by RestEndpoint
		}	
	}
	
	@Override
	public synchronized InputStream loadPhoto(long id) 
							throws FileNotFoundException
	{
		Photo photo = photoRepository.findOne(id);
		
		String path = baseDirPath + "photo" + photo.getId() + ".jpg";
					
		return new FileInputStream(path);		
	}

	public String getBaseDirPath() {
		return baseDirPath;
	}

	public void setBaseDirPath(String baseDirPath) {
		this.baseDirPath = baseDirPath;
	}

	@Override
	public List<Photo> getSharedPhotos() {
		return photoRepository.findPhotosByShared(true);
	}

	@Override
	public Photo getPhoto(long id, String username) {
		
		Photo photo = photoRepository.findOne(id);// may be null	
		
		if (photo == null) {
			throw new PhotoNotFoundException();
		}
			
		try {
			if ( username.equals(photo.getUsername()) ) {
				return photo;
			} else {
				System.out.println("throwing UnauthorizedException");
				throw new UnauthorizedException();
			}
		} catch (PhotoNotFoundException e) {
			throw e;
		}	
	}
	
	
	@Override
	public long createPhoto(
					MultipartFile uploadedFileRef, String username, 
					String title, boolean shared) throws IOException
	{			
		InputStream is = null;     
		OutputStream os = null;
		
		// This buffer will store the data read from 'uploadedFileRef'
		byte[] buffer = new byte[1000];
		int bytesRead = -1;
		int totalBytes = 0;
		    
		String fileName = "photo" + "Tmp.jpg";
		
		String path = baseDirPath + fileName;
		
		if (uploadedFileRef.getSize() == 0) {
			System.out.println("throwing NoUploadFileException");
			throw new NoUploadFileException();
		}
		
		is = uploadedFileRef.getInputStream();
		os = new FileOutputStream(path);
		
		while ((bytesRead = is.read(buffer)) != -1) {
			os.write(buffer);
			totalBytes += bytesRead;
		}
		os.close();
			
		if (totalBytes != uploadedFileRef.getSize()) {
			System.out.println("throwing");
			throw new PhotoUploadException();
		} else {
			// now update database
			Photo photo = new Photo();
			photo.setTitle(title);
			photo.setUsername(username);
			photo.setShared(shared);
			long newId = photoRepository.save(photo).getId();
				
			// now change file name
			Path source = FileSystems.getDefault().getPath(baseDirPath, fileName);
			Path target = FileSystems.getDefault().getPath(baseDirPath, "photo" + newId + ".jpg");
			//Path source = 
			Files.move(source, target);
					
			return newId;
		}	
		
	}

	@Override
	public void updatePhoto(Photo photo, String username) {
		
		if (!username.equals(photo.getUsername())) {
			throw new UnauthorizedException();
		}
		photoRepository.save(photo);
	}
	
}
