package fr.shinigota.engine.graphic;

import fr.shinigota.engine.entity.GameItem;
import fr.shinigota.engine.graphic.texture.Texture;

public class Skybox extends GameItem {
    public Skybox(Texture texture) throws Exception {
        super();
        Mesh skyboxMesh = Mesh.FACTORY.cubeMesh(texture);
        setPosition(0, 0, 0);
        setScale(50f);

        setMesh(skyboxMesh);
    }
}
