package physics2d.rigidbody;

import components.Component;
import org.joml.Vector2f;

public class Rigidbody2D extends Component {
    private Vector2f position = new Vector2f();
    private float rotation = 0f; // in degrees

    private Vector2f linearVelocity = new Vector2f();
    private float angularVelocity = 0f;
    private float linearDamping = 0;
    private float angularDamping = 0;

    private boolean fixedRotations = false;

    public Vector2f getPosition() {
        return position;
    }

    public float getRotation() {
        return rotation;
    }

    public void setTransform(Vector2f position, float rotation) {
        this.position.set(position);
        this.rotation = rotation;
    }

    public void setTransform(Vector2f position) {
        this.position.set(position);
    }
}
