package threedimensions.geometry.face.polygons;

import threedimensions.geometry.Face;
import threedimensions.math.Vector3;

import java.awt.*;
import java.util.Collection;

public class RegularPentagon extends Face {

    public RegularPentagon(Collection<Vector3> vertices, Color color) {
        super(vertices, 5, color);
    }
}
