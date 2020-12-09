package threedimensions.geometry.face;

import threedimensions.geometry.Edge;
import threedimensions.math.Vector3;

import java.util.*;
import java.util.stream.Collectors;

import static threedimensions.geometry.Edge.createEdges;

/*
Класс, описывающий грань
 */
public class Face implements Cloneable{
    //множество вершин, из которых состоит грань
    private List<Vector3> vertices;
    //множество ребер грани
    private List<Edge> edges;
    //длина ребра
    private double a;
    //размерность правильного n-угольника
    private int num;

    public Face(Collection<Vector3> vertices, int num) {
        this.vertices = new ArrayList<>(vertices);
        this.edges = createEdges(this.vertices, num);
        this.num = num;
    }

    public Face(Collection<Vector3> vertices, double a, int num) {
        this.vertices = new ArrayList<>(vertices);
        this.edges = createEdges(this.vertices, num);
        this.a = a;
    }

    public List<Vector3> getVertices() {
        return vertices;
    }

    public double getA() {
        return a;
    }

    public void setVertices(List<Vector3> vertices) {
        this.vertices = vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    @Override
    public Face clone() {
        Face face = null;
        try {
            face = (Face) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        face.vertices = vertices.stream().map(Vector3::clone).collect(Collectors.toCollection(ArrayList::new));
        face.edges = edges.stream().map(Edge::clone).collect(Collectors.toCollection(ArrayList::new));
        return face;
    }

    public int getNum() {
        return num;
    }

    public double getASquared(){
        return a * a;
    }
}
