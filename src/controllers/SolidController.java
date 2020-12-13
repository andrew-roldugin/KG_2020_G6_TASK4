package controllers;

import models.AbstractModel;
import models.SolidManager;
import threedimensions.geometry.Plane;

public class SolidController {
    private final Plane plane;
    private AbstractModel solid;
    private final SolidManager mgr;
    private Parts part = Parts.ORIGINAL;

    public SolidController(Plane plane) {
        this.plane = plane;
        this.mgr = new SolidManager(plane);
    }

    public void selectPart(Parts p) {
        this.part = p;
    }

    public void selectSolid(AbstractModel solid) {
        this.solid = solid;
    }

    public AbstractModel getSolid() {
        return solid;
    }

    public Parts getPart() {
        return part;
    }

    public AbstractModel getRequiredPart(AbstractModel solid, Parts part) {
        if (solid == null || plane == null)
            return null;
        return mgr.partition(solid, part);
    }

    public enum Parts {
        ORIGINAL,
        FIRST,
        SECOND,
        SECTION
    }
}