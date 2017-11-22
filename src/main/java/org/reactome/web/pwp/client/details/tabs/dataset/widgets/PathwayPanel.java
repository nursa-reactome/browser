package org.reactome.web.pwp.client.details.tabs.dataset.widgets;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.reactome.gsea.model.AnalysisResult;
import org.reactome.nursa.model.DataPoint;
import org.reactome.nursa.model.DataSet;
import org.reactome.web.analysis.client.AnalysisClient;
import org.reactome.web.analysis.client.AnalysisHandler;
import org.reactome.web.analysis.client.model.AnalysisError;
import org.reactome.web.pwp.client.details.tabs.dataset.GseaClient;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PathwayPanel extends VerticalPanel {

    final String GENE_NAMES_HEADER = "#Gene names";

    private DataSet dataset;
    Panel configPanel;
    private SimplePanel analysisPanel;

    private GseaConfigSlider gseaConfigSlider;

    public PathwayPanel(DataSet dataset) {
        this.dataset = dataset;
        // The configuration control panel.
        configPanel = buildConfigPanel();
        add(configPanel);
        // The analysis display panel.
        analysisPanel = new SimplePanel();
        add(analysisPanel);
    }

    protected void gseaAnalyse() {
        GseaClient client = GWT.create(GseaClient.class);
        // Transform the data points into the GSEA REST PUT
        // payload using the Java8 list comprehension idiom.
        List<List<String>> rankedList =
                this.dataset.getDataPoints()
                    .stream()
                    .map(PathwayPanel::pullRank)
                    .collect(Collectors.toList());
        // Obtain the dataset size parameters.
        int[] dataSetBounds = gseaConfigSlider.getValues();
        Integer dataSetSizeMinOpt = new Integer(dataSetBounds[0]);
        Integer dataSetSizeMaxOpt = new Integer(dataSetBounds[1]);
        // Call the GSEA REST service.
        client.analyse(rankedList, dataSetSizeMinOpt, dataSetSizeMaxOpt, new MethodCallback<List<AnalysisResult>>() {
        
            @Override
            public void onSuccess(Method method, List<AnalysisResult> result) {
                 showGseaResult(result);
             }
             
             @Override
             public void onFailure(Method method, Throwable exception) {
                 try {
                     throw new IOException("GSEA execution unsuccessful", exception);
                 } catch (IOException e) {
                     // TODO - how are I/O errors handled in Reactome?
                     throw new RuntimeException(e);
                 }
             }
         });
    }

    protected void showGseaResult(List<AnalysisResult> result) {
        Widget table = GseaResultTableFactory.getTable(result);
        analysisPanel.setWidget(table);
    }

    protected void binomialAnalyse() {
        // The input is a table of gene symbol lines.
        List<String> rankedList =
                this.dataset.getDataPoints()
                    .stream()
                    .map(dp -> dp.getSymbol())
                    .collect(Collectors.toList());
        rankedList.add(0, GENE_NAMES_HEADER);
        String data = String.join("\n", rankedList);
        AnalysisClient.analyseData(data, true, false, 0, 0, new AnalysisHandler.Result() {
            @Override
            public void onAnalysisServerException(String s) {
                // TODO
            }

            @Override
            public void onAnalysisResult(org.reactome.web.analysis.client.model.AnalysisResult result, long time) {
                showBinomialResult(result);
            }

            @Override
            public void onAnalysisError(AnalysisError analysisError) {
                // TODO
            }

        });
    }

    protected void showBinomialResult(org.reactome.web.analysis.client.model.AnalysisResult result) {
        Widget table = BinomialResultTableFactory.getTable(result);
        analysisPanel.setWidget(table);
    }
    
    /**
     * Pulls the gene symbol and FC from the given data point.
     * The FC is formatted as a string for the REST interface.
     * 
     * @param dataPoint the input data point.
     * @return the [symbol, FC] list.
     */
    private static List<String> pullRank(DataPoint dataPoint) {
        return Arrays.asList(
                dataPoint.getSymbol(),
                Double.toString(dataPoint.getFoldChange())
        );
    }

    private Panel buildConfigPanel() {
        RadioButton gseaBtn = new RadioButton("technique", "GSEA");
        RadioButton binomialBtn = new RadioButton("technique", "Binomial");
        gseaBtn.setValue(true);
        Button launchBtn = new Button("Launch");        
        launchBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (gseaBtn.getValue()) {
                    gseaAnalyse();
                } else {
                    binomialAnalyse();
                }
            }
        });
        Image img = new Image("static/images/gear.png");
        img.setPixelSize(16, 16);
        final Button configBtn = new Button();
        configBtn.getElement().appendChild(img.getElement());
        configBtn.getElement().getStyle().setPadding(1, Unit.PX);
        configBtn.getElement().getStyle().setPaddingTop(3, Unit.PX);
        gseaConfigSlider = new GseaConfigSlider();
        final Panel sliderPanel = new SimplePanel();
        sliderPanel.addStyleName("gsea-config");
        sliderPanel.getElement().appendChild(gseaConfigSlider.getElement());
        sliderPanel.setVisible(false);
        configBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                sliderPanel.setVisible(true);
                configBtn.setEnabled(false);
            }
        });

        // Assemble the panel.
        HorizontalPanel configPanel = new HorizontalPanel();
        configPanel.add(gseaBtn);
        configPanel.add(binomialBtn);
        configPanel.add(launchBtn);
        configPanel.add(configBtn);
        configPanel.add(sliderPanel);
        
        return configPanel;
    }
 }
