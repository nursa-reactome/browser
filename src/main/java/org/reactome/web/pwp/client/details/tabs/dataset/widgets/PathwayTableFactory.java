package org.reactome.web.pwp.client.details.tabs.dataset.widgets;

import java.util.Comparator;
import java.util.List;

import org.reactome.web.pwp.nursa.model.DataSetPathway;
import org.reactome.web.pwp.nursa.model.DataSet;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

public class PathwayTableFactory {
    private static final NumberCell DECIMAL_CELL = new NumberCell(NumberFormat.getDecimalFormat());
    private static final NumberCell SCIENTIFIC_CELL =new NumberCell(NumberFormat.getFormat("0.00E0"));

    public static Widget getTable(DataSet dataset) {
        // The table content.
        List<DataSetPathway> pathways = dataset.getPathways();
        // The sortable table.
        CellTable<DataSetPathway> table = new CellTable<DataSetPathway>();
        ListDataProvider<DataSetPathway> dataProvider = new ListDataProvider<DataSetPathway>();
        dataProvider.addDataDisplay(table);
        dataProvider.setList(dataset.getPathways());
        ListHandler<DataSetPathway> sorter = new ListHandler<DataSetPathway>(dataProvider.getList());
        table.addColumnSortHandler(sorter);
        // The exact row count.
        table.setRowCount(pathways.size(), true);

        // Define the columns.
        TextColumn<DataSetPathway> nameColumn = new TextColumn<DataSetPathway>() {
            @Override
            public String getValue(DataSetPathway pathway) {
              return pathway.getName();
            }
        };
        nameColumn.setSortable(true);
        sorter.setComparator(nameColumn, new Comparator<DataSetPathway>() {
            @Override
            public int compare(DataSetPathway p1, DataSetPathway p2) {
                return p1.getName().compareTo(p2.getName());
            }
        });
        
        Column<DataSetPathway, Number> pValueColumn = new Column<DataSetPathway, Number>(SCIENTIFIC_CELL) {
            @Override
            public Number getValue(DataSetPathway pathway) {
                return pathway.getPvalue();
            }
        };
        pValueColumn.setSortable(true);
        sorter.setComparator(pValueColumn, new Comparator<DataSetPathway>() {
            @Override
            public int compare(DataSetPathway p1, DataSetPathway p2) {
                return Double.compare(p1.getPvalue(), p2.getPvalue());
            }
        });
        
        Column<DataSetPathway, Number> fdrColumn = new Column<DataSetPathway, Number>(DECIMAL_CELL) {
            @Override
            public Number getValue(DataSetPathway pathway) {
                return pathway.getFdr();
            }
        };
        fdrColumn.setSortable(true);
        sorter.setComparator(fdrColumn, new Comparator<DataSetPathway>() {
            @Override
            public int compare(DataSetPathway p1, DataSetPathway p2) {
                return Double.compare(p1.getFdr(), p2.getFdr());
            }
        });
        
        Column<DataSetPathway, String> regTypeColumn = new TextColumn<DataSetPathway>() {
            @Override
            public String getValue(DataSetPathway pathway) {
                return pathway.getRegulationType().toString();
            }
        };
        regTypeColumn.setSortable(true);
        sorter.setComparator(regTypeColumn, new Comparator<DataSetPathway>() {
            @Override
            public int compare(DataSetPathway p1, DataSetPathway p2) {
                return p1.toString().compareTo(p2.toString());
            }
        });
        
        // Add the columns.
        table.addColumn(nameColumn, "Name");
        table.addColumn(pValueColumn, "P-Value");
        table.addColumn(fdrColumn, "FDR");
        table.addColumn(regTypeColumn, "Regulation Type");
        // Paginate the table.
        SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
        SimplePager pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
        pager.setDisplay(table);
        pager.setPageSize(20);
        // Assemble the widget.
        VerticalPanel vp = new VerticalPanel();
        vp.add(table);
        vp.add(pager);
        // TODO - Center the pager in the panel with a UIBind definition.
        
        return vp;
    }
}
