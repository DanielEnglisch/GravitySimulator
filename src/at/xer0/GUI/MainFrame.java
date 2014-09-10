
package at.xer0.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import at.xer0.Support.ColorEnum;
import at.xer0.Support.Vars;
import at.xer0.Support.Vec2D;

public class MainFrame extends JFrame implements Runnable
{

	private static final long serialVersionUID = 1L;
	private JPanel masterPanel;
	private JTextField t_mass;
	private JTextField t_xVelocity;
	private JTextField t_yVelocity;
	private JPanel controlPanel;

	// Public GUI
	public JPanel renderPanel;
	public JLabel l_Time;
	public JLabel l_Objects;
	public JButton b_StartStop;
	public JButton b_ReverseTime;
	public JCheckBox cb_speedVec;
	public JLabel l_timestep;
	public JButton b_nextStep;
	public JTextField t_steps;
	public JCheckBox cb_centerOfMass;
	public JCheckBox cb_drawPath;
	//

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public MainFrame()
	{

		b_StartStop = new JButton("Start Simulation");
		b_StartStop.setToolTipText("Start");
		b_StartStop.setBounds(10, 11, 178, 35);
		b_StartStop.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent arg0)
			{
				GUIEvents.startStop();
			}
		});

		l_timestep = new JLabel("0.00001");

		JLabel l_newObject = new JLabel("New Object:");
		l_newObject.setFont(new Font("Tahoma", Font.BOLD, 11));
		l_newObject.setBounds(10, 441, 178, 14);

		t_mass = new JTextField();
		t_mass.setText(Vars.defMassPreset);
		t_mass.setBounds(85, 466, 103, 20);
		t_mass.setColumns(10);

		t_xVelocity = new JTextField();
		t_xVelocity.setText(Vars.defxVelocityPreset);
		t_xVelocity.setBounds(85, 497, 103, 20);

		t_yVelocity = new JTextField();
		t_yVelocity.setText(Vars.defyVelocityPreset);
		t_yVelocity.setBounds(85, 528, 103, 20);
		t_yVelocity.setColumns(10);

		JLabel l_mass = new JLabel("Mass:");
		l_mass.setBounds(10, 469, 65, 14);

		JLabel l_xVelocity = new JLabel("x Velocity:");
		l_xVelocity.setBounds(10, 500, 65, 14);

		JLabel l_yVelocity = new JLabel("y Velocity:");
		l_yVelocity.setBounds(10, 531, 65, 14);

		final JComboBox<?> comboBox = new JComboBox<>();
		comboBox.setModel(new DefaultComboBoxModel(ColorEnum.values()));
		comboBox.setBounds(10, 584, 178, 20);

		JButton b_ApplyObject = new JButton("Apply Preset");
		b_ApplyObject.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent arg0)
			{

				try
				{
					double xVel = Double.parseDouble(t_xVelocity.getText());
					double yVel = Double.parseDouble(t_yVelocity.getText());
					double mass = Double.parseDouble(t_mass.getText());

					Vars.currentVelocityPreset = new Vec2D(xVel, yVel);
					Vars.currentMassPreset = mass;

					Vars.currentColorPreset = ColorEnum.getAWTColor((ColorEnum) comboBox.getSelectedItem());

				} catch (Exception e)
				{
					JOptionPane.showMessageDialog(null, "Keine gültigen Werte!");
					Vars.currentVelocityPreset = new Vec2D(Double.parseDouble(Vars.defxVelocityPreset), Double.parseDouble(Vars.defyVelocityPreset));
					Vars.currentMassPreset = Double.parseDouble(Vars.defMassPreset);
				}

				System.out.println("Preset set!");
			}
		});
		b_ApplyObject.setBounds(10, 615, 178, 23);

		JLabel lblTimestep = new JLabel("Timestep:");
		lblTimestep.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTimestep.setBounds(10, 125, 65, 14);

		b_ReverseTime = new JButton("Reverse Time");
		b_ReverseTime.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent arg0)
			{
				GUIEvents.reverseTime();
			}
		});
		b_ReverseTime.setBounds(10, 57, 178, 23);

		JButton b_Clear = new JButton("Reset Simulator");
		b_Clear.setBounds(10, 91, 178, 23);
		b_Clear.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				Vars.isResetRequested = true;
			}
		});

		renderPanel = new RenderPanel();
		renderPanel.addMouseListener(new MouseAdapter()
		{

			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				GUIEvents.addObject(arg0.getX(), arg0.getY());
			}
		});
		renderPanel.setBounds(218, 11, 666, 649);

		controlPanel = new JPanel();
		controlPanel.setBackground(Color.WHITE);
		controlPanel.setBounds(10, 11, 198, 649);
		controlPanel.setLayout(null);

		l_timestep.setBounds(85, 125, 103, 14);
		controlPanel.add(l_timestep);

		final JSlider slider = new JSlider();
		slider.setSnapToTicks(true);
		slider.setPaintTicks(true);
		slider.setMajorTickSpacing(1);
		slider.setMinorTickSpacing(1);
		slider.addChangeListener(new ChangeListener()
		{

			public void stateChanged(ChangeEvent arg0)
			{

				if (Vars.mainFrame != null)
					GUIEvents.updateTimestep(slider.getValue());

			}
		});
		slider.setValue(5);
		slider.setMaximum(10);
		slider.setBackground(Color.WHITE);
		slider.setBounds(10, 150, 178, 26);

		controlPanel.add(b_StartStop);
		controlPanel.add(l_newObject);
		controlPanel.add(t_mass);
		controlPanel.add(t_xVelocity);
		controlPanel.add(t_yVelocity);
		controlPanel.add(l_mass);
		controlPanel.add(l_xVelocity);
		controlPanel.add(l_yVelocity);
		controlPanel.add(b_ApplyObject);
		controlPanel.add(slider);
		controlPanel.add(lblTimestep);
		controlPanel.add(b_ReverseTime);
		controlPanel.add(b_Clear);
		controlPanel.add(comboBox);

		masterPanel = new JPanel();
		masterPanel.setBackground(new Color(238, 232, 170));
		masterPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		masterPanel.setLayout(null);
		masterPanel.add(controlPanel);
		masterPanel.add(renderPanel);

		JLabel lblColor = new JLabel("Color:");
		lblColor.setBounds(10, 559, 46, 14);
		controlPanel.add(lblColor);

		cb_speedVec = new JCheckBox("Draw Velocity Vector");
		cb_speedVec.setEnabled(false);
		cb_speedVec.setBackground(Color.WHITE);
		cb_speedVec.setBounds(10, 390, 178, 23);
		controlPanel.add(cb_speedVec);

		b_nextStep = new JButton(">");
		b_nextStep.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent arg0)
			{

				int s = 1;

				try
				{
					s = Integer.parseInt(t_steps.getText());
				} catch (Exception esx)
				{
					JOptionPane.showMessageDialog(null, "Keine gültigen Werte!");
					return;
				}

				if (s <= 0)
				{
					JOptionPane.showMessageDialog(null, "Keine gültigen Werte!");
					return;
				}

				Vars.steps = s;
				Vars.nextStep = true;
			}
		});
		b_nextStep.setFont(new Font("Tahoma", Font.BOLD, 14));
		b_nextStep.setBounds(10, 243, 178, 23);
		controlPanel.add(b_nextStep);

		t_steps = new JTextField();
		t_steps.setText("1");
		t_steps.setBounds(10, 212, 178, 20);
		controlPanel.add(t_steps);
		t_steps.setColumns(10);

		JLabel lblStepsPerClick = new JLabel("Steps per click:");
		lblStepsPerClick.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblStepsPerClick.setBounds(10, 187, 178, 14);
		controlPanel.add(lblStepsPerClick);

		cb_centerOfMass = new JCheckBox("Center Of Mass");
		cb_centerOfMass.setEnabled(false);
		cb_centerOfMass.setBackground(Color.WHITE);
		cb_centerOfMass.setBounds(10, 338, 178, 23);
		controlPanel.add(cb_centerOfMass);

		cb_drawPath = new JCheckBox("Draw Path");
		cb_drawPath.setSelected(true);
		cb_drawPath.setBackground(Color.WHITE);
		cb_drawPath.setBounds(10, 364, 97, 23);
		controlPanel.add(cb_drawPath);

		l_Time = new JLabel("Time: " + Vars.time);
		l_Time.setBounds(10, 11, 130, 14);
		renderPanel.add(l_Time);

		l_Objects = new JLabel("Objects: " + Vars.activeObjects.size());
		l_Objects.setBounds(10, 27, 102, 14);
		renderPanel.add(l_Objects);

		// JFrame:
		setBackground(Color.BLACK);
		setResizable(false);
		setTitle("x0 Gravity Simulator v" + Vars.version);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 700);
		setContentPane(masterPanel);

	}

	@Override
	public void run()
	{
		setVisible(true);
	}

	public JTextField t_steps()
	{
		return t_steps;
	}

	public JCheckBox getCb_centerOfMass()
	{
		return cb_centerOfMass;
	}

	public JCheckBox getCb_drawPath()
	{
		return cb_drawPath;
	}

}
