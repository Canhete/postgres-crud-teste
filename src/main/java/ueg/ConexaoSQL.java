package ueg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static ueg.ExemploConexao.obterConexao;

  

    public class ConexaoSQL {

        public void inserir(String descricao, int quantidade, double valor) {
            String sql = "INSERT INTO produto (descricao, quantidade,valor) VALUES( ?,  ?,  ?)";
            try (Connection conn = obterConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {

                // Substituir os placeholders ? pelos valores reais
                stmt.setString(1, descricao);
                stmt.setInt(2, quantidade);
                stmt.setDouble(3, valor);

                int linhasAfetadas = stmt.executeUpdate();
                System.out.println("Linhas inseridas: "
                        + linhasAfetadas);

            } catch (SQLException e) {
                System.err.println("Erro ao inserir: "
                        + e.getMessage());
            }
        }

        public void listarTodos() {
            String sql = "SELECT identificador, descricao, quantidade,valor FROM produto";
            try (Connection conn = obterConexao(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) { // rs.next() avança para a próxima linha
                    int id = rs.getInt("identificador");
                    String desc = rs.getString("descricao");
                    int qtd = rs.getInt("quantidade");
                    double valor = rs.getDouble("valor");
                    System.out.printf("ID: %d | %s | Qtd: %d | R$%.2f%n", id, desc, qtd, valor
                    );
                }

            } catch (SQLException e) {
                System.err.println("Erro ao listar: " + e.getMessage());
            }
        }

        public void buscarPorId(int id) {
            String sql = "SELECT identificador, descricao, quantidade,valor " + "FROM produto WHERE identificador = ?";
            try (Connection conn = obterConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        System.out.printf("Encontrado: %s (Qtd: %d, R$%.2f)%n", rs.getString("descricao"), rs.getInt("quantidade"), rs.getDouble("valor")
                        );
                    } else {
                        System.out.println("Produto não encontrado.");
                    }
                }

            } catch (SQLException e) {
                System.err.println("Erro ao buscar: " + e.getMessage());
            }
        }
    }

    public void atualizar(int id, String descricao, int quantidade,
            double valor) {
        String sql = "UPDATE produto SET descricao = ?, quantidade = ?, valor =  ? " + "WHERE identificador = ?";
        try (Connection conn = obterConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, descricao);
            stmt.setInt(2, quantidade);
            stmt.setDouble(3, valor);
            stmt.setInt(4, id);
            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("Produto atualizado com sucesso!");
            } else {
                System.out.println("Nenhum produto encontrado com o ID informado.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar: "
                    + e.getMessage());
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM produto WHERE identificador = ?";
        try (Connection conn = obterConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("Produto excluído com sucesso!");
            } else {
                System.out.println(
                        "Nenhum produto encontrado com o ID informado.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao excluir: "
                    + e.getMessage());
        }

    public static void main(String[] args) {
        // try-with-resources: fecha a conexão automaticamente
        // ao final
        try (Connection conn = obterConexao()) {
            System.out.println("Conexão estabelecida com sucesso!");
            System.out.println("Banco: " + conn.getMetaData().getDatabaseProductName());
            System.out.println("Versão: " + conn.getMetaData().getDatabaseProductVersion());
        } catch (SQLException e) {
            System.err.println("Erro ao conectar: " + e.getMessage());
        }
    }
}
