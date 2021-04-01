package renderer;

import components.SpriteRenderer;
import engine.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Renderer {
    private final int MAX_BATCH_SIZE = 1000;
    private List<RenderBatch> renderBatches;

    public Renderer() {
        this.renderBatches = new ArrayList<>();
    }

    public void add(GameObject go) {
        SpriteRenderer spr = go.getComponent(SpriteRenderer.class);
        if (spr != null) {
            add(spr);
        }
    }

    private void add(SpriteRenderer spr) {
        boolean added = false;
        for (RenderBatch batch : renderBatches) {
            if (batch.hasRoom()) {
                Texture tex = spr.getTexture();

                if (tex == null || (batch.hasTexture(tex) || batch.hasTextureRoom())) {
                    batch.addSprite(spr);
                    added = true;
                    break;
                }
            }
        }

        if (!added) {
            RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE);
            newBatch.start();
            renderBatches.add(newBatch);
            newBatch.addSprite(spr);
        }
    }

    public void render() {
        for (RenderBatch batch : renderBatches) {
            batch.render();
        }
    }
}
