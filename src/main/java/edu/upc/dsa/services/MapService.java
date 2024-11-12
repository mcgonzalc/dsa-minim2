package edu.upc.dsa.services;

import edu.upc.dsa.GameManager;
import edu.upc.dsa.GameManagerImpl;
import edu.upc.dsa.models.ElementType;
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

@Api(value = "/map", description = "Endpoint to Map Service")
@Path("/map")
public class MapService {

    private GameManager gm;

    public MapService() {
        this.gm = GameManagerImpl.getInstance();
        if (gm.sizePointsofInterest()==0) {
            this.gm.createPointofInterest(ElementType.DOOR, 564, 98);
            this.gm.createPointofInterest(ElementType.WALL, 45, 752);
            this.gm.createPointofInterest(ElementType.BRIDGE, 14, -456);
        }
    }

    @GET
    @ApiOperation(value = "Get all points of interest of a specific type")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = PointofInterest.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "Points of interest not found")
    })
    @Path("/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPointsofInterest(@PathParam("type") ElementType type) {

        List<PointofInterest> p = this.gm.listPointsofInterest(type);
        if (p == null)
            return Response.status(404).build();
        else
        {
            GenericEntity<List<PointofInterest>> entity = new GenericEntity<List<PointofInterest>>(p) {};
            return Response.status(201).entity(entity).build();
        }
    }

    @POST
    @ApiOperation(value = "create a new point of interest")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = PointofInterest.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newPointofInterest(PointofInterest p) {
        if (p.getType() == null || p.getHorizontal() == null || p.getVertical() == null)
            return Response.status(500).entity(p).build();
        else {
            this.gm.addPointofInterest(p);
            return Response.status(201).entity(p).build();
        }
    }

    @GET
    @ApiOperation(value = "Get all users that have gone through a point of interest")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "Point of interest not found")
    })
    @Path("/history")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersPointsofInterest(@QueryParam("horizontal") int horizontal, @QueryParam("vertical") int vertical) {

        List<User> users = this.gm.listUsersPointofInterest(horizontal, vertical);
        if (users == null)
            return Response.status(404).build();
        else
        {
            GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users) {};
            return Response.status(201).entity(entity).build();
        }
    }
}