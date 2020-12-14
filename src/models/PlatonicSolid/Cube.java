package models.PlatonicSolid;

import threedimensions.math.Vector3;
import models.common.AbstractModel;

import java.util.*;

import static java.lang.StrictMath.sqrt;

public class Cube extends AbstractModel {

    public Cube(double radius) {
        super(radius);
        faces = new ArrayList<>(6);
        vertices = new HashMap<>(8);
        computeAllVertices();
        createAllFaces();
    }

    @Override
    protected void computeAllVertices() {
        double a = getRadius() / sqrt(3);

        Vector3 A = new Vector3(a, a, a);
        Vector3 B  = new Vector3(a, a, -a);
        Vector3 C = new Vector3(-a, a, -a);
        Vector3 D = new Vector3(-a, a, a);
        Vector3 E = new Vector3(a, -a, a);
        Vector3 F = new Vector3(a, -a, -a);
        Vector3 G = new Vector3(-a, -a, -a);
        Vector3 H = new Vector3(-a, -a, a);

        vertices.put('A', A);
        vertices.put('B', B);
        vertices.put('C', C);
        vertices.put('D', D);
        vertices.put('E', E);
        vertices.put('F', F);
        vertices.put('G', G);
        vertices.put('H', H);
    }


    protected void createAllFaces() {
        super.createAllFaces(Arrays.asList("ABCD", "ABFE", "ADHE", "EFGH", "GCDH", "GFBC"), 4);
    }
}
