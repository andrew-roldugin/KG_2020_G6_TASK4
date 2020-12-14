package threedimensions.color;

import java.awt.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomColor {
    private static final Set<Color> set = new HashSet<>();

    public static Color generateNewColor(){
        Color c;
        Random random = new Random();
        do {
            c = new Color(
                    random.nextInt(255),
                    random.nextInt(255),
                    random.nextInt(255)
            );
        }while (!set.add(c));
        return c;
    }
}
