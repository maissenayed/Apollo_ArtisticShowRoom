package tn.esprit.Apollo.persistence;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

import tn.esprit.Apollo.Facade.EntityBone;

/**
 * Entity implementation class for Entity: Collection
 *
 */
@Entity

public class Collection extends EntityBone {

	   
	@Transient
	private static final long serialVersionUID = 1L;
	private String description;
	
	@ManyToMany
	private List<ArtWork> artworks;
	

	public List<ArtWork> getArtworks() {
		return artworks;
	}
	public void setArtworks(List<ArtWork> artworks) {
		this.artworks = artworks;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}


	
   
}
