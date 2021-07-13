import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

/**
 * Represents a shot that crosses the screen from bottom to up and then dismiss
 * @author Bernardo Copstein and Rafael Copstein
 * @author José Junior Borges Monteiro
 */
public class Shot extends BasicElement{
    public Shot(int px,int py){
        super(px,py);
    }

    @Override
    public void start(){
        setDirV(-1); // percurso da bala
        setSpeed(15); //velocidade do tiro
        setLargAlt(4,16);
    }

    @Override
    public void testaColisao(Character outro){
        // Só testa a colisão com personagens ignorando utilitários.
        if (outro instanceof Shot || outro instanceof Vida || outro instanceof Municao){
            return;
        }else{
            super.testaColisao(outro);
        }
    }

    @Override
    public void Update(long deltaTime){
        if (jaColidiu()){
            deactivate();
        }else{
            setPosY(getY() + getDirV() * getSpeed());
            // Se chegou na parte superior da tela ...
            if (getY() <= getLMinV()){
                // Desaparece
                deactivate();
            }
        }
    }

    @Override
    public int getAltura(){
        return 16;
    }

    @Override
    public int getLargura(){
        return 4;
    }

    public void Draw(GraphicsContext graphicsContext){
        graphicsContext.setFill(Paint.valueOf("#0b7ef3"));
        graphicsContext.fillOval(getX(), getY(), 4, 16);
    }
}
