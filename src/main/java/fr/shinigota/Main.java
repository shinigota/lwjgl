package fr.shinigota;

import fr.shinigota.engine.GameEngine;
import fr.shinigota.game.Controller;
import fr.shinigota.game.Game;
import fr.shinigota.game.Renderer;

public class Main {

    public static void main(String[] args) {
        try {
            boolean vSync = false;

            Controller gameController = new Controller();
            Renderer renderer = new Renderer(gameController);
            Game gameLogic = new Game(renderer, gameController);

            GameEngine gameEng = new GameEngine("Game", Game.WIDTH, Game.HEIGHT, vSync, gameLogic);
            gameEng.setInputProcessor(gameController);
            gameEng.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

}
