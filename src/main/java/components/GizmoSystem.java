package components;

import engine.KeyListener;
import engine.Window;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;

public class GizmoSystem extends Component {
    private Spritesheet gizoms;
    private int usingGizmo = 0;

    public GizmoSystem(Spritesheet gizmoSprites) {
        gizoms = gizmoSprites;
    }

    @Override
    public void start() {
        gameObject.addComponent(new TranslateGizmo(gizoms.getSprite(1),
                Window.getImguiLayer().getPropertiesWindow()));
        gameObject.addComponent(new ScaleGizmo(gizoms.getSprite(2),
                Window.getImguiLayer().getPropertiesWindow()));
    }

    @Override
    public void update(float dt) {
        if (usingGizmo == 0) {
            gameObject.getComponent(TranslateGizmo.class).setUsing();
            gameObject.getComponent(ScaleGizmo.class).setNotUsing();
        } else if (usingGizmo == 1) {
            gameObject.getComponent(ScaleGizmo.class).setUsing();
            gameObject.getComponent(TranslateGizmo.class).setNotUsing();
        }


        if (KeyListener.isKeyPressed(GLFW_KEY_E)) {
            usingGizmo = 0;
        } else if (KeyListener.isKeyPressed(GLFW_KEY_R)) {
            usingGizmo = 1;
        }
    }
}
