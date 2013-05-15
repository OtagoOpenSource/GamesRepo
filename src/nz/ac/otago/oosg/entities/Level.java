package nz.ac.otago.oosg.entities;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.Random;

public class Level extends Node {
    // remember what to orbit planets around
    private Sun sun;
    
    /* Useful scene objects */
    private AssetManager assetManager;    
    Camera cam;
    
    // planets added so far; used for automatic planet naming
    private int planetsAdded = 0;    
    
    public Level(String name, SimpleApplication app) {
        super(name);
        
        this.assetManager = app.getAssetManager();
        this.cam = app.getCamera();

        /* Create sun */        
        addObject(new Sun("Sun", Vector3f.ZERO, assetManager));
    }
    
    /* Gets next available planet name */
    private String getNewPlanetName() {
        return "planet" + (planetsAdded++);
    }
    
    /* Adds a celesital body to the scene */
    private void addObject(Celestial celestial) {        
        // if sun, remember so we can orbit around it
        if (celestial instanceof Sun) {
            sun = (Sun)celestial;
            this.attachChild(sun);
        } 
        else
        {
            sun.attachChild(celestial);
        }
    }
    
    /* Add a completely random planet. */
    public void addPlanet() {
        addPlanet(new Random().nextFloat() / .8f + .2f, 0f);
    }

    /* Add a planet the chosen name and size. */
    public void addPlanet(float size, float mass) {
        Random rand = new Random();

        Vector3f position = new Vector3f(
                ((rand.nextFloat() * 2) - 1) * 50,
                ((rand.nextFloat() * 2) - 1) * 50,
                ((rand.nextFloat() * 2) - 1) * 50);

        Vector3f velocity = new Vector3f(
                ((rand.nextFloat() * 2) - 1) * 5,
                ((rand.nextFloat() * 2) - 1) * 5,
                ((rand.nextFloat() * 2) - 1) * 5);
        
        // adds planet of type "default"
        addPlanet(position, velocity, size, mass);
    }
    
    /**
     * Creates a planet with specific position and velocity
     */
    public void addPlanet(Vector3f position, Vector3f velocity) {
        addPlanet(position, velocity, 1, 0);
    }
    
    /**
     * Creates a planet with specific position and velocity
     */
    public void addPlanet(Vector3f position, Vector3f velocity, float size, float mass) {                                        
        Planet newPlanet = new Planet(getNewPlanetName(), position, assetManager);        
        newPlanet.setVelocity(velocity);
                
        // add object to scene
        addObject(newPlanet);
    }
          
    /* 
     * Update celestials
     */
    public void update(float tpf) {
        for (Spatial s : getChildren())
        {
            if (s instanceof Celestial)
            {
                ((Celestial)s).update(tpf);
            }
        }
    }
}
