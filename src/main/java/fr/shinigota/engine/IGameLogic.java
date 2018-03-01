package fr.shinigota.engine;

import fr.shinigota.engine.input.IInputProcessor;
import fr.shinigota.engine.input.MouseInput;
import fr.shinigota.game.Controller;

public interface IGameLogic {
    void init(Window window) throws Exception;
    void input(Window window);
    void update(float interval);
    void render(Window window);
    void cleanup();
    void resize(Window window) throws Exception;
}
