package tn.esprit.Apollo.persistence;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

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
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private Set<ArtWork> artworks;

	public Set<ArtWork> getArtworks() {
		return artworks;
	}

	public void setArtworks(Set<ArtWork> artworks) {
		this.artworks = artworks;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
