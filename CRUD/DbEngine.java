import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class DbEngine {

    private final Properties prop = new Properties();
    private Connection conexao;
    private String url;

    public DbEngine() {
        try {
            InputStream input = Files.newInputStream(Paths.get(".env"));
            prop.load(input);
            url = prop.getProperty("DB_URL");

            if (url == null) {
                System.err.println("ERRO: A chave 'DB_URL' não foi encontrada dentro do arquivo .env");
                return;
            }

            Class.forName("org.postgresql.Driver");

            conexao = DriverManager.getConnection(url);
            System.out.println("Conexão com o banco estabelecida com sucesso!");
            
        } catch (Exception e) {
            System.err.println("ERRO: Falha ao conectar ao banco de dados.");
            e.printStackTrace();
        }
    }

    public void select() {
        String sql = "SELECT * FROM funcionario;";
        try {
            Statement stmt = conexao.createStatement();
            ResultSet resultado = stmt.executeQuery(sql);

            while (resultado.next()) {
                System.out.println("Nome: " + resultado.getString("nome"));
                System.out.println("Cargo: " + resultado.getString("cargo"));
                System.out.println("Salário: " + resultado.getString("salario"));
                System.out.println("E-mail: " + resultado.getString("email"));
                System.out.println("Status: " + (resultado.getBoolean("ativo") ? "Contrato Ativo" : "Contrato Expirado"));
                System.out.println("-----------------------------------");
            }
        } catch (Exception e) {
            System.err.println("Erro ao realizar SELECT no banco");
            e.printStackTrace();
        }
    }

    public void insert(String nome, String cargo, double salario, String email, boolean ativo) {
        String sql = "INSERT INTO funcionario (nome, cargo, salario, email, ativo) VALUES (?, ?, ?, ?, ?) RETURNING *;";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, nome);
            ps.setString(2, cargo);
            ps.setDouble(3, salario);
            ps.setString(4, email);
            ps.setBoolean(5, ativo);

            ResultSet resultado = ps.executeQuery();
            if (resultado.next()) {
                System.out.println("Funcionário Inserido Com Sucesso! ID: " + resultado.getInt("id"));
            }
        } catch (Exception e) {
            System.err.println("Erro ao Inserir Funcionário");
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM funcionario WHERE id = ?;";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);

            int linhasAfetadas = ps.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Funcionário apagado com sucesso!");
            } else {
                System.out.println("Nenhum funcionário encontrado com o ID fornecido.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao apagar funcionário");
            e.printStackTrace();
        }
    }

    public void update(int id, String novoCargo) {
        String sql = "UPDATE funcionario SET cargo = ? WHERE id = ?;";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, novoCargo);
            ps.setInt(2, id);

            int linhasAfetadas = ps.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Funcionário atualizado com sucesso!");
            } else {
                System.out.println("Nenhum funcionário encontrado para atualização.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao atualizar funcionário");
            e.printStackTrace();
        }
    } 

    public void insertDepartamento(String nome, String sigla) {
        String sql = "INSERT INTO departamento (nome, sigla) VALUES (?, ?) RETURNING *;";
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, nome);
            ps.setString(2, sigla);

            ResultSet resultado = ps.executeQuery();
            if (resultado.next()) {
                System.out.println("Departamento Cadastrado Com Sucesso! ID: " + resultado.getInt("id"));
            }
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar departamento");
            e.printStackTrace();
        }
    }
}