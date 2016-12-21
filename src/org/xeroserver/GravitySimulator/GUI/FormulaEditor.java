package org.xeroserver.GravitySimulator.GUI;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.xeroserver.GravitySimulator.Support.Vars;
import org.xeroserver.x0library.gui.InputField;

public class FormulaEditor extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private InputField txt_G;
	private InputField txt_R;

	@SuppressWarnings("serial")
	public FormulaEditor() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/img/gravsim64.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 228, 128);
		this.setTitle("Edit Formula");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setResizable(false);

		txt_G = new InputField(true, InputField.DOUBLE) {
			@Override
			public void update() {
				Vars.G = this.getDoubleValue();
				Vars.logger.info("Updated G to " + Vars.G);
			}
		};

		txt_G.setText(Vars.G + "");

		txt_G.setBounds(60, 11, 108, 20);
		contentPane.add(txt_G);
		txt_G.setColumns(10);

		JLabel lblG = new JLabel("G:");
		lblG.setBounds(21, 14, 29, 14);
		contentPane.add(lblG);

		txt_R = new InputField(true, InputField.DOUBLE) {
			@Override
			public void update() {
				Vars.rExponent = this.getDoubleValue();
				Vars.logger.info("Updated R-Exponent to " + Vars.rExponent);
			}
		};
		txt_R.setText(Vars.rExponent + "");

		txt_R.setBounds(60, 42, 108, 20);
		contentPane.add(txt_R);
		txt_R.setColumns(10);

		JLabel lblRe = new JLabel("r^E:");
		lblRe.setBounds(21, 45, 29, 14);
		contentPane.add(lblRe);

		this.setVisible(true);
	}
}
