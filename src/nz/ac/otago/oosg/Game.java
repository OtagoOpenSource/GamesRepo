package nz.ac.otago.oosg;

import com.jme3.app.SimpleApplication;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;

/**
 * Welcome to the Beginning of the Otago Open Source Game!
 *
 * I have put this class in the package nz.ac.otago.oosg. Which might be too
 * long or others may suggest a better one.
 *
 * Modified by Tim Sullivan.
 * Added to by Kevin Weatherall
 * 
 */
public class Game extends SimpleApplication {

    // for detecting input
    private ActionListener actionlistener;
    // for adding and handling planets
    private PlanetWorker worker;
    
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
        // objects for handling planets
        worker = new PlanetWorker("worker", assetManager);
        rootNode.attachChild(worker);
        
        // add the first planet
        worker.addPlanet("Sun", 2f, 100f, Vector3f.ZERO, Vector3f.ZERO, ColorRGBA.Orange);
        worker.addPlanet("Planet1", 1f, 0f, new Vector3f(10f,0f,-10f), new Vector3f(-10f,0f,-10f),ColorRGBA.Blue);
        worker.addPlanet("Planet2", 1f, 0f, new Vector3f(0f,40f,0f), new Vector3f(0f,0f,-8f),ColorRGBA.Red);
        worker.addPlanet("Planet3", 1f, 0f, new Vector3f(20f,0f,-20f), new Vector3f(-10f,0f,-10f),ColorRGBA.Yellow);
        
        //create some weak light for everything.
        AmbientLight light = new AmbientLight();
        rootNode.addLight(light);

        //light to represent the 'sun'
        DirectionalLight light2 = new DirectionalLight();
        light2.setDirection(new Vector3f(-0.5f, 0, -0.5f));
        light2.setColor(ColorRGBA.White);
        rootNode.addLight(light2);

        // map button presses
        initKeys();
        
        this.flyCam.setMoveSpeed(100); // make the camera move faster around.
    }

    /**
     * Is called over and over again to update what's going on in the game.
     *
     * @param tpf Time per frame. This is used to stop things happening faster
     * on faster machines and vice versa.
     */
    @Override
    public void simpleUpdate(float tpf) {
        worker.update(tpf);
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
                    //System.out.println("Adding planet to scene.");
                    //worker.addPlanet();
                }
            }
        };

        //map LMB to the input manager naming it NewPlanet
        this.inputManager.addMapping("NewPlanet", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));

        inputManager.addListener(this.actionlistener, new String[]{"NewPlanet"});
    }
}
