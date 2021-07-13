import javafx.scene.canvas.GraphicsContext;

/**
 * Represents the basic game character
 * @author Bernardo Copstein and Rafael Copstein
 * @author José Junior Borges Monteiro
 */
public interface Character {
    int getX();
    int getY();
    int getAltura();
    int getLargura();

    void testaColisao(Character c);
    boolean jaColidiu();
    void setColidiu();

    void start();
    boolean isActive();
    void Update(long deltaTime);
    void Draw(GraphicsContext graphicsContext);
}
