package org.reactome.web.pwp.client.details.tabs.dataset;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;
import org.reactome.nursa.model.DataSet;

@Path("/Nursa")
public interface NursaClient extends RestService {
    @GET
    @Path("/dataset")
    public void getDataset(@QueryParam("doi") String doi, MethodCallback<DataSet> dataset);
}
