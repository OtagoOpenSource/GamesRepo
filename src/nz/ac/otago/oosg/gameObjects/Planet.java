package nz.ac.otago.oosg.gameObjects;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import java.text.DecimalFormat;

/**
 * Extend the Geometry class and add mass and acceleration fields for our own physics.
 * We might not need to implement our own management for this. There is the option of 
 * using the physics control to handle this. But it might be good to have our own to
 * try out first.
 * 
 * Created by Tim Sullivan.
 * Modified by Kevin Weatherall.
 * 
 */
public class Planet extends Geometry {
    // current position of the planet
    public Vector3f position;
    // size of the planet; used for determining mass
    private float size;        
    // current velocity of planet
    private Vector3f velocity = Vector3f.ZERO;
    // mass of planet
    private float mass;
    
    private String type = null;
    
    public static final float dt = 1;
    // HUD text for planet
    private BitmapText text = null;
    // planet HUD
    private Node hud;
    
    private RigidBodyControl control;
    
    public Planet(String name, GameObject obj, Vector3f position, Vector3f velocity,
            BitmapFont bitmapFont) {
        
        this(name, obj.getMesh(), obj.getSize(), obj.getMass(), position, velocity, bitmapFont);
        
        this.type = obj.getObjectType();
    }
    
    public Planet(String name, Mesh mesh, float size, float mass, 
                  Vector3f position, Vector3f velocity, BitmapFont bitmapFont) {
        
        super(name, mesh);
        this.size = size;
        this.mass = mass;
        this.position = position;
        this.velocity = velocity;          
        
        hud = new Node();
        
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
        
        control = new RigidBodyControl();
        addControl(control);
        control.setKinematic(true);        
    }
    
    public RigidBodyControl getControl() {
        return control;
    }
    
    /* Sets the HUD text */
    public void setText(String newText) {
        if (text != null) {
            this.text.setText(newText);
        }
    }
    
    /* Gets the HUD node */
    public Node getHud() {
        return hud;
    }
    
    /* Move the geometry to the position of the planet. */
    public void move() {
        setLocalTranslation(position);
    }
    
    /* Returns the size of the planet */
    public float getSize() {
        return size;
    }
    
    /* Get the mass of the planet. */
    public float getMass() {
        return mass;
    }
    
    /* Add specified acceleration to velocity. */
    public void updatePos(Vector3f acc, float dt) {
        /* position mult scales the speed of the planets.
         * Used to keep planets from shooting off screen straight away.
         */
        this.velocity = this.velocity.add(acc.mult(0.5f*dt));
        this.position = this.position.add(this.velocity.mult(1.0f * dt));
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
        String result = "Planet at " + pos + "\n" +
                "Mass: " + df.format(this.getMass());
        
        if (type != null) {
            result = result.concat("\nType: " + type);            
        }        
        
        return result;
    }
    
    public void enableHud() {
        if (hud.hasChild(text)) {
            return;
        }
        
        hud.attachChild(text);
    }
    
    public void disableHud() {
        if (!hud.hasChild(text)) {
            return;
        }
        
        hud.detachChild(text);        
    }
}
