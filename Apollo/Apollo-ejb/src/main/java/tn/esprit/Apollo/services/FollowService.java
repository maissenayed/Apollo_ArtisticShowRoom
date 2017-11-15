package tn.esprit.Apollo.services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Date;

import tn.esprit.Apollo.persistence.Artist;
import tn.esprit.Apollo.persistence.Follow;
import tn.esprit.Apollo.persistence.FollowPk;
import tn.esprit.Apollo.persistence.User;

@Stateless
@LocalBean
public class FollowService implements FollowServiceLocal{
	
	@PersistenceContext
	EntityManager em;
	@Override
	public void follow(int artistId, int userId) {
		if(findFollow(userId, artistId) == null) {
			Follow f = new Follow();
			f.setArtist(em.find(Artist.class, artistId));
			f.setUser(em.find(User.class, userId));
			f.setFollowDate(new Date());
			FollowPk fk = new FollowPk();
			fk.setArtistId(artistId);
			fk.setUserId(userId);
			f.setFollowPk(fk);
			em.persist(f);
		}
	}

	@Override
	public void unfollow(int artistId, int userId) {
		em.remove(findFollow(userId, artistId));
	}

	@Override
	public long countFollowers(int artistId) {
		return (long) (em.createQuery("SELECT count(*) FROM Follow f WHERE f.artist.id = :artistId")
				.setParameter("artistId", artistId)).getSingleResult();
	}

	@Override
	public long countFollowings(int userId) {
		return  (long) (em.createQuery("SELECT count(*) FROM Follow f WHERE f.user.id = :userId")
				.setParameter("userId", userId)).getSingleResult();
	}

	@Override
	public List<User> getFollowers(int artistId) {
		return (List<User>) (em.createQuery("SELECT f.user FROM Follow f WHERE f.artist.id = :artistId")
				.setParameter("artistId", artistId)).getResultList();
	}

	@Override
	public List<Artist> getFollowings(int userId) {
		return (List<Artist>) (em.createQuery("SELECT f.artist FROM Follow f WHERE f.user.id = :userId")
				.setParameter("userId", userId)).getResultList();
	}

	@Override
	public Follow findFollow(int userId, int artistId) {
		List<Follow> f =(em.createQuery("SELECT f FROM Follow f WHERE f.user.id = :userId AND f.artist.id = :artistId")
				.setParameter("artistId", artistId)
				.setParameter("userId", userId)).getResultList();
		if(f.isEmpty()){
			return null;
		}else
			return (f.get(0));
	}

}
