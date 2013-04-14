package nz.ac.otago.oosg.gameObjects;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;

public class Sun extends Celestial {
    public Sun(String name, GameObject obj, Vector3f position, BitmapFont bitmapFont,
            Node rootNode, AssetManager assetManager) {
        
        super(name, obj, position, bitmapFont);   
        
        //add 'flare' particle effect around sun
        rootNode.attachChild(assetManager.loadModel("Scenes/sunFlare.j3o"));
    }
    
    public Sun(String name, Mesh mesh, float size, float mass, 
                  Vector3f position, BitmapFont bitmapFont,
                  Node rootNode, AssetManager assetManager) {
        super(name, mesh, size, mass, position, bitmapFont);        
        
        //add 'flare' particle effect around sun
        rootNode.attachChild(assetManager.loadModel("Scenes/sunFlare.j3o"));
    }
}
