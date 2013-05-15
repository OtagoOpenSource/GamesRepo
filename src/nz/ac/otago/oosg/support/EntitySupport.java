package nz.ac.otago.oosg.support;

import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Sphere;

public class EntitySupport {
    /* Makes a geometry sphere */
    public static Geometry makeSphere(String name, float size)
    {        
        Mesh m = new Sphere(32, 16, size);
        Geometry geo = new Geometry(name, m);
        return geo;
    }
}
