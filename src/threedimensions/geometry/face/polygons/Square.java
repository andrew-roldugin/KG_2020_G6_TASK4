package threedimensions.geometry.face.polygons;

import threedimensions.geometry.face.Face;
import threedimensions.math.Vector3;

import java.util.Collection;

public class Square extends Face {

    public Square(Collection<Vector3> c) {
        super(c, 4);
    }

    public Square(Collection<Vector3> c, double a) {
        super(c, a, 4);
    }

    public double getArea(){
        return getASquared();
    }
}
