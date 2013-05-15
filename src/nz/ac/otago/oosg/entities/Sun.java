package nz.ac.otago.oosg.entities;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

public class Sun extends Celestial {    
    public Sun(String name, Vector3f position, AssetManager assetManager) {
        
        // name, size, mass, position, asset manager, colour
        super(name, 3, 1000, position, assetManager, ColorRGBA.Yellow);
    }    
    
    /* Make sure we add flare for sun */
    @Override
    protected void createGeometry(AssetManager assetManager, ColorRGBA color)
    {
        super.createGeometry(assetManager, color);
        
        //add 'flare' particle effect around sun
        this.attachChild(assetManager.loadModel("Scenes/sunFlare.j3o"));
    }
}
