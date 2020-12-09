package models.PlatonicSolid;

import threedimensions.math.Vector3;
import models.AbstractModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Octahedron extends AbstractModel {

    public Octahedron(double radius) {
        super(radius);
        vertices = new HashMap<>(6);
        faces = new ArrayList<>(8);
        computeAllVertices();
        createAllFaces();
    }

    @Override
    protected void computeAllVertices() {
        double y = getRadius();

        Vector3 A = new Vector3(0, y, 0);
        Vector3 B = new Vector3(y, 0, 0);
        Vector3 C = new Vector3(0, 0, -y);
        Vector3 D = new Vector3(-y, 0, 0);
        Vector3 E = new Vector3(0, 0, y);
        Vector3 F = new Vector3(0, -y, 0);

        vertices.put('A', A);
        vertices.put('B', B);
        vertices.put('C', C);
        vertices.put('D', D);
        vertices.put('E', E);
        vertices.put('F', F);
    }

    protected void createAllFaces() {
        super.createAllFaces(Arrays.asList("ABC", "ACD", "ABE", "AED", "FEB", "FED", "FBC", "FCD"), 3);
    }
}
