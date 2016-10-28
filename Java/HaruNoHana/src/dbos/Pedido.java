package dbos;

import java.sql.*;
import java.util.Calendar;

public class Pedido {
	private int codPedido, quantidade, codCliente, codPrato;
	private Timestamp horario;
	
	public Pedido(int codPedido, int quantidade, int codCliente, int codPrato, Timestamp horario) throws Exception{
		this.setCodPedido(codPedido);
		this.setQuantidade(quantidade);
		this.setCodCliente(codCliente);
		this.setCodPrato(codPrato);
		this.setHorario(horario);
	}

	public int getCodPedido() {
		return codPedido;
	}

	public void setCodPedido(int codPedido) throws Exception{
		if (codPedido <= 0)
			throw new Exception ("C�digo do Pedido inv�lido");
			
		this.codPedido = codPedido;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) throws Exception{
		if (quantidade < 0)
			throw new Exception ("Quantidade inv�lida");
		
		this.quantidade = quantidade;
	}

	public int getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(int codCliente) throws Exception{
		if (codCliente <= 0)
			throw new Exception ("C�digo de Cliente inv�lido");
		
		this.codCliente = codCliente;
	}

	public int getCodPrato() {
		return codPrato;
	}

	public void setCodPrato(int codPrato) throws Exception{
		if (codPrato <= 0)
			throw new Exception ("C�digo de prato inv�lido");
		
		this.codPrato = codPrato;
	}

	public Timestamp getHorario() {
		return horario;
	}

	public void setHorario(Timestamp horario) throws Exception{
		Calendar cal = Calendar.getInstance();
		cal.setLenient(false);
		cal.setTime(horario);
		this.horario = (Timestamp)cal.getTime();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 666;
		result = prime * result + (new Integer(codCliente)).hashCode();
		result = prime * result + (new Integer(codPedido)).hashCode();
		result = prime * result + (new Integer(codPrato)).hashCode();
		result = prime * result + ((horario == null) ? 0 : horario.hashCode());
		result = prime * result + (new Integer(quantidade)).hashCode();
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		Pedido other = (Pedido) obj;
		
		if (codCliente != other.codCliente)
			return false;
		
		if (codPedido != other.codPedido)
			return false;
		
		if (codPrato != other.codPrato)
			return false;
		
		if (horario == null) {
			if (other.horario != null)
				return false;
		} else if (!horario.equals(other.horario))
			return false;
		
		if (quantidade != other.quantidade)
			return false;
		
		return true;
	}
	
	
	
	
}