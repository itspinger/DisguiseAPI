package net.pinger.disguise.http.request;

import net.pinger.disguise.http.HttpRequest;
import net.pinger.disguise.http.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class HttpPostRequest extends HttpRequest {

    public HttpPostRequest(String url) throws IOException {
        super(url);
        this.connection.setRequestMethod("POST");
    }

    @Override
    public HttpResponse connect() throws IOException {
        // Connect to the url
        this.connection.connect();

        try (Reader stream = new InputStreamReader(this.connection.getInputStream())
             ; BufferedReader in = new BufferedReader(stream)) {

            // Get the response
            StringBuilder response = new StringBuilder();
            String line;

            // Do loop until empty line
            while ((line = in.readLine()) != null) {
                response.append(line);
            }

            // Return the response
            return new HttpResponse(this.url, response.toString(), this.connection.getResponseCode());
        }
    }
}
