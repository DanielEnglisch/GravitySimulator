package at.xer0.GravitySimulator.GUI;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import at.xer0.GravitySimulator.Support.Vars;

public class FormulaEditor extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txt_G;
	private JTextField textField;

	public FormulaEditor() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				MainFrame.class.getResource("/img/gravsim64.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 228, 128);
		this.setTitle("Edit Formula");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setResizable(false);

		txt_G = new JTextField();
		txt_G.setText(Vars.G + "");

		txt_G.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {

				txt_G.setForeground(Color.RED);

				double G = Vars.G;

				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					try {

						G = Double.parseDouble(txt_G.getText());

					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Invalid input!");
						return;
					}

					Vars.G = G;
					txt_G.setForeground(Color.BLACK);

					System.out.println("G updated: " + Vars.G);

				}

			}
		});
		txt_G.setBounds(60, 11, 108, 20);
		contentPane.add(txt_G);
		txt_G.setColumns(10);

		JLabel lblG = new JLabel("G:");
		lblG.setBounds(21, 14, 29, 14);
		contentPane.add(lblG);

		textField = new JTextField();
		textField.setText(Vars.rExponent + "");
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				textField.setForeground(Color.RED);

				double r = Vars.rExponent;

				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					try {

						r = Double.parseDouble(textField.getText());

					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Invalid input!");
						return;
					}

					Vars.rExponent = r;
					textField.setForeground(Color.BLACK);
					System.out.println("r Exponent updated: " + Vars.rExponent);

				}

			}
		});
		textField.setBounds(60, 42, 108, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblRe = new JLabel("r^E:");
		lblRe.setBounds(21, 45, 29, 14);
		contentPane.add(lblRe);

		this.setVisible(true);
	}
}
