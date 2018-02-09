package org.reactome.web.pwp.client.details.tabs.dataset;

import com.google.gwt.event.shared.EventBus;

/**
 * @author Fred Loney <loneyf@ohsu.edu>
 */
public class GseaPresenter implements DataSetAnalysis.Presenter {

    private DataSetAnalysis.Display display;

    public GseaPresenter(EventBus eventBus, DataSetAnalysis.Display display) {
        this.display = display;
        this.display.setPresenter(this);
    }
}
