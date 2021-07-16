import java.sql.*;
import java.util.*;

/**
 * @author José Junior Borges Monteiro
 */

public class Recorde {
    private List<Jogador> jogadores = new ArrayList<>();
    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public void load() throws SQLException {
        Connection conexao = Conexao.getConexao();
        String selectSQL = "SELECT * FROM jogador ORDER BY pontuacao DESC LIMIT 10";
        Statement stmt = conexao.createStatement();
        ResultSet resultado = stmt.executeQuery(selectSQL);
        while (resultado.next()) {
            Integer id = resultado.getInt("id");
            String nome = resultado.getString("nome");
            Integer pontuacao = resultado.getInt("pontuacao");
            jogadores.add(new Jogador(id, nome, pontuacao));
        }
        stmt.close();
        conexao.close();
    }

    public void save(Integer pontos, String nome) throws SQLException {
        String insertSQL = "INSERT INTO jogador (nome, pontuacao) values (?,?);\n";
        Connection conexao = Conexao.getConexao();
        PreparedStatement stmt = conexao.prepareStatement(insertSQL);
        stmt.setString(1, nome);
        stmt.setInt(2, pontos);
        stmt.execute();
        stmt.close();
    }
}
