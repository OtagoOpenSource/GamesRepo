package nz.ac.otago.oosg.gameObjects;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import java.util.HashMap;

public class ObjectLoader {
    private HashMap<String, GameObject> objects;
    
    public ObjectLoader(AssetManager assetManager) {
        loadObjects(assetManager);
    }
    
    private void loadObjects(AssetManager assetManager) {
        /* NOTE: This should load these from a file! */
        
        // not sure what best object for this is
        objects = new HashMap<String, GameObject>();
        
        /* We should be loading this from a file! */
        
        GameObject def   = new GameObject(20, 30, 1f, 0);
        GameObject earth = new GameObject(20, 30, 1f, 0);
        GameObject sun   = new GameObject(20, 30, 2f, 0);
        GameObject mars  = new GameObject(20, 30, 1f, 0);
        GameObject venus = new GameObject(20, 30, 1f, 0);
        
        def.createMaterial(assetManager, ColorRGBA.White);
        earth.createMaterial(assetManager, ColorRGBA.Blue);
        sun.createMaterial(assetManager, ColorRGBA.Orange);
        mars.createMaterial(assetManager, ColorRGBA.Red);
        venus.createMaterial(assetManager, 
                new ColorRGBA(1.0f, 1.0f, 0.5f, 1.0f));    
        
        def.setObjectType("Default");
        earth.setObjectType("Earth");
        mars.setObjectType("Mars");
        venus.setObjectType("Venus");
        sun.setObjectType("Sun");
        
        objects.put("earth", earth);
        objects.put("sun", sun);
        objects.put("mars", mars);
        objects.put("venus", venus);
        objects.put("default", def);
    }
    
    public GameObject getObject(String objectName) {
        objectName = objectName.toLowerCase();
        
        if (!objects.containsKey(objectName)) {
            return null;
        }
        
        return objects.get(objectName);
    }
}
