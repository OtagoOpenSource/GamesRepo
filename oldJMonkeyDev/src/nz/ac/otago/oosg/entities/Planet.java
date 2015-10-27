package nz.ac.otago.oosg.entities;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

public class Planet extends Celestial {
    /* Create planet with specific position */
    public Planet(String name, Vector3f position, 
            AssetManager assetManager) {
        
        this(name, position, 1, 0, assetManager, ColorRGBA.Blue);
    }
    
    /* Create planet with specific position, size and mass */
    public Planet(String name, Vector3f position, float size, float mass, AssetManager assetManager)
    {
        this(name, position, size, mass, assetManager, ColorRGBA.Blue);
    }    
    
    /* Create planet with specific position, size, mass and colour */
    public Planet(String name, Vector3f position, float size, float mass, AssetManager assetManager, ColorRGBA color)
    {
        super(name, size, mass, position, assetManager, color);
    }
}