package models;

import threedimensions.math.Vector3;
import threedimensions.third.IModel;
import threedimensions.geometry.Plane;
import threedimensions.third.PolyLine3D;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Plane3D implements IModel {
    private Plane plane;

    public Plane3D(Plane plane) {
        this.plane = plane;
    }

    @Override
    public List<PolyLine3D> getLines() {
        LinkedList<PolyLine3D> lines = new LinkedList<>();
        double z1 = -plane.getABD().dotProduct(new Vector3(5, 5, 1)) / plane.getC();
        double z2 = -plane.getABD().dotProduct(new Vector3(-5, 5, 1)) / plane.getC();
        double z3 = -plane.getABD().dotProduct(new Vector3(-5, 0, 1)) / plane.getC();
        double z4 = -plane.getABD().dotProduct(new Vector3(5, 0, 1)) / plane.getC();

        lines.add(new PolyLine3D(Arrays.asList(
                new Vector3(5, 5, z1),
                new Vector3(-5, 5, z2),
                new Vector3(-5, 0, z3),
                new Vector3(5, 0, z4)), true));
        return lines;
    }
}
