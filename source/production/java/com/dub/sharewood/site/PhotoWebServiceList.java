package com.dub.sharewood.site;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.dub.sharewood.site.entities.Photo;

/** Helper class used by REST requests */
@XmlRootElement(name = "photos")
public class PhotoWebServiceList
{
    private List<Photo> photos;

    @XmlElement(name = "photos")
	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

   
}
