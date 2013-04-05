package nz.ac.otago.oosg.celestialBodies;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.font.BitmapFont;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class for adding and maintaining planets.
 *
 * Created by Kevin Weatherall. simGrav method created by Ben Knowles.
 *
 * @author Ben Knowles
 * @author Kevin Weatherall
 *
 */
public class PlanetWorker extends Node {
    // planets we are maintaining
    private ArrayList<Planet> planets = new ArrayList<Planet>();
    // used for loading textures
    private AssetManager assetManager;    
    private static final float dt = 0.00005f;
    private static final float G = 100;

    // the camera; used for updating planet text display
    Camera cam;
    // gui node; used to add planet info text to
    Node guiNode;
    
    /* Basic constructor */
    public PlanetWorker(String name, AssetManager assetManager, Node guiNode, Camera cam) {
        super(name);
        
        this.assetManager = assetManager;
        this.cam = cam;
        this.guiNode = guiNode;        
    }

    /**
     * Creates a planet, assignes material, mass, size and adds it to the scene.
     */
    public RigidBodyControl addPlanet(String name, float size, float mass,
            Vector3f position, Vector3f velocity, ColorRGBA colour) {
        //create the mesh that represents the planet.
        Sphere newPlanetMesh = new Sphere(20, 30, size); //create a sphere mesh
        
        // font for planet HUD text
        BitmapFont guiFont = assetManager.loadFont("Interface/Fonts/Console.fnt");
        
        //create the planet, give it a name, mesh, mass. acceleration is set to 000.
        Planet newPlanet = new Planet(name, newPlanetMesh, size, mass, position, velocity, guiFont);

        // attach planet text to gui node
        guiNode.attachChild(newPlanet.getHud());
        // update planet text
        handlePlanetText(newPlanet);
        
        //for shading, textures, shinyness etc.
        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat.setBoolean("UseMaterialColors", true);  // Set some parameters, e.g. blue.
        //mat.setColor("Ambient", colour);   // ... color of this object
        mat.setColor("Diffuse", colour);   // ... color of light being reflected
        newPlanet.setMaterial(mat);
        //TODO: generate random textures/colours.

        //move the planet to a random location in space
        //nextFloat range needs to be shifted to -1 and 1, then multiplied to give range on each plane.
        newPlanet.setLocalTranslation(position);

        // add our new planet
        planets.add(newPlanet);
        attachChild(newPlanet);
        System.out.println(newPlanet);
        
        return newPlanet.getControl();
    }
    
    /* Updates the text of a planet with its current information and moves it to correct location */
    private void handlePlanetText(Planet p) {
        // update planet's text
        p.setText(p.toString());
        // move text to planet's current location
        p.getHud().setLocalTranslation(cam.getScreenCoordinates(p.getLocalTranslation()));
    }

    /* Add a completely random planet. */
    public RigidBodyControl addPlanet() {
        return addPlanet("planet" + planets.size(), new Random().nextFloat() / .8f + .2f, 0f);
    }

    /* Add a planet the chosen name and size. */
    public RigidBodyControl addPlanet(String name, float size, float mass) {
        Random rand = new Random();

        Vector3f position = new Vector3f(
                ((rand.nextFloat() * 2) - 1) * 50,
                ((rand.nextFloat() * 2) - 1) * 50,
                ((rand.nextFloat() * 2) - 1) * 50);

        return addPlanet(name, size, mass, position, Vector3f.ZERO, ColorRGBA.randomColor());
    }

    public Planet getPlanet(int index) {
        if (index < 0 || index >= planets.size()) {
            return null;
        }
        
        return planets.get(index);
    }
    
    /* 
     * Gravity simulation. Code originally created by Ben Knowles.
     */
    private void simGrav(Planet p) {
        /* Stores the net acceleration while it is being calculated */
        Vector3f acc = new Vector3f();

        // add acceleration for each planet
        for (Planet p2 : planets) {
            // if planets are the same, skip 
            if (p == p2) {
                continue;
            }

            Vector3f dis = new Vector3f(
                    p.position.x - p2.position.x,
                    p.position.y - p2.position.y,
                    p.position.z - p2.position.z);

            double denom = Math.pow(dis.x, 2) + Math.pow(dis.y, 2) + Math.pow(dis.z, 2);//Our denominator for calculating acceleration

            float magAcc = (float) (G * p2.getMass() / denom);//The magnitude of acceleration
            float magDis = (float) (Math.pow(denom, 0.5));//The magnitude of our positions as a vector

            /* Turning position in a 3 part unit vector */
            Vector3f unit = new Vector3f(
                    dis.x / magDis,
                    dis.y / magDis,
                    dis.z / magDis);

            /* accelerating by direction as a vector multiplied by the scalar of accelerations magnitude */
            acc = acc.subtract(unit.mult(magAcc));
        }

        p.updatePos(acc, dt);//changes velocity by t
    }

    /* 
     * Update planet velocities and move them
     */
    public void update(float tpf) {
        // work out acceleration for each planet
        for (Planet p : planets) {
            simGrav(p);
        }

        // move each planet
        for (Planet p : planets) {
            p.move();
            handlePlanetText(p);
        }        
    }
}