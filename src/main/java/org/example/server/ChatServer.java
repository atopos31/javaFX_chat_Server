package org.example.server;

import org.example.database.Database;

import java.io.IOException;

public class ChatServer {
    public static void main(String[] args) throws IOException {
        Database database = new Database();
        database.start();
        Server server = new Server();
        server.start();
    }
}
