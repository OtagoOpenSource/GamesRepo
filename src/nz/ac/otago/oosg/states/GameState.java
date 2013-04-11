package nz.ac.otago.oosg.states;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import java.util.Random;
import nz.ac.otago.oosg.gameObjects.GameObject;
import nz.ac.otago.oosg.gameObjects.Player;
import nz.ac.otago.oosg.gameObjects.SolarSystem;
import nz.ac.otago.oosg.support.ObjectLoader;

/**
 * @author Kevin Weatherall
 */
public class GameState extends AbstractAppState {
    /* Useful objects from main class */
    private AssetManager assetManager;    
    private Node rootNode;
    private InputManager inputManager;
    private Camera cam;
    
    // for handling planets
    private SolarSystem worker = null;
    
    // for handling input
    private ActionListener actionlistener;
    private AnalogListener analogListener;

    /* Player-related */
    private Player player;    
    // if we are in control of the player
    private boolean controlPlayer = false;
    
    public GameState(SimpleApplication app) {
        /* Remember essential objects */
        assetManager = app.getAssetManager();
        rootNode = app.getRootNode();
        inputManager = app.getInputManager();
        cam = app.getCamera();
        
        // load object definitions
        ObjectLoader.loadObjects(assetManager, null, false);
        
        // set camera move speed        
        app.getFlyByCamera().setMoveSpeed(50);
                
        /* Set everything up */
        setUpSolarSystem(app);
        setUpLights();                
        setUpPlayer();        
        initKeys();
    }

    /* Create a Player object for moving around a planet */
    private void setUpPlayer() {        
        // get player info
        GameObject playerInfo = ObjectLoader.getObject("Player");
        // create player
        player = new Player("Player1", playerInfo.getMesh(), worker.getPlanet(0));        
        player.setMaterial(playerInfo.getMaterial());
        // add to scene
        rootNode.attachChild(player);
    }
    
    /* Sets up the PlanetWorker object and adds first planets */
    private void setUpSolarSystem(SimpleApplication app) {        
        worker = new SolarSystem("worker", app);
        rootNode.attachChild(worker);        
        worker.loadSolarSystem(null);        
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
                    Random rand = new Random();
                    worker.addPlanet("Default",
                            new Vector3f(
                            rand.nextFloat() * 100 - 50,
                            rand.nextFloat() * 100 - 50,
                            rand.nextFloat() * 100 - 50),
                            new Vector3f(
                            rand.nextFloat() * 20 - 10,
                            rand.nextFloat() * 20 - 10,
                            rand.nextFloat() * 20 - 10)
                            );
                } else if (controlPlayer && name.equals("Jump")) {                    
                    player.jump();
                } else if (name.equals("AttachCamera") && !isPressed) {
                    controlPlayer = !controlPlayer;                            
                } else if (name.equals("TogglePlanetHud") && !isPressed) {
                    worker.togglePlanetHud();
                }
            }
        };

        // for moving character
        this.analogListener = new AnalogListener() {
            public void onAnalog(String name, float value, float tpf) {
                if (controlPlayer) {
                    if (name.equals("MoveForward")) {                    
                        Vector3f v = cam.getDirection().mult(-0.001f);
                                                
                        player.addRotation(v);
                    }
                }
            }
        };
        
        //map LMB to the input manager naming it NewPlanet
        inputManager.addMapping("NewPlanet", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addMapping("MoveForward", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("AttachCamera", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("TogglePlanetHud", new KeyTrigger(KeyInput.KEY_O));
        
        inputManager.addListener(this.actionlistener, new String[]{"NewPlanet", "Jump", "AttachCamera", "TogglePlanetHud"});
        
        inputManager.addListener(this.analogListener, new String[] { "MoveForward" } );
    }
    
    @Override
    public void update(float tpf) {
        // update planets
        worker.update(tpf);
        // attach player to planet
        player.placePlayer();
        
        if (controlPlayer) {
            // fix camera to player;
            // mult determines how far back the camera is
            cam.setLocation(player.getLocalTranslation().add(cam.getDirection().mult(-1.5f + Player.SIZE * 2)));       
        }
        
        // update the player; only used for jumping at this point in time
        player.update(tpf);    
    }
}
