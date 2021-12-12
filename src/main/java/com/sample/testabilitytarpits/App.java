package com.sample.testabilitytarpits;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
        int port = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null);
        server.start();
    }

    static class MyHandler implements HttpHandler {

        public Map<String, String> queryToMap(String query) {
            if (query == null) {
                return null;
            }
            Map<String, String> result = new HashMap<>();
            for (String param : query.split("&")) {
                String[] entry = param.split("=");
                if (entry.length > 1) {
                    result.put(entry[0], entry[1]);
                } else {
                    result.put(entry[0], "");
                }
            }
            return result;
        }

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            Map<String, String> params = queryToMap(
                    httpExchange.getRequestURI().getQuery()
            );
            //String response = "Hello " + params.get("name") + "!";
            StringBuilder htmlResponse = new StringBuilder();
            htmlResponse.append("<html>")
                    .append("<body>")
                    .append("<p>")
                    .append(params.get("name"))
                    .append("</p>")
                    .append("</body>")
                    .append("</html>");
            httpExchange.sendResponseHeaders(200, htmlResponse.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(htmlResponse.toString().getBytes());
            os.close();
        }
    }
}
