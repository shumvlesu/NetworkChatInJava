package sample;

import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {

  private Socket socket;
  private MyServer myServer;
  private DataInputStream dataInputStream;
  private DataOutputStream dataOutputStream;

  public ClientHandler(MyServer myServer, Socket socket) {

    try {
      this.socket = socket;
      this.myServer = myServer;
      this.dataInputStream = new DataInputStream(socket.getInputStream());
      this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
      //создаем поток и стартуем его
      new Thread(() -> {
        try {
          readMessages();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }).start();

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private void readMessages() throws IOException {
    while (true) {
      Message message = new Gson().fromJson(dataInputStream.readUTF(), Message.class);
      myServer.broadcastMessage(message);
    }
  }

  //метод послыает сообщение клиенту
  public void sendMessage(Message message) {
    try {
      dataOutputStream.writeUTF(new Gson().toJson(message));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
