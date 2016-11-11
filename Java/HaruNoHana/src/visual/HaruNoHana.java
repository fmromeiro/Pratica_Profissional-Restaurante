package visual;

import java.awt.EventQueue;
import banco.de.dados.*;
import daos.*;
import dbos.*;
import Core.*;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JTabbedPane;
import javax.swing.JLayeredPane;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;

public class HaruNoHana implements ActionListener{

	private JFrame frame;
	private ButtonGroup btnGrpOrdemCliente = new ButtonGroup();
	private JTable tbl_clientes;
	private DefaultTableModel tableModel,tableModelMesas;
	private JCheckBox chckbxDecrescente, chckbxMesaDecrescente;
	private JTable tbl_mesas;
	private JRadioButton rdbtnNome,rdbtnDataDeCadastro,rdbtnUltimaVisita,rdbtnFrequencia,rdbtnMediaGasta,rdbtnReservadas,rdbtnNoReservadas,rdbtnOcupadas,rdbtnLivres,rdbtnHoraFechamento,rdbtnHoraAbertura,rdbtnValorTotal;
	private final ButtonGroup btnGrpReservadas = new ButtonGroup();
	private final ButtonGroup btnGrpOcupadas = new ButtonGroup();
	private final ButtonGroup btnGrpOrdemMesa = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HaruNoHana window = new HaruNoHana();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public HaruNoHana(){
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(){
		frame = new JFrame();
		frame.setBounds(100, 100, 592, 419);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.EAST);
		panel.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JLabel lbl_pedidos = new JLabel("\u00DAltimos Pedidos");
		panel_1.add(lbl_pedidos, BorderLayout.NORTH);
		
		JList lst_pedidos = new JList();
		panel_1.add(lst_pedidos, BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JLabel lbl_fechamentos = new JLabel("\u00DAltimos Fechamentos");
		panel_2.add(lbl_fechamentos, BorderLayout.NORTH);
		
		JList lst_fechamentos = new JList();
		panel_2.add(lst_fechamentos, BorderLayout.CENTER);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel pnl_pedidos = new JPanel();
		tabbedPane.addTab("Pedidos", null, pnl_pedidos, null);
		
		JPanel pnl_mesas = new JPanel();
		tabbedPane.addTab("Mesas", null, pnl_mesas, null);
		pnl_mesas.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		pnl_mesas.add(tabbedPane_1, BorderLayout.CENTER);
		
		JPanel pnl_consultaMesas = new JPanel();
		tabbedPane_1.addTab("Consulta", null, pnl_consultaMesas, null);
		pnl_consultaMesas.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_5 = new JPanel();
		pnl_consultaMesas.add(panel_5, BorderLayout.EAST);
		panel_5.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblMostrarMesasCom = new JLabel("Mostrar mesas");
		panel_5.add(lblMostrarMesasCom);
		
		rdbtnReservadas = new JRadioButton("Reservadas");
		rdbtnReservadas.setActionCommand("reserva = 1");
		btnGrpReservadas.add(rdbtnReservadas);
		panel_5.add(rdbtnReservadas);
		
		rdbtnNoReservadas = new JRadioButton("N\u00E3o Reservadas");
		rdbtnNoReservadas.setActionCommand("reserva = 0");
		btnGrpReservadas.add(rdbtnNoReservadas);
		panel_5.add(rdbtnNoReservadas);
		
		rdbtnOcupadas = new JRadioButton("Ocupadas");
		rdbtnOcupadas.setActionCommand("statusMesa = 1");
		btnGrpOcupadas.add(rdbtnOcupadas);
		panel_5.add(rdbtnOcupadas);
		
		rdbtnLivres = new JRadioButton("Livres");
		rdbtnLivres.setActionCommand("statusMesa = 0");
		btnGrpOcupadas.add(rdbtnLivres);
		panel_5.add(rdbtnLivres);
		
		JLabel lblOrdenarPor_1 = new JLabel("Ordenar Por:");
		panel_5.add(lblOrdenarPor_1);
		
		rdbtnHoraFechamento = new JRadioButton("Hora Fechamento");
		rdbtnHoraFechamento.setActionCommand("horaFechamento");
		btnGrpOrdemMesa.add(rdbtnHoraFechamento);
		panel_5.add(rdbtnHoraFechamento);
		
		rdbtnHoraAbertura = new JRadioButton("Hora Abertura");
		rdbtnHoraAbertura.setActionCommand("horario");
		btnGrpOrdemMesa.add(rdbtnHoraAbertura);
		panel_5.add(rdbtnHoraAbertura);
		
		rdbtnValorTotal = new JRadioButton("Valor Total");
		rdbtnValorTotal.setActionCommand("valorTotal");
		btnGrpOrdemMesa.add(rdbtnValorTotal);
		panel_5.add(rdbtnValorTotal);
		
		rdbtnReservadas.addActionListener(this);
		rdbtnNoReservadas.addActionListener(this);
		rdbtnOcupadas.addActionListener(this);
		rdbtnLivres.addActionListener(this);
		rdbtnHoraFechamento.addActionListener(this);
		rdbtnHoraAbertura.addActionListener(this);
		rdbtnValorTotal.addActionListener(this);
		
		chckbxMesaDecrescente = new JCheckBox("Decrescente");
		panel_5.add(chckbxMesaDecrescente);
		
		String [] col = {"codMesa","reserva","horario","horaPrevista","formaPagamento","valorTotal","horaFechamento","statusMesa","codCliente"};
		tableModelMesas = new DefaultTableModel(col, 0);
		tbl_mesas = new JTable(tableModelMesas);
		JScrollPane scrollPaneMesas = new JScrollPane (tbl_mesas);
		pnl_consultaMesas.add(scrollPaneMesas, BorderLayout.CENTER);
		
		JPanel pnl_alteraMesas = new JPanel();
		tabbedPane_1.addTab("Altera\u00E7\u00E3o", null, pnl_alteraMesas, null);
		
		JPanel pnl_clientes = new JPanel();
		tabbedPane.addTab("Clientes", null, pnl_clientes, null);
		pnl_clientes.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		pnl_clientes.add(panel_3, BorderLayout.EAST);
		
		panel_3.setLayout(new BorderLayout(0, 0));JLabel lblOrdenarPor = new JLabel("Ordenar por:");
		panel_3.add(lblOrdenarPor, BorderLayout.NORTH);
		
		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new GridLayout(6, 1, 0, 0));
		
		rdbtnNome = new JRadioButton("Nome");
		rdbtnNome.setActionCommand("nome");
		rdbtnNome.setSelected(true);
		btnGrpOrdemCliente.add(rdbtnNome);
		panel_4.add(rdbtnNome);
		
		rdbtnDataDeCadastro = new JRadioButton("Data de Cadastro");
		rdbtnDataDeCadastro.setActionCommand("dataCadastro");
		btnGrpOrdemCliente.add(rdbtnDataDeCadastro);
		panel_4.add(rdbtnDataDeCadastro);
		
		rdbtnUltimaVisita = new JRadioButton("\u00DAltima Visita");
		rdbtnUltimaVisita.setActionCommand("ultimaVisita");
		btnGrpOrdemCliente.add(rdbtnUltimaVisita);
		panel_4.add(rdbtnUltimaVisita);
		
		rdbtnFrequencia = new JRadioButton("Frequ\u00EAncia");
		rdbtnFrequencia.setActionCommand("frequencia");
		btnGrpOrdemCliente.add(rdbtnFrequencia);
		panel_4.add(rdbtnFrequencia);
		
		rdbtnMediaGasta = new JRadioButton("M\u00E9dia Gasta");
		rdbtnMediaGasta.setActionCommand("mediaGasta");
		btnGrpOrdemCliente.add(rdbtnMediaGasta);
		panel_4.add(rdbtnMediaGasta);
		
		chckbxDecrescente = new JCheckBox("Decrescente");
		panel_4.add(chckbxDecrescente);
		
		rdbtnNome.addActionListener(this);
		rdbtnDataDeCadastro.addActionListener(this);
		rdbtnFrequencia.addActionListener(this);
		rdbtnUltimaVisita.addActionListener(this);
		rdbtnMediaGasta.addActionListener(this);
		
		String [] header = {"codCliente","userLogin","frequencia","nome","ultimaVisita","dataCadastro","mediaGasta","celular"};
		tableModel = new DefaultTableModel(header, 0);
		
		
		
		tbl_clientes = new JTable(tableModel);
		tbl_clientes.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				rdbtnNome.doClick();
			}
		});
		JScrollPane scrollPane = new JScrollPane(tbl_clientes);
		pnl_clientes.add(scrollPane, BorderLayout.CENTER);
		//pnl_clientes.add(tbl_clientes, BorderLayout.CENTER);
		
		
		
		JPanel pnl_promocoes = new JPanel();
		tabbedPane.addTab("Promo\u00E7\u00F5es", null, pnl_promocoes, null);
	}

	public void actionPerformed(ActionEvent e) {
		if ((e.getSource() == rdbtnNome)||(e.getSource() == rdbtnDataDeCadastro)||(e.getSource() == rdbtnUltimaVisita)||(e.getSource() == rdbtnFrequencia)||(e.getSource() == rdbtnMediaGasta)) {
			MeuResultSet clientes = null;
			tbl_clientes.setEnabled(true);
			
			try 
			{
				clientes = DAOs.getClientes().getClientesOrdenado(e.getActionCommand(),chckbxDecrescente.isSelected());
			}
			catch (Exception erro)
			{
				JOptionPane.showMessageDialog(null, erro.toString(), "Error",
	                    JOptionPane.ERROR_MESSAGE);
			}
			
			try
			{
				tableModel.setRowCount(0);
				
				while (clientes.next())
					tableModel.addRow(new Object[] {clientes.getInt("codCliente"),clientes.getString("userLogin"),clientes.getFloat("frequencia"),
						clientes.getString("nome"),clientes.getTimestamp("ultimaVisita"),clientes.getTimestamp("dataCadastro"),
						clientes.getFloat("mediaGasta"),clientes.getString("celular")});
			}
			catch (SQLException erro)
			{JOptionPane.showMessageDialog(null, erro.toString(), "Error",
	                JOptionPane.ERROR_MESSAGE);
			}
			
			tbl_clientes.setEnabled(false);
		} else {
			MeuResultSet mesas = null;
			tbl_mesas.setEnabled(true);
			
			try 
			{
				String [] condicoes = new String [2];
				
				String ordem = "";
				
				if (this.btnGrpOcupadas.getSelection() != null)
					condicoes [0] = this.btnGrpOcupadas.getSelection().getActionCommand();
				
				if (this.btnGrpReservadas.getSelection() != null)
					condicoes[1] = this.btnGrpReservadas.getSelection().getActionCommand();
				
				if (this.btnGrpOrdemMesa.getSelection() != null)
					ordem = this.btnGrpOrdemMesa.getSelection().getActionCommand();
				
				mesas = DAOs.getMesas().getMesasOrdenado(condicoes, ordem, this.chckbxMesaDecrescente.isSelected());
				//mesas = DAOs.getMesas().getMesas();
			}
			catch (Exception erro)
			{
				JOptionPane.showMessageDialog(null, erro.toString(), "Error",
	                    JOptionPane.ERROR_MESSAGE);
			}
			
			try
			{
				tableModelMesas.setRowCount(0);
								
				while (mesas.next()){
					boolean reserva = mesas.getInt("reserva") == 1;
					boolean statusMesa = mesas.getInt("statusMesa") == 1;

					tableModelMesas.addRow(new Object[] {mesas.getInt("codMesa"),reserva,mesas.getTimestamp("horario"),mesas.getTimestamp("horaPrevista"),mesas.getString("formaPagamento"),
							mesas.getBigDecimal("valorTotal"),mesas.getTimestamp("horaFechamento"),statusMesa,mesas.getInt("codCliente")});
				}
			}
			catch (SQLException erro)
			{JOptionPane.showMessageDialog(null, erro.toString(), "Error",
	                JOptionPane.ERROR_MESSAGE);
			}
			
			tbl_mesas.setEnabled(false);
		}
	}
}
