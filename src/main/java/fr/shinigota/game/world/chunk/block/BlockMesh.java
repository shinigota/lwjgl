package fr.shinigota.game.world.chunk.block;

import fr.shinigota.engine.graphic.mesh.FaceMesh;
import fr.shinigota.engine.graphic.mesh.Mesh;
import fr.shinigota.engine.graphic.texture.Texture;

public class BlockMesh {
    public final Mesh grass;
    public final Mesh dirt;
    public final Mesh stone;
    public final Mesh sand;

    public BlockMesh(Texture texture) {
        grass = Mesh.FACTORY.cubeMesh(BlockType.GRASS.getCubeTexture(texture));
        dirt = Mesh.FACTORY.cubeMesh(BlockType.DIRT.getCubeTexture(texture));
        stone = FaceMesh.down(BlockType.STONE.getCubeTexture(texture).getDown());
        sand = Mesh.FACTORY.cubeMesh(BlockType.SAND.getCubeTexture(texture));
    }
}
