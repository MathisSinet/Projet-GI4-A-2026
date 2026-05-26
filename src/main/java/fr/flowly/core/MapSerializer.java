package fr.flowly.core;

import java.io.*;
import fr.flowly.geometry.Point;

public class MapSerializer {

    public static void exportMapBinary(MapManager manager, String path) {
        
    	try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            System.out.println("Succé de la sauvegarde de la carte dans " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MapManager importMapBinary(String path, Point mapCorner, Point mapSize) {
        MapManager manager = new MapManager(mapCorner, mapSize);
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            System.out.println("succé du chargement de la carte depuis" + path);
        } catch (IOException  e) {
            e.printStackTrace();
        }
        return manager;
    }
}