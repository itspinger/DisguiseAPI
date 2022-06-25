package net.pinger.disguise.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class HttpRequest {

    protected final String url;
    protected final HttpURLConnection connection;

    protected HttpRequest(String url) throws IOException {
        this.url = url;

        // Create the connection
        this.connection = (HttpURLConnection) new URL(this.url).openConnection();
    }

    public abstract HttpResponse connect() throws IOException;
}
