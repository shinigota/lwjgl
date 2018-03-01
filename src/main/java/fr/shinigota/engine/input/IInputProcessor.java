package fr.shinigota.engine.input;

public interface IInputProcessor {
    void mouseMoved(double x, double y);
    void mouseEntered();
    void mouseLeaved();
    void mouseClick(int button, int type);
}
