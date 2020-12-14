package models.PlatonicSolid;

import threedimensions.math.Vector3;
import models.common.AbstractModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static java.lang.StrictMath.sqrt;

public class Tetrahedron extends AbstractModel {

    public Tetrahedron(double radius) {
        super(radius);
        faces = new ArrayList<>(4);
        vertices = new HashMap<>(4);
        computeAllVertices();
        createAllFaces();
    }

    @Override
    protected void computeAllVertices() {
        double a = 4 * getRadius() / sqrt(6);
        Vector3 A = new Vector3(0, a * sqrt(2. / 3.), 0);
        Vector3 B = new Vector3(a / sqrt(3), 0, 0);
        Vector3 C = new Vector3(-a / (2 * sqrt(3)), 0, -a / 2);
        Vector3 D = new Vector3(-a / (2 * sqrt(3)), 0, a / 2);

        vertices.put('A', A);
        vertices.put('B', B);
        vertices.put('C', C);
        vertices.put('D', D);
    }

    protected void createAllFaces() {
        super.createAllFaces(Arrays.asList("ABD", "ACD", "ABC", "CBD"), 3);
    }
}
