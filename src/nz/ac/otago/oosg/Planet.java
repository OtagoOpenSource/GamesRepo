
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
 */
public class Planet extends Geometry {
    private float mass;
    //stores the magnitude of the acceleration for direction.
    private Vector3f acceleration;
    
    public Planet (String name, Mesh mesh, float mass){
        super(name, mesh);
        this.mass = mass;
        //acceleration defaults to a newly created vector at 0.
        this.acceleration = new Vector3f(Vector3f.ZERO);
    }
    
    public float getMass(){
        return mass;
    }
    
    public void setMass(float mass){
        this.mass = mass;
    }
    
    /**
     * Printing friendy method.
     * @return planetinfo
     */
    @Override
    public String toString(){
        return "Planet at "+ this.getLocalTranslation() + 
                " Mass: " + this.mass +
                " Acceleration: " + this.acceleration;
    }
    
}
