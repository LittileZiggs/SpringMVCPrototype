package xluo.github.spring.springcrud.util;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by plin on 6/25/15.
 */
public class HttpUtils {
    static final Logger LOG = LoggerFactory.getLogger(HttpUtils.class);

    private static CloseableHttpClient httpClient;
    private static PoolingHttpClientConnectionManager connectionManager;
    private static RequestConfig requestConfig;

    private static final int maxConn = 150;
    private static final int connectionRequestTimeout = 5 * 1000;
    private static final int connectTimeout = 5 * 1000;
    private static final int socketTimeout = 20 * 1000;

    private volatile static HttpUtils instance;

    private HttpUtils() {
        init();
    }

    public static HttpUtils getInstance() {
        if (instance == null) {
            synchronized (HttpUtils.class) {
                if (instance == null) {
                    instance = new HttpUtils();
                }
            }
        }
        return instance;
    }

    private void init() {
        if (this.httpClient != null || this.connectionManager != null) {
            this.close();
        }

        this.connectionManager = new PoolingHttpClientConnectionManager();
        this.connectionManager.setMaxTotal(maxConn * 2);
        connectionManager.setDefaultMaxPerRoute(maxConn);

        this.requestConfig = RequestConfig.custom()
                .setSocketTimeout(socketTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setConnectTimeout(connectTimeout)
                .setStaleConnectionCheckEnabled(true)
                .build();

        this.httpClient = HttpClientBuilder.create()
                .setConnectionManager(this.connectionManager)
                .setDefaultRequestConfig(requestConfig).build();
    }

    private void close() {
        if (this.httpClient != null) {
            try {
                this.httpClient.close();
            } catch (IOException var2) {
                LOG.error("HttpClient close fail." + var2);
            }

            this.httpClient = null;
        }

        if (this.connectionManager != null) {
            this.connectionManager.close();
            this.connectionManager = null;
        }
    }

    public CloseableHttpResponse execute(HttpUriRequest httpUriRequest) throws IOException {
        CloseableHttpClient client = null;
        try {
            client = HttpClientBuilder.create()
                    //.setConnectionManager(this.connectionManager)
                    .setDefaultRequestConfig(requestConfig).build();
            return client.execute(httpUriRequest);
        } finally {
            try {
                //client.close();
            } catch (Exception ignore) {
                LOG.error("close", ignore);
            }
        }
    }
}
