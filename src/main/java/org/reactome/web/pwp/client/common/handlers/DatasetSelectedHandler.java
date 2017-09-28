package org.reactome.web.pwp.client.common.handlers;

import org.reactome.web.pwp.client.common.events.DatasetSelectedEvent;

import com.google.gwt.event.shared.EventHandler;

/**
 * @author Fred Loney <loneyf@ohsu.edu>
 */
public interface DatasetSelectedHandler extends EventHandler {

    void onDatasetSelected(DatasetSelectedEvent datasetSelectedEvent);

}
