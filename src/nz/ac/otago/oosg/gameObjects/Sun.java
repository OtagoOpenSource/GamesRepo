package nz.ac.otago.oosg.gameObjects;

import com.jme3.font.BitmapFont;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;

public class Sun extends Celestial {
    public Sun(String name, GameObject obj, Vector3f position, BitmapFont bitmapFont) {
        
        super(name, obj, position, bitmapFont);         
    }
    
    public Sun(String name, Mesh mesh, float size, float mass, 
                  Vector3f position, BitmapFont bitmapFont) {
        super(name, mesh, size, mass, position, bitmapFont);        
    }
}
