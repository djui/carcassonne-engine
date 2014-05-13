package carcassonne;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import carcassonne.server.Server;

public class Main {

  public static void main(String... args) throws Exception {
    Config conf = ConfigFactory.load();
    new Server(conf.getInt("server.port")).run();
  }

}
