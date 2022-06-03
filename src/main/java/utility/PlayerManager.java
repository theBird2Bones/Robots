package utility;

import lombok.Getter;
import objects.entities.Player;
import objects.entities.PurplePlayer;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarFile;

public class PlayerManager extends ClassLoader {
  @Getter private Player player;
  @Getter
  private static final String redPlayerJar = "src/main/java/objects/entities/RedPlayer.jar";
  @Getter
  private static final String purplePlayerJar = "src/main/java/objects/entities/PurplePlayer.jar";

  public PlayerManager() {
    this.player = loadPlayerInstanceFromJar(purplePlayerJar);
  }

  public void changePlayerWith(String jarPath){
    Player newPlayer = loadPlayerInstanceFromJar(jarPath);
    player.syncWith(newPlayer);
    player = newPlayer;
  }

  private Player loadPlayerInstanceFromJar(String jarPath) {
    try (JarFile jar = new JarFile(jarPath)) {
      URLClassLoader child =
          new URLClassLoader(
              new URL[] {new URL("jar:file:%s!/".formatted(jar.getName()))},
              this.getClass().getClassLoader());
      var enumerations = jar.entries();
      while(enumerations.hasMoreElements()){
        var entry = enumerations.nextElement();
        if(entry.isDirectory() || !entry.getName().endsWith(".class")){
          continue;
        }

        var className = entry.getName().replace(".class", "").replaceAll("/",".");
        Class playerClass = Class.forName("objects.entities." + className, true, child);
        return (Player) playerClass.newInstance();
      }
      return null;
    } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }
}
