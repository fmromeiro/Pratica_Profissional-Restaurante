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
import javax.swing.DefaultListModel;
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
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.ListSelectionModel;
import javax.swing.JSplitPane;

public class HaruNoHana extends Thread implements ActionListener {

	private static JFrame frame;
	private ButtonGroup btnGrpOrdemCliente = new ButtonGroup();
	private JTable tbl_clientes;
	private DefaultTableModel tableModel,tableModelMesas,tableModelPromocoes,tableModelPedidos;
	private static DefaultTableModel tableModelPeds,tableModelFechs;
	private JCheckBox chckbxDecrescente, chckbxMesaDecrescente;
	private JTable tbl_mesas;
	private JRadioButton rdbtnNome,rdbtnDataDeCadastro,rdbtnUltimaVisita,rdbtnFrequencia,rdbtnMediaGasta,rdbtnReservadas,rdbtnNoReservadas,rdbtnOcupadas,rdbtnLivres,rdbtnHoraFechamento,rdbtnHoraAbertura,rdbtnValorTotal;
	private final ButtonGroup btnGrpReservadas = new ButtonGroup();
	private final ButtonGroup btnGrpOcupadas = new ButtonGroup();
	private final ButtonGroup btnGrpOrdemMesa = new ButtonGroup();
	private JTable tbl_Promocoes;
	private JTextField txtNome;
	private JTextField txtDescricao;
	private JTextField txtCondicao;
	private JButton btnAtender,btnAtualizar,btnAtualizarCodigos;
	private JTable tablePedidos;
	private static JTable tablePeds;
	private static JTable tableFechs;
	private static JPanel panel;

	public void run() {
		try {
			HaruNoHana window = new HaruNoHana();
			window.frame.setVisible(true);
			panel = new JPanel();
			frame.getContentPane().add(panel, BorderLayout.EAST);
			panel.setLayout(new GridLayout(2, 1, 0, 0));
			
			JPanel panel_1 = new JPanel();
			panel.add(panel_1);
			panel_1.setLayout(new BorderLayout(0, 0));
			
			JLabel lbl_pedidos = new JLabel("\u00DAltimos Pedidos");
			panel_1.add(lbl_pedidos, BorderLayout.NORTH);
			tablePeds = new JTable(tableModelPeds);
			JScrollPane scrollPanePeds = new JScrollPane (tablePeds);
			panel_1.add(scrollPanePeds, BorderLayout.CENTER);
			
			JPanel panel_2 = new JPanel();
			panel.add(panel_2);
			panel_2.setLayout(new BorderLayout(0, 0));
			
			JLabel lbl_fechamentos = new JLabel("\u00DAltimos Fechamentos");
			panel_2.add(lbl_fechamentos, BorderLayout.NORTH);
			tableFechs = new JTable(tableModelFechs);
			JScrollPane scrollPaneFechs = new JScrollPane(tableFechs);
			panel_2.add(scrollPaneFechs, BorderLayout.CENTER);
		} catch (Exception e) {
			e.printStackTrace();}
		}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		HaruNoHana window = new HaruNoHana();
		window.frame.setVisible(true);
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.EAST);
		panel.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JLabel lbl_pedidos = new JLabel("\u00DAltimos Pedidos");
		panel_1.add(lbl_pedidos, BorderLayout.NORTH);
		tablePeds = new JTable(tableModelPeds);
		JScrollPane scrollPanePeds = new JScrollPane (tablePeds);
		panel_1.add(scrollPanePeds, BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JLabel lbl_fechamentos = new JLabel("\u00DAltimos Fechamentos");
		panel_2.add(lbl_fechamentos, BorderLayout.NORTH);
		tableFechs = new JTable(tableModelFechs);
		JScrollPane scrollPaneFechs = new JScrollPane(tableFechs);
		panel_2.add(scrollPaneFechs, BorderLayout.CENTER);
		AtualizaPedidos atPed = new AtualizaPedidos(HaruNoHana.tableModelPeds,HaruNoHana.tableModelFechs);
		atPed.start();
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
		frame.setBounds(100, 100, 900, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		String [] colunas = {"codPrato","qtde","hora"};
		tableModelPeds = new DefaultTableModel (colunas,0);
		
		String [] head = {"codMesa","hora","valor"};
		tableModelFechs = new DefaultTableModel (head,0);
		
		
		String [] columns = {"codPedido","codPrato","quantidade","horario","codCliente"};
		tableModelPedidos = new DefaultTableModel(columns,0);
		
		String [] col = {"codMesa","reserva","horario","horaPrevista","formaPagamento","valorTotal","horaFechamento","statusMesa","codCliente"};
		tableModelMesas = new DefaultTableModel(col, 0);
		
		String [] header = {"codCliente","userLogin","frequencia","nome","ultimaVisita","dataCadastro","mediaGasta","celular"};
		tableModel = new DefaultTableModel(header, 0);
		
		String [] cols = {"codPromocao","nome","descricao","desconto (%)","condicao"};
		tableModelPromocoes = new DefaultTableModel(cols,0);
		
		DefaultListModel listModel = new DefaultListModel();
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel pnl_pedidos = new JPanel();	
		pnl_pedidos.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {

				MeuResultSet pedidos = null;
				
				try
				{
					pedidos = DAOs.getPedidos().getPedidos();
				}
				catch (Exception erro)
				{
					JOptionPane.showMessageDialog(null, erro.toString(), "Error",
						JOptionPane.ERROR_MESSAGE);
				}
				
				try
				{
					tableModelPedidos.setRowCount(0);
				
					while (pedidos.next())
						tableModelPedidos.addRow(new Object[] {pedidos.getInt("codPedido"),pedidos.getInt("codPrato"),pedidos.getInt("quantidade"),pedidos.getTimestamp("horario"),pedidos.getInt("codCliente")});
				}
				catch(Exception erro)
				{
					JOptionPane.showMessageDialog(null, erro.toString(), "Error",
						JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		tabbedPane.addTab("Pedidos", null, pnl_pedidos, null);
		pnl_pedidos.setLayout(new BorderLayout(0, 0));
		tablePedidos = new JTable(tableModelPedidos);
		JScrollPane scrollPanePedidos = new JScrollPane (tablePedidos);
		pnl_pedidos.add(scrollPanePedidos, BorderLayout.CENTER);
		
		JLabel lblSelecioneALinha = new JLabel("Selecione a linha do pedido que deseja atender");
		pnl_pedidos.add(lblSelecioneALinha, BorderLayout.NORTH);
		
		JPanel panel_11 = new JPanel();
		pnl_pedidos.add(panel_11, BorderLayout.SOUTH);
		
		btnAtender = new JButton("Atender");
		btnAtender.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tablePedidos.getSelectedRow() != -1) {
					try
					{
						DAOs.getPedidos().excluir((Integer)tablePedidos.getValueAt(tablePedidos.getSelectedRow(), 0));
					}
					catch (Exception erro)
					{
						JOptionPane.showMessageDialog(null, erro.toString(), "Error",
				                JOptionPane.ERROR_MESSAGE);
					}
					
					btnAtualizar.doClick();
				}
				
			}
		});
		panel_11.add(btnAtender);
		
		btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MeuResultSet pedidos = null;
				
				try
				{
					pedidos = DAOs.getPedidos().getPedidos();
				}
				catch (Exception erro)
				{
					JOptionPane.showMessageDialog(null, erro.toString(), "Error",
						JOptionPane.ERROR_MESSAGE);
				}
				
				try
				{
					tableModelPedidos.setRowCount(0);
				
					while (pedidos.next())
						tableModelPedidos.addRow(new Object[] {pedidos.getInt("codPedido"),pedidos.getInt("codPrato"),pedidos.getInt("quantidade"),pedidos.getTimestamp("horario"),pedidos.getInt("codCliente")});
				}
				catch(Exception erro)
				{
					JOptionPane.showMessageDialog(null, erro.toString(), "Error",
						JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel_11.add(btnAtualizar);
		
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
		
		panel_3.setLayout(new BorderLayout(0, 0));
		JLabel lblOrdenarPor = new JLabel("Ordenar por:");
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
		pnl_promocoes.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane_2 = new JTabbedPane(JTabbedPane.TOP);
		pnl_promocoes.add(tabbedPane_2, BorderLayout.CENTER);
		
		JPanel panel_12 = new JPanel();
		tabbedPane_2.addTab("Consulta", null, panel_12, null);
		panel_12.setLayout(new BorderLayout(0, 0));
		tbl_Promocoes = new JTable(tableModelPromocoes);
		JScrollPane scrollPanePromocoes = new JScrollPane (tbl_Promocoes);
		panel_12.add(scrollPanePromocoes);
		
		JButton btnAtualizarPromo = new JButton("Atualizar");
		btnAtualizarPromo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MeuResultSet promos = null;
				
				try
				{
					promos = DAOs.getPromocoes().getPromocoes();
				}
				catch (Exception erro)
				{
					JOptionPane.showMessageDialog(null, erro.toString(), "Error",
						JOptionPane.ERROR_MESSAGE);
				}
				
				try
				{
					tableModelPromocoes.setRowCount(0);
				
					while (promos.next())
						tableModelPromocoes.addRow(new Object[] {promos.getInt("codPromocao"),promos.getString("nome"),promos.getString("descricao"),promos.getInt("desconto"),promos.getString("condicao")});
				}
				catch(Exception erro)
				{
					JOptionPane.showMessageDialog(null, erro.toString(), "Error",
						JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel_12.add(btnAtualizarPromo, BorderLayout.SOUTH);
		
		JPanel pnl_alteraPromocoes = new JPanel();
		tabbedPane_2.addTab("Inclus\u00E3o e Exclus\u00E3o", null, pnl_alteraPromocoes, null);
		pnl_alteraPromocoes.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel panel_7 = new JPanel();
		pnl_alteraPromocoes.add(panel_7);
		panel_7.setLayout(new BorderLayout(0, 0));
		
		JLabel lblIncluso = new JLabel("Inclus\u00E3o");
		panel_7.add(lblIncluso, BorderLayout.NORTH);
		
		JPanel panel_8 = new JPanel();
		panel_7.add(panel_8, BorderLayout.CENTER);
		panel_8.setLayout(new GridLayout(5, 2, 0, 0));
		
		JLabel lblNome = new JLabel("Nome: ");
		panel_8.add(lblNome);
		
		txtNome = new JTextField();
		panel_8.add(txtNome);
		txtNome.setColumns(10);
		
		JLabel lblDescrio = new JLabel("Descri\u00E7\u00E3o: ");
		panel_8.add(lblDescrio);
		
		txtDescricao = new JTextField();
		panel_8.add(txtDescricao);
		txtDescricao.setColumns(10);
		
		JLabel lblDesconto = new JLabel("Desconto: ");
		panel_8.add(lblDesconto);
		
		JSpinner spnDesconto = new JSpinner();
		spnDesconto.setModel(new SpinnerNumberModel(1, 1, 100, 1));
		panel_8.add(spnDesconto);
		
		JLabel lblAviso = new JLabel("");
		
		JButton btnIncluirPromoo = new JButton("Incluir Promo\u00E7\u00E3o");
		btnIncluirPromoo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if ((txtNome.getText() == null)||(txtNome.getText().equals(""))||(txtDescricao.getText() == null)||(txtDescricao.getText().equals(""))||(txtCondicao.getText() == null)||(txtCondicao.getText().equals("")))
						lblAviso.setText("Preencha todos os campos");
				else {
					try
					{
						spnDesconto.commitEdit();
						Promocao promo = new Promocao (1,(Integer)spnDesconto.getValue(),txtDescricao.getText(),txtNome.getText(),txtCondicao.getText());
						DAOs.getPromocoes().incluir(promo);
					}
					catch (Exception erro)
					{
						JOptionPane.showMessageDialog(null, erro.toString(), "Error",
			                JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		JLabel lblCondio = new JLabel("Condi\u00E7\u00E3o: ");
		panel_8.add(lblCondio);
		
		txtCondicao = new JTextField();
		panel_8.add(txtCondicao);
		txtCondicao.setColumns(10);
		panel_8.add(btnIncluirPromoo);
		
		panel_8.add(lblAviso);
		
		JPanel panel_6 = new JPanel();
		pnl_alteraPromocoes.add(panel_6);
		panel_6.setLayout(new BorderLayout(0, 0));
		
		JLabel lblExcluso = new JLabel("Exclus\u00E3o");
		panel_6.add(lblExcluso, BorderLayout.NORTH);
		
		JPanel panel_9 = new JPanel();
		panel_6.add(panel_9, BorderLayout.CENTER);
		panel_9.setLayout(new GridLayout(3, 2, 0, 0));
		
		JLabel lblDigiteOCdigo = new JLabel("Escolha o C\u00F3digo da Promo\u00E7\u00E3o que deseja excluir");
		panel_9.add(lblDigiteOCdigo);
		JList lstCodPromocoes = new JList(listModel);
		lstCodPromocoes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		btnAtualizarCodigos = new JButton("Atualizar C\u00F3digos");
		btnAtualizarCodigos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MeuResultSet promos = null;
				listModel.clear();
				
				try
				{
					promos = DAOs.getPromocoes().getPromocoes();					
				}
				catch (Exception erro)
				{
						JOptionPane.showMessageDialog(null, erro.toString(), "Error",
			                JOptionPane.ERROR_MESSAGE);					
				}
				
				try
				{
					while (promos.next())
						listModel.addElement(promos.getInt("codPromocao"));
				}
				catch (Exception erro)
				{
					JOptionPane.showMessageDialog(null, erro.toString(), "Error",
		                JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel_9.add(btnAtualizarCodigos);
		
		JLabel lblCdigoDaPromoo = new JLabel("C\u00F3digo da Promo\u00E7\u00E3o");
		panel_9.add(lblCdigoDaPromoo);
		
		panel_9.add(lstCodPromocoes);
		
		JButton btnNewButton = new JButton("Excluir");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (lstCodPromocoes.getSelectedIndex() >= 0)
					try {
						DAOs.getPromocoes().excluir((Integer)lstCodPromocoes.getSelectedValue());
					} catch (Exception erro) {
						JOptionPane.showMessageDialog(null, erro.toString(), "Error",
			                    JOptionPane.ERROR_MESSAGE);
					}

				btnAtualizarCodigos.doClick();
				}
		});
		panel_9.add(btnNewButton);
		
		JPanel panel_10 = new JPanel();
		tabbedPane.addTab("Pratos", null, panel_10, null);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tabbedPane,HaruNoHana.panel);
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);
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
		} else if ((e.getSource() == rdbtnReservadas)||(e.getSource() == rdbtnNoReservadas)||(e.getSource() == rdbtnOcupadas)||(e.getSource() == rdbtnLivres)||(e.getSource() == rdbtnHoraFechamento)||(e.getSource() == rdbtnHoraAbertura)||(e.getSource() == rdbtnValorTotal)){
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
		} else if (e.getSource() == btnAtender) {
			try
			{
				DAOs.getPedidos().excluir((Integer)tablePedidos.getValueAt(tablePedidos.getSelectedRow(), 0));
				btnAtender.setText("" + tablePedidos.getValueAt(tablePedidos.getSelectedRow(), 1));
			}
			catch (Exception erro)
			{
				JOptionPane.showMessageDialog(null, erro.toString(), "Error",
		                JOptionPane.ERROR_MESSAGE);
			}
			
			btnAtualizar.doClick();
		} else if (e.getSource() == btnAtualizar) {
			MeuResultSet pedidos = null;
			
			try
			{
				pedidos = DAOs.getPedidos().getPedidos();
			}
			catch (Exception erro)
			{
				JOptionPane.showMessageDialog(null, erro.toString(), "Error",
					JOptionPane.ERROR_MESSAGE);
			}
			
			try
			{
				tableModelPedidos.setRowCount(0);
			
				while (pedidos.next())
					tableModelPedidos.addRow(new Object[] {pedidos.getInt("codPedido"),pedidos.getInt("codPrato"),pedidos.getInt("quantidade"),pedidos.getTimestamp("horario"),pedidos.getInt("codCliente")});
			}
			catch(Exception erro)
			{
				JOptionPane.showMessageDialog(null, erro.toString(), "Error",
					JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
