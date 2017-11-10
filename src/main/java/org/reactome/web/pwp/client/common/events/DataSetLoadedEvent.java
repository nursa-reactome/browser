package org.reactome.web.pwp.client.common.events;

import org.reactome.web.pwp.client.common.handlers.DataSetLoadedHandler;
import org.reactome.nursa.model.DataSet;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Fred Loney <loneyf@ohsu.edu>
 */
public class DataSetLoadedEvent extends GwtEvent<DataSetLoadedHandler> {
    public static Type<DataSetLoadedHandler> TYPE = new Type<>();
    
    private DataSet dataset;

    public DataSetLoadedEvent(DataSet dataset) {
        this.dataset = dataset;
    }

    public DataSet getDataSet() {
        return dataset;
    }

    @Override
    public Type<DataSetLoadedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(DataSetLoadedHandler handler) {
        handler.onDataSetLoaded(this);
    }
}
