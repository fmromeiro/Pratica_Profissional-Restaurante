package daos;

import java.sql.*;
import Core.*;
import banco.de.dados.*;
import dbos.Cliente;

public class Clientes
{
	public boolean cadastrado (int codigo) throws Exception
	{
		boolean retorno = false;
		
		try
		{
			String sql;
			
			sql = "SELECT * FROM Cliente WHERE codCliente = ?";
			
			DAOs.getBD().prepareStatement(sql);
			
			DAOs.getBD().setInt(1,codigo);
			
			MeuResultSet resultado = (MeuResultSet)DAOs.getBD().executeQuery();
			
			retorno = resultado.first();
		}
		catch (SQLException erro)
		{
			throw new Exception ("Erro ao procurar Cliente");
		}
		
		return retorno;
	}
	
	public Cliente getCliente (int cod) throws Exception
	{
		Cliente cliente = null;
		
		try
		{
			String sql = "SELECT * FROM Cliente WHERE codCliente = ?";
			
			DAOs.getBD().prepareStatement(sql);
			
			DAOs.getBD().setInt(1, cod);
			
			MeuResultSet resultado = (MeuResultSet)DAOs.getBD().executeQuery();
			
			if (!resultado.first())
				throw new Exception ("N�o cadastrado");
			
			cliente = new Cliente (resultado.getInt("codCliente"),resultado.getString("userLogin"),resultado.getString("Senha"),
					resultado.getString("Nome"),resultado.getString("Celular"),resultado.getFloat("Frequencia"),
					resultado.getFloat("mediaGasta"),resultado.getTimestamp("ultimaVisita"),
					resultado.getTimestamp("dataCadastro"));
		}
		catch (SQLException erro)
		{
			throw new Exception ("Erro ao procurar cliente");
		}
		
		return cliente;
	}
	
	public MeuResultSet getClientes () throws Exception {
		MeuResultSet resultado = null;
		
		try
		{
			String sql = "SELECT * FROM Cliente";
			
			DAOs.getBD().prepareStatement(sql);
			
			resultado = (MeuResultSet)DAOs.getBD().executeQuery();
		}
		catch (SQLException erro)
		{
			throw new Exception ("Erro ao recuperar clientes");
		}
		
		return resultado;
	}
}