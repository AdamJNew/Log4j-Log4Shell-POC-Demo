import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class VulnerableApp {
    private static final Logger logger = LogManager.getLogger(VulnerableApp.class);

    public static void main(String[] args) throws Exception {
        System.out.println("Vulnerable Java Application is running on port 8080...");

        // Create an HTTP server on port 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Define a handler for incoming requests
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) {
                try {
                    // Log the User-Agent header
                    String userAgent = exchange.getRequestHeaders().getFirst("User-Agent");
                    if (userAgent != null) {
                        logger.info("User-Agent: " + userAgent);
                    }

                    // Send a simple HTTP response
                    String response = "<!DOCTYPE html><html><head><title>Java Backend App</title><body><h1>Welcome to the Backend Overlay</h1><p>This Website is Powered by Log4j!</body></html>";
                    exchange.sendResponseHeaders(200, response.getBytes().length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                } catch (Exception e) {
                    logger.error("Error handling request: ", e);
                }
            }
        });

        // Start the server
        server.start();
    }
}
