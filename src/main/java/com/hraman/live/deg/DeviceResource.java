package com.hraman.live.deg;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/device-event/{id}")
@Consumes(MediaType.APPLICATION_JSON)
public class DeviceResource {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @POST
    public void deviceEvent(@PathParam("id") String id, String event) {
        log.info(event);
    }
}
