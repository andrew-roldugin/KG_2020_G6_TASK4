/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package threedimensions;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.*;

import threedimensions.draw.IDrawer;
import threedimensions.draw.SimpleEdgeDrawer;
import threedimensions.math.Vector3;
import threedimensions.screen.ScreenConverter;
import threedimensions.third.Camera;
import threedimensions.geometry.Plane;
import threedimensions.third.Scene;
import models.*;
import models.PlatonicSolid.*;

/**
 *
 * @author Alexey
 */
public class DrawPanel extends JPanel
        implements CameraController.RepaintListener {
    private Scene scene;
    private ScreenConverter sc;
    private Camera cam;
    private CameraController camController;

    public DrawPanel() {
        super();
        sc = new ScreenConverter(-1, 1, 2, 2, 1, 1);
        cam = new Camera();
        camController = new CameraController(cam, sc);
        scene = new Scene(Color.WHITE.getRGB());
        scene.showAxes();
        
        /*scene.getModelsList().add(new Parallelepiped(
                new Vector3(-0.4f, -0.4f, -0.4f), 
                new Vector3(0.4f, 0.4f, 0.4f)
        ));

         */

        //scene.getModelsList().add(t);
        Plane pl = new Plane(
                new Vector3(1.34, -0.49, 0),
                new Vector3(0, -1.03, 0),
                new Vector3(0, 0, 0.75)
        );

        Plane pl1 = new Plane(
                new Vector3(-3.04312,-1.37837,0),
                new Vector3(0,-1.03,0),
                new Vector3(2.31938,1.775,0.75)
        );

        SolidManager solidManager = new SolidManager(pl);
        scene.getModelsList().add(solidManager.partition(new Icosahedron(3))[0]);

        camController.addRepaintListener(this);
        addMouseListener(camController);
        addMouseMotionListener(camController);
        addMouseWheelListener(camController);
    }
    
    @Override
    public void paint(Graphics g) {
        sc.setScreenSize(getWidth(), getHeight());
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D)bi.getGraphics();
        IDrawer dr = new SimpleEdgeDrawer(sc, graphics);
        scene.drawScene(dr, cam);
        g.drawImage(bi, 0, 0, null);
        graphics.dispose();
    }

    @Override
    public void shouldRepaint() {
        repaint();
    }
    public void add(){
        scene.getModelsList().add(new Icosahedron(1));
    }
}
