import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
/**
 * @author José Junior Borges Monteiro
 */

public class Recorde {
    private List<Jogador> jogadores = new ArrayList<>();

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public void load() {
        String currDir = Paths.get("").toAbsolutePath().toString();
        Path path2 = Paths.get(currDir + "\\src\\main\\resources\\recorde.top");
        try (Scanner sc = new Scanner(Files.newBufferedReader(path2,
                Charset.defaultCharset()))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String palavras[] = line.split(";");
                jogadores.add(new Jogador(palavras[0], Integer.parseInt(palavras[1])));
            }
        } catch (IOException x) {
            System.err.format("Erro de E/S: %s%n", x);
        }
    }

    public void save(Integer pontos, String nome) {
        load(); // carrega os recordes que estavam no arq texto e guarda no list
        jogadores.add(new Jogador(nome,pontos));
        Collections.sort(jogadores);
        String currDir = Paths.get("").toAbsolutePath().toString();
        String caminho = currDir + "\\src\\main\\resources\\recorde.top";
        try (FileWriter fl = new FileWriter(caminho, false); PrintWriter writer = new PrintWriter(new BufferedWriter(fl))) {
            jogadores.stream().
                    limit(10). // limita o for para os 10 primeiros elementos
                    forEach(e ->
                    writer.append(e.getNome() + ";" + e.getPontuacao() + "\n"));
        } catch (IOException x) {
            System.err.format("Erro de E/S: %s%n", x);
        }
    }
}
