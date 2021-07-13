import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.*;

/**
 * Handles the game lifecycle and behavior
 *
 * @author Bernardo Copstein and Rafael Copstein/*
 * @author José Junior Borges Monteiro
 */
public class Game {
    private static Game game = null;
    private Nave nave;
    private List<Character> activeChars;
    private boolean gameOver;
    private Integer pontos;

    private Game() {
        gameOver = false;
        pontos = 0;
    }

    public void setGameOver() {
        gameOver = true;
    }

    public void setNewGame() { // metodo utilizado para zerar o jogo após o game over
        game = null;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Integer getPontos() {
        return this.pontos;
    }

    public void incPontos(int quantPontos) {
        pontos = getPontos() + quantPontos;
    }

    public static Game getInstance() {
        if (game == null) {
            game = new Game();
        }
        return (game);
    }

    public void addChar(Character c) {
        activeChars.add(c);
        c.start();
    }

    public void eliminate(Character c) {
        activeChars.remove(c);
    }

    // metodos para a classe nave
    // -- metodos para munição:
    public int getQuantMunicao() {
        return nave.getQuantMunicao();
    }

    public void recarregaMunicao() {
        nave.recarregaMunicao();
    }

    //--- Metodos para vida
    public void aumentaVida() {
        if (getVidas() == 1) {
            nave.aumentaVida();
        }
    }

    public void diminuiVida() {
        nave.diminuiVida();
    }

    public int getVidas() {
        return nave.getVidas();
    }

    public void Start() {
        // Repositório de personagens
        activeChars = new LinkedList<>();
        // Adiciona o canhao
        nave = new Nave(400, 530); // posicao inicial da nave x - horizontal e y - vertical
        activeChars.add(nave);
        //Adiciona o kraken
        for (int i = 0; i < 3; i++) {
            activeChars.add(new Kraken(100 - (i * 150), 200));
        }
        //Adiciona o orion
        activeChars.add(new Orion(150, 150));
        activeChars.add(new Orion(300, 200));
        activeChars.add(new Orion(10, 300));
        // Adiciona a munição
        activeChars.add(new Municao());
        activeChars.forEach(c -> c.start());
    }

    public List<Character> getActiveChars() {
        return activeChars;
    }

    // Metodo utilizado para calcular a posicao dos personagens
    public void Update(long currentTime, long deltaTime) {
        if (gameOver) {
            return;
        }
        for (int i = 0; i < activeChars.size(); i++) {
            // chama o personagem e verifica o seu comportamento
            Character este = activeChars.get(i);
            este.Update(deltaTime);
            for (int j = 0; j < activeChars.size(); j++) {
                Character outro = activeChars.get(j);
                if (este != outro) {
                    este.testaColisao(outro);
                }
            }
        }
    }


    public void OnInput(KeyCode keyCode, boolean isPressed) {
        nave.OnInput(keyCode, isPressed);
    }

    //percorre a lista de personagens e reescreve as posicoes
    public void Draw(GraphicsContext graphicsContext) {
        for (Character c : activeChars) {
            c.Draw(graphicsContext);
        }
    }
}
