package tn.esprit.Apollo.Api;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.jsonwebtoken.Jwts;
import tn.esprit.Apollo.Facade.SearchCriteria;
import tn.esprit.Apollo.persistence.ArtWork;
import tn.esprit.Apollo.persistence.ArtWorkCategory;
import tn.esprit.Apollo.persistence.Artist;
import tn.esprit.Apollo.services.ArtWorkService;
import tn.esprit.Apollo.services.UserServiceLocal;
import tn.esprit.Authentificateur.JWTTokenNeeded;
import tn.esprit.Authentificateur.UserCourant;
import tn.esprit.Apollo.Api.UserController;

@Path(value = "ArtWork")
@Stateless
public class ArtWorkRest {
	@EJB
	private ArtWorkService ARTWORK;
	@EJB
	UserServiceLocal UserService ;
	
	
	
	
	
	// recherche par id ( very simple one )
	@GET
	@Path(value = "find/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response find(@PathParam("id") int id) {
		if (ARTWORK.read(id) == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.ok(ARTWORK.read(id)).build();
	}
	// recherche par paginateur(10 per page)
	@GET
	@Path(value = "findall/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findall(@PathParam("id") int id) {
		if (ARTWORK.paginateur(id) == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.ok(ARTWORK.paginateur(id)).build();
	}
	// advance search(search per criteria of what the user really need be proud
	// with this maissen)
	@GET
	@Path(value = "search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll(@QueryParam(value = "search") String search) {
		List<SearchCriteria> params = new ArrayList<SearchCriteria>();
		if (search != null) {
			Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
			Matcher matcher = pattern.matcher(search + ",");
			while (matcher.find()) {
				params.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
			}
		}
		return Response.ok(ARTWORK.searchArtWork(params)).build();
	}
	// add an artwork
	@POST
	@JWTTokenNeeded(role="user")
	@Path(value = "add")
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(ArtWork art,@HeaderParam("AUTHORIZATION") String token ) {
		Artist artist=  new Artist();
		String s = "maissen";
		// Extract the token from the HTTP Authorization header
		token =token.substring("Bearer".length() + 1).trim();
		String id=Jwts.parser().setSigningKey(s).parseClaimsJws(token).getBody().getId();
		artist.setId(UserService.FindUserById(Integer.valueOf(id)).getId());
	    art.setArtist(artist);
		if (ARTWORK.create(art) == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		// return Response.status(Response.Status.OK).build();
		return Response.ok().build();

	}
	// delete art work
	@DELETE
	@Path(value = "remove/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response remove(@PathParam("id") int id) {
		ArtWork newArtWork = new ArtWork();
		newArtWork.setId(id);
		System.out.println(newArtWork.toString());
		if (ARTWORK.delete(newArtWork) == false) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		// return Response.status(Response.Status.OK).build();
		return Response.ok().build();

	}
	@POST
	@Path(value = "update")
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@FormParam("id") int id, @FormParam("title") String title,
			@FormParam("descreption") String descreption, @FormParam("price") float price,
			@FormParam("category") ArtWorkCategory category) {
		ArtWork newArtWork = new ArtWork();
		newArtWork.setId(id);
		newArtWork.setTitle(title);
		newArtWork.setDescreption(descreption);
		newArtWork.setCategory(category);
		newArtWork.setPrice(price);
		System.out.println(newArtWork.toString());
		if (ARTWORK.update(newArtWork) == false) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		// return Response.status(Response.Status.OK).build();
		return Response.ok().build();
	}
}
