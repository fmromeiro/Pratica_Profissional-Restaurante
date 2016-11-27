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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Timer;
import java.util.TimerTask;

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
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.Calendar;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import static java.nio.file.StandardCopyOption.*;
import javax.swing.JFileChooser;

public class HaruNoHana extends Thread implements ActionListener {

	private static JFrame frmHaruNoHana;
	private ButtonGroup btnGrpOrdemCliente = new ButtonGroup();
	private JTable tbl_clientes;
	private DefaultTableModel tableModel,tableModelMesas,tableModelPromocoes,tableModelPedidos,tableModelPratos;
	private DefaultTableModel tableModelPeds,tableModelFechs;
	private JCheckBox chckbxDecrescente, chckbxMesaDecrescente;
	private JTable tbl_mesas;
	private JRadioButton rdbtnNome,rdbtnDataDeCadastro,rdbtnUltimaVisita,rdbtnQtdVisitas,rdbtnMediaGasta,rdbtnReservadas,
						rdbtnNoReservadas,rdbtnOcupadas,rdbtnLivres,rdbtnHoraFechamento,rdbtnHoraAbertura,rdbtnValorTotal,
						rdbtnEntradas,rdbtnPratos,rdbtnSobremesas,rdbtnBebidas,rdbtnTodas;
	private final ButtonGroup btnGrpReservadas = new ButtonGroup();
	private final ButtonGroup btnGrpOcupadas = new ButtonGroup();
	private final ButtonGroup btnGrpOrdemMesa = new ButtonGroup();
	private JTable tbl_Promocoes;
	private JTextField txtNome;
	private JTextField txtDescricao;
	private JButton btnAtender,btnAtualizar,btnAtualizarCodigos;
	private JTable tablePedidos;
	private JTable tablePeds;
	private JTable tableFechs;
	private JPanel panel;
	private JTable tblPratos;
	private JTable tblQuest;
	private final ButtonGroup btnGrpPratos = new ButtonGroup();
	private JTextField txtNomePrato;
	private JTextField txtDescPrato;
	private JTextField txtIngPrato;

	public void run() {
		try {
			HaruNoHana window = new HaruNoHana();
			window.frmHaruNoHana.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();}
		}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
 			public void run() {
 				try {
 					HaruNoHana window = new HaruNoHana();
 					window.frmHaruNoHana.setVisible(true);
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
		frmHaruNoHana = new JFrame();
		frmHaruNoHana.setTitle("Haru no Hana");
		frmHaruNoHana.setBounds(100, 100, 900, 500);
		frmHaruNoHana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHaruNoHana.getContentPane().setLayout(new BorderLayout(0, 0));		
		
		String [] columns = {"codPedido","codPrato","quantidade","horario","codCliente"};
		tableModelPedidos = new DefaultTableModel(columns,0);
		
		String [] col = {"codMesa","reserva","horario","horaPrevista","formaPagamento","valorTotal","horaFechamento","statusMesa","codCliente"};
		tableModelMesas = new DefaultTableModel(col, 0);
		
		String [] header = {"codCliente","userLogin","qtdVisitas","nome","ultimaVisita","dataCadastro","mediaGasta","celular"};
		tableModel = new DefaultTableModel(header, 0);
		
		String [] cols = {"codPromocao","nome","descricao","desconto (%)","condicao"};
		tableModelPromocoes = new DefaultTableModel(cols,0);
		
		DefaultListModel listModel = new DefaultListModel();
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmHaruNoHana.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
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
					AtualizaPedidos();
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
		pnl_alteraMesas.setLayout(new GridLayout(3, 1, 0, 0));
		
		JPanel panel_13 = new JPanel();
		pnl_alteraMesas.add(panel_13);
		panel_13.setLayout(new BorderLayout(0, 0));
		
		JLabel lblAdicionarMesas = new JLabel("Adicionar Mesas");
		panel_13.add(lblAdicionarMesas, BorderLayout.NORTH);
		
		JPanel panel_16 = new JPanel();
		panel_13.add(panel_16, BorderLayout.CENTER);
		panel_16.setLayout(new GridLayout(2, 2, 0, 0));
		
		JLabel lblEscolhaONmero = new JLabel("Escolha o n\u00FAmero da mesa que deseja adicionar: ");
		panel_16.add(lblEscolhaONmero);
		
		JSpinner spnMesa = new JSpinner();
		spnMesa.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		panel_16.add(spnMesa);
		
		JLabel lblAvisoMesa = new JLabel("");
		
		JButton btnAdicionarMesa = new JButton("Adicionar Mesa");
		btnAdicionarMesa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					spnMesa.commitEdit();
					if (DAOs.getMesas().cadastrado((Integer)spnMesa.getValue())) {
						lblAvisoMesa.setText("Esse c�digo de mesa j� existe");
						return;
					}
				}
				catch (Exception erro)
				{
					JOptionPane.showMessageDialog(null, erro.toString(), "Error",
							JOptionPane.ERROR_MESSAGE);					
				}
				
				lblAvisoMesa.setText("");
				
				long data = new Date().getTime();
				
				try
				{
					DAOs.getMesas().incluir(new Mesa((Integer)spnMesa.getValue(),0,0,1,new Timestamp(data),new Timestamp(data),new Timestamp(data),"D�bito",new BigDecimal(0.0F)));
				}
				catch (Exception erro)
				{
					JOptionPane.showMessageDialog(null, erro.toString(), "Error",
							JOptionPane.ERROR_MESSAGE);	
				}
			}
		});
		panel_16.add(btnAdicionarMesa);
		
		
		panel_16.add(lblAvisoMesa);
		
		JPanel panel_15 = new JPanel();
		pnl_alteraMesas.add(panel_15);
		panel_15.setLayout(new BorderLayout(0, 0));
		
		JLabel lblReservarMesas = new JLabel("Reservar Mesas");
		panel_15.add(lblReservarMesas, BorderLayout.NORTH);
		
		JPanel panel_18 = new JPanel();
		panel_15.add(panel_18, BorderLayout.CENTER);
		panel_18.setLayout(new GridLayout(3, 2, 0, 0));
		
		JLabel lblEscolhaONmero_1 = new JLabel("Escolha o n\u00FAmero da mesa: ");
		panel_18.add(lblEscolhaONmero_1);
		
		DefaultListModel listModelMesas = new DefaultListModel();
		JList lstMesa = new JList(listModelMesas);
		lstMesa.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panel_18.add(lstMesa);
		
		JLabel lblEscolhaOHorrio = new JLabel("Escolha o hor\u00E1rio: ");
		panel_18.add(lblEscolhaOHorrio);
		
		SpinnerDateModel spnModelHora = new SpinnerDateModel(new Date(),null, null, Calendar.DAY_OF_YEAR);
		JSpinner spnHora = new JSpinner(spnModelHora);
		panel_18.add(spnHora);
		
		JButton btnAtualizarMesas = new JButton("Atualizar Mesas");
		JButton btnAtualizarMesasReserva = new JButton("Atualizar Mesas");
		
		JButton btnReservarMesa = new JButton("Reservar Mesa");
		btnReservarMesa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (lstMesa.getSelectedIndex() < 0)
					return;
				
				try
				{
					DAOs.getMesas().reservar((Integer)lstMesa.getSelectedValue(), new Timestamp(spnModelHora.getDate().getTime()));
				}
				catch (Exception erro)
				{
					JOptionPane.showMessageDialog(null, erro.toString(), "Error",
						JOptionPane.ERROR_MESSAGE);	
				}
			
				btnAtualizarMesas.doClick();
				btnAtualizarMesasReserva.doClick();
			}
		});
		panel_18.add(btnReservarMesa);
	
		btnAtualizarMesas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				listModelMesas.clear();
				MeuResultSet mesas = null;
				
				try
				{
					mesas =DAOs.getMesas().getMesasOrdenado(new String [] {"reserva = 0"}, "", false);
				}
				catch (Exception erro)
				{
					JOptionPane.showMessageDialog(null, erro.toString(), "Error",
							JOptionPane.ERROR_MESSAGE);	
				}
				
				try
				{
					while (mesas.next())
						listModelMesas.addElement(mesas.getInt("codMesa"));
				}
				catch (Exception erro)
				{
					JOptionPane.showMessageDialog(null, erro.toString(), "Error",
							JOptionPane.ERROR_MESSAGE);	
				}
			}
		});
		panel_18.add(btnAtualizarMesas);
		
		JPanel panel_14 = new JPanel();
		pnl_alteraMesas.add(panel_14);
		panel_14.setLayout(new BorderLayout(0, 0));
		
		JLabel lblCancelarReservas = new JLabel("Cancelar Reservas");
		panel_14.add(lblCancelarReservas, BorderLayout.NORTH);
		
		JPanel panel_19 = new JPanel();
		panel_14.add(panel_19, BorderLayout.CENTER);
		panel_19.setLayout(new GridLayout(2, 2, 0, 0));
		
		JLabel lblEscolhaONmero_2 = new JLabel("Escolha o n\u00FAmero da mesa cuja reserva deseja cancelar:");
		panel_19.add(lblEscolhaONmero_2);
		
		DefaultListModel<Integer> lstModelReserva = new DefaultListModel<Integer> ();
		JList<Integer> lstMesasReserva = new JList<Integer>(lstModelReserva);
		panel_19.add(lstMesasReserva);
				
		JButton btnCancelarReserva = new JButton("Cancelar Reserva");
		btnCancelarReserva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (lstMesasReserva.getSelectedIndex() < 0)
					return;
				
				try
				{
					DAOs.getMesas().cancelarReserva(lstMesasReserva.getSelectedValue().intValue());
				}
				catch (Exception erro)
				{
					JOptionPane.showMessageDialog(null, erro.toString(), "Error",
							JOptionPane.ERROR_MESSAGE);	
				}
				
				btnAtualizarMesasReserva.doClick();
				btnAtualizarMesas.doClick();
			}
		});
		panel_19.add(btnCancelarReserva);
		
		btnAtualizarMesasReserva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lstModelReserva.clear();
				MeuResultSet mesas = null;
				
				try
				{
					mesas = DAOs.getMesas().getMesasOrdenado(new String [] {"reserva = 1"}, "", false);
				}
				catch (Exception erro)
				{
					JOptionPane.showMessageDialog(null, erro.toString(), "Error",
							JOptionPane.ERROR_MESSAGE);	
				}
				
				try
				{
					while (mesas.next())
						lstModelReserva.addElement(mesas.getInt("codMesa"));
				}
				catch (Exception erro)
				{
					JOptionPane.showMessageDialog(null, erro.toString(), "Error",
							JOptionPane.ERROR_MESSAGE);	
				}
			}
		});
		panel_19.add(btnAtualizarMesasReserva);
		
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
		
		rdbtnQtdVisitas = new JRadioButton("Quantidade Visitas");
		rdbtnQtdVisitas.setActionCommand("qtdVisitas");
		btnGrpOrdemCliente.add(rdbtnQtdVisitas);
		panel_4.add(rdbtnQtdVisitas);
		
		rdbtnMediaGasta = new JRadioButton("M\u00E9dia Gasta");
		rdbtnMediaGasta.setActionCommand("mediaGasta");
		btnGrpOrdemCliente.add(rdbtnMediaGasta);
		panel_4.add(rdbtnMediaGasta);
		
		chckbxDecrescente = new JCheckBox("Decrescente");
		panel_4.add(chckbxDecrescente);
		
		rdbtnNome.addActionListener(this);
		rdbtnDataDeCadastro.addActionListener(this);
		rdbtnQtdVisitas.addActionListener(this);
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
				tbl_Promocoes.setEnabled(true);
				
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
				tbl_Promocoes.setEnabled(false);
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

		JComboBox cmbxCond = new JComboBox();
		JComboBox cmbxSinal = new JComboBox();
		JSpinner spnValor = new JSpinner();
		
		JButton btnIncluirPromoo = new JButton("Incluir Promo\u00E7\u00E3o");
		btnIncluirPromoo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (txtDescricao.getText().equals("")||txtNome.getText().equals("")) {
					lblAviso.setText("Preencha todos os campos");
					return;
				}
				try {
					spnValor.commitEdit();
				} catch (ParseException e) {}
				String texto = (String)cmbxCond.getSelectedItem() + (String)cmbxSinal.getSelectedItem() + (Integer)spnValor.getValue() + "";
			
				lblAviso.setText(texto);
				try
				{
					spnDesconto.commitEdit();
					Promocao promo = new Promocao (1,(Integer)spnDesconto.getValue(),txtDescricao.getText(),txtNome.getText(),texto);
					DAOs.getPromocoes().incluir(promo);
				}
				catch (Exception erro)
				{
					JOptionPane.showMessageDialog(null, erro.toString(), "Error",
		                JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JLabel lblCondio = new JLabel("Condi\u00E7\u00E3o: ");
		panel_8.add(lblCondio);
		
		JPanel panel_17 = new JPanel();
		panel_8.add(panel_17);
		panel_17.setLayout(new GridLayout(1, 3, 0, 0));
		
		cmbxCond.setModel(new DefaultComboBoxModel(new String[] {"mediaGasta", "qtdVisitas"}));
		panel_17.add(cmbxCond);
		
		cmbxSinal.setModel(new DefaultComboBoxModel(new String[] {"<", "<=", "=", ">=", ">"}));
		panel_17.add(cmbxSinal);
		
		spnValor.setModel(new SpinnerNumberModel(1, 1, 100, 1));
		panel_17.add(spnValor);
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
		panel_10.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane_3 = new JTabbedPane(JTabbedPane.TOP);
		panel_10.add(tabbedPane_3);
		
		JPanel panel_20 = new JPanel();
		tabbedPane_3.addTab("Consulta", null, panel_20, null);
		panel_20.setLayout(new BorderLayout(0, 0));
		
		String [] cabecalho = {"codPrato","nomePrato","ingredientes","descricao","classificacao","preco"};
		tableModelPratos = new DefaultTableModel(cabecalho,0);
		tblPratos = new JTable(tableModelPratos);
		JScrollPane scrollPanePratos = new JScrollPane(tblPratos);
		panel_20.add(scrollPanePratos, BorderLayout.CENTER);
		
		JPanel panel_22 = new JPanel();
		panel_20.add(panel_22, BorderLayout.EAST);
		panel_22.setLayout(new GridLayout(6, 1, 0, 0));
		
		JLabel lblAbas = new JLabel("Abas:");
		panel_22.add(lblAbas);
		
		rdbtnEntradas = new JRadioButton("Entradas");
		rdbtnEntradas.setActionCommand("Entrada");
		rdbtnEntradas.addActionListener(this);
		btnGrpPratos.add(rdbtnEntradas);
		panel_22.add(rdbtnEntradas);
		
		rdbtnPratos = new JRadioButton("Pratos");
		rdbtnPratos.setActionCommand("Pratos");
		rdbtnPratos.addActionListener(this);
		btnGrpPratos.add(rdbtnPratos);
		panel_22.add(rdbtnPratos);
		
		rdbtnSobremesas = new JRadioButton("Sobremesas");
		rdbtnSobremesas.setActionCommand("Sobremesa");
		rdbtnSobremesas.addActionListener(this);
		btnGrpPratos.add(rdbtnSobremesas);
		panel_22.add(rdbtnSobremesas);
		
		rdbtnBebidas = new JRadioButton("Bebidas");
		rdbtnBebidas.setActionCommand("Bebidas");
		rdbtnBebidas.addActionListener(this);
		btnGrpPratos.add(rdbtnBebidas);
		panel_22.add(rdbtnBebidas);
		
		rdbtnTodas = new JRadioButton("Todas");
		rdbtnTodas.setActionCommand("Todas");
		rdbtnTodas.addActionListener(this);
		btnGrpPratos.add(rdbtnTodas);
		panel_22.add(rdbtnTodas);
		
		JPanel panel_21 = new JPanel();
		tabbedPane_3.addTab("Altera\u00E7\u00E3o", null, panel_21, null);
		panel_21.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel panel_24 = new JPanel();
		panel_21.add(panel_24);
		panel_24.setLayout(new BorderLayout(0, 0));
		
		JLabel lblAdicionarMesa = new JLabel("Adicionar Prato");
		panel_24.add(lblAdicionarMesa, BorderLayout.NORTH);
		
		JPanel panel_26 = new JPanel();
		panel_24.add(panel_26, BorderLayout.CENTER);
		panel_26.setLayout(new GridLayout(6, 2, 0, 0));
		
		JLabel lblDigiteONome = new JLabel("Digite o nome do prato");
		panel_26.add(lblDigiteONome);
		
		txtNomePrato = new JTextField();
		panel_26.add(txtNomePrato);
		txtNomePrato.setColumns(10);
		
		JLabel lblDigiteADescrio = new JLabel("Digite a descri\u00E7\u00E3o do prato");
		panel_26.add(lblDigiteADescrio);
		
		txtDescPrato = new JTextField();
		panel_26.add(txtDescPrato);
		txtDescPrato.setColumns(10);
		
		JLabel lblDigiteOsIngredientes = new JLabel("Digite os ingredientes do prato");
		panel_26.add(lblDigiteOsIngredientes);
		
		txtIngPrato = new JTextField();
		panel_26.add(txtIngPrato);
		txtIngPrato.setColumns(10);
		
		JLabel lblEscolhaAClassificao = new JLabel("Escolha a classifica\u00E7\u00E3o do prato");
		panel_26.add(lblEscolhaAClassificao);
		
		JComboBox cmbxClassificacao = new JComboBox();
		cmbxClassificacao.setModel(new DefaultComboBoxModel(new String[] {"Entrada", "Pratos", "Sobremesa", "Bebidas"}));
		panel_26.add(cmbxClassificacao);
		
		JLabel lblAvisoPrato = new JLabel("");
	
		JSpinner spnPrecoPrato = new JSpinner();
		
		JButton btnAdicionarPrato = new JButton("Adicionar Prato");
		btnAdicionarPrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (txtNomePrato.getText().equals("")||txtNomePrato.getText() == null||txtDescPrato.getText().equals("")||txtDescPrato.getText() == null||txtIngPrato.getText().equals("")||txtIngPrato.getText() == null) {
					lblAvisoPrato.setText("Preencha todos os campos");
					return;
				}
				
				try
				{
					DAOs.getPratos().incluir(new Prato(1, (String)cmbxClassificacao.getSelectedItem(), txtIngPrato.getText(), txtDescPrato.getText(),txtNomePrato.getText(), new BigDecimal((Float)spnPrecoPrato.getValue())));
				}
				catch (Exception erro)
				{
					JOptionPane.showMessageDialog(null, erro.toString(), "Error",
	                    JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				MeuResultSet codPratos = null;
				
				try 
				{
					codPratos = DAOs.getPratos().getPratos();
				}
				catch (Exception erro)
				{
					JOptionPane.showMessageDialog(null, erro.toString(), "Error",
	                    JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				int codPrato;
				
				try
				{
					codPratos.last();
					codPrato = codPratos.getInt("codPrato");
				}
				catch (Exception erro)
				{
					JOptionPane.showMessageDialog(null, erro.toString(), "Error",
	                    JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				lblAvisoPrato.setText("");
				
				JOptionPane.showMessageDialog(null, "Insira a imagem do " + txtNomePrato.getText()+ " na pasta site/Imagens/ com o nome de " + codPrato + ".jpg");
			}	
		});
		
		JLabel lblEscolhaOPreo = new JLabel("Escolha o pre\u00E7o do prato");
		panel_26.add(lblEscolhaOPreo);
		
		spnPrecoPrato.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(1)));
		panel_26.add(spnPrecoPrato);
		panel_26.add(btnAdicionarPrato);

		panel_26.add(lblAvisoPrato);
		
		JPanel panel_25 = new JPanel();
		panel_21.add(panel_25);
		panel_25.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_27 = new JPanel();
		panel_25.add(panel_27, BorderLayout.CENTER);
		panel_27.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblEscoclhaOCdigo = new JLabel("Escolha o c\u00F3digo do prato que deseja deletar");
		panel_27.add(lblEscoclhaOCdigo);
		
		DefaultListModel<Integer> listModelPratos = new DefaultListModel<Integer>();
		JList<Integer> lstPratos = new JList<Integer>(listModelPratos);
		lstPratos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPanePratosDel = new JScrollPane (lstPratos);
		panel_27.add(scrollPanePratosDel);

		JButton btnAtualizarPratos = new JButton("Atualizar Pratos");
		
		JButton btnDeletarprato = new JButton("Deletar Prato");
		btnDeletarprato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (lstPratos.getSelectedIndex() < 0)
					return;
				
				try
				{
					DAOs.getPratos().excluir(lstPratos.getSelectedValue());
				}
				catch (Exception erro)
				{
					JOptionPane.showMessageDialog(null, erro.toString(), "Error",
	                    JOptionPane.ERROR_MESSAGE);
					return;
				}
				btnAtualizarPratos.doClick();
			}
		});
		panel_27.add(btnDeletarprato);
		
		btnAtualizarPratos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MeuResultSet pratos = null;
				listModelPratos.clear();
						
				try
				{
					pratos = DAOs.getPratos().getPratos();
				}
				catch (Exception erro)
				{
					JOptionPane.showMessageDialog(null, erro.toString(), "Error",
	                    JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				try
				{
					while (pratos.next())
						listModelPratos.addElement(pratos.getInt("codPrato"));
				}
				catch (Exception erro)
				{
					JOptionPane.showMessageDialog(null, erro.toString(), "Error",
		                    JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		});
		panel_27.add(btnAtualizarPratos);

		panel = new JPanel();
		frmHaruNoHana.getContentPane().add(panel, BorderLayout.EAST);
		panel.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		

		
		String [] colunas = {"codPrato","qtde","hora"};
		tableModelPeds = new DefaultTableModel (colunas,0);
		JLabel lbl_pedidos = new JLabel("\u00DAltimos Pedidos");
		panel_1.add(lbl_pedidos, BorderLayout.NORTH);
		tablePeds = new JTable(tableModelPeds);
		JScrollPane scrollPanePeds = new JScrollPane (tablePeds);
		panel_1.add(scrollPanePeds, BorderLayout.CENTER);
		
		
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));

		
		String [] head = {"codMesa","hora","valor"};
		tableModelFechs = new DefaultTableModel (head,0);
		JLabel lbl_fechamentos = new JLabel("\u00DAltimos Fechamentos");
		panel_2.add(lbl_fechamentos, BorderLayout.NORTH);
		tableFechs = new JTable(tableModelFechs);
		JScrollPane scrollPaneFechs = new JScrollPane(tableFechs);
		panel_2.add(scrollPaneFechs, BorderLayout.CENTER);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tabbedPane,panel);
		
		JPanel panel_23 = new JPanel();
		tabbedPane.addTab("Question\u00E1rios", null, panel_23, null);
		panel_23.setLayout(new BorderLayout(0, 0));

		String [] cabec = {"codQuest","codCliente","qualidadeComida","atendimento","tempoEspera","observacoes"};
		DefaultTableModel tableModelQuest = new DefaultTableModel (cabec,0);
		JTable tblQuest = new JTable(tableModelQuest);
		JScrollPane scrollPaneQuest = new JScrollPane(tblQuest);
		panel_23.add(scrollPaneQuest);
		
		JButton btnAtualizaQuest = new JButton("Atualizar");
		btnAtualizaQuest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tableModelQuest.setRowCount(0);
				tblQuest.setEnabled(true);
				MeuResultSet quest = null;
				
				try
				{
					quest = DAOs.getQuestionarios().getQuestionarios();
				}
				catch (Exception erro)
				{
					JOptionPane.showMessageDialog(null, erro.toString(), "Error",
		                    JOptionPane.ERROR_MESSAGE);
				}
				
				try
				{
					while (quest.next())
						tableModelQuest.addRow(new Object [] {quest.getInt("codQuest"),quest.getInt("codCliente"),quest.getFloat("qualidadeComida"),quest.getFloat("atendimento"),quest.getFloat("tempoEspera"),quest.getString("observacoes")});
				}
				catch (Exception erro)
				{
					JOptionPane.showMessageDialog(null, erro.toString(), "Error",
		                    JOptionPane.ERROR_MESSAGE);
				}
				tblQuest.setEnabled(false);
			}
		});
		panel_23.add(btnAtualizaQuest, BorderLayout.SOUTH);
		frmHaruNoHana.getContentPane().add(splitPane, BorderLayout.CENTER);
		
		long delay = 2000;   // delay de 2 seg.
	    long interval = 1000;  // intervalo de 1 seg.
	    Timer timer = new Timer();
	    
	    timer.scheduleAtFixedRate(new TimerTask() {
	            public void run() {
	                AtualizaPedidos();
	            }
	        }, delay, interval);
	}

	public void actionPerformed(ActionEvent e) {
		if ((e.getSource() == rdbtnNome)||(e.getSource() == rdbtnDataDeCadastro)||(e.getSource() == rdbtnUltimaVisita)||(e.getSource() == rdbtnQtdVisitas)||(e.getSource() == rdbtnMediaGasta)) {
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
					tableModel.addRow(new Object[] {clientes.getInt("codCliente"),clientes.getString("userLogin"),clientes.getInt("qtdVisitas"),
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
		} else if ((e.getSource() == rdbtnEntradas)||(e.getSource() == rdbtnPratos)||(e.getSource() == rdbtnSobremesas)||(e.getSource() == rdbtnBebidas)||(e.getSource()==rdbtnTodas)) {
			MeuResultSet pratos = null;
			tableModelPratos.setRowCount(0);
			tblPratos.setEnabled(true);
			
			try
			{
				pratos = DAOs.getPratos().getPratosOrdenado(e.getActionCommand());
			}
			catch (Exception erro)
			{
				JOptionPane.showMessageDialog(null, erro.toString(), "Error",
						JOptionPane.ERROR_MESSAGE);				
			}
			
			try
			{
				while (pratos.next())
					tableModelPratos.addRow(new Object [] {pratos.getInt("codPrato"),pratos.getString("nomePrato"),pratos.getString("ingredientes"),pratos.getString("descricao"),pratos.getString("classificacao"),pratos.getBigDecimal("preco")});
			}
			catch (Exception erro)
			{
				JOptionPane.showMessageDialog(null, erro.toString(), "Error",
					JOptionPane.ERROR_MESSAGE);	
			}
			
			tblPratos.setEnabled(false);
		}	
	}
	
	public void AtualizaPedidos ()
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
			tableModelPeds.setRowCount(0);
		
			while (pedidos.next())
				tableModelPeds.addRow(new Object[] {pedidos.getInt("codPrato"),pedidos.getInt("quantidade"),pedidos.getTimestamp("horario")});
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
	
	private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
}
