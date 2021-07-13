import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
/**
 * @author José Junior Borges Monteiro
 */
public class Missil extends BasicElement{
        private Image missil;

        public Missil(int px, int py){
            super(px,py);
            try{
                missil =  new Image( "missil.png",0,20,true,true );
            }catch(Exception e){
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }

        @Override
        public void start(){
            setDirV(1);
            setDirH(0);
            setSpeed(5);
            setLargAlt(20,20);
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
            graphicsContext.drawImage(missil, getX(),getY());
        }

        @Override
            public int getAltura(){
        return 20;
    }

        @Override
            public int getLargura(){
        return 20;
    }
    }

