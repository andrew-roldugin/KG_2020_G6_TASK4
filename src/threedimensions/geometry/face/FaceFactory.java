package threedimensions.geometry.face;

import threedimensions.geometry.Face;
import threedimensions.geometry.face.polygons.RegularPentagon;
import threedimensions.geometry.face.polygons.RegularTriangle;
import threedimensions.geometry.face.polygons.Square;
import threedimensions.math.Vector3;

import java.awt.*;
import java.util.Collection;

public class FaceFactory {
    public static Face getFace(Collection<Vector3> c, int numOfSide, Color color){
        return switch (numOfSide) {
            case 3 -> new RegularTriangle(c, color);
            case 4 -> new Square(c, color);
            case 5 -> new RegularPentagon(c, color);
            default -> null;
        };
    }
}
