package Model;

public class Game {
    private Game game;
    private Game(){

    }
    public Game getInstance(){
        if (game == null) return new Game();
        else return game;
    }
}
