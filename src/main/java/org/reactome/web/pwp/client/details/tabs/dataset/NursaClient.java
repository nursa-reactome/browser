package org.reactome.web.pwp.client.details.tabs.dataset;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;
import org.reactome.web.pwp.nursa.model.DataSet;

@Path("/NursaContent")
public interface NursaContentClient extends RestService {
    @GET
    @Path("/dataset")
    public void getDataset(@QueryParam("doi") String doi, MethodCallback<DataSet> dataset);
}
