package components;

import engine.Camera;
import engine.Window;
import org.joml.Vector2f;
import org.joml.Vector3f;
import renderer.DebugDraw;
import util.Settings;

public class GridLines extends Component {
    @Override
    public void editorUpdate(float dt) {
        Camera camera = Window.getScene().camera();
        Vector2f caperaPosition = camera.position;
        Vector2f projectionSize = camera.getProjectionSize();

        float firstX = (caperaPosition.x / Settings.GRID_WIDTH - 1) * Settings.GRID_WIDTH;
        float firstY = (caperaPosition.y / Settings.GRID_HEIGHT - 1) * Settings.GRID_HEIGHT;

        int numVerLines = (int) (projectionSize.x * camera.getZoom() / Settings.GRID_WIDTH) + 2;
        int numHorLines = (int) (projectionSize.y * camera.getZoom() / Settings.GRID_HEIGHT) + 2;

        float width = projectionSize.x * camera.getZoom() + Settings.GRID_WIDTH * 2;
        float height = projectionSize.y * camera.getZoom() + Settings.GRID_HEIGHT * 2;

        int maxLines = Math.max(numVerLines, numHorLines);
        Vector3f color = new Vector3f(0.2f, 0.2f, 0.2f);

        for (int i = 0; i < maxLines; i++) {
            float x = firstX + (Settings.GRID_WIDTH * i);
            float y = firstY + (Settings.GRID_HEIGHT * i);

            if (i < numVerLines) {
                DebugDraw.addLine2D(new Vector2f(x, firstY), new Vector2f(x, firstY + height), color, 2);
            }

            if (i < numHorLines) {
                DebugDraw.addLine2D(new Vector2f(firstX, y), new Vector2f(firstX + width, y), color, 2);
            }
        }
    }
}
