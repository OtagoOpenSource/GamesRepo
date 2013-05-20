package nz.ac.otago.oosg.support;

import com.jme3.math.Matrix4f;

/* 
 * Class for math operations that we may need
 */
public class MathsOperations {
    /* Returns the X rotation matrix */
    public static Matrix4f getXMatrix(float theta) {
        return new Matrix4f(
                1, 0, 0, 0, 
                0, (float)Math.cos(theta), (float)-Math.sin(theta), 0, 
                0, (float)Math.sin(theta), (float)Math.cos(theta), 0, 
                0, 0, 0, 1);
    }
    
    /* Returns the Y rotation matrix */
    public static Matrix4f getYMatrix(float theta) {
        return new Matrix4f(
                (float)Math.cos(theta), 0, (float)Math.sin(theta), 0, 
                0, 1, 0, 0, 
                -(float)Math.sin(theta), 0, (float)Math.cos(theta), 0, 
                0, 0, 0, 1);
    }
    
    /* Returns the Z rotation matrix */
    public static Matrix4f getZMatrix(float theta) {
        return new Matrix4f(
                (float)Math.cos(theta), -(float)Math.sin(theta), 0, 0, 
                (float)Math.sin(theta), (float)Math.cos(theta), 0, 0, 
                0, 0, 1, 0, 
                0, 0, 0, 1);
    }
}
