package nz.ac.otago.oosg.gameObjects;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.Random;
import nz.ac.otago.oosg.support.ObjectLoader;

/**
 * Class for adding and maintaining celestial objects.
 *
 * Created by Kevin Weatherall. simGrav method created by Ben Knowles.
 *
 * @author Ben Knowles
 * @author Kevin Weatherall
 *
 */
public class SolarSystem extends Node {
    /* Celestial bodies */
    private ArrayList<Planet> planets = new ArrayList<Planet>();
    private Sun sun;
    
    /* Useful scene objects */
    private AssetManager assetManager;    
    Camera cam;
    
    /* Physics stuff - needs commented by someone who understands it!!! */
    private static final float G = 5;

    // TRUE if want to see each planet's HUD
    private boolean planetHudEnabled = false;
    
    // planets added so far; used for automatic planet naming
    private int planetsAdded = 0;    
    
    // gui node; used to add planet info text to    
    Node hudNode;
    
    // default font; get using method!
    private BitmapFont defaultFont = null;
    
    public SolarSystem(String name, SimpleApplication app) {
        super(name);
        
        this.assetManager = app.getAssetManager();
        this.cam = app.getCamera();
        this.hudNode = app.getGuiNode();
        
        /* Create sun */
        GameObject sunObject = ObjectLoader.getObject("DefaultSun");
        if (sunObject != null) {
            sun = new Sun("Sun", sunObject, Vector3f.ZERO, getDefaultFont(),
                    app.getRootNode(), assetManager);
            addObject(sun);
        }
    }

    /* Loads a solar system from the provided file.
     * Providing NULL loads default system.
     */
    public void loadSolarSystem(String file) {
        if (file == null) {
            // add the first planets
            addPlanet("Earth", new Vector3f(10f, 0f, -10f), new Vector3f(-0.45f, 0f, -0.45f));
            addPlanet("Venus", new Vector3f(0f, 40f, 0f), new Vector3f(0f, 0f, -0.25f));
            addPlanet("Mars", new Vector3f(20f, 0f, -20f), new Vector3f(-0.25f, 0f, -0.25f));
        }
    }
        
    private BitmapFont getDefaultFont() {
        if (defaultFont == null) {
            defaultFont = assetManager.loadFont("Interface/Fonts/Console.fnt");
        }
        
        return defaultFont;
    }
    
    /* Gets next available planet name */
    private String getNewPlanetName() {
        return "planet" + (planetsAdded++);
    }
    
    /* Adds a celesital body to the scene */
    private void addObject(Celestial celestial) {
        // attach planet HUD to gui node        
        hudNode.attachChild(celestial.getHud());

        // if HUD disabled, disable it for 
        if (!planetHudEnabled) {
            celestial.disableHud();
        }
        
        // update planet text
        handlePlanetText(celestial);
        
        // add body to correct place
        if (celestial instanceof Sun) {
            sun = (Sun)celestial;
        } else if (celestial instanceof Planet) {
            planets.add((Planet)celestial);
        }
        
        // attach body to scene
        attachChild(celestial);
        
        // debug text
        System.out.println(celestial);
    }
    
    /**
     * Creates a planet, assignes material, mass, size and adds it to the scene.
     */
    public void addPlanet(String planetType, Vector3f position, Vector3f velocity) {                
        // get info for this sort of planet
        GameObject planetObject = ObjectLoader.getObject(planetType);
        if (planetObject == null) {
            System.err.println("Specified planet type '" + planetType + "' could not be found!");
            return;
        }        
        
        //create the planet, give it a name, mesh, mass. acceleration is set to 000.
        Planet newPlanet = new Planet(getNewPlanetName(), planetObject, position, 
                velocity, getDefaultFont());
        
        // add object to scene
        addObject(newPlanet);
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

        // adds planet of type "default"
        addPlanet("default", position, Vector3f.ZERO);
    }

    
    /* Updates the text of a planet with its current information and moves it to correct location */
    private void handlePlanetText(Celestial c) {
        // update planet's text
        c.setText(c.toString());
        // move text to planet's current location
        c.getHud().setLocalTranslation(cam.getScreenCoordinates(c.getLocalTranslation()));        
    }
    
    /* Returns the chosen planet; probably don't need this if we code right */
    public Planet getPlanet(int index) {
        if (index < 0 || index >= planets.size()) {
            return null;
        }
        
        return planets.get(index);
    }
    
    /* 
     * Gravity simulation. Code originally created by Ben Knowles.
     * 
     * dt provides time since last frame update
     */
    private void simGrav(Planet p, float dt) {
        // if there is no sun, there is no gravity
        if (sun == null) {
            return;
        }
        
        /* Stores the net acceleration while it is being calculated */
        Vector3f acc = new Vector3f();

        Vector3f dis = new Vector3f(
                p.position.x - sun.position.x,
                p.position.y - sun.position.y,
                p.position.z - sun.position.z);

        double denom = Math.pow(dis.x, 2) + Math.pow(dis.y, 2) + Math.pow(dis.z, 2);//Our denominator for calculating acceleration

        float magAcc = (float) (G * sun.getMass() / denom);//The magnitude of acceleration
        float magDis = (float) (Math.pow(denom, 0.5));//The magnitude of our positions as a vector

        /* Turning position in a 3 part unit vector */
        Vector3f unit = new Vector3f(
                dis.x / magDis,
                dis.y / magDis,
                dis.z / magDis);

        /* accelerating by direction as a vector multiplied by the scalar of accelerations magnitude */
        acc = acc.subtract(unit.mult(magAcc));
        
        p.updatePos(acc, dt);
    }
    
    /* 
     * Update planet velocities and move them
     */
    public void update(float tpf) {
        // work out acceleration for each planet
        for (Planet p : planets) {
            simGrav(p,tpf);
        }

        // move each planet
        for (Planet p : planets) {
            p.move();
                        
            handlePlanetText(p);            
        }        
    }
    
    public void togglePlanetHud() {
        planetHudEnabled = !planetHudEnabled;
        
        if (planetHudEnabled) {
            for(Planet p : planets) {
                p.enableHud();
            }
        } else {
            for(Planet p : planets) {
                p.disableHud();
            }
        }
    }
}
