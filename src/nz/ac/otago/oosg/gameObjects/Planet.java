package nz.ac.otago.oosg.gameObjects;

import com.jme3.font.BitmapFont;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;

public class Planet extends Celestial {
    // current velocity of planet
    private Vector3f velocity = Vector3f.ZERO;
    
    public Planet(String name, GameObject obj, Vector3f position, Vector3f velocity,
            BitmapFont bitmapFont) {
        
        super(name, obj, position, bitmapFont); 
        
        this.velocity = velocity;
    }
    
    public Planet(String name, Mesh mesh, float size, float mass, 
                  Vector3f position, Vector3f velocity, BitmapFont bitmapFont) {
        super(name, mesh, size, mass, position, bitmapFont);
        
        this.velocity = velocity;
    }
    
    /* Move the geometry to the position of the planet. */
    public void move() {
        setLocalTranslation(position);
    }
    
    /* Add specified acceleration to velocity. */
    public void updatePos(Vector3f acc, float dt) {
        /* position mult scales the speed of the planets.
         * Used to keep planets from shooting off screen straight away.
         */
        this.velocity = this.velocity.add(acc.mult(0.5f*dt));
        this.position = this.position.add(this.velocity.mult(1.0f * dt));
    }
}