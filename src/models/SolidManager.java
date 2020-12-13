package models;

import controllers.SolidController;
import threedimensions.geometry.Edge;
import threedimensions.geometry.face.Face;
import threedimensions.math.Vector3;
import threedimensions.geometry.Plane;
import org.apache.commons.math3.util.Pair;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
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
      /*  Solid part1 = null;
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

       */
    }

    private AbstractModel getSection(Map<Face, List<Pair<Edge, Vector3>>> faceListMap){
       /* Set<Vector3> set = new HashSet<>();
        List<Vector3> list =  new ArrayList<>();
        List<Edge> listEdges =  new ArrayList<>();
        if(faceListMap.isEmpty())
            return null;
        //Edge edge = faceListMap.keySet();
        Queue<Vector3> queue = new LinkedList<>();
        for (List<Pair<Edge, Vector3>> l : faceListMap.values()) {
            for (Pair<Edge, Vector3> pair : l) {
                Vector3 vector3 = pair.getValue();
                if(!queue.contains(vector3))
                    /*for (int i = 1; i < list.size(); i += 2) {
                        if(list.get(i).compareTo(l.get(0).getValue()));
                    }


                    queue.add(vector3);
            }
            /*Comparator<Vector3> comparator = new Comparator<Vector3>() {
                @Override
                public int compare(Vector3 o1, Vector3 o2) {
                    return (int) (o1.length() - o2.length());
                }
            };
            comparator.thenComparing(new Comparator<Vector3>() {
                @Override
                public int compare(Vector3 o1, Vector3 o2) {
                    return 0;
                }
            });


        }

        */

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
       /* res.add(list.get(0));
        for (int i = 1; i < list.size() - 1 ; i++) {
          //  if(i == list.size() - 1){
          //      res.add(list.get(i));
          //      break;
          //  }else{
                res.add(i == 1 ? selectMaxOf3(res.get(0), list.get(i), list.get(i + 1)) : selectMinOf3(res.get(i - 1), list.get(i - 1), list.get(i + 1)));
          //  }
        }
        res.add(list.get(1));
        //list.sort((v1, v2) ->  (v2.length() - v1.length()) < -1e-6 ? -1 : (v2.length() - v1.length()) > 1e-6 ? 1 : 0);
       /* list.sort(new Comparator<Vector3>() {

            public int compare(Vector3 o1, Vector3 o2) {
                return (int) (o2.subtract(o1).length());

                /*return (int) (o1.getX() == o2.getX() ? o2.subtract(new Vector3(o2.getX(), 0, 0)).length() - o1.subtract(new Vector3(o1.getX(), 0, 0)).length()
                        : (o1.getY() == o2.getY() ? o2.subtract(new Vector3(0, o2.getY(), 0)).length() - o1.subtract(new Vector3(0, o1.getY(), 0)).length()
                        : o1.getZ() == o2.getZ() ? o2.subtract(new Vector3(0, 0, o2.getZ())).length() - o1.subtract(new Vector3(0, 0, o1.getZ())).length() : 0));



            }

        });

        */
        ArrayList<Vector3> res = temp
                .stream()
                .map(Edge::getStartVector)
                .collect(Collectors.toCollection(ArrayList::new));
        return new Section(res);
    }

   /* private Vector3 selectMinOf3(Vector3 a, Vector3 b, Vector3 c){
        return b.subtract(a).length() < c.subtract(a).length() ? b : c;
    }
    private Vector3 selectMaxOf3(Vector3 a, Vector3 b, Vector3 c){
        return b.subtract(a).length() < c.subtract(a).length() ? c : b;
    }

    */
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
        solid.faces = solid.faces
                .stream()
                .filter(face -> {
                    return face.getVertices()
                            .stream()
                            .filter(predicate1).count() == face.getVertices().size();
                })
                .collect(Collectors.toList());
    }
}