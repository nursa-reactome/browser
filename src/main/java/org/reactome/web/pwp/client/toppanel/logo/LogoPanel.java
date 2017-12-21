package org.reactome.web.pwp.client.toppanel.logo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.*;
import org.reactome.web.pwp.client.Browser;
import org.reactome.web.pwp.client.common.CommonImages;
import org.reactome.web.pwp.client.common.utils.Console;
import org.reactome.web.pwp.model.client.common.ContentClientHandler;
import org.reactome.web.pwp.model.client.content.ContentClient;
import org.reactome.web.pwp.model.client.content.ContentClientError;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class LogoPanel extends Composite {

    private SimplePanel releasePanel;

    public LogoPanel() {
        this.releasePanel = new SimplePanel();
        this.releasePanel.setStyleName(RESOURCES.getCSS().dataVersion());

        FlowPanel hp = new FlowPanel();
        hp.setStyleName(RESOURCES.getCSS().logo());

        Image image = new Image(CommonImages.INSTANCE.logo());
        SafeHtml safeHtml = SafeHtmlUtils.fromSafeConstant(image.toString());
        Anchor logo = new Anchor(safeHtml, "/");
        logo.setTitle("Back to homepage");
        hp.add(logo);

        if(Browser.BETA) {
            Image beta = new Image(RESOURCES.beta());
            beta.setStyleName(RESOURCES.getCSS().beta());
            hp.add(beta);
        }

        FlowPanel vp = new FlowPanel();
        vp.setStyleName(RESOURCES.getCSS().versionsPanel());

        vp.add(getBrowserVersionPanel());
        vp.add(this.releasePanel);
        this.releasePanel.add(getReactomeReleasePanel("-", "Please wait while loading..."));

        hp.add(vp);
        initWidget(hp);

        setDataVersion();
    }

    private Widget getBrowserVersionPanel() {
        FlowPanel fp = new FlowPanel();
        fp.add(new Image(RESOURCES.version()));
        fp.add(new InlineLabel(Browser.VERSION));

        SafeHtml safeHtml = SafeHtmlUtils.fromSafeConstant(fp.toString());
        Anchor anchor = new Anchor(safeHtml, "//github.com/reactome-pwp", "_blank");
        anchor.setStyleName(RESOURCES.getCSS().appVersion());
        anchor.setTitle("Pathway Browser version " + Browser.VERSION);
        return anchor;
    }

    private Widget getReactomeReleasePanel(String release, String title) {
        FlowPanel fp = new FlowPanel();
        fp.add(new Image(RESOURCES.release()));
        fp.add(new InlineLabel(release));

        SafeHtml safeHtml = SafeHtmlUtils.fromSafeConstant(fp.toString());
        Anchor anchor = new Anchor(safeHtml, "//www.reactome.org/category/release/", "_blank");
        anchor.setTitle(title);
        return anchor;
    }

    private void setDataVersion() {
        ContentClient.getDatabaseVersion(new ContentClientHandler.Version() {
            @Override
            public void onVersionLoaded(String version) {
                releasePanel.clear();
                releasePanel.add(getReactomeReleasePanel(version, "Reactome database release " + version));
            }

            @Override
            public void onContentClientException(Type type, String message) {
                Console.error(message);
            }

            @Override
            public void onContentClientError(ContentClientError error) {
                Console.error(error.getMessage().toString());
            }
        });
    }


    public static Resources RESOURCES;

    static {
        RESOURCES = GWT.create(Resources.class);
        RESOURCES.getCSS().ensureInjected();
    }

    /**
     * A ClientBundle of resources used by this widget.
     */
    public interface Resources extends ClientBundle {
        /**
         * The styles used in this widget.
         */
        @Source(ResourceCSS.CSS)
        ResourceCSS getCSS();

        @Source("images/release.png")
        ImageResource release();

        @Source("images/version.png")
        ImageResource version();

        @Source("images/beta.png")
        ImageResource beta();
    }

    /**
     * Styles used by this widget.
     */
    @CssResource.ImportedWithPrefix("pwp-ToolLauncher")
    public interface ResourceCSS extends CssResource {
        /**
         * The path to the default CSS styles used by this resource.
         */
        String CSS = "org/reactome/web/pwp/client/toppanel/logo/LogoPanel.css";

        String logo();

        String beta();

        String versionsPanel();

        String appVersion();

        String dataVersion();

    }
}
