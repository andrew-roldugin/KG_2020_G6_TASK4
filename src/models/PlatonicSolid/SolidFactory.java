package models.PlatonicSolid;

import models.common.AbstractModel;

public class SolidFactory {

    public static AbstractModel createSolid(Solids type, double radius) {
        switch (type){
            case CUBE:
                return new Cube(radius);
            case OCTAHEDRON:
                return new Octahedron(radius);
            case ICOSAHEDRON:
                return new Icosahedron(radius);
            case TETRAHEDRON:
                return new Tetrahedron(radius);
            case DODECAHEDRON:
                return new Dodecahedron(radius);
            default:
                return null;
        }
    }
}
