package threedimensions.geometry.face.polygons;

import threedimensions.geometry.Face;
import threedimensions.math.Vector3;

import java.awt.*;
import java.util.Collection;

public class RegularTriangle extends Face {
    public RegularTriangle(Collection<Vector3> vertices, Color color) {
        super(vertices, 3, color);
    }
}
