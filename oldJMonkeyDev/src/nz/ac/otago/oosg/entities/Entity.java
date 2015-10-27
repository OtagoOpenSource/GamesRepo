package nz.ac.otago.oosg.entities;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

/* An entity placed within the world. */
public abstract class Entity extends Node {
    /* Object information */    
    private Geometry geo = null;
        
    /* Make a geometry for the entity */
    protected void setUpGeometry(Geometry geo, AssetManager assetManager, ColorRGBA color)
    {
        // make sure we haven't already added a geometry
        if (this.geo != null)
        {
            this.detachChild(this.geo);
        }
            
        this.geo = geo;
        
        // set up material
        Material material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Ambient", color.mult(.1f));
        material.setColor("Diffuse", color);
        
        geo.setMaterial(material);
        
        // attach to parent node
        this.attachChild(geo);
    }     
    
    // each entity needs to update
    public abstract void update(float dt);    
}
