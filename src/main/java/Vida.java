import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
/**
 * @author José Junior Borges Monteiro
 */
public class Vida extends BasicElement{
    private Image vida;

    public Vida(int px,int py){
        super(px,py);
        try{
            // Carrega a imagem ajustando a altura para 40 pixels
            // mantendo a proporção em ambas dimensões
            vida =  new Image( "vida.png",0,30,true,true );
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void start(){
        setDirV(1); // percurso da bala
        setDirH(0);
        setSpeed(5); //velocidade do tiro
        setLargAlt(30,30);
    }

    @Override
    public void testaColisao(Character outro){
        if (outro instanceof Nave){
            super.testaColisao(outro); // só verifica a colisão com a nave, ignorando os demais personagens
        }else{
            return;
        }
    }

    @Override
    public void Update(long deltaTime){
        if (colidiu){
            Game.getInstance().aumentaVida();
            deactivate();
        }else{
            setPosY(getY() + getDirV() * getSpeed());
        }
    }

    public void Draw(GraphicsContext graphicsContext){
        graphicsContext.drawImage(vida, getX(),getY());
    }

    @Override
    public int getAltura(){
        return 30;
    }

    @Override
    public int getLargura(){
        return 30;
    }
}