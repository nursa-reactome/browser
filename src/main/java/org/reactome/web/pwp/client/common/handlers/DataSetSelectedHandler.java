package org.reactome.web.pwp.client.common.handlers;

import org.reactome.web.pwp.client.common.events.DataSetSelectedEvent;

import com.google.gwt.event.shared.EventHandler;

/**
 * @author Fred Loney <loneyf@ohsu.edu>
 */
public interface DataSetSelectedHandler extends EventHandler {

    void onDataSetSelected(DataSetSelectedEvent datasetSelectedEvent);

}
