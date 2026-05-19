package flowly.core;

import java.io.*;

public class MapSerializer {

    public static void exportMapBinary(MapManager manager, String path) {
        
    	try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            System.out.println("Succé de la sauvegarde de la carte dans " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MapManager importMapBinary(String path) {
        MapManager manager = new MapManager();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            System.out.println("succé du chargement de la carte depuis" + path);
        } catch (IOException  e) {
            e.printStackTrace();
        }
        return manager;
    }
}