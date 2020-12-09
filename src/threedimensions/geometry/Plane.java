package threedimensions.geometry;

import threedimensions.math.Vector3;

/**
 * Класс, описывающий плоскость
 */
public class Plane {
    private float[] coef = new float[4];
    private Vector3 point1, point2, point3;

    public Plane(float a, float b, float c, float d) {
        this.coef[0] = a;
        this.coef[1] = b;
        this.coef[2] = c;
        this.coef[3] = d;
    }

    public Plane(Vector3 point1, Vector3 point2, Vector3 point3) {
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
        this.coef = createEquation();
    }

    /**
     * Создает уравнение плоскости по 3 точкам
     * @return найденные коэффициенты
     */
    private float[] createEquation(){
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

    public float getA() {
        return (float) this.coef[0];
    }
    public float getB() {
        return (float) this.coef[1];
    }
    public float getC() {
        return (float) this.coef[2];
    }

    public float getD() {
        return (float) this.coef[3];
    }

    public double valueAt(Vector3 point){
        return getNormal().dotProduct(point) + getD();
    }

}
