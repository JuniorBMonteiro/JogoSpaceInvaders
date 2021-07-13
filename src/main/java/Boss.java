import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;
/**
 * @author José Junior Borges Monteiro
 */
public class Boss extends BasicElement{
    private Image boss;
    private Random random = new Random();
    private int contAtaque= random.nextInt(110);

    public Boss(int px, int py) {
        super(px, py);
        try{
            boss =  new Image( "boss.png",0,50,true,true );
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void start(){
        setDirH(1);
        setDirV(0);
        setSpeed(3);
        setLargAlt(50,50);
    }

    @Override
    public void testaColisao(Character outro){
        if (outro instanceof Shot){
            super.testaColisao(outro);
        }else{
            return;
        }
    }

    @Override
    public void Update(long deltaTime) {
        if (colidiu){
            Game.getInstance().incPontos(1000);
            Game.getInstance().addChar(new Vida(posX,posY));// Ao ser atingido instancia um vida que ao se colidir com a nave
                                                            // incrementa uma vida.
            deactivate();
        }else{
            setPosX(getX() + getDirH() * getSpeed());
            if (contAtaque <= 0) {
                Game.getInstance().addChar(new Meteoro(posX, posY));
                contAtaque = random.nextInt(70) + 80; // instancia bombas de tempos e tempos que ao atingir
                                                             // a nave decrementa uma vida.
            }
            contAtaque--;
            if (getX() >= getLMaxH()){
                deactivate();
            }
        }
    }


    @Override
    public void Draw(GraphicsContext graphicsContext) {
            graphicsContext.drawImage(boss, getX(),getY());
    }
}
