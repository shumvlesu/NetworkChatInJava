package sample.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {

  //Создаем БД
  public static boolean createDB() {

    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/sample/DB/Accounts.s3db")) { //схема БД - testBD.s3db
      Statement statement = conn.createStatement();
      return statement.execute("CREATE TABLE IF NOT EXISTS 'accounts' (\n" +
              "\t 'login' VARCHAR(500) PRIMARY KEY NOT NULL COLLATE NOCASE,\n" + //логин должен быть уникальным
              "\t 'password' VARCHAR(500) NOT NULL COLLATE NOCASE,\n" +
              "\t 'nick' VARCHAR(500) NOT NULL COLLATE NOCASE,\n" +
              ");\n");
    } catch (SQLException throwables) {
      return false;
    }

  }







}
