
package nz.ac.otago.oosg;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;

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
    public static final float dt = 1;
    
    public Planet(String name, Mesh mesh, float size, float mass, 
                  Vector3f position, Vector3f velocity) {
        super(name, mesh);
        this.size = size;
        this.mass = mass;
        this.position = position;
        this.velocity = velocity;
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
        /* Multiplier (.001f) scales the speed of the planets.
         * Used to keep planets from shooting off screen straight away.
         */
        this.velocity = this.velocity.add(acc.mult(0.5f*dt));
        this.position = this.position.add(this.velocity.mult(dt));
    }
    
    /**
     * Printing friendy method.
     * @return planetinfo
     */
    @Override
    public String toString(){
        return "Planet at "+ this.getLocalTranslation() + 
                " Mass: " + this.getMass();
    }
    
}
