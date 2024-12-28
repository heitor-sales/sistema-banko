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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import modelo.Conta;
import modelo.Correntista;
import modelo.Especial;
import regras_negocio.Fachada;

public class TelaConta {
    private JDialog frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JLabel label;
    private JTextField textFieldCPF;
    private JTextField textFieldLimite;

    /**
     * Create the application.
     */
    public TelaConta() {
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
        frame.setTitle("Conta");
        frame.setBounds(100, 100, 729, 385);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                listagemContas();
            }
        });

        scrollPane = new JScrollPane();
        scrollPane.setBounds(21, 43, 674, 148);
        frame.getContentPane().add(scrollPane);

        table = new JTable();
        table.setGridColor(Color.BLACK);
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

        JLabel lblCPF = new JLabel("CPF do Correntista:");
        lblCPF.setHorizontalAlignment(SwingConstants.LEFT);
        lblCPF.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblCPF.setBounds(21, 190, 150, 14);
        frame.getContentPane().add(lblCPF);

        textFieldCPF = new JTextField();
        textFieldCPF.setFont(new Font("Dialog", Font.PLAIN, 12));
        textFieldCPF.setBounds(170, 187, 195, 20);
        frame.getContentPane().add(textFieldCPF);

        JLabel lblLimite = new JLabel("Limite (Conta Especial):");
        lblLimite.setHorizontalAlignment(SwingConstants.LEFT);
        lblLimite.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblLimite.setBounds(21, 220, 150, 14);
        frame.getContentPane().add(lblLimite);

        textFieldLimite = new JTextField();
        textFieldLimite.setFont(new Font("Dialog", Font.PLAIN, 12));
        textFieldLimite.setBounds(170, 217, 195, 20);
        frame.getContentPane().add(textFieldLimite);

        JButton btnCriarContaSimples = new JButton("Criar Conta Simples");
        btnCriarContaSimples.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (textFieldCPF.getText().isEmpty()) {
                        label.setText("Preencha o campo cpf para criar uma conta simples.");
                        return;
                    }
                    String cpf = textFieldCPF.getText();
                    Fachada.criarConta(cpf);
                    label.setText("Conta simples criada para o correntista com CPF: " + cpf);
                    listagemContas();
                } catch (Exception ex) {
                    label.setText(ex.getMessage());
                }
            }
        });
        btnCriarContaSimples.setBounds(400, 187, 150, 23);
        frame.getContentPane().add(btnCriarContaSimples);

        JButton btnCriarContaEspecial = new JButton("Criar Conta Especial");
        btnCriarContaEspecial.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (textFieldCPF.getText().isEmpty() || textFieldLimite.getText().isEmpty()) {
                        label.setText("Preencha os campos cpf e limite para criar uma conta especial.");
                        return;
                    }
                    String cpf = textFieldCPF.getText();
                    double limite = Double.parseDouble(textFieldLimite.getText());
                    Fachada.criarContaEspecial(cpf, limite);
                    label.setText("Conta especial criada com limite de: " + limite);
                    listagemContas();
                } catch (Exception ex) {
                    label.setText(ex.getMessage());
                }
            }
        });
        btnCriarContaEspecial.setBounds(400, 217, 150, 23);
        frame.getContentPane().add(btnCriarContaEspecial);

        JButton btnApagarConta = new JButton("Apagar Conta");
        btnApagarConta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = table.getSelectedRow();
                if (linhaSelecionada != -1) {
                    try {
                        //personalizar opções "Sim" e "Não"
                        Object[] options = { "Sim", "Não" };
                        int escolha = JOptionPane.showOptionDialog(frame, 
                            "Deseja realmente apagar a conta selecionada?", 
                            "Confirmação", 
                            JOptionPane.YES_NO_OPTION, 
                            JOptionPane.QUESTION_MESSAGE, 
                            null, 
                            options, 
                            options[1]);

                        //se escolher "Sim"
                        if (escolha == JOptionPane.YES_OPTION) {
                            //obter o ID da conta selecionada
                            int idConta = (int) table.getValueAt(linhaSelecionada, 0);
                            Fachada.apagarConta(idConta);
                            label.setText("Conta apagada com sucesso!");
                            listagemContas();
                        }
                    } catch (Exception ex) {
                        label.setText(ex.getMessage());
                    }
                } else {
                    label.setText("Nenhuma conta selecionada.");
                }
            }
        });
        btnApagarConta.setBounds(400, 260, 160, 23);
        frame.getContentPane().add(btnApagarConta);

        JButton btnAdicionarCotitular = new JButton("Adicionar Cotitular");
        btnAdicionarCotitular.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = table.getSelectedRow();
                if (linhaSelecionada != -1) {
                    String cpf = JOptionPane.showInputDialog(frame, "Digite o CPF do correntista a ser associado:");
                    if (cpf != null && !cpf.isEmpty()) {
                        try {
                            //obter o ID da conta selecionada
                            int idConta = (int) table.getValueAt(linhaSelecionada, 0);
                            Fachada.inserirCorrentistaConta(idConta, cpf);
                            label.setText("Correntista com CPF " + cpf + " adicionado à conta com ID " + idConta);
                            listagemContas();
                        } catch (Exception ex) {
                            label.setText(ex.getMessage());
                        }
                    }
                } else {
                    label.setText("Nenhuma conta selecionada.");
                }
            }
        });
        btnAdicionarCotitular.setBounds(580, 187, 150, 23);
        frame.getContentPane().add(btnAdicionarCotitular);

        JButton btnRemoverCotitular = new JButton("Remover Cotitular");
        btnRemoverCotitular.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = table.getSelectedRow();
                if (linhaSelecionada != -1) {
                    String cpf = JOptionPane.showInputDialog(frame, "Digite o CPF do correntista a ser desvinculado:");
                    if (cpf != null && !cpf.isEmpty()) {
                        try {
                            //obter o ID da conta selecionada
                            int idConta = (int) table.getValueAt(linhaSelecionada, 0);
                            Fachada.removerCorrentistaConta(idConta, cpf);
                            label.setText("Correntista com CPF " + cpf + " removido da conta com ID " + idConta);
                            listagemContas();
                        } catch (Exception ex) {
                            label.setText(ex.getMessage());
                        }
                    }
                } else {
                    label.setText("Nenhuma conta selecionada.");
                }
            }
        });
        btnRemoverCotitular.setBounds(580, 217, 150, 23);
        frame.getContentPane().add(btnRemoverCotitular);
    }

    private void listagemContas() {
        try {
            List<Conta> lista = Fachada.listarContas();
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Saldo");
            model.addColumn("Limite");
            model.addColumn("Correntistas Associados");

            for (Conta c : lista) {
                double limite = (c instanceof Especial) ? ((Especial) c).getLimite() : 0;

                List<Correntista> correntistas = c.getCorrentistas();
                StringBuilder cpfsCorrentistas = new StringBuilder();
                for (Correntista correntista : correntistas) {
                    cpfsCorrentistas.append(correntista.getCpf()).append(", ");
                }
                
                //remover a última vírgula
                if (cpfsCorrentistas.length() > 0) {
                    cpfsCorrentistas.setLength(cpfsCorrentistas.length() - 2);
                }

                model.addRow(new Object[] { c.getId(), c.getSaldo(), limite, cpfsCorrentistas.toString() });
            }

            table.setModel(model);
        } catch (Exception e) {
            label.setText(e.getMessage());
        }
    }
}
