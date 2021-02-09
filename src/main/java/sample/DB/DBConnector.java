package sample.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {

  //Создаем БД
  public static boolean createDB() {

    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/java/sample/DB/Accounts.s3db")) { //схема БД - testBD.s3db
      Statement statement = conn.createStatement();
      //логин должен быть уникальным
      return statement.execute("CREATE TABLE IF NOT EXISTS 'users' (\n" +
              "\t 'login' VARCHAR(500) PRIMARY KEY NOT NULL COLLATE NOCASE,\n" +
              "\t 'password' VARCHAR(500) NOT NULL COLLATE NOCASE,\n" +
              "\t 'nick' VARCHAR(500) NOT NULL COLLATE NOCASE\n" +
              ");\n");
    } catch (SQLException throwables) {
      throwables.printStackTrace();
      return false;
    }

  }


}
