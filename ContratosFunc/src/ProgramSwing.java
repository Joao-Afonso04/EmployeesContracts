import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

public class ProgramSwing extends JFrame {
    
    private JTextField txtDepartamento;
    private JTextField txtNome;
    private JComboBox<String> cbNivel;
    private JTextField txtSalarioBase;
    private JTextField txtDataContrato;
    private JTextField txtValorHora;
    private JTextField txtHoras;
    private JTextArea taContratos;
    private JTextField txtMesAno;
    private JLabel lblResultado;
    
    private Worker worker;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public ProgramSwing() {
        setTitle("Sistema de Contratos - Funcionários");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 700);
        setLocationRelativeTo(null);
        setResizable(true);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainPanel.add(criarPainelDepartamento());
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(criarPainelTrabalhador());
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(criarPainelContratos());
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(criarPainelIncome());

        JScrollPane scroll = new JScrollPane(mainPanel);
        add(scroll);
    }

    private JPanel criarPainelDepartamento() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Departamento"));

        panel.add(new JLabel("Nome:"));
        txtDepartamento = new JTextField(20);
        panel.add(txtDepartamento);

        return panel;
    }

    private JPanel criarPainelTrabalhador() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Dados do Trabalhador"));

        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p1.add(new JLabel("Nome:"));
        txtNome = new JTextField(20);
        p1.add(txtNome);
        panel.add(p1);

        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p2.add(new JLabel("Nível:"));
        cbNivel = new JComboBox<>(new String[]{"JUNIOR", "MID_LEVEL", "SENIOR"});
        p2.add(cbNivel);
        panel.add(p2);

        JPanel p3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p3.add(new JLabel("Salário Base:"));
        txtSalarioBase = new JTextField(15);
        p3.add(txtSalarioBase);
        panel.add(p3);

        JPanel p4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnCriar = new JButton("Criar Trabalhador");
        btnCriar.addActionListener(e -> criarTrabalhador());
        p4.add(btnCriar);
        panel.add(p4);

        return panel;
    }

    private JPanel criarPainelContratos() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Adicionar Contratos"));

        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p1.add(new JLabel("Data (dd/MM/yyyy):"));
        txtDataContrato = new JTextField(15);
        p1.add(txtDataContrato);
        panel.add(p1);

        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p2.add(new JLabel("Valor/Hora:"));
        txtValorHora = new JTextField(15);
        p2.add(txtValorHora);
        p2.add(new JLabel("Horas:"));
        txtHoras = new JTextField(10);
        p2.add(txtHoras);
        panel.add(p2);

        JPanel p3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnAdicionar = new JButton("Adicionar Contrato");
        btnAdicionar.addActionListener(e -> adicionarContrato());
        p3.add(btnAdicionar);
        panel.add(p3);

        panel.add(new JLabel("Contratos Adicionados:"));
        taContratos = new JTextArea(6, 50);
        taContratos.setEditable(false);
        taContratos.setLineWrap(true);
        JScrollPane scroll = new JScrollPane(taContratos);
        panel.add(scroll);

        return panel;
    }

    private JPanel criarPainelIncome() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Calcular Income"));

        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p1.add(new JLabel("Mês/Ano (MM/yyyy):"));
        txtMesAno = new JTextField(15);
        p1.add(txtMesAno);
        JButton btnCalcular = new JButton("Calcular");
        btnCalcular.addActionListener(e -> calcularIncome());
        p1.add(btnCalcular);
        panel.add(p1);

        lblResultado = new JLabel("");
        lblResultado.setFont(new Font("Arial", Font.BOLD, 12));
        lblResultado.setForeground(new Color(0, 102, 204));
        panel.add(lblResultado);

        return panel;
    }

    private void criarTrabalhador() {
        try {
            String nomeDept = txtDepartamento.getText().trim();
            if (nomeDept.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Insira o nome do departamento!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String nome = txtNome.getText().trim();
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Insira o nome do trabalhador!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String nivel = (String) cbNivel.getSelectedItem();
            double salario = Double.parseDouble(txtSalarioBase.getText().trim());

            Departament dept = new Departament(nomeDept);
            WorkerLevell workerLevel = WorkerLevell.valueOf(nivel);
            worker = new Worker(nome, workerLevel, salario, dept);

            taContratos.setText("");
            JOptionPane.showMessageDialog(this, "Trabalhador criado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Salário deve ser um número válido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void adicionarContrato() {
        try {
            if (worker == null) {
                JOptionPane.showMessageDialog(this, "Crie um trabalhador primeiro!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Date data = sdf.parse(txtDataContrato.getText().trim());
            double valorHora = Double.parseDouble(txtValorHora.getText().trim());
            int horas = Integer.parseInt(txtHoras.getText().trim());

            HourContract hc = new HourContract(data, valorHora, horas);
            worker.addContract(hc);

            String info = String.format("Data: %s | Valor/h: R$ %.2f | Horas: %d | Total: R$ %.2f\n",
                    txtDataContrato.getText(), valorHora, horas, hc.totalValue());

            taContratos.append(info);

            txtDataContrato.setText("");
            txtValorHora.setText("");
            txtHoras.setText("");

            JOptionPane.showMessageDialog(this, "Contrato adicionado!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Data inválida! Use dd/MM/yyyy", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Valores devem ser números!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calcularIncome() {
        try {
            if (worker == null) {
                JOptionPane.showMessageDialog(this, "Crie um trabalhador primeiro!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String mesAno = txtMesAno.getText().trim();
            if (!mesAno.contains("/")) {
                JOptionPane.showMessageDialog(this, "Use formato MM/yyyy", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String[] partes = mesAno.split("/");
            int mes = Integer.parseInt(partes[0]);
            int ano = Integer.parseInt(partes[1]);

            if (mes < 1 || mes > 12) {
                JOptionPane.showMessageDialog(this, "Mês deve estar entre 01 e 12!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double income = worker.income(ano, mes);
            lblResultado.setText(String.format(
                    "Income para %s: R$ %.2f | Departamento: %s | Trabalhador: %s",
                    mesAno, income, worker.getDepartament().getName(), worker.getName()));

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Formato inválido! Use MM/yyyy", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProgramSwing frame = new ProgramSwing();
            frame.setVisible(true);
        });
    }
}
