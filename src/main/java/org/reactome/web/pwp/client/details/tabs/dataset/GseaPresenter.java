package org.reactome.web.pwp.client.details.tabs.dataset;

import java.util.List;
import org.reactome.gsea.model.AnalysisResult;
import com.google.gwt.event.shared.EventBus;

/**
 * @author Fred Loney <loneyf@ohsu.edu>
 */
public class GseaPresenter implements DataSetAnalysis.Presenter, GseaCompletedHandler {

    private DataSetAnalysis.Display display;

    public GseaPresenter(EventBus eventBus, DataSetAnalysis.Display display) {
        this.display = display;
        this.display.setPresenter(this);
        eventBus.addHandler(GseaCompletedEvent.TYPE, this);
    }

    @Override
    public void onCompleted(GseaCompletedEvent event) {
        List<AnalysisResult> result = event.getResult();
        // TODO - this.display.showGseaResult(result);
    }
}
