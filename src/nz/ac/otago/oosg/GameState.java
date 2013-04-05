/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.otago.oosg;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import java.util.Random;
import nz.ac.otago.oosg.celestialBodies.PlanetWorker;

/**
 * @author Kevin Weatherall
 */
public class GameState extends AbstractAppState {
    // for loading assets
    private AssetManager assetManager;
    // for handling planets
    private PlanetWorker worker = null;
    // root node for adding nodes to
    private Node rootNode;
    // for handling input
    private ActionListener actionlistener;
    private AnalogListener analogListener;
    // for receiving input
    private InputManager inputManager;

    private Camera cam;
    
    private BulletAppState bulletAppState;
    
    private Player player;
    
    public GameState(SimpleApplication app) {
        bulletAppState = new BulletAppState();
        app.getStateManager().attach(bulletAppState);
        
        // remember essential objects
        assetManager = app.getAssetManager();
        rootNode = app.getRootNode();
        inputManager = app.getInputManager();
        cam = app.getCamera();
        
        // set camera move speed
        app.getFlyByCamera().setMoveSpeed(50);
        
        // set up planets
        setUpInitialPlanets(app);
        // set up lights
        setUpLights();
        // map button presses
        initKeys();
        
        // set up the player
        setUpPlayer();
    }

    private void setUpPlayer() {
        Sphere p = new Sphere(32, 32, .1f);
        player = new Player("Player", p, worker.getPlanet(1));        
        
        Material mat = new Material(assetManager, 
                "Common/MatDefs/Light/Lighting.j3md");
        player.setMaterial(mat);        
        rootNode.attachChild(player);        
        bulletAppState.getPhysicsSpace().add(player.getControl());
    }
    
    /* Sets up the PlanetWorker object and adds first planets */
    private void setUpInitialPlanets(SimpleApplication app) {
        // handles planets
        worker = new PlanetWorker("worker", assetManager, app.getGuiNode(), app.getCamera());
        rootNode.attachChild(worker);
        
        // add the first planets        
        addPlanetControl(worker.addPlanet("Sun", 2f, 100f, Vector3f.ZERO, Vector3f.ZERO, ColorRGBA.Orange));
        addPlanetControl(worker.addPlanet("Planet1", 1f, 0f, new Vector3f(10f, 0f, -10f), new Vector3f(-10f, 0f, -10f), ColorRGBA.Blue));
        addPlanetControl(worker.addPlanet("Planet2", 1f, 0f, new Vector3f(0f, 40f, 0f), new Vector3f(0f, 0f, -8f), ColorRGBA.Red));
        addPlanetControl(worker.addPlanet("Planet3", 1f, 0f, new Vector3f(20f, 0f, -20f), new Vector3f(-10f, 0f, -10f), ColorRGBA.Yellow));
    }
    
    private void addPlanetControl(RigidBodyControl control) {
        bulletAppState.getPhysicsSpace().add(control);
    }
    
    /* Sets up the lights */
    private void setUpLights() {
        //create some weak light for everything.
        AmbientLight light = new AmbientLight();
        rootNode.addLight(light);

        //light to represent the 'sun'
        DirectionalLight light2 = new DirectionalLight();
        light2.setDirection(new Vector3f(-0.5f, 0, -0.5f));
        light2.setColor(ColorRGBA.White);
        rootNode.addLight(light2);
    }

    /**
     * Sets up key bindings to listerners to handle mouse and keyboard events.
     */
    private void initKeys() {
        this.actionlistener = new ActionListener() {
            public void onAction(String name, boolean isPressed, float tpf) {
                if (name.equals("NewPlanet") && !isPressed) {
                    //add another planet randomly
                    //System.out.println("Adding planet to scene.");
                    Random rand = new Random();
                    worker.addPlanet("p", 1, 0,
                            new Vector3f(
                            rand.nextFloat() * 100 - 50,
                            rand.nextFloat() * 100 - 50,
                            rand.nextFloat() * 100 - 50),
                            new Vector3f(
                            rand.nextFloat() * 20 - 10,
                            rand.nextFloat() * 20 - 10,
                            rand.nextFloat() * 20 - 10),
                            ColorRGBA.Blue);
                } else if (name.equals("Jump")) {
                    if (!player.isJumping()) {
                        player.jump();
                    }
                }
            }
        };

        this.analogListener = new AnalogListener() {
            public void onAnalog(String name, float value, float tpf) {
                if (name.equals("MoveForward")) {                    
                    Vector3f v = cam.getDirection().mult(-0.001f);
                    //v.y = 0;
                    
                    player.addRotation(v);
                    
                }
            }
        };
        
        //map LMB to the input manager naming it NewPlanet
        inputManager.addMapping("NewPlanet", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addMapping("MoveForward", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
        
        inputManager.addListener(this.actionlistener, new String[]{"NewPlanet", "Jump"});
        
        inputManager.addListener(this.analogListener, new String[] { "MoveForward" } );
        
    }
    
    @Override
    public void update(float tpf) {
        worker.update(tpf);
        player.placePlayer();
        
        Vector3f unit = cam.getDirection().divide(cam.getDirection().length());       
        
        cam.setLocation(player.getLocalTranslation().add(unit.mult(-2)));
        
        player.update(tpf);
        
    }
}
