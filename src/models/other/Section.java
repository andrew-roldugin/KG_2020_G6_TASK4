package models.other;

import models.common.AbstractModel;
import threedimensions.geometry.face.Face;
import threedimensions.math.Vector3;

import java.util.Arrays;
import java.util.List;

public class Section extends AbstractModel {
    private List<Vector3> list;

    public Section(List<Vector3> list) {
        super(0);
        this.list = list;
        faces = Arrays.asList(new Face(list, 0));
    }

    @Override
    protected void computeAllVertices() {
    }
}
