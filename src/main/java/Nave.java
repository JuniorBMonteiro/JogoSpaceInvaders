import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

/**
 * Represents the game Gun
 *
 * @author Bernardo Copstein, Rafael Copstein
 * @author José Junior Borges Monteiro
 */
public class Nave extends BasicElement implements KeyboardCtrl {
    private int RELOAD_TIME = 500000000; // Time is in nanoseconds
    private int shot_timer = 0;
    private Image nave;
    private Image nave1;
    private int quantMunicao = 10;
    private int vidas = 2;
    private int animacao = 0;

    public Nave(int px, int py) {
        super(px, py);
        try {
            nave = new Image("nave.png", 0, 60, true, true);
            nave1 = new Image("nave1.png", 0, 60, true, true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
    public int getQuantMunicao() {
        return quantMunicao;
    }

    public int getVidas() {
        return vidas;
    }

    public void aumentaVida() {
        this.vidas = getVidas() + 1;
    }

    public void diminuiVida() {
        this.vidas = getVidas() - 1;
    }

    public void recarregaMunicao() {
        this.quantMunicao = this.quantMunicao + 15;
    }

    @Override
    public void start() {
        setLimH(20, Params.WINDOW_WIDTH - 20);
        setLimV(Params.WINDOW_HEIGHT - 100, Params.WINDOW_HEIGHT);
        setSpeed(5);
        setLargAlt(60,60);
    }

    @Override
    public void Update(long deltaTime) {
        if (getDirH() == 1) {
            if (getX() < getLMaxH() - 50) {
                setPosX(getX() + getDirH() * getSpeed());
            }
        } else {
            if (getX() > getLMinH() - 20) {
                setPosX(getX() + getDirH() * getSpeed());
            }
        }
        if (vidas == 0) {
            Game.getInstance().setGameOver();
        }
        if (shot_timer > 0) shot_timer -= deltaTime;
    }

    @Override
    public void OnInput(KeyCode keyCode, boolean isPressed) {
        // verifica o botao pressionado
        if (keyCode == KeyCode.LEFT) {
            int dh = isPressed ? -1 : 0;
            setDirH(dh);
        }
        if (keyCode == KeyCode.RIGHT) {
            int dh = isPressed ? 1 : 0;
            setDirH(dh);
        }
        if (keyCode == KeyCode.SPACE) {
            if (shot_timer <= 0 && quantMunicao != 0) {
                Game.getInstance().addChar(new Shot(getX() + 34, getY() - 32));
                shot_timer = RELOAD_TIME;
                quantMunicao--;
            }
        }
    }

    @Override
    public int getAltura() {
        return 60;
    }

    @Override
    public int getLargura() {
        return 60;
    }


    @Override
    public void Draw(GraphicsContext graphicsContext) {
        if (animacao < 15 || animacao >= 30) {
            if (animacao > 30) {
                animacao = 0;
            }
            graphicsContext.drawImage(nave, getX(), getY());
            animacao++;
        } else if (animacao < 30) {
            graphicsContext.drawImage(nave1, getX(), getY());
            animacao++;
        }
    }
}


