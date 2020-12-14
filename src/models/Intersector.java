package models;

import controllers.SolidController;
import models.common.AbstractModel;
import models.common.Solid;
import models.other.Section;
import threedimensions.geometry.Edge;
import threedimensions.geometry.face.Face;
import threedimensions.math.Vector3;
import threedimensions.geometry.Plane;
import org.apache.commons.math3.util.Pair;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Intersector {
    //секущая плоскость
    private Plane plane;

    public Intersector(Plane plane) {
        this.plane = plane;
    }

    /**
     * Разбивает исходное тело плоскостью
     * @param model исходное тело
     * @return результат работы: исходное тело, 1 часть (ниже плоскости), 2 часть (выше)
     */
    public AbstractModel partition(AbstractModel model, SolidController.Parts parts) {
        if(parts == SolidController.Parts.ORIGINAL)
            return model;

        Solid part = null;
        try {
            part = new Solid(model);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        switch (parts){
            case SECTION -> {
                return getSection(part.intersectWith(plane));
            }

            case FIRST -> {
                getPart(
                        part,
                        part.intersectWith(plane),
                        (v1) -> plane.valueAt(v1) >= 0,
                        (v2) -> plane.valueAt(v2) > -1e-5
                );
                return part;
            }
            case SECOND -> {
                getPart(
                        part,
                        part.intersectWith(plane),
                        (v1) -> plane.valueAt(v1) <= 0,
                        (v2) -> plane.valueAt(v2) < 1e-5
                );
                return part;
            }
            default -> {
                return null;
            }
        }
    }

    private AbstractModel getSection(Map<Face, List<Pair<Edge, Vector3>>> faceListMap){
        if(faceListMap.isEmpty())
            return null;
        Queue<Edge> queueEdges = new LinkedList<>();
        for (List<Pair<Edge, Vector3>> l : faceListMap.values()) {
            if(l.size() != 2)
                continue;
            queueEdges.add(new Edge(l.get(0).getValue(), l.get(1).getValue()));
        }

        if(queueEdges.isEmpty())
            return null;
        ArrayList<Edge> temp = new ArrayList<>();
        temp.add(queueEdges.poll());
        int i = 0;
        while (!queueEdges.isEmpty()) {
            Edge e = queueEdges.poll();
            if (temp.get(i).getEndVector().equals(e.getStartVector())) {
                temp.add(e);
                i++;
            } else {
                Edge e1 = e.reverse();
                if (temp.get(i).getEndVector().equals(e1.getStartVector())) {
                    temp.add(e1);
                    i++;
                }else
                    queueEdges.add(e);
            }
        }
        ArrayList<Vector3> res = temp
                .stream()
                .map(Edge::getStartVector)
                .collect(Collectors.toCollection(ArrayList::new));
        return new Section(res);
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
            if(temp.isEmpty())
                continue;
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
        solid.setFaces(solid.getFaces()
                .stream()
                .filter(face -> {
                    return face.getVertices()
                            .stream()
                            .filter(predicate1).count() == face.getVertices().size();
                })
                .collect(Collectors.toList())
        );
    }
}