package com.hraman.live.deg;


import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class DeviceResource {

    @GET
    public String isAlive(){
        return "I am here!";
    }
}
