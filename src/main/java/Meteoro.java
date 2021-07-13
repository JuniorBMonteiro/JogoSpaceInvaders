import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
/**
 * @author José Junior Borges Monteiro
 */
public class Meteoro extends BasicElement{
        private Image meteoro;

        public Meteoro(int px, int py){
            super(px,py);
            try{
                meteoro =  new Image( "meteoro.png",0,50,true,true );
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
            setLargAlt(50,50);
        }

        @Override
        public void testaColisao(Character outro){
            if (outro instanceof Nave){
                super.testaColisao(outro);
            }else{
                return;
            }
        }

        @Override
        public void Update(long deltaTime){
           if (colidiu){
                Game.getInstance().diminuiVida();
                deactivate();
            }else{
                setPosY(getY() + getDirV() * getSpeed());
            }
        }

        public void Draw(GraphicsContext graphicsContext){
            graphicsContext.drawImage(meteoro, getX(),getY());
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

