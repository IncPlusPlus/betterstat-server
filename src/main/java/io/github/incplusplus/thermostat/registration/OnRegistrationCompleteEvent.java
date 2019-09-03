package io.github.incplusplus.thermostat.registration;

import io.github.incplusplus.thermostat.persistence.model.Client;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@SuppressWarnings("serial")
public class OnRegistrationCompleteEvent extends ApplicationEvent
{

    private final String appUrl;
    private final Locale locale;
    private final Client client;

    public OnRegistrationCompleteEvent(final Client client, final Locale locale, final String appUrl) {
        super(client);
        this.client = client;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    //

    public String getAppUrl() {
        return appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public Client getClient() {
        return client;
    }

}
