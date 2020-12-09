package threedimensions.geometry.face;

import threedimensions.geometry.face.polygons.RegularPentagon;
import threedimensions.geometry.face.polygons.RegularTriangle;
import threedimensions.geometry.face.polygons.Square;
import threedimensions.math.Vector3;

import java.util.Collection;

public class FaceFactory {

    public static Face getFace(Collection<Vector3> c, int numOfSide){
        return switch (numOfSide) {
            case 3 -> new RegularTriangle(c);
            case 4 -> new Square(c);
            case 5 -> new RegularPentagon(c);
            default -> null;
        };
    }
}
