package tn.esprit.Apollo.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


/**
 * Entity implementation class for Entity: GalleryOwner
 *
 */
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class GalleryOwner extends User implements Serializable {
    @OneToMany(mappedBy="galleryOwner")
	private List<Gallery> galleries; 
	
	
	private int PhoneNumber;
	
	public int getPhoneNumber() {
		return PhoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		PhoneNumber = phoneNumber;
	}

	private static final long serialVersionUID = 1L;

	public GalleryOwner() {
		super();
	}
   
}
