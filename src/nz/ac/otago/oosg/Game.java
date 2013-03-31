package nz.ac.otago.oosg;

import com.jme3.app.SimpleApplication;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import com.jme3.system.AppSettings;
import java.util.Random;

/**
 * Welcome to the Beginning of the Otago Open Source Game!
 *
 * I have put this class in the package nz.ac.otago.oosg. Which might be too
 * long or others may suggest a better one.
 *
 */
public class Game extends SimpleApplication {

    private Geometry planet;
    private ActionListener actionlistener;
    private Node planetsNode;
    /* Number of planets loaded */
    private int planetNum;

    public static void main(String[] args) {

        //Custom settings to 'brand' the game launcher
        AppSettings settings = new AppSettings(true);
        settings.setTitle("Otago Open Source Game");
        settings.setSettingsDialogImage("Interface/oosgsplash.png"); //temp image
        settings.setResolution(800, 600);

        Game app = new Game(); //create instance of this Game class
        app.setSettings(settings); //apply the settings above
        app.start();
    }

    /**
     * This method is called first when the game starts. It gives us the change
     * to initilise objects and add them to the scene in the game. It is only
     * called once.
     */
    @Override
    public void simpleInitApp() {
        //create a 'node' where all the planets get attached to
        planetsNode = new Node();
        rootNode.attachChild(planetsNode);

        //create a sphere with 20 latitude lines and 30 longitude lines and radius 1.
        Sphere planet1 = new Sphere(20, 30, 1.5f); //create a sphere mesh
        planet = new Geometry("FirstPlanet", planet1); // create geometry for it.

        //for shading, textures, shinyness etc.
        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        planet.setMaterial(mat);

        //attach the 'planet' to the root node of the scene.
        planetsNode.attachChild(planet);

        //create some weak light for everything.
        AmbientLight light = new AmbientLight();
        rootNode.addLight(light);

        //light to represent the 'sun'
        DirectionalLight light2 = new DirectionalLight();
        light2.setDirection(new Vector3f(-0.5f, 0, -0.5f));
        light2.setColor(ColorRGBA.Orange);

        rootNode.addLight(light2);

        initKeys();
        this.flyCam.setMoveSpeed(10); // make the camera move faster around.
    }

    /**
     * Is called over and over again to update what's going on in the game.
     *
     * @param tpf Time per frame. This is used to stop things happening faster
     * on faster machines and vice versa.
     */
    @Override
    public void simpleUpdate(float tpf) {
        
        planet.rotate(0, tpf * 2, 0); //rotate the central planet about the y axis.
        
        
        //could call simulate gravity here. all the planets are in planetsNode.getChildren() int a <list>
        
        //planetsNode.rotate(0, 0.5f * tpf, 0); //dumb way of making ALL the planets orbit without physics.
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    /**
     * Sets up key bindings to listerners to handle mouse and keyboard events.
     */
    private void initKeys() {
        this.actionlistener = new ActionListener() {
            public void onAction(String name, boolean isPressed, float tpf) {
                if (name.equals("NewPlanet") && !isPressed) {
                    //add another planet randomly
                    System.out.println("Adding planet to scene.");
                    addPlanet();
                }
            }
        };

        //map LMB to the input manager naming it NewPlanet
        this.inputManager.addMapping("NewPlanet", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));

        inputManager.addListener(this.actionlistener, new String[]{"NewPlanet"});
    }

    /**
     * Creates a planet, assignes material, mass, size and adds it to the scene.
     */
    private void addPlanet() {
        Random rand = new Random();
        //create the mesh that represents the planet.
        Sphere newPlanetMesh = new Sphere(20, 30, rand.nextFloat()); //create a sphere mesh
        //create the planet, give it a name, mesh, mass. acceleration is set to 000.
        Planet newPlanet = new Planet(String.valueOf(planetNum++), newPlanetMesh, rand.nextFloat());

        //for shading, textures, shinyness etc.
        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        newPlanet.setMaterial(mat);
        //TODO: generate random textures/colours.

        //move the planet to a random location in space
        //nextFloat range needs to be shifted to -1 and 1, then multiplied to give range on each plane.
        newPlanet.setLocalTranslation(
                ((rand.nextFloat() * 2) - 1) * 20,
                ((rand.nextFloat() * 2) - 1) * 20,
                ((rand.nextFloat() * 2) - 1) * 20);


        planetsNode.attachChild(newPlanet);
        System.out.println(newPlanet);
    }

}
