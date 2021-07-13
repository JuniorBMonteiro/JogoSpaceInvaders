import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;
/**
 * @author José Junior Borges Monteiro
 */
public class Sirius extends BasicElement{
        private Image sirius;
        private Image sirius2;
        private int vida = 2;
        private int contAtaque = 170; // temporizador para atirar o missil
        private int quantVoltas = 0; // personagem pode dar 3 voltas no mapa depois é eliminado

    public Sirius(int px, int py){
            super(px,py);
            try{
                sirius =  new Image( "sirius.png",0,50,true,true );
                sirius2 =  new Image( "sirius2.png",0,50,true,true );
            }catch(Exception e){
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }

        @Override
        public void start(){
            setDirH(-1);
            setDirV(0);
            setSpeed(2);
            setLargAlt(50,50);
        }

        @Override
        public void testaColisao(Character outro){
            if (outro instanceof Shot) {
                super.testaColisao(outro);
            }else{
                return;
            }
        }

        @Override
        public void Update(long deltaTime){
            if (quantVoltas ==3){
                deactivate();
            }
            if (colidiu){
                // Sirius da 600 pontos, 200 se tirar uma vida e mais 400 se conseguir matar
                // Se não for destruido pela nave ele atravessa 3 vezes o mapa e depois é eliminado.
                if (vida == 1){
                    Game.getInstance().incPontos(400);
                    deactivate();
                    colidiu = false;
                }else {
                    Game.getInstance().incPontos(200);
                    vida--;
                    colidiu = false;
                }
            }else {
                setPosX(getX() + getDirH() * getSpeed());
                if (getX() <= getLMinH()-50) {
                    setPosX(getLMaxH());
                    quantVoltas++;
                }
                if (contAtaque <= 0) {
                    Random random = new Random();
                    Game.getInstance().addChar(new Missil(posX, posY));
                    contAtaque = random.nextInt(80) + 80; // instancia bombas de tempos e tempos que ao atingir
                    // a nave decrementa uma vida.
                }
                contAtaque--;
            }
        }

        public void Draw(GraphicsContext graphicsContext){
            graphicsContext.drawImage(sirius, getX(),getY());
            if (vida == 2){
                graphicsContext.drawImage(sirius, getX(),getY());
            }else{
                graphicsContext.drawImage(sirius2, getX(),getY());
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