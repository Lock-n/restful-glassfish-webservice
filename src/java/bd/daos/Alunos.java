package bd.daos;

import java.sql.*;
import bd.*;
import bd.core.*;
import bd.dbos.*;

public class Alunos
{
    public static boolean cadastrado (String ra) throws Exception
    {
        boolean retorno = false;

        try
        {
            String sql;

            sql = "SELECT * " +
                  "FROM Aluno_Apli_Dis " +
                  "WHERE RA = ?";

            BDSQLServer.COMANDO.prepareStatement (sql);

            BDSQLServer.COMANDO.setString (1, ra);

            MeuResultSet resultado = (MeuResultSet)BDSQLServer.COMANDO.executeQuery ();

            retorno = resultado.first(); // pode-se usar resultado.last() ou resultado.next() ou resultado.previous() ou resultado.absotule(numeroDaLinha)

            /* // ou, se preferirmos,

            String sql;

            sql = "SELECT COUNT(*) AS QUANTOS " +
                  "FROM Aluno_Apli_Dis " +
                  "WHERE CODIGO = ?";

            BDSQLServer.COMANDO.prepareStatement (sql);

            BDSQLServer.COMANDO.setInt (1, codigo);

            MeuResultSet resultado = (MeuResultSet)BDSQLServer.COMANDO.executeQuery ();

            resultado.first();

            retorno = resultado.getInt("QUANTOS") != 0;

            */
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao procurar aluno");
        }

        return retorno;
    }

    public static void incluir (Aluno aluno) throws Exception
    {
        if (aluno==null)
            throw new Exception ("Aluno nao fornecido");

        try
        {
            String sql;

            sql = "INSERT INTO Aluno_Apli_Dis " +
                  "(RA,NOME,EMAIL) " +
                  "VALUES " +
                  "(?,?,?)";

            BDSQLServer.COMANDO.prepareStatement (sql);

            BDSQLServer.COMANDO.setString    (1, aluno.getRA ());
            BDSQLServer.COMANDO.setString (2, aluno.getNome ());
            BDSQLServer.COMANDO.setString  (3, aluno.getEmail ());

            BDSQLServer.COMANDO.executeUpdate ();
            BDSQLServer.COMANDO.commit        ();
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao inserir aluno");
        }
    }

    public static void excluir (String ra) throws Exception
    {
        if (!cadastrado (ra))
            throw new Exception ("Nao cadastrado");

        try
        {
            String sql;

            sql = "DELETE FROM Aluno_Apli_Dis " +
                  "WHERE RA=?";

            BDSQLServer.COMANDO.prepareStatement (sql);

            BDSQLServer.COMANDO.setString (1, ra);

            BDSQLServer.COMANDO.executeUpdate ();
            BDSQLServer.COMANDO.commit        ();        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao excluir aluno");
        }
    }

    public static void alterar (Aluno aluno) throws Exception
    {
        if (aluno==null)
            throw new Exception ("Aluno nao fornecido");

        if (!cadastrado (aluno.getRA()))
            throw new Exception ("Nao cadastrado");

        try
        {
            String sql;

            sql = "UPDATE Aluno_Apli_Dis " +
                  "SET NOME=? " +
                  ", EMAIL=? " +
                  "WHERE RA = ?";

            BDSQLServer.COMANDO.prepareStatement (sql);

            BDSQLServer.COMANDO.setString (1, aluno.getNome ());
            BDSQLServer.COMANDO.setString  (2, aluno.getEmail ());
            BDSQLServer.COMANDO.setString    (3, aluno.getRA ());

            BDSQLServer.COMANDO.executeUpdate ();
            BDSQLServer.COMANDO.commit        ();
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao atualizar dados de aluno");
        }
    }

    public static Aluno getAluno (String ra) throws Exception
    {
        Aluno aluno = null;

        try
        {
            String sql;

            sql = "SELECT * " +
                  "FROM Aluno_Apli_Dis " +
                  "WHERE RA = ?";

            BDSQLServer.COMANDO.prepareStatement (sql);

            BDSQLServer.COMANDO.setString (1, ra);

            MeuResultSet resultado = (MeuResultSet)BDSQLServer.COMANDO.executeQuery ();

            if (!resultado.first())
                throw new Exception ("Nao cadastrado");

            aluno = new Aluno (resultado.getString   ("RA"),
                               resultado.getString("NOME"),
                               resultado.getString ("EMAIL"));
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao procurar aluno");
        }

        return aluno;
    }
    
    public static Aluno getAlunoNome (String nome) throws Exception
    {
        Aluno aluno = null;

        try
        {
            String sql;

            sql = "SELECT * " +
                  "FROM Aluno_Apli_Dis " +
                  "WHERE NOME = ?";

            BDSQLServer.COMANDO.prepareStatement (sql);

            BDSQLServer.COMANDO.setString (1, nome);

            MeuResultSet resultado = (MeuResultSet)BDSQLServer.COMANDO.executeQuery ();

            if (!resultado.first())
                throw new Exception ("Nao cadastrado");

            aluno = new Aluno (resultado.getString   ("RA"),
                               resultado.getString("NOME"),
                               resultado.getString ("EMAIL"));
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao procurar aluno");
        }

        return aluno;
    }

    public static MeuResultSet getAlunos () throws Exception
    {
        MeuResultSet resultado = null;

        try
        {
            String sql;

            sql = "SELECT * " +
                  "FROM Aluno_Apli_Dis";

            BDSQLServer.COMANDO.prepareStatement (sql);

            resultado = (MeuResultSet)BDSQLServer.COMANDO.executeQuery ();
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao recuperar alunos");
        }

        return resultado;
    }
}