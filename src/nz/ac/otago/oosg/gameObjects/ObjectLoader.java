package nz.ac.otago.oosg.gameObjects;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import java.util.HashMap;

public class ObjectLoader {
    // information for each object type
    private static HashMap<String, GameObject> objects = null;
            
    private static String lastLoadedFile = null;
    
    /* Loads all the definitions for planet names; should be loaded
     * from a file!
     */
    public static void loadObjects(AssetManager assetManager, String file, 
            boolean reloadIfFileAlreadyLoaded) {
        /* NOTE: This should load these from a file! */
        
        // if the last time we loaded a file it was this one and
        // we don't want to reload the file, don't do anything
        if (lastLoadedFile != null && 
                lastLoadedFile.equals(file) &&
                !reloadIfFileAlreadyLoaded
                ) {
            return;
        }
        
        // not sure what best object for this is        
        objects = new HashMap<String, GameObject>();
        lastLoadedFile = file;
        
        /* We should be loading this from a file!!! */
        
        // Our planet definitionss
        GameObject def   = new GameObject(20, 30, 1f,   0);
        GameObject earth = new GameObject(20, 30, 1f,   0);
        GameObject sun   = new GameObject(20, 30, 2f, 100);
        GameObject mars  = new GameObject(20, 30, 1f,   0);
        GameObject venus = new GameObject(20, 30, 1f,   0);
        
        GameObject player = new GameObject(20, 30, 0.1f, 0);
        
        // materials for the definitions
        def.createMaterial(assetManager, ColorRGBA.White);
        earth.createMaterial(assetManager, ColorRGBA.Blue);
        sun.createMaterial(assetManager, ColorRGBA.Orange);
        mars.createMaterial(assetManager, ColorRGBA.Red);
        venus.createMaterial(assetManager, 
                new ColorRGBA(1.0f, 1.0f, 0.5f, 1.0f));    
        player.createMaterial(assetManager, ColorRGBA.Cyan);
        
        // add type names so we can display them in the HUD
        def.setObjectType("Default");
        earth.setObjectType("Earth");
        mars.setObjectType("Mars");
        venus.setObjectType("Venus");
        sun.setObjectType("Sun");
                
        // add definitions to the hashmap; make sure names are lowercase!
        objects.put("earth", earth);
        objects.put("sun", sun);
        objects.put("mars", mars);
        objects.put("venus", venus);
        objects.put("default", def);
        objects.put("player", player);
    }
    
    /* Get the GameObject associated with the name provided */
    public static GameObject getObject(String objectName) {
        if (objects == null) {
            System.err.println("ObjectLoader has not loaded objects!");
            return null;
        }
        
        objectName = objectName.toLowerCase();
        
        if (!objects.containsKey(objectName)) {
            return null;
        }
        
        return objects.get(objectName);
    }
}
