package sample.DB;

import java.sql.*;

public class DBConnector {
  public static final String TABLE_NAME = "users";

  private static Connection conn;
  private static Statement statement;

  public static void connectToDB() {
    try { //схема БД - testBD.s3db
      conn = DriverManager.getConnection("jdbc:sqlite:src/main/java/sample/DB/Accounts.s3db");
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  //Создаем БД
  public static boolean createDB() {
    connectToDB();
    try {
      statement = conn.createStatement();
      statement.execute("CREATE TABLE IF NOT EXISTS '" + TABLE_NAME + "' (\n" +
              "\t 'login' VARCHAR(500) PRIMARY KEY NOT NULL COLLATE NOCASE,\n" +
              "\t 'password' VARCHAR(500) NOT NULL COLLATE NOCASE,\n" +
              "\t 'nick' VARCHAR(500) NOT NULL COLLATE NOCASE\n" +
              ");\n");

      //проверяю что таблица создана
      DatabaseMetaData dbm = conn.getMetaData();
      ResultSet databases = dbm.getTables(null, null, "%", null);

      while (databases.next()) {
        String databaseName = databases.getString(3);
        if (databaseName.equalsIgnoreCase(TABLE_NAME)) {
          statement.close();
          return true;
        }
      }
      return false;
    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return false;
    }

  }

  public static void createNewUser(String login, String password, String nick) {
    connectToDB();
    try {
      PreparedStatement preparedStatement = conn.prepareStatement("insert into "+TABLE_NAME+" (login,password,nick) values(?,?,?)");
      //preparedStatement.setString(1, TABLE_NAME);
      preparedStatement.setString(1, login);
      preparedStatement.setString(2, password);
      preparedStatement.setString(3, nick);
      preparedStatement.execute();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

  }

}
