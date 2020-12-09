package models.PlatonicSolid;

import java.util.Arrays;

public enum Solids {
    TETRAHEDRON("Тетраэдр"),
    CUBE("Куб"),
    OCTAHEDRON("Октаэдр"),
    DODECAHEDRON("Додекаэдр"),
    ICOSAHEDRON("Икосаэдр");

    private String name;
    Solids(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public static String[] getNames(){
        return Arrays.stream(values()).map(solids -> solids.name).toArray(String[]::new);
    }
}
