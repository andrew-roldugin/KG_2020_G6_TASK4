package models;

import threedimensions.geometry.Edge;
import threedimensions.geometry.face.Face;
import threedimensions.geometry.face.FaceFactory;
import threedimensions.math.Vector3;
import threedimensions.third.IModel;
import threedimensions.geometry.Plane;
import threedimensions.third.PolyLine3D;
import org.apache.commons.math3.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractModel implements IModel, Cloneable {

    public static final float GOLDEN_RATIO = 1.6180339887499f;
    //радиус описанной сферы
    private double radius;
    //все вершины многогранника
    protected Map<Character, Vector3> vertices;
    //все грани
    protected List<Face> faces;

    //все ребра многогранника
    //protected List<Edge> edges;


    public AbstractModel(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public List<PolyLine3D> getLines() {
        /*List<PolyLine3D> lines = new ArrayList<>();

        faces.forEach(edges ->
                lines.add(new PolyLine3D(edges
                        .stream()
                        .map(Edge::getStartVector)
                        .collect(Collectors.toCollection(ArrayList::new)), true)));
         */
        return asPolyline(faces);
    }

    private ArrayList<PolyLine3D> asPolyline(List<Face> list) {
        return list.stream()
                .map(face -> new PolyLine3D(face.getVertices(), true))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    protected abstract void computeAllVertices();

    protected void createAllFaces(List<String> list, int num) {
        for (String str: list) {
            List<Vector3> subList = new ArrayList<>();
            for (int i = 0; i < str.length(); i++) {
                char ch = str.charAt(i);
                subList.add(vertices.get(ch));
            }
            faces.add(FaceFactory.getFace(subList, num));
        }
    }

    public Map<Face, List<Pair<Edge, Vector3>>> intersectWith(Plane p){
        Map<Face, List<Pair<Edge, Vector3>>> points = new HashMap<>();
        for (Face face : faces) {
            Vector3 temp = null;
            for (Edge edge : face.getEdges()) {
                Vector3 res = edge.intersectWith(p);
                if(res != null) {
                    if (res.equals(temp)) {
                        temp = null;
                        continue;
                    }
                    if (points.containsKey(face))
                        points.get(face).add(new Pair<>(edge, res));
                    else {
                        ArrayList<Pair<Edge, Vector3>> collection = new ArrayList<Pair<Edge, Vector3>>();
                        collection.add(new Pair<Edge, Vector3>(edge, res));
                        points.put(face, collection);
                    }
                    if (res.equals(edge.getStartVector()) || res.equals(edge.getEndVector())) {
                        temp = res;
                    }
                }
            }
        }
        return points;
    }

    @Override
    protected AbstractModel clone() throws CloneNotSupportedException {
        AbstractModel model = (AbstractModel) super.clone();
        model.faces = faces.stream().map(Face::clone).collect(Collectors.toCollection(ArrayList::new));
        return model;
    }
}
