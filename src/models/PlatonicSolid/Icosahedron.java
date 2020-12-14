package models.PlatonicSolid;

import threedimensions.math.Vector3;
import models.common.AbstractModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static java.lang.StrictMath.*;

public class Icosahedron extends AbstractModel {

    public Icosahedron(double radius) {
        super(radius);
        vertices = new HashMap<>(12);
        faces = new ArrayList<>(20);
        computeAllVertices();
        createAllFaces();
    }

    @Override
    protected void computeAllVertices() {

        double S = 2 * getRadius() / (sqrt(5 * GOLDEN_RATIO));
        //double t1 = 2.0 * Math.PI / 5;
        double t2 = Math.PI / 10.0;
        double t4 = Math.PI / 5.0;
        //double t3 = -3.0 * Math.PI / 10.0;
        double R = (S / 2.0) / sin(t4);
        double h = cos(t4) * R;
        double Cx = R * sin(t2);
        double Cz = R * cos(t2);
        double H1 = sqrt(S * S - R * R);
        double H2 = sqrt((h + R) * (h + R) - h * h);
        double Y2 = (H2 - H1) / 2.0;
        double Y1 = Y2 + H1;

        Vector3 A = new Vector3(0, Y1, 0);
        Vector3 B = new Vector3(R, Y2, 0);
        Vector3 C = new Vector3(Cx, Y2, Cz);
        Vector3 D = new Vector3(-h, Y2, S / 2);
        Vector3 E = new Vector3(-h, Y2, -S / 2);
        Vector3 F = new Vector3(Cx, Y2, -Cz);
        Vector3 G = new Vector3(-R, -Y2, 0);
        Vector3 H = new Vector3(-Cx, -Y2, -Cz);
        Vector3 I = new Vector3(h, -Y2, -S / 2);
        Vector3 J = new Vector3(h, -Y2, S / 2);
        Vector3 K = new Vector3(-Cx, -Y2, Cz);
        Vector3 L = new Vector3(0, -Y1, 0);

        vertices.put('A', A);
        vertices.put('B', B);
        vertices.put('C', C);
        vertices.put('D', D);
        vertices.put('E', E);
        vertices.put('F', F);
        vertices.put('G', G);
        vertices.put('H', H);
        vertices.put('I', I);
        vertices.put('J', J);
        vertices.put('K', K);
        vertices.put('L', L);
    }

    protected void createAllFaces() {
        super.createAllFaces(Arrays.asList("ABC", "ACD", "ADE", "ABF", "AFE",
                "EGD", "EHF", "EHG", "FHI", "FIB",
                "BIJ", "BCJ", "CJK", "DCK", "DKG", "GHL", "HIL", "IJL", "KJL", "GKL"), 3);
    }
}
