package nz.ac.otago.oosg.gui;

import com.jme3.app.SimpleApplication;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import nz.ac.otago.oosg.states.GameState;

/**
 * Handles events triggered from the NiftyGUI.
 * 
 * Should hopefully help direct game testing flow, view debug output live, 
 * accept commands and all that cool stuff. Each screen is currently being
 * controlled here. This could be split up to different classes if it gets 
 * too large.
 * 
 * @author Tim Sullivan
 */
public class GuiController implements ScreenController {
    private final Nifty nifty;
    private final SimpleApplication app;
    
    public GuiController(Nifty nifty, SimpleApplication app){
        this.nifty = nifty;
        this.app = app;
    }

    public void bind(Nifty nifty, Screen screen) {
        System.out.println("GUI: bind( " + screen.getScreenId() + ")");
    }

    public void onStartScreen() {
        System.out.println("GUI: onStartScreen");

    }

    public void onEndScreen() {
        System.out.println("GUI: onEndScreen");
    }
    
    /**************************************************************************
     * Main Menu Screen Events
     * 
     */
    
    public void clickedPlayButton(){
        System.out.println("GUI: clicked Play button");
        //snap the input back into the game.
        app.getFlyByCamera().setEnabled(true);
        app.getInputManager().setCursorVisible(false);
        
        //run the game now since the menu is closed
        app.getStateManager().getState(GameState.class).setEnabled(true);
        nifty.gotoScreen("end"); //empty gui screen
    }

    public void clickedQuitButton() {
        System.out.println("GUI: clicked Quit button");
        app.stop(); //stop the game.
    }

    public void clickedSettingsButton() {
        System.out.println("GUI: Clicked Settings button");
        nifty.gotoScreen("settings_screen");
    }
    
    public void clickedHelpButton(){
        System.out.println("GUI: Clicked Help button");
        nifty.gotoScreen("help_screen");
    }
    
    /**************************************************************************
     * Settings Screen Events
     * 
     */
    
    public void clickedSettingsBackButton(){
        System.out.print("GUI: Clicked Settings Back Button");
        nifty.gotoScreen("start");
    }
    
    /**************************************************************************
     * Help Screen Events
     *
     */
    
    public void clickedHelpBackButton(){
        System.out.println("GUI: Clicked Help Back Button");
        nifty.gotoScreen("start");
    }
    
    
    
}