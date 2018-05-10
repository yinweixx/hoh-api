package com.anyun.cloud.management.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 22/07/2017
 */
@Path("/sys")
public class SystemAPI {

    @Path("/status")
    @Consumes({"application/json"})
    public String getVersion() {
        return null;
    }
}
