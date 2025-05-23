package org.example.lld.sse;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleSseServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Server started on port 8080");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(() -> {
                try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                    // Set SSE headers
                    out.print("HTTP/1.1 200 OK\r\n");
                    out.print("Content-Type: text/event-stream\r\n");
                    out.print("Cache-Control: no-cache\r\n");
                    out.print("Connection: keep-alive\r\n");
                    out.print("\r\n");
                    out.flush();

                    // Send events
                    for (int i = 0; i < 5; i++) {
                        String data = "data: Event " + i + "\n\n";
                        out.print(data);
                        out.flush();
                        Thread.sleep(2000); // Simulate event interval
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}