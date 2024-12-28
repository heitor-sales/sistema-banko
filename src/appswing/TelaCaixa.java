package appswing;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import regras_negocio.Fachada;

public class TelaCaixa {
    private JDialog frame;
    private JTextField textFieldIdConta;
    private JTextField textFieldValor;
    private JTextField textFieldCpf;
    private JTextField textFieldSenha;
    private JTextField textFieldIdDestino;

    /**
     * Create the application.
     */
    public TelaCaixa() {
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
        frame.setTitle("Caixa");
        frame.setBounds(100, 100, 400, 350);
        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
            }
        });

        JLabel lblIdConta = new JLabel("ID da Conta:");
        lblIdConta.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblIdConta.setBounds(20, 20, 100, 20);
        frame.getContentPane().add(lblIdConta);

        textFieldIdConta = new JTextField();
        textFieldIdConta.setFont(new Font("Dialog", Font.PLAIN, 12));
        textFieldIdConta.setBounds(130, 20, 200, 20);
        frame.getContentPane().add(textFieldIdConta);

        JLabel lblCpf = new JLabel("CPF:");
        lblCpf.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblCpf.setBounds(20, 60, 100, 20);
        frame.getContentPane().add(lblCpf);

        textFieldCpf = new JTextField();
        textFieldCpf.setFont(new Font("Dialog", Font.PLAIN, 12));
        textFieldCpf.setBounds(130, 60, 200, 20);
        frame.getContentPane().add(textFieldCpf);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblSenha.setBounds(20, 100, 100, 20);
        frame.getContentPane().add(lblSenha);

        textFieldSenha = new JTextField();
        textFieldSenha.setFont(new Font("Dialog", Font.PLAIN, 12));
        textFieldSenha.setBounds(130, 100, 200, 20);
        frame.getContentPane().add(textFieldSenha);

        JLabel lblValor = new JLabel("Valor:");
        lblValor.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblValor.setBounds(20, 140, 100, 20);
        frame.getContentPane().add(lblValor);

        textFieldValor = new JTextField();
        textFieldValor.setFont(new Font("Dialog", Font.PLAIN, 12));
        textFieldValor.setBounds(130, 140, 200, 20);
        frame.getContentPane().add(textFieldValor);

        JButton btnCreditar = new JButton("Creditar");
        btnCreditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int idConta = Integer.parseInt(textFieldIdConta.getText());
                    String cpf = textFieldCpf.getText();
                    String senha = textFieldSenha.getText();
                    double valor = Double.parseDouble(textFieldValor.getText());
                    Fachada.creditarValor(idConta, cpf, senha, valor);
                    JOptionPane.showMessageDialog(frame, "Valor creditado com sucesso!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            }
        });
        btnCreditar.setBounds(20, 180, 120, 30);
        frame.getContentPane().add(btnCreditar);

        JButton btnDebitar = new JButton("Debitar");
        btnDebitar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int idConta = Integer.parseInt(textFieldIdConta.getText());
                    String cpf = textFieldCpf.getText();
                    String senha = textFieldSenha.getText();
                    double valor = Double.parseDouble(textFieldValor.getText());
                    Fachada.debitarValor(idConta, cpf, senha, valor);
                    JOptionPane.showMessageDialog(frame, "Valor debitado com sucesso!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            }
        });
        btnDebitar.setBounds(160, 180, 120, 30);
        frame.getContentPane().add(btnDebitar);

        JLabel lblIdDestino = new JLabel("ID Destino:");
        lblIdDestino.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblIdDestino.setBounds(20, 220, 100, 20);
        frame.getContentPane().add(lblIdDestino);

        textFieldIdDestino = new JTextField();
        textFieldIdDestino.setFont(new Font("Dialog", Font.PLAIN, 12));
        textFieldIdDestino.setBounds(130, 220, 200, 20);
        frame.getContentPane().add(textFieldIdDestino);

        JButton btnTransferir = new JButton("Transferir");
        btnTransferir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int idConta = Integer.parseInt(textFieldIdConta.getText());
                    String cpf = textFieldCpf.getText();
                    String senha = textFieldSenha.getText();
                    double valor = Double.parseDouble(textFieldValor.getText());
                    int idDestino = Integer.parseInt(textFieldIdDestino.getText());
                    Fachada.transferirValor(idConta, cpf, senha, valor, idDestino);
                    JOptionPane.showMessageDialog(frame, "Valor transferido com sucesso!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            }
        });
        btnTransferir.setBounds(20, 260, 310, 30);
        frame.getContentPane().add(btnTransferir);
    }
}
