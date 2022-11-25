package rickandmorty.rickandmortyapp.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

@Component
public class HttpClient {
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final ObjectMapper objectMapper = new ObjectMapper();

    {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public <T> T get(String url, Class<T> clazz) {
        HttpGet request = new HttpGet(url);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            InputStream inputStream = response.getEntity().getContent();
            StringBuilder builder = new StringBuilder();
            for (int ch; (ch = inputStream.read()) != -1; ) {
                if (ch != 10) {
                    builder.append((char) ch);
                }
            }
            return objectMapper.readValue(builder.toString(), clazz);
        } catch (IOException e) {
            throw new RuntimeException("Cannot fetch info from URL:" + url, e);
        }
    }
}
