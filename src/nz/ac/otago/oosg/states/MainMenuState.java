package nz.ac.otago.oosg.states;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import de.lessvoid.nifty.Nifty;
import java.util.logging.Level;
import java.util.logging.Logger;
import nz.ac.otago.oosg.gui.GuiController;

public class MainMenuState extends AbstractAppState {
    
    public MainMenuState(SimpleApplication app) {
        initGui(app);
    }
    
    /**
     * Load the welcome GUI menu system.
     * Registers GuiController is responsible for all gui menu events.
     */
    private void initGui(SimpleApplication app) {
        ViewPort guiViewPort = app.getGuiViewPort(); 
        InputManager inputManager = app.getInputManager();
        
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(app.getAssetManager(),
                inputManager,
                app.getAudioRenderer(),
                guiViewPort);

        Nifty nifty = niftyDisplay.getNifty();
        //nifty.setDebugOptionPanelColors(true); //DEBUG
        nifty.fromXml("Interface/gamegui.xml", "start", new GuiController(nifty, app));
        //get insane console spam if the logging level isn't set for niftygui
        Logger.getLogger("de.lessvoid.nifty").setLevel(Level.SEVERE);
        Logger.getLogger("NiftyInputEventHandlingLog").setLevel(Level.SEVERE);
        // attach the nifty display to the gui view port as a processor
        guiViewPort.addProcessor(niftyDisplay);

        //must disable the flycamera for gui to 'get' the mouse.
        app.getFlyByCamera().setEnabled(false);
        inputManager.setCursorVisible(true);        
    }
}
