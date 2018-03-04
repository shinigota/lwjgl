package fr.shinigota.engine;

public interface IGameLogic {
    void init(Window window) throws Exception;
    void update(float interval);
    void render(Window window);
    void cleanup();
    void resize(Window window) throws Exception;
}
