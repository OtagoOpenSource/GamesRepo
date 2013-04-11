package nz.ac.otago.oosg;

import nz.ac.otago.oosg.states.GameState;
import com.jme3.app.SimpleApplication;
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
        GameState gs = new GameState(this);
        stateManager.attach(gs);
    }
}
