package org.reactome.web.pwp.client.toppanel.dataset;

import com.google.gwt.event.shared.EventBus;

import org.reactome.web.pwp.client.common.events.DatasetSelectedEvent;

/**
 * @author Fred Loney <loneyf@ohsu.edu>
 */
public class DatasetSelectorPresenter implements DatasetSelector.Presenter {

    private DatasetSelector.Display display;
    private EventBus eventBus;
    
    public DatasetSelectorPresenter(EventBus eventBus, DatasetSelector.Display display) {
        this.eventBus = eventBus;
        this.display = display;
        this.display.setPresenter(this);
    }

    @Override
    public void search() {
        String datasetId = "TODO - get from text box"; 
        DatasetSelectedEvent event = new DatasetSelectedEvent(datasetId);
        eventBus.fireEventFromSource(event, this);
    }
}
