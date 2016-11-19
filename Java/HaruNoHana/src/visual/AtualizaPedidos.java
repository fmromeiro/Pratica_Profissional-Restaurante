package visual;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Core.MeuResultSet;
import banco.de.dados.DAOs;

public class AtualizaPedidos extends Thread{

	private DefaultTableModel tableModelPedidos,tableModelFechs;

	public AtualizaPedidos (DefaultTableModel pedidos, DefaultTableModel fechs)
	{
		this.tableModelFechs = fechs;
		this.tableModelPedidos = pedidos;
	}
	
	public void run ()
	{
		
		MeuResultSet pedidos = null;
		
		try
		{
			pedidos = DAOs.getPedidos().getPedidosDesc();
		}
		catch (Exception erro)
		{
			JOptionPane.showMessageDialog(null, erro.toString(), "Error nos pedidos",
				JOptionPane.ERROR_MESSAGE);
		}
		
		try
		{
			tableModelPedidos.setRowCount(0);
		
			while (pedidos.next())
				tableModelPedidos.addRow(new Object[] {pedidos.getInt("codPrato"),pedidos.getInt("quantidade"),pedidos.getTimestamp("horario")});
		}
		catch(Exception erro)
		{
			JOptionPane.showMessageDialog(null, erro.toString(), "Error nos pedidos 2",
				JOptionPane.ERROR_MESSAGE);
		}
		
		MeuResultSet mesas = null;
		
		try
		{
			mesas = DAOs.getMesas().getMesasOrdenado(new String [] {"statusMesa = 0"}, "horaFechamento", true);
		}
		catch (Exception erro)
		{
			JOptionPane.showMessageDialog(null, erro.toString(), "Error nas mesaw",
				JOptionPane.ERROR_MESSAGE);
		}
		
		try
		{
			tableModelFechs.setRowCount(0);
		
			while (mesas.next())
				tableModelFechs.addRow(new Object[] {mesas.getInt("codMesa"),mesas.getTimestamp("horaFechamento"),mesas.getBigDecimal("valorTotal")});
		}
		catch(Exception erro)
		{
			JOptionPane.showMessageDialog(null, erro.toString(), "Error nas mesas 2",
				JOptionPane.ERROR_MESSAGE);
		}
	}
}
