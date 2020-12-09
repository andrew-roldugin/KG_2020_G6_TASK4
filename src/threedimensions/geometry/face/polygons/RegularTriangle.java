package threedimensions.geometry.face.polygons;

import threedimensions.geometry.face.Face;
import threedimensions.math.Vector3;

import java.util.Collection;

import static java.lang.StrictMath.sqrt;

public class RegularTriangle extends Face {

    public RegularTriangle(Collection<Vector3> vertices) {
        super(vertices, 3);
    }

    public RegularTriangle(Collection<Vector3> c, double a) {
        super(c, a, 3);
    }

    public double getArea(){
        return sqrt(3) * getASquared() / 4;
    }

}
