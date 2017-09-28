package org.reactome.web.pwp.client.common.events;

import org.reactome.web.pwp.client.common.handlers.DatasetLoadedHandler;
import org.reactome.web.pwp.nursa.model.client.classes.Dataset;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Fred Loney <loneyf@ohsu.edu>
 */
public class DatasetLoadedEvent extends GwtEvent<DatasetLoadedHandler> {
    public static Type<DatasetLoadedHandler> TYPE = new Type<>();
    
    private Dataset dataset;

    public DatasetLoadedEvent(Dataset dataset) {
        this.dataset = dataset;
    }

    public Dataset getDataset() {
        return dataset;
    }

    @Override
    public Type<DatasetLoadedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(DatasetLoadedHandler handler) {
        handler.onDatasetLoaded(this);
    }
}
