package nz.ac.otago.oosg.support;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import java.util.HashMap;
import nz.ac.otago.oosg.gameObjects.GameObject;

public class ObjectLoader {
    // information for each object type
    private static HashMap<String, GameObject> objects = null;
            
    // the last file we loaded; used to avoid reloading files that we don't want to reload
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
        
        /* Steps to adding a new definition
         * 1. Create a new GameObject
         * 2. Give GameObject a material, e.g. ise createMaterial() method
         * 3. Optional: Give definition a name for HUD text, e.g. Earth
         * 4. Add GameObject to definitions list
         */
        
        /* Planet definitions */
        
        // default planet
        GameObject def   = new GameObject(20, 30, 1f,   0);
        def.createMaterial(assetManager, ColorRGBA.White);
        def.setObjectType("Default");
        objects.put("default", def);
        
        // earth        
        GameObject earth = new GameObject(20, 30, 1f,   0);        
        earth.createMaterial(assetManager, ColorRGBA.Blue);
        earth.setObjectType("Earth");
        objects.put("earth", earth);
        
        // mars
        GameObject mars  = new GameObject(20, 30, 1f,   0);
        mars.createMaterial(assetManager, ColorRGBA.Red);
        mars.setObjectType("Mars");
        objects.put("mars", mars);
        
        GameObject venus = new GameObject(20, 30, 1f,   0);
        venus.createMaterial(assetManager, 
                new ColorRGBA(1.0f, 1.0f, 0.5f, 1.0f));    
        venus.setObjectType("Venus");
        objects.put("venus", venus);
      
        /* Sun definitions */
        
        // default sun
        GameObject defsun = new GameObject(20, 30, 2f, 2);
        defsun.createMaterial(assetManager, ColorRGBA.Orange);
        defsun.setObjectType("DefaultSun");
        objects.put("defaultsun", defsun);

        /* Player definition */
        
        // player
        GameObject player = new GameObject(20, 30, 0.1f, 0);        
        player.createMaterial(assetManager, ColorRGBA.Cyan);
        objects.put("player", player);        
    }
    
    /* Get the GameObject associated with the name provided */
    public static GameObject getObject(String objectName) {
        if (objects == null) {
            System.err.println("ObjectLoader has not loaded objects!");
            return null;
        }
        
        // make sure we get the object, no matter what capitalisation has been used
        objectName = objectName.toLowerCase();
        
        // if we don't have the desired definition, return nothing
        if (!objects.containsKey(objectName)) {
            return null;
        }
        
        return objects.get(objectName);
    }
}
