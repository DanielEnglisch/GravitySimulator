package at.xer0.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import at.xer0.Support.Vars;

public class MainFrame extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	private JPanel masterPanel;
	private JTextField t_mass;
	private JTextField t_xVelocity;
	private JTextField t_yVelocity;
	private JPanel controlPanel;
	
	//Public GUI
	public  JPanel renderPanel;
	public JLabel l_Time;
	public JLabel l_Objects;
	//
	
	public MainFrame()
	{


		JButton b_StartStop = new JButton("Start/Stop");
		b_StartStop.setToolTipText("Starts/Stops the simulation");
		b_StartStop.setBounds(10, 11, 178, 35);
		b_StartStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GUIEvents.startStop();
			}
		});
		
		
		
		JLabel l_newObject = new JLabel("New Object:");
		l_newObject.setFont(new Font("Tahoma", Font.BOLD, 11));
		l_newObject.setBounds(10, 397, 178, 14);
		
		t_mass = new JTextField();
		t_mass.setText(Vars.defMassPreset);
		t_mass.setBounds(85, 422, 103, 20);
		t_mass.setColumns(10);

		t_xVelocity = new JTextField();
		t_xVelocity.setText(Vars.defxVelocityPreset);
		t_xVelocity.setBounds(85, 453, 103, 20);
		
		t_yVelocity = new JTextField();
		t_yVelocity.setText(Vars.defyVelocityPreset);
		t_yVelocity.setBounds(85, 484, 103, 20);
		t_yVelocity.setColumns(10);
		
		JLabel l_mass = new JLabel("Mass:");
		l_mass.setBounds(10, 425, 65, 14);
		
		JLabel l_xVelocity = new JLabel("x Velocity:");
		l_xVelocity.setBounds(10, 456, 65, 14);
		
		JLabel l_yVelocity = new JLabel("y Velocity:");
		l_yVelocity.setBounds(10, 487, 65, 14);
		
		JButton b_ApplyObject = new JButton("Apply");
		b_ApplyObject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try
				{
					double xVel = Double.parseDouble(t_xVelocity.getText());
					double yVel = Double.parseDouble(t_yVelocity.getText());
					double mass = Double.parseDouble(t_mass.getText());
					
					Vars.currentxVelocityPreset = xVel;
					Vars.currentyVelocityPreset = yVel;
					Vars.currentMassPreset = mass;

				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Keine g�ltigen Werte!");
					Vars.currentxVelocityPreset = Double.parseDouble(Vars.defxVelocityPreset);
					Vars.currentyVelocityPreset = Double.parseDouble(Vars.defyVelocityPreset);
					Vars.currentMassPreset = Double.parseDouble(Vars.defMassPreset);
				}
				
				
			

				
				System.out.println("Preset set!");
			}
		});
		b_ApplyObject.setBounds(10, 515, 178, 23);
		
		JButton b_EditFormula = new JButton("Edit Formula");
		b_EditFormula.setEnabled(false);
		b_EditFormula.setBounds(10, 165, 178, 35);
		
		JSlider slider = new JSlider();
		slider.setEnabled(false);
		slider.setBackground(Color.WHITE);
		slider.setBounds(10, 128, 178, 26);
		
		JLabel lblTimestep = new JLabel("Timestep:");
		lblTimestep.setEnabled(false);
		lblTimestep.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTimestep.setBounds(10, 103, 65, 14);
		
		JButton btnNewButton = new JButton("Reverse Time");
		btnNewButton.setEnabled(false);
		btnNewButton.setBounds(10, 57, 178, 35);
		
		JButton btnNewButton_1 = new JButton("Select Output File");
		btnNewButton_1.setEnabled(false);
		btnNewButton_1.setBounds(10, 211, 178, 35);
		
		JButton b_DrawPath = new JButton("Draw Path");
		b_DrawPath.setEnabled(false);
		b_DrawPath.setBounds(10, 289, 178, 35);
		
		JSlider slider_1 = new JSlider();
		slider_1.setEnabled(false);
		slider_1.setBackground(Color.WHITE);
		slider_1.setBounds(10, 360, 178, 26);
		
		JLabel lblZoomLevel = new JLabel("Zoom Level:");
		lblZoomLevel.setEnabled(false);
		lblZoomLevel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblZoomLevel.setBounds(10, 335, 103, 14);
		
		JButton b_Clear = new JButton("Clear Simulator");
		b_Clear.setBounds(10, 255, 178, 23);
		b_Clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIEvents.clearSimulation();
			}
		});
		
		renderPanel = new RenderPanel();
		renderPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				GUIEvents.addObject(arg0.getX(), arg0.getY());
			}
		});
		renderPanel.setBounds(218, 11, 600, 549);
		
		controlPanel = new JPanel();
		controlPanel.setBackground(Color.WHITE);
		controlPanel.setBounds(10, 11, 198, 549);
		controlPanel.setLayout(null);
		controlPanel.add(b_StartStop);
		controlPanel.add(l_newObject);
		controlPanel.add(t_mass);
		controlPanel.add(t_xVelocity);
		controlPanel.add(t_yVelocity);
		controlPanel.add(l_mass);
		controlPanel.add(l_xVelocity);
		controlPanel.add(l_yVelocity);
		controlPanel.add(b_ApplyObject);
		controlPanel.add(b_EditFormula);
		controlPanel.add(slider);
		controlPanel.add(lblTimestep);
		controlPanel.add(btnNewButton);
		controlPanel.add(b_DrawPath);
		controlPanel.add(slider_1);
		controlPanel.add(btnNewButton_1);
		controlPanel.add(lblZoomLevel);
		controlPanel.add(b_Clear);
		
		masterPanel = new JPanel();
		masterPanel.setBackground(new Color(238, 232, 170));
		masterPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		masterPanel.setLayout(null);
		masterPanel.add(controlPanel);
		masterPanel.add(renderPanel);
		
		l_Time = new JLabel("Time: " + Vars.time);
		l_Time.setBounds(10, 11, 130, 14);
		renderPanel.add(l_Time);
		
		l_Objects = new JLabel("Objects: " + Vars.sObjects.size());
		l_Objects.setBounds(10, 27, 102, 14);
		renderPanel.add(l_Objects);
		
		//JFrame:
		setBackground(Color.BLACK);
		setResizable(false);
		setTitle("x0 Gravity Simulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 835, 600);
		setContentPane(masterPanel);
		
	}


	@Override
	public void run() {
		setVisible(true);	
	}
}
