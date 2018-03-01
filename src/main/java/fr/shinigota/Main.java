package fr.shinigota;

import fr.shinigota.engine.GameEngine;
import fr.shinigota.engine.IGameLogic;
import fr.shinigota.game.Controller;
import fr.shinigota.game.Game;

public class Main {

    public static void main(String[] args) {
        try {
            boolean vSync = true;
            Controller gameController = new Controller();
            IGameLogic gameLogic = new Game(gameController);
            GameEngine gameEng = new GameEngine("Game", 1280, 720, vSync, gameLogic);
            gameEng.setInputProcessor(gameController);
            gameEng.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

}
