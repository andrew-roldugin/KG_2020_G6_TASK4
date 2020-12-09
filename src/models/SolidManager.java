package models;

import threedimensions.geometry.Edge;
import threedimensions.geometry.face.Face;
import threedimensions.math.Vector3;
import threedimensions.geometry.Plane;
import org.apache.commons.math3.util.Pair;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SolidManager {
    //секущая плоскость
    private Plane plane;

    public SolidManager(Plane plane) {
        this.plane = plane;
    }

    /**
     * Разбивает исходное тело плоскостью
     * @param model исходное тело
     * @return результат работы: исходное тело, 1 часть (ниже плоскости), 2 часть (выше)
     */
    public AbstractModel[] partition(AbstractModel model) {
        Solid part1 = null;
        Solid part2 = null;
        try {
            part1 = new Solid(model);
            part2 = new Solid(model);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        getPart(
                part1,
                part1.intersectWith(plane),
                (v1) -> plane.valueAt(v1) > 0,
                (v2) -> plane.valueAt(v2) > -1e-5
        );

        getPart(
                part2,
                part2.intersectWith(plane),
                (v1) -> plane.valueAt(v1) < 0,
                (v2) -> plane.valueAt(v2) < 1e-5
        );
        return new AbstractModel[]{model, part1, part2};
    }

    private void getPart(Solid solid, Map<Face, List<Pair<Edge, Vector3>>> points,
                         Predicate<Vector3> predicate, Predicate<Vector3> predicate1) {
        if (points.isEmpty())
            return;
        for (Map.Entry<Face, List<Pair<Edge, Vector3>>> kv : points.entrySet()) {
            List<Vector3> temp = kv.getKey().getVertices()
                    .stream()
                    .filter(predicate)
                    .collect(Collectors.toCollection(ArrayList::new));
            new ArrayList<>(temp)
                    .forEach(point -> {
                        kv.getValue().forEach(pair -> {
                            if (pair.getKey().getEndVector().equals(point)) {
                                Vector3 val = pair.getValue();
                                int idx = temp.indexOf(point);
                                temp.add(idx, val);
                                pair.getKey().setFrom(val);
                            } else if (pair.getKey().getStartVector().equals(point)) {
                                Vector3 val = pair.getValue();
                                int idx = temp.indexOf(point);
                                temp.add(idx + 1, val);
                                pair.getKey().setTo(val);
                            }
                        });
                    });
            kv.getKey().setVertices(temp);
        }
        solid.faces = solid.faces
                .stream()
                .filter(face -> {
                    return face.getVertices()
                            .stream()
                            .takeWhile(predicate1).count() == face.getVertices().size();
                })
                .collect(Collectors.toList());
    }
}