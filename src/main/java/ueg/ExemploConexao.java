package ueg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ExemploConexao {
// Parâmetros de conexão

    private static final String URL = "jdbc:postgresql://10.100.3.190:5432/crud_demo";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "ueg";

    public static Connection obterConexao() throws SQLException {

        // A partir do JDBC 4.0, o driver é carregado
        // automaticamente // (não é necessário
        //Class
        //.forName("org.postgresql.Driver")
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
