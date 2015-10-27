package nz.ac.otago.oosg.entities;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import nz.ac.otago.oosg.support.EntitySupport;

public abstract class Celestial extends Entity {    
    // mass of entity used for gravity, size of entity used for distance
    private float mass, size;
    // our velocity and position
    private Vector3f velocity = Vector3f.ZERO, position = Vector3f.ZERO;
    
    // gravity-related
    private static final float G = 5;
    
    public Celestial(String name, float size, float mass, 
                  Vector3f position, AssetManager assetManager,
                  ColorRGBA color) {
                      
        // set initial properties
        this.mass = mass;
        this.size = size;
        this.position = position;
        
        // place object in world
        setLocalTranslation(position);
        
        // create a geometry for the entity
        createGeometry(assetManager, color);
    }
        
    /* Create a geometry for the entity */
    protected void createGeometry(AssetManager assetManager, ColorRGBA color)
    {
        // create geometry
        Geometry geo = EntitySupport.makeSphere(null, getSize());        
        // set up geometry with mesh
        setUpGeometry(geo, assetManager, color);
    }
        
    /* Move the node to the position of the entity. */
    public void updateLocation() {
        setLocalTranslation(position);
    }
    
    /* Add velocity to position. */
    public void updatePos(float dt) {        
        this.position = this.position.add(this.velocity.mult(1.0f * dt));
    }
    
    /* Add acceleration to velocity */
    public void updateAcceleration(Vector3f acc, float dt)
    {
        this.velocity = this.velocity.add(acc.mult(0.5f*dt));
    }
    
    /* updates and applies gravity to children */
    @Override
    public void update(float dt)
    {   
        for (Spatial s : getChildren())
        {
            // if children are celestials
            if (s instanceof Celestial)
            {
                // do gravity
                simGrav((Celestial)s, dt);
                
                // not sure if we can do this now, or if we need to
                // update CBs after finishing all simGrav calls
                ((Celestial)s).update(dt);
            }
        }
        
        // update our position
        updatePos(dt);
        // set our position in world to our position variable
        setLocalTranslation(position);
    }
    
    /* Apply gravity to the chosen celestial */
    private void simGrav(Celestial cb, float dt) {                
        /* Stores the net acceleration while it is being calculated */
        Vector3f acc = new Vector3f();

        Vector3f dis = new Vector3f(
                cb.position.x - 0, //position.x,
                cb.position.y - 0, //position.y,
                cb.position.z - 0); //position.z);

        double denom = Math.pow(dis.x, 2) + Math.pow(dis.y, 2) + Math.pow(dis.z, 2);//Our denominator for calculating acceleration

        float magAcc = (float) (G * mass / denom);//The magnitude of acceleration
        float magDis = (float) (Math.pow(denom, 0.5));//The magnitude of our positions as a vector

        /* Turning position in a 3 part unit vector */
        Vector3f unit = new Vector3f(
                dis.x / magDis,
                dis.y / magDis,
                dis.z / magDis);

        /* accelerating by direction as a vector multiplied by the scalar of accelerations magnitude */
        acc = acc.subtract(unit.mult(magAcc));
        
        cb.updateAcceleration(acc, dt);
    }
    
    /* GETTERS AND SETTERS */        
    public float getSize() {
        return size;
    }
    public float getMass() {
        return mass;
    }    
    public void setVelocity(Vector3f newVelocity)
    {
        this.velocity = newVelocity;
    }
}
