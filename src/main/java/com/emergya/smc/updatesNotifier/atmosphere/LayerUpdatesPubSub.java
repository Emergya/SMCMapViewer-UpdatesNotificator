/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.emergya.smc.updatesNotifier.atmosphere;

import java.util.Date;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.atmosphere.annotation.Broadcast;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.jersey.Broadcastable;
import org.atmosphere.jersey.SuspendResponse;

/**
 * Simple PubSub resource that demonstrate many functionality supported by
 * Atmosphere Javascript and Atmosphere Jersey extension.
 *
 * @author Jeanfrancois Arcand
 */
@Path("/layerUpdates/{layerIdentifier}")
public class LayerUpdatesPubSub {

    private
    @PathParam("layerIdentifier")
    Broadcaster layerUpdatesBroadcaster;

    @GET
    public SuspendResponse<String> subscribe() {
        return new SuspendResponse.SuspendResponseBuilder<String>()
                .broadcaster(layerUpdatesBroadcaster)
                .outputComments(true)
                .addListener(new EventsLogger())
                .build();
    }

    @POST
    @Broadcast
    @Produces("text/html;charset=ISO-8859-1")
    public Broadcastable notifyUpdate() {
        return new Broadcastable((new Date()).toString(), "", layerUpdatesBroadcaster);
    }
}
