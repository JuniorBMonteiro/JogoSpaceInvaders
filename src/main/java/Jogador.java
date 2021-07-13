/**
 * @author José Junior Borges Monteiro
 * */
public class Jogador implements Comparable<Jogador> {
    private String nome;
    private Integer pontuacao;

    public Jogador(String nome, Integer pontuacao) {
        this.nome = nome;
        this.pontuacao = pontuacao;
    }
    @Override
    public int compareTo(Jogador o) {
        if (this.pontuacao > o.getPontuacao()) {
            return -1;
        }
        if (this.pontuacao < o.getPontuacao()) {
            return 1;
        } else {
            return 0;
        }
    }
    public String getNome() {
        return nome;
    }

    public Integer getPontuacao() {
        return pontuacao;
    }
}
