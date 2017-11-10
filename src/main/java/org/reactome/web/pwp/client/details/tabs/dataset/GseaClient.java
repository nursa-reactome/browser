package org.reactome.web.pwp.client.details.tabs.dataset;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;
import org.reactome.gsea.model.AnalysisResult;

@Path("/Gsea")
public interface GseaClient extends RestService {
    @POST
    @Path("/analyse")
    public void analyse(List<List<String>> rankedList, MethodCallback<List<AnalysisResult>> result);
}
