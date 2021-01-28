package sample.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer {
  public static final int PORT = 8081;
  private List<ClientHandler> clients;
  private AuthService authService;

  public MyServer() {
    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      authService = new BaseAuthService();
      //это метод класса а не потока
      authService.start();

      clients = new ArrayList<>();
      while (true) {
        System.out.println("Ожидаем подключение клиентов:");
        Socket socket = serverSocket.accept();
        System.out.println("Клиент подключился");
        //new ClientHandler(this, socket);
        //клиент подключился, содаем ClientHandler
        new ClientHandler(this, socket);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      //это метод класса а не потока
      if (authService != null) {
        authService.stop();
      }
    }
  }





  //Отправка сообщений всем доступным клиентам
  public synchronized void broadcastMessage(Message message) {
    for (ClientHandler client : clients) {
      client.sendMessage(message);
    }
  }

  public synchronized boolean isNickBusy(String nick) {
    for (ClientHandler client : clients) {
      if (nick.equals(client.getNick())) {
        return true;
      }
    }
    return false;
  }

  public AuthService getAuthService() {
    return authService;
  }

  //клиент входит в чат, заносим его в список
  public synchronized void subscribe(ClientHandler clientHandler) {
    clients.add(clientHandler);
  }

  //клиент выходит из чата
  public synchronized void unsubscribe(ClientHandler clientHandler) {
    clients.remove(clientHandler);
  }

}
