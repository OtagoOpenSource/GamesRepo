package nz.ac.otago.oosg.gameObjects;

import com.jme3.math.Matrix4f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import nz.ac.otago.oosg.support.MathsOperations;

/* 
 * The placement of the player is currently determined by rotating the player
 * around the planet they are attached to. Because I wasn't sure the best way
 * to place the player and move around the planet, this was what I chose. Wasn't
 * sure if there was existing way to do this, so I am manually going the matrix
 * operations myself.
 * 
 * The current method is probably not great for jumping from planet to planet.
 * It's probably possible to adapt, but the object does't actually adhere to
 * physics. It is "stuck" to the planet surface at the chosen distance and is
 * rotated around it. When the player jumps, this is simply jump increasing the
 * distance used in the rotation operations. If we are using a grappling hook
 * to move from planet to planet, I suppose it would be possible to adapt this
 * to fit that.
 * 
 */

public class Player extends Geometry {
    /* Positioning */    
    private Vector3f rotation; // out rotation around the planet we are on    
    private Planet planet; // the planet we are on
    
    /* Jumping */    
    private float jumpProgress = -1;
    private float jumpHeight = 0.5f;    
    private float jumpSpeed = 2.0f;
    
    // size of the player
    public static final float SIZE = 0.1f;
    
    public Player(String name, Mesh mesh, Planet planet) {
        super(name, mesh);
        
        this.planet = planet;
        
        // set default location on the planet
        rotation = new Vector3f(0, (float)Math.PI / 2, (float)Math.PI / 2);
        
        // update our location
        placePlayer();
    }
    
    /* Update method; only used for jumping at this point in time */
    public void update(float tpf) {
        // if we are jumping
        if (jumpProgress > -1) {
            // add to jump progress
            jumpProgress += jumpSpeed * tpf;
            
            // if we have finished out jump, stop jumping
            if (jumpProgress >= Math.PI) {
                jumpProgress = -1;
            }
        }
    }
    
    /* Update the location of the player */
    public final void placePlayer() {
        Matrix4f pos = Matrix4f.IDENTITY;        
        
        // set matrix to position of player
        pos.setTranslation(0, 1f + (jumpProgress > -1 ? (float)Math.sin(jumpProgress) * jumpHeight : 0), 0);
                
        // use rotation matrices
        pos = MathsOperations.getXMatrix(rotation.x).mult(
                MathsOperations.getYMatrix(rotation.y).mult(
                MathsOperations.getZMatrix(rotation.z).mult(
                pos
                )
                )
                );
        
        // convert rotation matrix to position vector;
        // mult is for size of player and stops it from clipping with the planet
        Vector3f newPosition = planet.getLocalTranslation().add(
                pos.toTranslationVector().mult(1 + SIZE)
                );
        
        // change out position
        setLocalTranslation(
                newPosition
                );       
    }
    
    /* Move around the planet */
    public void addRotation(Vector3f rot) {
        rotation = rotation.add(rot);
    }
    
    /* Starts player jumping */
    public void jump() {
        // if we are not already jumping
        if (jumpProgress == -1) {
            jumpProgress = 0;
        }
    }
}
