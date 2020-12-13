package models.PlatonicSolid;

import models.Solid;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.Collectors;

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
    public static Solids getType(String n){
        //EnumSet<Solids> set = EnumSet.of(Solids.values());
        return Arrays.stream(values())
                .filter(solid -> solid.getName().equals(n))
                .collect(Collectors.toList())
                .get(0);
    }
}
