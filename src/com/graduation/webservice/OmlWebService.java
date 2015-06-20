package com.graduation.webservice;

import com.graduation.model.OmlElement;
import com.graduation.service.IOmlElementService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OmlWebService implements IOmlWebService {

    private IOmlElementService omlElementService;


    @Override
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_XML)
    public List<OmlElement> getData(@QueryParam("ra") String ra,
                                    @QueryParam("dec") String dec, @QueryParam("snu") String snu) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        if (ra != null && !"".equals(ra.trim()))
            parameters.put("ra", ra);
        if (dec != null && !"".equals(dec.trim()))
            parameters.put("dec", dec);
        if (snu != null && !"".equals(snu.trim()))
            parameters.put("snu", snu);
        List<OmlElement> ls = this.omlElementService.list(parameters);
        return ls;
    }


    public void setOmlElementService(IOmlElementService omlElementService) {
        this.omlElementService = omlElementService;
    }


    @Override
    @GET
    @Path("/test")
    @Produces("application/xml")
    public String test() {
        return "test";
    }


}
