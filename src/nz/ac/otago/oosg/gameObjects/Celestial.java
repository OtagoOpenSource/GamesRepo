package nz.ac.otago.oosg.gameObjects;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import java.text.DecimalFormat;

/**
 * Used for objects we add to the solar system.
 * 
 * Created by Tim Sullivan.
 * Modified by Kevin Weatherall.
 * 
 */
public abstract class Celestial extends Geometry {
    // current position of the planet
    public Vector3f position;
    // size of the planet; used for determining mass
    private float size;            
    // mass of planet
    private float mass;
    // used to dispaly which type of object it is in the HUD, e.g. Earth
    private String type = null;
    // HUD text for planet
    private BitmapText text = null;
    // planet HUD
    private Node hud;
    
    public Celestial(String name, GameObject obj, Vector3f position, BitmapFont bitmapFont) {
        
        this(name, obj.getMesh(), obj.getSize(), obj.getMass(), position, bitmapFont);
        
        this.type = obj.getObjectType();
        setMaterial(obj.getMaterial());
    }
    
    public Celestial(String name, Mesh mesh, float size, float mass, 
                  Vector3f position, BitmapFont bitmapFont) {
        
        super(name, mesh);
        this.size = size;
        this.mass = mass;
        this.position = position;          
        
        hud = new Node();
        
        // set up HUD text only if a font was provided
        if (bitmapFont != null) {
            /* set up HUD text */
            // initialise
            text = new BitmapText(bitmapFont, false);
            // set default text
            text.setText("");
            // set size
            text.setSize(20);   
            // set color
            text.setColor(ColorRGBA.Green);    
            hud.attachChild(text);
        }
        
        // place object in world
        setLocalTranslation(position);
    }
    
    /**
     * Printing friendy method.
     * @return planetinfo
     */
    @Override
    public String toString(){
        // number of # after decimal point indicates number of place to round to
        DecimalFormat df = new DecimalFormat("#.##");        
                
        // create a string from the vector
        String pos = "(" +
                df.format(this.getLocalTranslation().x) + ", " +
                df.format(this.getLocalTranslation().y) + ", " +
                df.format(this.getLocalTranslation().z) + ")";
        
        // return result
        String result = "Position: " + pos + "\n" +
                "Mass: " + df.format(this.getMass());
        
        if (type != null) {
            result = result.concat("\nType: " + type);            
        }        
        
        return result;
    }
    
    /* GETTERS AND SETTERS */    
    /* Sets the HUD text */
    public void setText(String newText) {
        if (text != null) {
            this.text.setText(newText);
        }
    }
    public Node getHud() {
        return hud;
    }
    public float getSize() {
        return size;
    }
    public float getMass() {
        return mass;
    }
    /* Enables object's text info in the HUD */
    public void enableHud() {
        if (hud.hasChild(text)) {
            return;
        }
        
        hud.attachChild(text);
    }
    /* Disables object's text info in the HUD */
    public void disableHud() {
        if (!hud.hasChild(text)) {
            return;
        }
        
        hud.detachChild(text);        
    }
}
