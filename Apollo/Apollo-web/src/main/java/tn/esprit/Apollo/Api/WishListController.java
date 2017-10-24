package tn.esprit.Apollo.Api;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import tn.esprit.Apollo.services.UserService;
import tn.esprit.Apollo.services.WishListService;

@Path(value="wishlist")
public class WishListController {
	@EJB
	WishListService wishListService;
	@EJB
	UserService userService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getWishList(){
		return Response.status(Status.OK).entity(wishListService.getWishList(userService.FindUserById(1))).build();
	}
	
	
	@POST
	@Path(value="{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addItem(@PathParam("id")int itemId) {
		wishListService.addItem(itemId,userService.FindUserById(1));
		return Response.status(Status.OK).build();
	}
	
	@DELETE
	@Path(value="{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteItem(@PathParam("id") int id) {
		wishListService.deleteItem(id,userService.FindUserById(1));
		return Response.status(Status.OK).build();
	}
}
