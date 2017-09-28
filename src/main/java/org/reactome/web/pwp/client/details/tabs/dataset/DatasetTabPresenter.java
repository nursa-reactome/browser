package org.reactome.web.pwp.client.details.tabs.dataset;

import java.util.ArrayList;
import java.util.List;

import org.reactome.web.pwp.client.common.events.DatasetLoadedEvent;
import org.reactome.web.pwp.client.common.events.DatasetSelectedEvent;
import org.reactome.web.pwp.client.common.events.StateChangedEvent;
import org.reactome.web.pwp.client.common.handlers.DatasetLoadedHandler;
import org.reactome.web.pwp.client.common.handlers.DatasetSelectedHandler;
import org.reactome.web.pwp.nursa.model.client.classes.Dataset;
import org.reactome.web.pwp.nursa.model.client.classes.DatasetGene;
import org.reactome.web.pwp.nursa.model.client.classes.DatasetPathway;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;

/**
 * @author Fred Loney <loneyf@ohsu.edu>
 */
public class DatasetTabPresenter
implements DatasetTab.Presenter, DatasetSelectedHandler, DatasetLoadedHandler {

    private DatasetTab.Display display;
    private EventBus eventBus;

    public DatasetTabPresenter(EventBus eventBus, DatasetTab.Display display) {
        this.eventBus = eventBus;
        this.display = display;
        this.display.setPresenter(this);
        eventBus.addHandler(DatasetSelectedEvent.TYPE, this);
        eventBus.addHandler(DatasetLoadedEvent.TYPE, this);
    }

    @Override
    public void onStateChanged(StateChangedEvent event) {
        // The Dataset tab is insensitive to the object selected in the viewport.
    }

    @Override
    public void onDatasetSelected(DatasetSelectedEvent datasetSelectedEvent) {
        String datasetId = datasetSelectedEvent.getDatasetId();
        this.display.showLoading(datasetId);
        
        Timer t = new Timer() {
            @Override
            public void run() {
                    Dataset dataset = mockDataset();
                    DatasetLoadedEvent event = new DatasetLoadedEvent(dataset);
                    eventBus.fireEventFromSource(event, this);
            }
          };
          t.schedule(2000);
    }

    @Override
    public void onDatasetLoaded(DatasetLoadedEvent datasetLoadedEvent) {
        Dataset dataset = datasetLoadedEvent.getDataset();
        this.display.showDetails(dataset);
    }

    private Dataset mockDataset() {
        Dataset dataset = new Dataset();
        dataset.setDOI("10.1621/mpN8PDaTeM");
        
        List<DatasetGene> genes = new ArrayList<>();
        DatasetGene gene = new DatasetGene();
        gene.setSymbol("RHOU");
        gene.setFoldChange(18.867);
        gene.setPValue(4.29E-8);
        genes.add(gene);
        gene = new DatasetGene();
        gene.setSymbol("SGK1");
        gene.setFoldChange(16.505);
        gene.setPValue(6.59E-8);
        genes.add(gene);
        dataset.setGenes(genes);
        
        List<DatasetPathway> pathways = new ArrayList<>();
        DatasetPathway pathway = new DatasetPathway();
        pathway.setDescription("TP53 Regulates Transcription of Cell Death Genes");
        pathway.setPValue(2.64E-4);
        pathway.setFDR(9.01E-02);
        pathway.setRegulationType(DatasetPathway.RegulationType.UP);
        pathways.add(pathway);
        pathway = new DatasetPathway();
        pathway.setDescription("IRE1alpha activates chaperones");
        pathway.setPValue(1.10E-3);
        pathway.setFDR(1.57E-1);
        pathway.setRegulationType(DatasetPathway.RegulationType.UP);
        pathways.add(pathway);
        dataset.setPathways(pathways);
        
        return dataset;
    }
}
