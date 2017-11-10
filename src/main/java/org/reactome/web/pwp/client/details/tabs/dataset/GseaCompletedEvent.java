package org.reactome.web.pwp.client.details.tabs.dataset;

import java.util.List;

import org.reactome.gsea.model.AnalysisResult;
import org.reactome.nursa.model.DataSet;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Fred Loney <loneyf@ohsu.edu>
 */
public class GseaCompletedEvent extends GwtEvent<GseaCompletedHandler> {
    public static Type<GseaCompletedHandler> TYPE = new Type<>();
    
    private List<AnalysisResult> result;

    public GseaCompletedEvent(List<AnalysisResult> result) {
        this.result = result;
    }

    public List<AnalysisResult> getResult() {
        return result;
    }

    @Override
    public Type<GseaCompletedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(GseaCompletedHandler handler) {
        handler.onCompleted(this);
    }
}
