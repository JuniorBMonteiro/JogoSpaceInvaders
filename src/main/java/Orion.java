import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;
/**
 * @author José Junior Borges Monteiro
 */
public class Orion extends BasicElement {
    private Image orion;
    private Image orion2;
    private int animacao = 0;
    private int colisao;

    public Orion(int px, int py){
        super(px,py);
        try{
            orion =  new Image( "orion.png",0,40,true,true );
            orion2 =  new Image( "orion2.png",0,40,true,true );
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
    @Override
    public void start(){
        setDirH(1);
        setDirV(1);
        setSpeed(6);
        setLargAlt(40,40);
    }

    @Override
    public void testaColisao(Character outro){
        if (outro instanceof Nave){
           super.testaColisao(outro);
           colisao = colidiu ? 1 : -1;  //colisao 1 = nave perde vida
        }else if (outro instanceof Shot) {
           super.testaColisao(outro);
           colisao = colidiu ? 0 : -1; // colisao 0 = estrela é apagada
        }else{
            return;
        }
    }

    @Override
    public void Update(long deltaTime){
        // Verifica a colisão
        if (colidiu){
            if (colisao == 1) {
                Game.getInstance().diminuiVida();
                deactivate();
            }else if (colisao == 0){
                Game.getInstance().incPontos(350);
                deactivate();
            }
        // Verifica a posição
        }else{
            setPosX(getX() + getDirH() * getSpeed());
            if (getX() >= getLMaxH() || getX() < getLMinH()){
                // Inverte a direção
                setDirH(getDirH()*-1);
                setPosY(getY()+40);
            }
        }
    }

    public void Draw(GraphicsContext graphicsContext){
        if (animacao < 15 || animacao >= 30){
            if (animacao > 30){
                Random random = new Random();
                setSpeed(random.nextInt(5) +1);// valor aleatorio de 1 a 6
                animacao = 0;
            }
            graphicsContext.drawImage(orion, getX(),getY());
            animacao++;
        }else if (animacao < 30){
            graphicsContext.drawImage(orion2, getX(),getY());
            animacao ++;
        }
    }
    @Override
    public int getAltura(){
        return 40;
    }

    @Override
    public int getLargura(){
        return 40;
    }
}
