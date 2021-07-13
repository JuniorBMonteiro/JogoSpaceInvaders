import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.*;

/**
 * Handles window initialization and primary game setup
 *
 * @author Bernardo Copstein, Rafael Copstein
 * @author José Junior Borges Monteiro
 */

public class Main extends Application {
    private Stage janela;
    private Scene menu;
    private Scene recorde;
    private Scene jogo;
    private Scene menuLogin;
    private Scene gameOver;
    private String caminhoCSS = getClass().getResource("Style.css").toExternalForm();
    private Image vida = new Image("vida.png", 0, 20, true, true);
    private Image score = new Image("score.jpg", 100, 50, true, true);
    private Image municao = new Image("municao.png", 40, 40, true, true);
    private String nome;
    private Integer pontuacaoFinal;
    private Map<String, Integer> contador = new HashMap<String, Integer>();

    @Override
    public void start(Stage stage) throws Exception {
        // Initialize Window
        janela = stage;
        criarMenuPrincipal();
        criarMenuLogin();
        janela.setScene(menu);
        janela.setTitle(Params.WINDOW_TITLE);
        janela.setResizable(false); // redimensionar a tela
        janela.show();

    }

    private void criarMenuLogin() {
        Button iniciar = new Button("Iniciar Jogo");
        TextField tf = new TextField();
        iniciar.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (tf.getText().length() < 3 || tf.getText().length() > 10) {
                alert.setContentText("Insira pelomenos três e no maximo dez caracteres!");
                alert.setTitle("Erro de preenchimento");
                alert.setHeaderText(null);
                alert.show();
            } else if (tf.getText().contains(" ") || tf.getText().contains(";")) {
                alert.setContentText("Não é permitido a utilização de caracteres especiais no campo nome!");
                alert.setTitle("Erro de preenchimento");
                alert.setHeaderText(null);
                alert.show();
            } else {
                nome = tf.getText();
                criarJogo();
            }
        });
        Button retornarMenu = new Button("Voltar");
        retornarMenu.setOnAction(e -> {
            janela.setScene(menu);
        });
        VBox fundo = new VBox();
        VBox botao = new VBox();
        // configurações dos botões
        iniciar.setMinWidth(200);
        retornarMenu.setMinWidth(200);
        // configurações das box
        botao.setMaxWidth(200);
        botao.setMaxHeight(300);
        botao.setAlignment(Pos.CENTER);
        fundo.setAlignment(Pos.CENTER);
        //adicina estilo nas box
        botao.getStyleClass().add("botao");
        fundo.getStyleClass().add("conteudo");
        // cria um textfield para cadastrar o nome do usuario e aplica o estilo
        Label nome = new Label("Insira seu nome");
        nome.getStyleClass().add("textoGrande");
        // adiciona os elementos dentro das box
        botao.getChildren().add(nome);
        botao.getChildren().add(tf);
        botao.getChildren().add(iniciar);
        botao.getChildren().add(retornarMenu);
        fundo.getChildren().add(botao);
        // cria a scene menu login, tela intermediaria entre o menu principal e o jogo
        // tem a função de cadastrar o nome do usuário que ira jogar.
        menuLogin = new Scene(fundo, 800, 600);
        menuLogin.getStylesheets().add(caminhoCSS);
    }

    private void criarMenuPrincipal() {
        Button jogar = new Button("Jogar");
        jogar.setOnAction(e -> {
            janela.setScene(menuLogin);
        });
        Button recorde = new Button("Recordes");
        recorde.setOnAction(e -> {
            criarRecorde();
            janela.setScene(this.recorde);
        });
        VBox box = new VBox();
        VBox botao = new VBox();
        // configurações dos botões
        jogar.setMinWidth(200);
        jogar.setMinHeight(30);
        recorde.setMinWidth(200);
        recorde.setMinHeight(30);
        // adiciona os botoes em uma box e aplica as configurações de estilo
        botao.getChildren().add(jogar);
        botao.getChildren().add(recorde);
        botao.setAlignment(Pos.CENTER);
        // adiciona os botões na box principal
        box.getStyleClass().add("conteudo");
        box.setAlignment(Pos.CENTER);
        botao.getStyleClass().add("botao");
        box.getChildren().add(botao);
        // cria a scene menu
        menu = new Scene(box, 800, 600);
        menu.getStylesheets().add(caminhoCSS);
        menu.getStylesheets().add("https://fonts.googleapis.com/css2?family=Press+Start+2P");
    }

    private void criarRecorde() {
        VBox box = new VBox();
        Label info = new Label("Nome      Score");
        info.getStyleClass().add("recorde");
        info.setPadding(new Insets(0, 100, 0, 90));
        box.getChildren().add(info);
        // obtem a lista das 10 melhores pontuações
        Recorde rec = new Recorde();
        rec.load();
        // cria um box para cada recorde
            rec.getJogadores().forEach(e ->  {
            String nome = e.getNome();
            Integer valor = e.getPontuacao();
            //Cria um label para jogador e coloca dentro de uma box
            Label nomeJogador = new Label(nome);
            nomeJogador.getStyleClass().add("texto");
            HBox jog = new HBox();
            jog.setMinWidth(250);
            jog.setMaxWidth(250);
            jog.setPadding(new Insets(5, 20, 0, 40));
            jog.getChildren().add(nomeJogador);
            //cria um label para pontuação e coloca dentro de uma box
            Label pontuacao = new Label(valor + "");
            pontuacao.getStyleClass().add("texto");
            HBox pont = new HBox();
            pont.getChildren().add(pontuacao);
            pont.setMinWidth(200);
            pont.setMaxWidth(200);
            pont.setPadding(new Insets(5, 0, 0, 20));
            // une as duas box criada para nome e pontuação em uma unica box jogador
            HBox jogador = new HBox();
            jogador.maxWidth(310);
            jogador.minWidth(310);
            jogador.setAlignment(Pos.CENTER);
            jogador.getChildren().add(jog);
            jogador.getChildren().add(pont);
            //jogador.getStyleClass().add("texto");
            box.getChildren().add(jogador);
            box.setAlignment(Pos.CENTER);
        });

        Button voltar = new Button("Voltar");
        voltar.setOnAction(e -> {
            janela.setScene(menu);
        });
        // configurações dos botões
        VBox botao = new VBox();
        voltar.setMinWidth(200);
        voltar.setMinHeight(30);
        botao.getChildren().add(voltar);
        botao.setMaxWidth(200);
        botao.setMaxHeight(300);
        botao.setAlignment(Pos.CENTER);
        botao.setPadding(new Insets(10));
        // adiciona os elementos na box e adicionando os estilos do CSS
        box.getStyleClass().add("fundoRecorde");
        box.setAlignment(Pos.CENTER);
        botao.getStyleClass().add("botao");
        box.getChildren().add(botao);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(200, 50, 50, 70));
        // cria a scene recorde
        recorde = new Scene(box, 800, 600);
        recorde.getStylesheets().add(caminhoCSS);
        recorde.getStylesheets().add("https://fonts.googleapis.com/css2?family=Press+Start+2P");
    }

    private void criarGameOver() {
        Button jogar = new Button("Jogar Novamente");
        jogar.setOnAction(e -> {
            criarJogo();
        });
        Button voltarMenu = new Button("Retornar ao Menu");
        voltarMenu.setOnAction(e -> {
            janela.setScene(menu);
        });
        VBox box = new VBox();
        VBox botao = new VBox();
        //configuração dos botões
        jogar.setMinWidth(300);
        jogar.setMinHeight(30);
        voltarMenu.setMinWidth(300);
        voltarMenu.setMinHeight(30);
        botao.getChildren().add(jogar);
        botao.getChildren().add(voltarMenu);
        botao.setAlignment(Pos.CENTER);
        // add os elementos no box e adicionando os os estilos do CSS
        box.getStyleClass().add("fundoGameOver");
        box.setAlignment(Pos.CENTER);
        botao.getStyleClass().add("botao");
        Label pontos = new Label("" + pontuacaoFinal);
        pontos.getStyleClass().add("texto");
        box.getChildren().add(pontos);
        box.getChildren().add(new Label(""));
        box.getChildren().add(botao);
        // criando a scene Game over
        gameOver = new Scene(box, 800, 600);
        gameOver.getStylesheets().add(caminhoCSS);
        gameOver.getStylesheets().add("https://fonts.googleapis.com/css2?family=Press+Start+2P");
    }

    private void criarJogo() {
        ImageView iv = new ImageView();
        iv.setImage(new Image("fundo.png"));
        iv.setFitWidth(800);
        iv.setFitHeight(600);

        Group root = new Group();
        root.getChildren().add(iv);
        jogo = new Scene(root, 800, 600);
        janela.setScene(jogo);

        Canvas canvas = new Canvas(Params.WINDOW_WIDTH, Params.WINDOW_HEIGHT);

        root.getChildren().add(canvas);

        // Setup Game object
        Game.getInstance().Start();

        // Register User Input Handler
        jogo.setOnKeyPressed((KeyEvent event) -> {
            Game.getInstance().OnInput(event.getCode(), true);
        });

        jogo.setOnKeyReleased((KeyEvent event) -> {
            Game.getInstance().OnInput(event.getCode(), false);
        });
        jogo.setOnKeyTyped((KeyEvent event) -> {
            Game.getInstance().OnInput(event.getCode(), true);
        });

        // Register Game Loop
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        // adiciona um contador para cada personagem em um HashMap, para ir adicionando novos personagens no mapa
        contador.put("kraken", 100);
        contador.put("orion", 200);
        contador.put("sirius", 400);
        contador.put("boss", 700);
        contador.put("municao", 1000);

        new AnimationTimer() {
            long lastNanoTime = System.nanoTime();

            @Override
            public void handle(long currentNanoTime) {
                long deltaTime = currentNanoTime - lastNanoTime;
                Game.getInstance().Update(currentNanoTime, deltaTime);
                gc.setStroke(Color.BLUEVIOLET);
                gc.setFont(new Font("Press Start 2P", 20));
                gc.clearRect(0, 0, Params.WINDOW_WIDTH, Params.WINDOW_HEIGHT);
                gc.drawImage(score, 320, 10);
                gc.strokeText("" + Game.getInstance().getPontos(), 450, 35);
                gc.drawImage(municao, 10, 550);
                gc.strokeText("" + Game.getInstance().getQuantMunicao(), 60, 585);
                gc.drawImage(vida, 720, 570);
                if (Game.getInstance().getVidas() == 2) {
                    gc.drawImage(vida, 750, 570);
                }
                Game.getInstance().Draw(gc);

                if (Game.getInstance().isGameOver()) {
                    pontuacaoFinal= Game.getInstance().getPontos();
                    criarGameOver();
                    Recorde rec = new Recorde();
                    rec.save(pontuacaoFinal, nome);
                    stop();
                    janela.setScene(gameOver);
                    Game.getInstance().setNewGame();
                }
                // decrementa todos os personagens
                for (String key : contador.keySet()) {
                    contador.put(key, contador.get(key) - 1);
                }

                // verifica se o valor do HashMap chegou a 0, se for verdadeiro instancia um novo personagem
                Random random = new Random();

                Integer valor = contador.get("kraken").hashCode();
                if (valor == 0) {
                    if (Game.getInstance().getPontos() > 50000) {
                        Game.getInstance().addChar(new Kraken(0, random.nextInt(300) + 50));
                        contador.put("kraken", 130);
                    } else {
                        Game.getInstance().addChar(new Kraken(0, random.nextInt(300) + 50));
                        contador.put("kraken", 150);
                    }
                }
                valor = contador.get("orion").hashCode();
                if (valor == 0) {
                    if (Game.getInstance().getPontos() > 70000) {
                        Game.getInstance().addChar(new Orion(random.nextInt(800), 140));
                        contador.put("orion", 230);
                    } else {
                        Game.getInstance().addChar(new Orion(random.nextInt(800), 140));
                        contador.put("orion", 250);
                    }
                }
                valor = contador.get("sirius").hashCode();
                if (valor == 0) {
                    Game.getInstance().addChar(new Sirius(random.nextInt(800), 100));
                    contador.put("sirius", 500);
                }
                valor = contador.get("boss").hashCode();
                if (valor == 0) {
                    if (Game.getInstance().getPontos() > 100000) {
                        Game.getInstance().addChar(new Boss(0, 50));
                        contador.put("boss", 650);
                    } else {
                        Game.getInstance().addChar(new Boss(0, 50));
                        contador.put("boss", 700);
                    }
                }
                valor = contador.get("municao").hashCode();
                if (valor == 0) {
                    Game.getInstance().addChar(new Municao());
                   contador.put("municao", 1000);
                }
                lastNanoTime = currentNanoTime;
            }
        }.start();
    }

    public static void main(String args[]) {
        launch(args);
    }
}