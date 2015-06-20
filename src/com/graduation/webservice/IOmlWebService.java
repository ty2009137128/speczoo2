package com.graduation.webservice;

import com.graduation.model.OmlElement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/oml")
public interface IOmlWebService {

    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_XML)
    public String test();


    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_XML)
    public List<OmlElement> getData(@QueryParam("ra") String ra, @QueryParam("dec") String dec, @QueryParam("snu") String snu);

}
