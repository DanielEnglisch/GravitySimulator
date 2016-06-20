package org.xeroserver.GravitySimulator.confgen;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ConfigGenerator extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tb_timestep;
	private JTextField tb_scale;
	private JTextField tb_deltax;
	private JTextField tb_deltay;
	private JTextField tb_pathsize;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfigGenerator frame = new ConfigGenerator();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ConfigGenerator() {
		setResizable(false);
		setTitle("Config Generator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 283, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTimestep = new JLabel("Timestep");
		lblTimestep.setBounds(10, 11, 46, 14);
		contentPane.add(lblTimestep);
		
		tb_timestep = new JTextField();
		tb_timestep.setBounds(61, 8, 199, 20);
		contentPane.add(tb_timestep);
		tb_timestep.setColumns(10);
		
		JLabel lblD = new JLabel("Scale 1:");
		lblD.setBounds(10, 36, 46, 14);
		contentPane.add(lblD);
		
		tb_scale = new JTextField();
		tb_scale.setBounds(61, 33, 199, 20);
		contentPane.add(tb_scale);
		tb_scale.setColumns(10);
		
		JLabel lblDeltax = new JLabel("DeltaX");
		lblDeltax.setBounds(10, 61, 46, 14);
		contentPane.add(lblDeltax);
		
		JLabel lblDeltay = new JLabel("DeltaY");
		lblDeltay.setBounds(10, 86, 46, 14);
		contentPane.add(lblDeltay);
		
		tb_deltax = new JTextField();
		tb_deltax.setBounds(61, 58, 199, 20);
		contentPane.add(tb_deltax);
		tb_deltax.setColumns(10);
		
		tb_deltay = new JTextField();
		tb_deltay.setBounds(61, 83, 199, 20);
		contentPane.add(tb_deltay);
		tb_deltay.setColumns(10);
		
		JLabel lblPathsize = new JLabel("Pathsize");
		lblPathsize.setBounds(10, 111, 46, 14);
		contentPane.add(lblPathsize);
		
		tb_pathsize = new JTextField();
		tb_pathsize.setBounds(61, 108, 199, 20);
		contentPane.add(tb_pathsize);
		tb_pathsize.setColumns(10);
	}
}
