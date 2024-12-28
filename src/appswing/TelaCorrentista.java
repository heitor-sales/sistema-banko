package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
//import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import modelo.Correntista;
import regras_negocio.Fachada;

public class TelaCorrentista {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JLabel label;
	private JTextField textFieldNome;
	private JTextField textFieldCPF;
	private JTextField textFieldSenha;

	/**
	 * Create the application.
	 */
	public TelaCorrentista() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JDialog();
		frame.setModal(true);
		frame.setResizable(false);
		frame.setTitle("Correntista");
		frame.setBounds(100, 100, 729, 385);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				listagemCorrentistas();
			}
		});

		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 43, 674, 148);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(Color.WHITE);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		label = new JLabel("");
		label.setForeground(Color.BLUE);
		label.setBounds(21, 321, 688, 14);
		frame.getContentPane().add(label);

		JLabel lblCPF = new JLabel("CPF:");
		lblCPF.setHorizontalAlignment(SwingConstants.LEFT);
		lblCPF.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCPF.setBounds(21, 190, 71, 14);
		frame.getContentPane().add(lblCPF);

		textFieldCPF = new JTextField();
		textFieldCPF.setFont(new Font("Dialog", Font.PLAIN, 12));
		textFieldCPF.setColumns(10);
		textFieldCPF.setBounds(68, 187, 195, 20);
		frame.getContentPane().add(textFieldCPF);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setHorizontalAlignment(SwingConstants.LEFT);
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNome.setBounds(281, 190, 63, 14);
		frame.getContentPane().add(lblNome);

		textFieldNome = new JTextField();
		textFieldNome.setFont(new Font("Dialog", Font.PLAIN, 12));
		textFieldNome.setColumns(10);
		textFieldNome.setBounds(336, 187, 168, 20);
		frame.getContentPane().add(textFieldNome);

		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setHorizontalAlignment(SwingConstants.LEFT);
		lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSenha.setBounds(21, 220, 71, 14);
		frame.getContentPane().add(lblSenha);

		textFieldSenha = new JTextField();
		textFieldSenha.setFont(new Font("Dialog", Font.PLAIN, 12));
		textFieldSenha.setColumns(10);
		textFieldSenha.setBounds(68, 217, 195, 20);
		frame.getContentPane().add(textFieldSenha);

		JButton btnCriarCorrentista = new JButton("Criar Correntista");
		btnCriarCorrentista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (textFieldCPF.getText().isEmpty() || textFieldNome.getText().isEmpty()
							|| textFieldSenha.getText().isEmpty()) {
						label.setText("Preencha todos os campos para cadastrar um correntista.");
						return;
					}	
					String cpf = textFieldCPF.getText();
					String nome = textFieldNome.getText();
					String senha = textFieldSenha.getText();
					Fachada.criarCorrentista(cpf, nome, senha);
					label.setText("Correntista criado: " + nome);
					listagemCorrentistas();
				} catch (Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		btnCriarCorrentista.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCriarCorrentista.setBounds(535, 187, 160, 23);
		frame.getContentPane().add(btnCriarCorrentista);
	}

	private void listagemCorrentistas() {
		try {
			List<Correntista> lista = Fachada.listarCorrentistas();
			DefaultTableModel model = new DefaultTableModel();
			model.addColumn("CPF");
			model.addColumn("Nome");
			model.addColumn("Contas");

			for (Correntista c : lista) {
	            StringBuilder contas = new StringBuilder();
	            c.getContas().forEach(conta -> {
	            	contas.append(conta.getId()).append(", ");
	            });

	            //remover a última vírgula
	            if (contas.length() > 0) {
	                contas.setLength(contas.length() - 2);
	            }

	            model.addRow(new Object[] { c.getCpf(), c.getNome(), contas.toString() });
	        }

	        table.setModel(model);
		} catch (Exception e) {
			label.setText(e.getMessage());
		}
	}
}
