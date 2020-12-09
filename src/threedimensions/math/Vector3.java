/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package threedimensions.math;

import java.util.Arrays;

import static java.lang.StrictMath.sqrt;

/**
 * Класс, хранящий трёхмерный вектор / точку в трёхмерном пространстве.
 * @author Alexey
 */
public class Vector3 implements Cloneable{
    private float[] values; /*Значения хранятся в виде массива из трёх элементов*/
    
    /**
     * Создаёт экземпляр вектора на основе значений трёх составляющих
     * @param x первая составляющая, описывающая X-координату
     * @param y вторая составляющая, описывающая Y-координату
     * @param z третья составляющая, описывающая Z-координату
     */

    public Vector3(float x, float y, float z) {
        values = new float[]{x, y, z};
    }

    public Vector3(double x, double y, double z) {
        values = new float[]{(float) x, (float) y, (float) z};
    }
    /**
     * X-составляющая вектора
     * @return X-составляющая вектора
     */
    public float getX() {
        return values[0];
    }

    /**
     * Y-составляющая вектора
     * @return Y-составляющая вектора
     */
    public float getY() {
        return values[1];
    }

    /**
     * Z-составляющая вектора
     * @return Z-составляющая вектора
     */
    public float getZ() {
        return values[2];
    }
    
    /**
     * Метод, возвращающий составляющую вектора по порядковому номеру
     * @param idx порядковый номер
     * @return значение составляющей вектора
     */
    public float at(int idx) {
        return values[idx];
    }
    
    public static final float EPSILON = 1e-10f;
    /**
     * Метод, возвращающий длину вектора
     * @return длина вектора
     */
    public float length() {
        float lenSqr = values[0] * values[0] + values[1] * values[1] + values[2] * values[2];
        if (lenSqr < EPSILON)
            return 0;
        return (float)Math.sqrt(lenSqr);
    }

    /**
     * Метод, складывающий два вектора
     * @param other 2 вектор
     * @return возвращает результирующий вектор
     */
    public Vector3 add(Vector3 other){
        return new Vector3(this.getX() + other.getX(),
                this.getY() + other.getY(),
                this.getZ() + other.getZ());
    }

    /**
     * Метод, вычитающий два вектора
     * @param other 2 вектор
     * @return возвращает результирующий вектор
     */
    public Vector3 subtract(Vector3 other){
        return new Vector3(
                this.getX() - other.getX(),
                this.getY() - other.getY(),
                this.getZ() - other.getZ()
        );
    }

    public Vector3 mul(double a) {
        return new Vector3(
                a * getX(),
                a * getY(),
                a * getZ()
        );
    }
    public float getNorm(){
        return this.getX() * this.getX() + this.getY() * this.getY() + this.getZ() * this.getZ();
    }

    public Vector3 normalized(Vector3 v) {
        double len = v.length();
        if (len < EPSILON)
            return new Vector3(this.getX(), this.getY(), this.getZ());
        len = 1 / sqrt(len);
        return v.mul(len);
    }

    /**
     * Вычисляет векторное произведение двух векторов
     *
     * @param v1 первый вектор
     * @param v2 второй вектор
     * @returns Новый результирующий вектор
     */
    public Vector3 crossProduct(Vector3 v1, Vector3 v2) {
        double ax = v1.getX(), ay = v1.getY(), az = v1.getZ(),
                bx = v2.getX(), by = v2.getY(), bz = v2.getZ();
        return new Vector3(
                ay * bz - az * by,
                az * bx - ax * bz,
                ax * by - ay * bx
        );
    }
    /**
     * Вычисляет скалярное произведение двух векторов
     *
     * @param
     * @returns Новый результирующий вектор
     */
    public float dotProduct(Vector3 v2) {
        return this.getX() * v2.getX() + this.getY() * v2.getY() + this.getZ() * v2.getZ();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector3 vector3 = (Vector3) o;

        return Arrays.equals(values, vector3.values);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(values);
    }

    @Override
    public Vector3 clone(){
        return new Vector3(this.getX(), this.getY(), this.getZ());
    }
}
