package physics2d.forces;

import org.joml.Vector2f;
import physics2d.rigidbody.Rigidbody2D;

public class Gravity2D implements ForceGenerator {
    private Vector2f gravity;

    public Gravity2D(Vector2f force) {
        this.gravity = new Vector2f(force);
    }

    @Override
    public void updateForce(Rigidbody2D rigidbody, float dt) {
        rigidbody.addForce(new Vector2f(gravity).mul(rigidbody.getMass()));
    }
}
