package threedimensions.geometry.face.polygons;

import threedimensions.geometry.face.Face;
import threedimensions.math.Vector3;

import java.util.Collection;

public class RegularPentagon extends Face {

    public RegularPentagon(Collection<Vector3> vertices) {
        super(vertices, 5);
    }

    public RegularPentagon(Collection<Vector3> vertices, double a) {
        super(vertices, a, 5);
    }

    public double getArea(){
        return 5 * getASquared() / (4 * Math.tan(Math.PI / 5));
    }
}
