package org.reactome.web.pwp.client.details.tabs.downloads.widgets;

import com.google.gwt.user.client.Window;
import org.reactome.web.pwp.client.details.common.widgets.button.CustomButton;
import org.reactome.web.pwp.model.client.classes.DatabaseObject;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class DownloadPanel extends CustomButton {

    public DownloadPanel(final String dbName, final DownloadType type, final DatabaseObject databaseObject){
        super();
        this.addStyleName("elv-Download-Item");
        this.setWidth("100px");
        this.setText(type.getName());
        this.setResource(type.getIcon());
        this.addClickHandler(clickEvent -> Window.open(type.getUrl(dbName, databaseObject.getDbId()), "_blank", ""));
        this.setTitle("View/download in " + type.getTooltip() + " format");
    }

}
