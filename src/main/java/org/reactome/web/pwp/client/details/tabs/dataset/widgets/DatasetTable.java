package org.reactome.web.pwp.client.details.tabs.dataset.widgets;

import com.google.gwt.user.client.ui.VerticalPanel;
import org.reactome.web.pwp.nursa.model.client.classes.Dataset;

/**
 * @author Fred Loney <loneyf@ohsu.edu>
 */
public class DatasetTable extends VerticalPanel {
	public DatasetTable(Dataset dataset) {
		add(DatasetRowFactory.getOverviewRow(dataset));
		add(DatasetRowFactory.getComparisonRow(dataset));
		add(DatasetRowFactory.getGenesRow(dataset));
		add(DatasetRowFactory.getPathwayRow(dataset));
	}
}
