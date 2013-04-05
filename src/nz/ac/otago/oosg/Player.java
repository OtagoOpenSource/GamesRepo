package nz.ac.otago.oosg;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Matrix4f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import nz.ac.otago.oosg.celestialBodies.Planet;

public class Player extends Geometry {
    private Vector3f rotation, oldPlanetLocation;
    private Planet planet;
    
    private RigidBodyControl control;
    private float jump = -1;
    private float jumpHeight = 0.5f;
    
    public Player(String name, Mesh mesh, Planet planet) {
        super(name, mesh);
        
        this.planet = planet;
        oldPlanetLocation = planet.getLocalTranslation();
        
        rotation = new Vector3f(0, (float)Math.PI / 2, (float)Math.PI / 2);
        
        control = new RigidBodyControl(1);
        addControl(control);   
        control.setKinematic(true);
        
        placePlayer();
    }
    
    private Matrix4f getXMatrix(float theta) {
        return new Matrix4f(
                1, 0, 0, 0, 
                0, (float)Math.cos(theta), (float)-Math.sin(theta), 0, 
                0, (float)Math.sin(theta), (float)Math.cos(theta), 0, 
                0, 0, 0, 1);
    }
    
    private Matrix4f getYMatrix(float theta) {
        return new Matrix4f(
                (float)Math.cos(theta), 0, (float)Math.sin(theta), 0, 
                0, 1, 0, 0, 
                -(float)Math.sin(theta), 0, (float)Math.cos(theta), 0, 
                0, 0, 0, 1);
    }
    
    private Matrix4f getZMatrix(float theta) {
        return new Matrix4f(
                (float)Math.cos(theta), -(float)Math.sin(theta), 0, 0, 
                (float)Math.sin(theta), (float)Math.cos(theta), 0, 0, 
                0, 0, 1, 0, 
                0, 0, 0, 1);
    }
    
    public void update(float tpf) {
        if (jump > -1) {
            jump += 2f * tpf;
            
            if (jump >= Math.PI) {
                jump = -1;
            }
        }
    }
    
    public void placePlayer() {
        Matrix4f pos = Matrix4f.IDENTITY;        
        pos.setTranslation(0, 1f + (jump > -1 ? (float)Math.sin(jump) * jumpHeight : 0), 0);
        //pos = getXMatrix(rotation.x).mult(pos);
        
        pos = getXMatrix(rotation.x).mult(
                getYMatrix(rotation.y).mult(
                getZMatrix(rotation.z).mult(
                pos
                )
                )
                );
        
        Vector3f newPosition = planet.getLocalTranslation().add(
                pos.toTranslationVector().mult(1.1f)
                );
        
        setLocalTranslation(
                newPosition
                );       
    }
    
    public void addRotation(Vector3f rot) {
        rotation = rotation.add(rot);
    }
    
    public void setPosition(Vector3f position) {
        control.setPhysicsLocation(position);
    }
    
    public RigidBodyControl getControl() {
        return control;
    }    
    
    public void jump() {
        jump = 0;
    }
    
    public boolean isJumping() {
        return jump > -1;
    }
    
}
