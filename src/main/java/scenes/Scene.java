package scenes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import components.Component;
import components.ComponentDeserializer;
import editor.GameViewWindow;
import engine.Camera;
import engine.GameObject;
import engine.GameObjectDeserializer;
import imgui.ImGui;
import renderer.Renderer;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Scene {
    protected Renderer renderer = new Renderer();
    protected Camera camera;
    private boolean isRunning = false;
    protected List<GameObject> gameObjects = new ArrayList<>();
    protected boolean levelLoaded = false;

    public Scene() {

    }

    public void init() {

    }

    public void start() {
        for (GameObject go : gameObjects) {
            go.start();
            this.renderer.add(go);
        }
        isRunning = true;
    }

    public void addGameObjectToScene(GameObject gameObject) {
        if (!isRunning) {
            gameObjects.add(gameObject);
        } else {
            gameObjects.add(gameObject);
            gameObject.start();
            renderer.add(gameObject);
        }
    }

    public GameObject getGameObject(int id) {
        Optional<GameObject> result = this.gameObjects.stream().filter(go -> go.getUid() == id).findFirst();
        return result.orElse(null);
    }

    public abstract void update(float dt);

    public abstract void render();

    public Camera camera() {
        return this.camera;
    }

    public void imgui() {

    }

    public void saveExit() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentDeserializer())
                .registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
                .create();

        try {
            FileWriter writer = new FileWriter("level.txt");
            writer.write(gson.toJson(this.gameObjects));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentDeserializer())
                .registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
                .create();
        String inFile = "";
        try {
            inFile = new String(Files.readAllBytes(Paths.get("level.txt")));

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!inFile.equals("")) {
            int maxGoId = -1;
            int maxCompId = -1;
            GameObject[] objects = gson.fromJson(inFile, GameObject[].class);
            for (int i = 0; i < objects.length; i++) {
                addGameObjectToScene(objects[i]);

                for (Component c : objects[i].getAllComponents()) {
                    if (c.uid() > maxCompId) {
                        maxCompId = c.uid();
                    }
                }
                if (objects[i].getUid() > maxGoId) {
                    maxGoId = objects[i].getUid();
                }
            }

            maxGoId++;
            maxCompId++;

            GameObject.init(maxGoId);
            Component.init(maxCompId);
            this.levelLoaded = true;
        }
    }
}
