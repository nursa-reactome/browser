package org.reactome.web.pwp.client.toppanel.dataset;

import com.google.gwt.event.shared.EventBus;

import org.reactome.web.pwp.client.common.events.DataSetSelectedEvent;

/**
 * @author Fred Loney <loneyf@ohsu.edu>
 */
public class DataSetSelectorPresenter implements DataSetSelector.Presenter {

    private DataSetSelector.Display display;
    private EventBus eventBus;

    public DataSetSelectorPresenter(EventBus eventBus, DataSetSelector.Display display) {
        this.eventBus = eventBus;
        this.display = display;
        this.display.setPresenter(this);
    }

    @Override
    public void search() {
        SearchDialog dialog = new SearchDialog();
        dialog.show();
        String doi = "10.1621/gTqItVnDEP";
        DataSetSelectedEvent event = new DataSetSelectedEvent(doi);
        eventBus.fireEventFromSource(event, this);
    }
}
