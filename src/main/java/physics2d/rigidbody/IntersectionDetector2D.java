package physics2d.rigidbody;

import org.joml.Vector2f;
import physics2d.primitives.AABB;
import physics2d.primitives.Box2D;
import physics2d.primitives.Circle;
import renderer.Line2D;
import util.Maths;

public class IntersectionDetector2D {
    // ===================================
    // Point vs Primitives tests
    // ===================================
    public static boolean pointOnLine(Vector2f point, Line2D line) {
        float dy = line.getEnd().y - line.getStart().y;
        float dx = line.getEnd().x - line.getStart().x;
        float m = dy / dx;
        float b = line.getEnd().y - m * line.getEnd().x;
        // check line equation
        return point.y == m * point.x + b;
    }

    public static boolean pointInCircle(Vector2f point, Circle circle) {
        Vector2f circleCenter = circle.getCenter();
        Vector2f centerToPoint = new Vector2f(point).sub(circleCenter);

        return centerToPoint.lengthSquared() < circle.getRadius() * circle.getRadius();
    }

    public static boolean pointInAABB(Vector2f point, AABB box) {
        Vector2f min = box.getMin();
        Vector2f max = box.getMax();

        return point.x <= max.x && min.x <= point.x &&
                point.y <= max.y && min.y <= point.y;
    }

    public static boolean pointInBox2D(Vector2f point, Box2D box) {
        Vector2f localPoint = new Vector2f(point);
        Maths.rotate(localPoint, box.getRigidbody().getRotation(), box.getRigidbody().getPosition());

        Vector2f min = box.getMin();
        Vector2f max = box.getMax();

        return localPoint.x <= max.x && min.x <= localPoint.x &&
                localPoint.y <= max.y && min.y <= localPoint.y;
    }

    // ===================================
    // Line vs Primitives tests
    // ===================================
}
