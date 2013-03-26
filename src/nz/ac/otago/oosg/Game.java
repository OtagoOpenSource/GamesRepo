package nz.ac.otago.oosg;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;

/**
 * Welcome to the Beginning of the Otago Open Source Game!
 * 
 * I have put this class in the package nz.ac.otago.oosg. Which might be too long
 * or others may suggest a better one.
 * 
 * @author tim
 */
public class Game extends SimpleApplication {

    public static void main(String[] args) {
        
        //Custom settings to 'brand' the game launcher
        AppSettings settings = new AppSettings(true);
        settings.setTitle("Otago Open Source Game");
        settings.setSettingsDialogImage("Interface/oosgsplash.png"); //temp
        settings.setResolution(800, 600);
        
        Game app = new Game(); //create instance of this class
        app.setSettings(settings); //apply the settings above
        app.start();
    }

    /**
     * This method is called first when the game starts. It gives us the change to 
     * initilise objects and add them to the scene in the game. It is only called 
     * once.
     */
    @Override
    public void simpleInitApp() {
        Box b = new Box(Vector3f.ZERO, 1, 1, 1);
        Geometry geom = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Green);
        geom.setMaterial(mat);

        rootNode.attachChild(geom);
    }
    
    /**
     * Is called over and over again to update what's going on in the game.
     * 
     * @param tpf Time per frame. This is used to stop things happening
     * faster on faster machines and vice versa.
     */
    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
