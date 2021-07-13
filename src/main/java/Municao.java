import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;
/**
 * @author José Junior Borges Monteiro
 */
public class Municao extends BasicElement{
    private Image municao;
    private Random random = new Random();

    public Municao() {
        try{
            municao =  new Image( "municao.png",0,40,true,true );
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void start(){
        setPosX(random.nextInt(700) + 50); // sorteia uma posicao horizontal entre 50 e 750 para largar a recarga
        setPosY(0);
        setDirH(0);
        setDirV(1);
        setSpeed(4);
        setLargAlt(40,40);
    }

    @Override
    public void testaColisao(Character outro){
        if (outro instanceof Nave){
            super.testaColisao(outro);// só verifica a colisão com a nave, ignorando os demais elementos
        }else{
            return;
        }
    }

    @Override
    public void Update(long deltaTime) {
            // atualiza os efeitos da colisao
            if (colidiu){
             deactivate();
             Game.getInstance().recarregaMunicao();
            }
            // atualiza a posição
            if (getY() >= getLMaxV()){
                Game.getInstance().eliminate(this);
            }else {
                setPosY(getY() + getDirV() * getSpeed());
            }
        }

    @Override
    public void Draw(GraphicsContext graphicsContext) {
        graphicsContext.drawImage(municao, getX(),getY());
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

