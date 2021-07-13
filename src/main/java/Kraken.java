import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * @author José Junior Borges Monteiro
 */
public class Kraken extends BasicElement{
    private Image kraken;
    private Image kraken2;
    private int animacao = 0;
    private int colisao;
    private int bloqueiaRepeticao = 0;

    public Kraken(int px, int py){
        super(px,py);
        try{
            kraken =  new Image( "kraken.png",0,50,true,true );
            kraken2 =  new Image( "kraken2.png",0,50,true,true );
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void start(){
        setDirH(1);
        setDirV(1);
        setSpeed(2);
        setLargAlt(50,50);
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
                Game.getInstance().incPontos(200);
                deactivate();
            }
        // Verifica a posição
        }else{
            setPosX(getX() + getDirH() * getSpeed());
            if (getX() >= getLMaxH()){
                // Reposiciona no lado esquerdo
                setPosX(getLMinH());
                setPosY(getY() + 50);
              if (getY() > 300 && bloqueiaRepeticao == 0){
                  // Variavel bloqueiaRepetição foi criado para que a velocidade só aumente
                  // uma vez, evitando entrar em loop.
                  setSpeed(getSpeed() * 2);
                  bloqueiaRepeticao++;
              }
            }
        }
    }

    public void Draw(GraphicsContext graphicsContext){
            if (animacao < 40 || animacao >= 80){
                if (animacao > 80){
                    animacao = 0;
                }
            graphicsContext.drawImage(kraken, getX(),getY());
            animacao++;
        }else if (animacao < 80){
            graphicsContext.drawImage(kraken2, getX(),getY());
            animacao ++;
        }
    }
    @Override
    public int getAltura(){
        return 50;
    }

    @Override
    public int getLargura(){
        return 50;
    }
}
