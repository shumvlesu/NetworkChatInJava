package sample;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer {
  public static final int PORT = 8081;
  private List<ClientHandler> clients;

  public MyServer() {
    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      clients = new ArrayList<>();
      while (true) {
        System.out.println("Ожидаем подключение клиентов:");
        Socket socket = serverSocket.accept();
        System.out.println("Клиент подключился");
        //new ClientHandler(this, socket);
        //клиент подключился, содаем ClientHandler и добавляем его в список подключенных клиентов
        clients.add(new ClientHandler(this, socket));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  //Отправка сообщений всем доступным клиентам
  public synchronized void broadcastMessage(Message message) {
    for (ClientHandler client : clients) {
      client.sendMessage(message);
    }
  }
}
