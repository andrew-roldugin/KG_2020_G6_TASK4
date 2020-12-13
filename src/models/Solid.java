package models;

public class Solid extends AbstractModel{

    public Solid(AbstractModel model) throws CloneNotSupportedException {
        super(model.getRadius());
        AbstractModel clone = model.clone();
        //this.vertices = clone.vertices;
        this.faces = clone.faces;
    }

    @Override
    protected void computeAllVertices() {

    }
}
