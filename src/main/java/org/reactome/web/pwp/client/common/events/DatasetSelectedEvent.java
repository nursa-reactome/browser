package org.reactome.web.pwp.client.common.events;

import org.reactome.web.pwp.client.common.handlers.DatasetSelectedHandler;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Fred Loney <loneyf@ohsu.edu>
 */
public class DatasetSelectedEvent extends GwtEvent<DatasetSelectedHandler> {
    public static Type<DatasetSelectedHandler> TYPE = new Type<>();
    
    private String datasetId;

    public DatasetSelectedEvent(String datasetId) {
        this.datasetId = datasetId;
    }

    public String getDatasetId() {
        return datasetId;
    }

    @Override
    public Type<DatasetSelectedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    public void dispatch(DatasetSelectedHandler handler) {
        handler.onDatasetSelected(this);
    }
}
