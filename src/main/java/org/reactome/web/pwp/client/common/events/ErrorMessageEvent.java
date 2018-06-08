package org.reactome.web.pwp.client.common.events;

import com.google.gwt.event.shared.GwtEvent;
import org.reactome.web.pwp.client.common.handlers.ErrorMessageHandler;

import java.util.Collections;
import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class ErrorMessageEvent extends GwtEvent<ErrorMessageHandler> {
    public static final Type<ErrorMessageHandler> TYPE = new Type<>();

    private List<String> message;
    private Throwable throwable;

    public ErrorMessageEvent(String message) {
        this(Collections.singletonList(message));
    }

    public ErrorMessageEvent(List<String> message) {
        this(message, null);
    }

    public ErrorMessageEvent(String message, Throwable throwable) {
        this(Collections.singletonList(message), throwable);
    }

    public ErrorMessageEvent(List<String> message, Throwable throwable) {
        this.message = message;
        this.throwable = throwable;
    }

    @Override
    public Type<ErrorMessageHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ErrorMessageHandler handler) {
        handler.onInternalMessage(this);
    }

    public String getMessage() {
        StringBuilder rtn = new StringBuilder();
        for (String s : message) {
            rtn.append(s).append("\n");
        }
        return rtn.toString();
    }

    public Throwable getThrowable() {
        return throwable;
    }

    @Override
    public String toString() {
        return "InternalMessageEvent{" +
                "message='" + message + '\'' +
                '}';
    }
}
