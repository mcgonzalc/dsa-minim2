package edu.upc.dsa.services;

import edu.upc.dsa.GameManager;
import edu.upc.dsa.GameManagerImpl;
import edu.upc.dsa.models.PointofInterest;
import edu.upc.dsa.models.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.util.List;

@Api(value = "/user", description = "Endpoint to User Service")
@Path("/user")
public class UsersService {

    private GameManager gm;

    public UsersService() {
        this.gm = GameManagerImpl.getInstance();
        if (gm.sizeUsers()==0) {
            this.gm.createUser(0, "Jonathan", "Yolo", "g@g.com", "04/11/1964");
            this.gm.createUser(1, "Mary", "Pop", "e@e.com", "13/12/1985");
        }
    }

    @GET
    @ApiOperation(value = "Get all Users in alphabetical order")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class, responseContainer="List"),
    })
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        List<User> pilots = this.gm.listUsersDescending();
        GenericEntity<List<User>> entity = new GenericEntity<List<User>>(pilots) {};
        return Response.status(201).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get a User")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") Integer id) {
        User u = this.gm.getUser(id);
        if (u == null) return Response.status(404).build();
        else  return Response.status(201).entity(u).build();
    }

    @POST
    @ApiOperation(value = "create a new User")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })

    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newUser(User user) {
        if (user.getName() == null || user.getSurname() == null || user.getBirthday() == null || user.getEmail() == null)
            return Response.status(500).entity(user).build();
        if (user.getId() == null)
        {
            user.setId(this.gm.sizeUsers());
        }
        this.gm.addUser(user);
        return Response.status(201).entity(user).build();
    }

    @GET
    @ApiOperation(value = "get the list of points of interest that a User has passed through in order")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = PointofInterest.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/{id}/poi")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListofPointsofInterest(@PathParam("id") Integer id) {
        List<PointofInterest> list = this.gm.listUserPointsofInterest(id);
        if (list == null) return Response.status(404).build();
        else
        {
            GenericEntity<List<PointofInterest>> entity = new GenericEntity<List<PointofInterest>>(list) {};
            return Response.status(201).entity(entity).build();
        }
    }

    @PUT
    @ApiOperation(value = "add a point of interest to the history of an User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 404, message = "User/point of interest not found")

    })

    @Path("/{id}/poi")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addListofPointsofInterest(@PathParam("id") Integer id, PointofInterest point)
    {
        if (this.gm.addUserPointofInterest(id, point.getHorizontal(), point.getVertical()) == null)
            return Response.status(404).build();
        else
        {
            return Response.status(200).build();
        }
    }
}