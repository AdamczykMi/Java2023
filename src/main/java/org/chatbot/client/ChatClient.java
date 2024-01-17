package org.chatbot.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private BufferedReader userInputReader;

    public ChatClient(String address, int port) throws Exception {
        // TODO Zainicjuj połączenie z serwerem chatu
        socket = new Socket(address, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        userInputReader = new BufferedReader(new InputStreamReader(System.in));
        // ...
    }

    public void send(String message) {
        out.println(message);
    }

    public String receive() throws Exception {
        return in.readLine();
    }

    public void close() throws Exception {
        in.close();
        out.close();
        socket.close();
        userInputReader.close();
    }

    public static void main(String[] args) throws Exception {
        ChatClient client = new ChatClient("localhost", 3306);

        System.out.println("Connected to chatbot. Type your messages:");
        // TODO Zaimplementuj pętlę do komunikacji z serwerem
        //  która wczytuje input z konsoli, przesyła do serwera i odbiera odpowiedź
        try {
            while (true) {
                System.out.print("You: ");
                String userInput = client.userInputReader.readLine();
                if (userInput.equalsIgnoreCase("exit")) {
                    break;
                }

                client.send(userInput);

                String response = client.receive();
                System.out.println("Chatbot says: " + response);
            }
        } finally {
            client.close();
        }
    }
}
