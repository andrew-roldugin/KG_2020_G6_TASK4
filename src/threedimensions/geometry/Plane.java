package threedimensions.geometry;

import threedimensions.math.Vector3;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Класс, описывающий плоскость
 */
public class Plane {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private float A, B, C, D;

    public Plane(){
        this(0, 0, 0,  0);
    }

    public Plane(float a, float b, float c, float d) {
        this.A = a;
        this.B = b;
        this.C = c;
        this.D = d;
    }

    public Plane(Vector3 point1, Vector3 point2, Vector3 point3) {
        new Plane(createEquation(point1, point2, point3));
    }

    private Plane(float[] arr){
        this.A = arr[0];
        this.B = arr[1];
        this.C = arr[2];
        this.D = arr[3];
    }
    /**
     * Создает уравнение плоскости по 3 точкам
     * @return найденные коэффициенты
     * @param point1
     * @param point2
     * @param point3
     */
    private float[] createEquation(Vector3 point1, Vector3 point2, Vector3 point3){
        float a = point2.getX() - point1.getX();
        float b = point2.getY() - point1.getY();
        float c = point2.getZ() - point1.getZ();
        float d = point3.getX() - point1.getX();
        float e = point3.getY() - point1.getY();
        float f = point3.getZ() - point1.getZ();
        float A = b * f - e * c;
        float B = a * f - d * c;
        float C = a * e - b * d;
        float D = point1.getY() * B - point1.getX() * A - point1.getZ() * C;
        return new float[]{A, -B, C, D};
    }

    /**
     *
     * @return вектор нормали для данной плоскости
     */
    public Vector3 getNormal() {
        return new Vector3(getA(), getB(), getC());
    }

    public Vector3 getABD() {
        return new Vector3(getA(), getB(), getD());
    }
    public Vector3 getACD() {
        return new Vector3(getA(), getC(), getD());
    }

    public Vector3 getBCD() {
        return new Vector3(getB(), getC(), getD());
    }

    public float getA() {
        return this.A;
    }
    public float getB() {
        return this.B;
    }

    public float getC() {
        return this.C;
    }

    public float getD() {
        return this.D;
    }

    public void setA(float newVal){
        support.firePropertyChange("A", this.A, newVal);
        this.A = newVal;
    }

    public void setB(float newVal){
        support.firePropertyChange("B", this.B, newVal);
        this.B = newVal;
    }

    public void setC(float newVal){
        support.firePropertyChange("C", this.C, newVal);
        this.C = newVal;
    }

    public void setD(float newVal){
        support.firePropertyChange("D", this.D, newVal);
        this.D = newVal;
    }

    public double valueAt(Vector3 point){
        return getNormal().dotProduct(point) + getD();
    }

    public void setNewValue(String value, float num) {
        synchronized (this) {
            switch (value.charAt(0)) {
                case 'A':
                    setA(num);
                    break;
                case 'B':
                    setB(num);
                    break;
                case 'C':
                    setC(num);
                    break;
                case 'D':
                    setD(num);
                    break;
                default:
                    break;
            }
        }
    }

    public void addPropertyChangeListener(String name, PropertyChangeListener l) {
        support.addPropertyChangeListener(name, l);
    }

    public void removePropertyChangeListener(String name, PropertyChangeListener l) {
        support.removePropertyChangeListener(name, l);
    }

    public boolean isPresent() {
        return getA() != 0 || getB() != 0 || getC() != 0;
    }
}
