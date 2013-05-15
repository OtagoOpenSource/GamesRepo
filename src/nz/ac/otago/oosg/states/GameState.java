package nz.ac.otago.oosg.states;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.util.SkyFactory;
import java.util.Random;
import nz.ac.otago.oosg.entities.Level;

public class GameState extends AbstractAppState {
    /* Useful objects from main class */
    private AssetManager assetManager;    
    private Node rootNode;
    private InputManager inputManager;
    private Camera cam;
    
    // for handling planets
    private Level level = null;
    
    // for handling input
    private ActionListener actionlistener;
    private AnalogListener analogListener;

    public GameState(SimpleApplication app) {
        /* Remember essential objects */
        assetManager = app.getAssetManager();
        rootNode = app.getRootNode();
        inputManager = app.getInputManager();
        cam = app.getCamera();
                
        // set camera move speed        
        app.getFlyByCamera().setMoveSpeed(50);
                
        /* Set everything up */
        setUpSolarSystem(app);
        setUpLights();
        setUpSky();
        initKeys();
    }
    
    /* Sets up the PlanetWorker object and adds first planets */
    private void setUpSolarSystem(SimpleApplication app) {        
        level = new Level("level", app);
        rootNode.attachChild(level);        
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
     * Load a simple black skymap in the background.
     */
    private void setUpSky(){
        rootNode.attachChild(SkyFactory.createSky(assetManager,
                "Textures/blacktest.jpg", true));
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
                    level.addPlanet(
                            new Vector3f(
                            rand.nextFloat() * 100 - 50,
                            rand.nextFloat() * 100 - 50,
                            rand.nextFloat() * 100 - 50),
                            new Vector3f(
                            rand.nextFloat() * 20 - 10,
                            rand.nextFloat() * 20 - 10,
                            rand.nextFloat() * 20 - 10)
                            );
                }
            }
        };

        // for moving character
        this.analogListener = new AnalogListener() {
            public void onAnalog(String name, float value, float tpf) {
                
            }
        };
        
        //map LMB to the input manager naming it NewPlanet
        inputManager.addMapping("NewPlanet", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        
        inputManager.addListener(this.actionlistener, new String[]{"NewPlanet"});
        
        //inputManager.addListener(this.analogListener, new String[] {  } );
    }
    
    @Override
    public void update(float tpf) {
        // update level
        level.update(tpf);
    }
}
