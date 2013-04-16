package nz.ac.otago.oosg.gameObjects;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Sphere;

public class GameObject {
    /* Object information */
    private Mesh mesh;    
    private Material material = null;
    private float size, mass;        
    private String objectType = null; // name given to this type of object; used for HUD
    
    /* Creates a sphere object */
    public GameObject(int zSamples, int radialSamples, float radius,
            float mass) {
        this( new Sphere(zSamples, radialSamples, radius), radius, mass );        
    }
    
    /* Creates with custom mesh */
    public GameObject(Mesh mesh, float size, float mass) {
        this.mesh = mesh;
        this.size = size;
        this.mass = mass;
    }
    
    /* Creates a material of the chosen colour for this object */
    public void createMaterial(AssetManager assetManager,
            ColorRGBA color) {
        
        material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Ambient", color.mult(.1f));
        material.setColor("Diffuse", color);        
    }
    
    /* Creates a default material for this object */
    public void generateDefaultMaterial(AssetManager assetManager) {
        createMaterial(assetManager, ColorRGBA.White);
    }
    
    /* GETTERS AND SETTERS */    
    public void setMaterial(Material material) {
        this.material = material;
    }    
    public Mesh getMesh() {
        return mesh;
    }    
    public float getSize() {
        return size;
    }    
    public float getMass() {
        return mass;
    }    
    public Material getMaterial() {
        return material;
    }    
    public void setObjectType(String type) {
        this.objectType = type;
    }
    public String getObjectType() {
        return objectType;
    }
}
