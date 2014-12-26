
package at.xer0.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import at.xer0.Support.FileManager;
import at.xer0.Support.Obj;
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
	private JTextField t_yPos;
	private JTextField t_xPos;

	// Public GUI
	
	public JPanel renderPanel;
	
	public JLabel l_Time;
	public JLabel l_Objects;
	
	public JButton b_StartStop;
	public JButton b_nextStep;
	
	public JTextField t_steps;
	public JTextField t_pathSize;
	public JTextField t_timestep;
	public JTextField t_massstabInput;
	
	public JCheckBox cb_drawPath;
	public JCheckBox cb_showNames;

	public JLabel l_Timestep;
	public JLabel l_pathsize;
	public JLabel l_massstab;
	public JLabel l_steps;

	public double lastMouseWheelState = 1;
	public Vec2D mouseClickPos = new Vec2D(0, 0);
	public Vec2D mouseReleasePos = new Vec2D(0, 0);
	private JTextField t_nameField;


	//

	public MainFrame()
	{

		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/img/32x32.png")));

		GUIEvents.initGlobalKeyEvents();

		masterPanel = new JPanel();
		masterPanel.setBackground(new Color(238, 232, 170));
		masterPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		masterPanel.setLayout(null);
		masterPanel.setFocusable(false);

		controlPanel = new JPanel();
		controlPanel.setBackground(Color.WHITE);
		controlPanel.setBounds(10, 11, 198, 648);
		controlPanel.setLayout(null);

		renderPanel = new RenderPanel();

		initRenderPanel();
		initMasterPanel();
		initControlPanel();

	}
	
	@Override
	public void run()
	{
		setVisible(true);
	}


	private void initControlPanel()
	{

		b_StartStop = new JButton("Start Simulation");
		b_StartStop.setToolTipText("Start");
		b_StartStop.setBounds(10, 11, 178, 71);
		b_StartStop.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				GUIEvents.startStop();
			}
		});

		JLabel l_newObject = new JLabel("Object Presets:");
		l_newObject.setFont(new Font("Tahoma", Font.BOLD, 11));
		l_newObject.setBounds(10, 378, 178, 14);

		t_mass = new JTextField();
		t_mass.addKeyListener(new KeyAdapter()
		{

			@Override
			public void keyPressed(KeyEvent arg0)
			{
				t_mass.setForeground(Color.RED);
			}
		});
		t_mass.setText(Vars.preset_Mass + "");
		t_mass.setBounds(85, 403, 103, 20);
		t_mass.setColumns(10);

		t_xVelocity = new JTextField();
		t_xVelocity.setText(Vars.preset_Velocity.getX() + "");
		t_xVelocity.setBounds(85, 428, 103, 20);

		t_xVelocity.addKeyListener(new KeyAdapter()
		{

			@Override
			public void keyPressed(KeyEvent arg0)
			{
				t_xVelocity.setForeground(Color.RED);
			}
		});

		t_yVelocity = new JTextField();
		t_yVelocity.setText(Vars.preset_Velocity.getY() + "");
		t_yVelocity.setBounds(85, 453, 103, 20);
		t_yVelocity.setColumns(10);

		t_yVelocity.addKeyListener(new KeyAdapter()
		{

			@Override
			public void keyPressed(KeyEvent arg0)
			{
				t_yVelocity.setForeground(Color.RED);
			}
		});

		JLabel l_mass = new JLabel("Mass:");
		l_mass.setBounds(10, 406, 65, 14);

		JLabel l_xVelocity = new JLabel("x Velocity:");
		l_xVelocity.setBounds(10, 431, 65, 14);

		JLabel l_yVelocity = new JLabel("y Velocity:");
		l_yVelocity.setBounds(10, 456, 65, 14);

		JButton b_ApplyObject = new JButton("Apply Preset");
		b_ApplyObject.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{

				try
				{
					double xVel = Double.parseDouble(t_xVelocity.getText());
					double yVel = Double.parseDouble(t_yVelocity.getText());
					double mass = Double.parseDouble(t_mass.getText());
					String name = t_nameField.getText();
					
					Vars.preset_Velocity = new Vec2D(xVel, yVel);
					Vars.preset_Mass = mass;
					Vars.preset_Name = name;

					t_mass.setForeground(Color.BLACK);
					t_xVelocity.setForeground(Color.BLACK);
					t_yVelocity.setForeground(Color.BLACK);
					t_nameField.setForeground(Color.BLACK);

				} catch (Exception e)
				{
					JOptionPane.showMessageDialog(null, "Invalid input!");
					return;
				}

				System.out.println("New Preset: Velocity " + Vars.preset_Velocity.toString() + " - Mass:" + Vars.preset_Mass + " - Name: " + Vars.preset_Name);
			}
		});
		b_ApplyObject.setBounds(10, 506, 178, 23);

		l_Timestep = new JLabel("Timestep: 0.0001");
		l_Timestep.setFont(new Font("Tahoma", Font.BOLD, 13));
		l_Timestep.setBounds(10, 93, 178, 14);

		JLabel l_maßstabLabel = new JLabel("Scale = 1:");
		l_maßstabLabel.setHorizontalAlignment(SwingConstants.LEFT);
		l_maßstabLabel.setBounds(10, 353, 91, 14);

		b_nextStep = new JButton(">");
		b_nextStep.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Vars.nextStep = true;
			}
		});
		b_nextStep.setFont(new Font("Tahoma", Font.BOLD, 14));
		b_nextStep.setBounds(10, 265, 178, 23);

		l_steps = new JLabel("Steps: 1");
		l_steps.setFont(new Font("Tahoma", Font.BOLD, 13));
		l_steps.setBounds(10, 209, 178, 14);

		t_steps = new JTextField();
		t_steps.addKeyListener(new KeyAdapter()
		{

			@Override
			public void keyPressed(KeyEvent e)
			{

				t_steps.setForeground(Color.RED);

				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					int steps = 1;

					try
					{
						steps = Integer.parseInt(t_steps.getText());

						if (steps < 1)
						{
							JOptionPane.showMessageDialog(null, "Invalid input!");
							return;
						}
					} catch (Exception ex)
					{
						JOptionPane.showMessageDialog(null, "Invalid input!");
						return;
					}

					Vars.steps = steps;
					l_steps.setText("Steps: " + Vars.steps);
					t_steps.setForeground(Color.BLACK);

				}

			}
		});
		t_steps.setText("1");
		t_steps.setBounds(10, 234, 178, 20);
		t_steps.setColumns(10);

		cb_drawPath = new JCheckBox("Draw Path");
		cb_drawPath.setSelected(true);
		cb_drawPath.setBackground(Color.WHITE);
		cb_drawPath.setBounds(10, 295, 182, 23);

		l_pathsize = new JLabel("Pathsize: 300");
		l_pathsize.setFont(new Font("Tahoma", Font.BOLD, 13));
		l_pathsize.setBounds(10, 151, 176, 16);

		t_pathSize = new JTextField();
		t_pathSize.addKeyListener(new KeyAdapter()
		{

			@Override
			public void keyPressed(KeyEvent arg0)
			{

				t_pathSize.setForeground(Color.RED);

				if (arg0.getKeyCode() == KeyEvent.VK_ENTER)
				{
					int pathsize = 0;

					try
					{
						pathsize = Integer.parseInt(t_pathSize.getText());

						if (pathsize < 0)
						{
							JOptionPane.showMessageDialog(null, "Invalid input!");
							return;
						}

					} catch (Exception ecc)
					{
						JOptionPane.showMessageDialog(null, "Invalid input!");
						return;
					}

					Vars.pathSize = pathsize;
					l_pathsize.setText("Pathsize: " + Vars.pathSize);

					t_pathSize.setForeground(Color.BLACK);

				}

			}
		});

		t_pathSize.setText("300");
		t_pathSize.setBounds(10, 178, 178, 20);
		t_pathSize.setColumns(10);

		t_timestep = new JTextField();
		t_timestep.addKeyListener(new KeyAdapter()
		{

			@Override
			public void keyPressed(KeyEvent arg0)
			{

				t_timestep.setForeground(Color.RED);

				if (arg0.getKeyCode() == KeyEvent.VK_ENTER)
				{
					try
					{
						double d = Double.parseDouble(t_timestep.getText());

						Vars.timeStep = d;

						l_Timestep.setText("Timestep: " + Vars.timeStep);

						t_timestep.setForeground(Color.BLACK);

					} catch (Exception ex)
					{
						JOptionPane.showMessageDialog(null, "Invalid input!");
						return;
					}
				}
			}
		});
		t_timestep.setText("0.0001");
		t_timestep.setBounds(10, 118, 178, 22);
		t_timestep.setColumns(10);

		cb_showNames = new JCheckBox("Show names");
		cb_showNames.setSelected(true);
		cb_showNames.setBackground(Color.WHITE);
		cb_showNames.setBounds(10, 321, 178, 25);

		t_yPos = new JTextField();

		t_yPos.setText("0");
		t_yPos.setBounds(85, 571, 103, 20);
		t_yPos.setColumns(10);

		t_xPos = new JTextField();

		t_xPos.setText("0");
		t_xPos.setBounds(85, 540, 103, 20);
		t_xPos.setColumns(10);

		final JLabel l_xPos = new JLabel("x Position:");
		l_xPos.setBounds(10, 543, 65, 14);

		final JLabel l_yPos = new JLabel("y Position:");
		l_yPos.setBounds(10, 574, 65, 14);

		JButton btnPlaceObject = new JButton("Place Object");
		btnPlaceObject.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{

				double x = 0;
				double y = 0;

				try
				{
					x = Double.parseDouble(t_xPos.getText());
					y = Double.parseDouble(t_yPos.getText());

				} catch (Exception ee)
				{
					JOptionPane.showMessageDialog(null, "Invalid input!");
					return;
				}

				GUIEvents.addObject(x, y);

			}
		});
		btnPlaceObject.setBounds(10, 602, 178, 35);

		t_massstabInput = new JTextField();
		t_massstabInput.setText("1000");
		t_massstabInput.addKeyListener(new KeyAdapter()
		{

			@Override
			public void keyPressed(KeyEvent arg0)
			{

				t_massstabInput.setForeground(Color.RED);

				if (arg0.getKeyCode() == KeyEvent.VK_ENTER)
				{
					double d = 1;

					try
					{
						d = Double.parseDouble(t_massstabInput.getText());
						t_massstabInput.setForeground(Color.BLACK);

					} catch (Exception ex)
					{
						JOptionPane.showMessageDialog(null, "Invalid input!");
						return;
					}

					if (d < 1)
					{
						JOptionPane.showMessageDialog(null, "Invalid input!");
						return;
					}

					// Reset Color
					Vars.scaling_ZoomFactor = 1 / d;
					Vars.clearPoints = true;

				}

			}
		});
		t_massstabInput.setBounds(85, 350, 103, 20);
		t_massstabInput.setColumns(10);

		l_Time = new JLabel("Time: " + Vars.time);
		l_Time.setForeground(Color.WHITE);
		l_Time.setBounds(10 - (renderPanel.getWidth() / 2), 11 - (renderPanel.getHeight() / 2), renderPanel.getWidth() - 10, 14);
		renderPanel.add(l_Time);

		l_Objects = new JLabel("Objects: " + Vars.activeObjects.size());
		l_Objects.setForeground(Color.WHITE);
		l_Objects.setBounds(10 - (renderPanel.getWidth() / 2), 27 - (renderPanel.getHeight() / 2), renderPanel.getWidth() - 10, 14);
		renderPanel.add(l_Objects);

		l_massstab = new JLabel("Ma\u00DFstab 1:1");
		l_massstab.setForeground(Color.WHITE);
		l_massstab.setBounds(10 - (renderPanel.getWidth() / 2), 43 - (renderPanel.getHeight() / 2), renderPanel.getWidth() - 10, 14);
		renderPanel.add(l_massstab);

		// MenuBar:
		setUpMenuBar();

		// JFrame:
		setBackground(Color.BLACK);
		setResizable(false);
		setTitle("x0 Gravity Simulator v" + Vars.version);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1080, 720);

		controlPanel.add(l_maßstabLabel);
		controlPanel.add(b_StartStop);
		controlPanel.add(l_newObject);
		controlPanel.add(t_mass);
		controlPanel.add(t_xVelocity);
		controlPanel.add(t_yVelocity);
		controlPanel.add(l_mass);
		controlPanel.add(l_xVelocity);
		controlPanel.add(l_yVelocity);
		controlPanel.add(b_ApplyObject);
		controlPanel.add(l_Timestep);
		controlPanel.add(b_nextStep);
		controlPanel.add(t_steps);
		controlPanel.add(l_steps);
		controlPanel.add(cb_drawPath);
		controlPanel.add(t_pathSize);
		controlPanel.add(t_timestep);
		controlPanel.add(l_pathsize);
		controlPanel.add(cb_showNames);
		controlPanel.add(t_yPos);
		controlPanel.add(t_xPos);
		controlPanel.add(l_yPos);
		controlPanel.add(l_xPos);
		controlPanel.add(btnPlaceObject);
		controlPanel.add(t_massstabInput);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(10, 481, 65, 14);
		controlPanel.add(lblName);
		
		t_nameField = new JTextField();
		t_nameField.setText("");
		t_nameField.setBounds(85, 478, 103, 20);
		t_nameField.addKeyListener(new KeyAdapter()
		{

			@Override
			public void keyPressed(KeyEvent arg0)
			{
				t_nameField.setForeground(Color.RED);
			}
		});
		
		controlPanel.add(t_nameField);
		t_nameField.setColumns(10);

	}

	private InputStream getDemoInputStream(String name)
	{
		return getClass().getResourceAsStream("/demofiles/" + name);
	}

	private void initMasterPanel()
	{
		masterPanel.add(controlPanel);
		masterPanel.add(renderPanel);
	}

	private void initRenderPanel()
	{
		renderPanel.addMouseWheelListener(new MouseWheelListener()
		{

			@Override
			public void mouseWheelMoved(MouseWheelEvent arg0)
			{

				// Clear Points:
				Vars.clearPoints = true;

				if (!Vars.isActive)
				{
					for (Obj o : Vars.activeObjects)
					{
						o.clearPoints();
					}
				}

				if (arg0.isShiftDown())
				{
					lastMouseWheelState += (arg0.getUnitsToScroll() * 1000000);
				} else
				{
					lastMouseWheelState += arg0.getUnitsToScroll();
				}

				if (lastMouseWheelState < 1)
				{
					lastMouseWheelState = 1;
				}

				double d = lastMouseWheelState;

				Vars.scaling_ZoomFactor = 1 / d;

			}
		});
		renderPanel.setBounds(218, 11, 846, 648);
		renderPanel.setIgnoreRepaint(true);
		renderPanel.setFocusable(false);
		renderPanel.addMouseListener(new MouseAdapter()
		{

			@Override
			public void mousePressed(MouseEvent arg0)
			{

				if (SwingUtilities.isLeftMouseButton(arg0))
				{
					if (arg0.isControlDown())
					{
						mouseClickPos.setX(arg0.getX());
						mouseClickPos.setY(arg0.getY());

					} else
						GUIEvents.addObject(arg0.getX(), arg0.getY());
				}

			}

			@Override
			public void mouseReleased(MouseEvent arg0)
			{

				if (arg0.isControlDown())
				{

					// Clear Points:
					Vars.clearPoints = true;

					if (!Vars.isActive)
					{
						for (Obj o : Vars.activeObjects)
						{
							o.clearPoints();
						}
					}

					mouseReleasePos.setX(arg0.getX());
					mouseReleasePos.setY(arg0.getY());

					double fac = (int) (1 / Vars.scaling_ZoomFactor);

					int deltaX = (int) ((mouseReleasePos.getX() - mouseClickPos.getX()));
					int deltaY = (int) ((mouseReleasePos.getY() - mouseClickPos.getY()));

					Vars.scaling_Delta.setX(Vars.scaling_Delta.getX() + deltaX * fac);
					Vars.scaling_Delta.setY(Vars.scaling_Delta.getY() + deltaY * fac);

				}
			}
		});

	}

	private void setUpMenuBar()
	{
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmLoadConfiguration = new JMenuItem("Load Configuration");
		mntmLoadConfiguration.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				GUIEvents.loadConf();
			}
		});
		mnFile.add(mntmLoadConfiguration);

		JMenuItem mntmSaveConfiguration = new JMenuItem("Save Configuration");
		mntmSaveConfiguration.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				FileManager.saveConfiguration();
			}
		});
		mnFile.add(mntmSaveConfiguration);

		JMenuItem mntmCloseSimulator = new JMenuItem("Close Simulator");
		mntmCloseSimulator.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});

		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				JOptionPane.showMessageDialog(null, "x0 Gravity Simulator version " + Vars.version + "\n developed by Daniel 'Xer0' Englisch \n http://xeroserver.org/");
			}
		});

		JMenuItem mntmReloadLastFile = new JMenuItem("Reload Configuration (Strg-R)");
		mntmReloadLastFile.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{

				if (Vars.lastFile == null)
				{
					GUIEvents.loadConf();
				} else
				{
					GUIEvents.reloadConfig();
				}

			}
		});
		mnFile.add(mntmReloadLastFile);

		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		mnFile.add(mntmAbout);

		JSeparator separator_2 = new JSeparator();
		mnFile.add(separator_2);
		mnFile.add(mntmCloseSimulator);

		JMenu mnActions = new JMenu("Actions");
		menuBar.add(mnActions);

		JMenuItem mntmResetSimulator = new JMenuItem("Reset Simulator");
		mntmResetSimulator.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Vars.isResetRequested = true;
			}
		});
		
		JMenuItem mntmEditGformula = new JMenuItem("Edit G-Formula");
		mntmEditGformula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				new FormulaEditor();
				
			}
		});
		mnActions.add(mntmEditGformula);
		mnActions.add(mntmResetSimulator);

		JMenuItem mntmTakeScreenshot = new JMenuItem("Take Screenshot (Strg-S)");
		mntmTakeScreenshot.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				GUIEvents.takeScreenShot(true);

			}
		});
		mnActions.add(mntmTakeScreenshot);

		JMenu mnDemo = new JMenu("Demo");
		menuBar.add(mnDemo);

		JMenuItem mntmOrbits = new JMenuItem("3 Orbits");
		mntmOrbits.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{

				FileManager.loadConfigFromInputStream(getDemoInputStream("3orbits.x0"));
			}
		});

		setContentPane(masterPanel);

		JMenuItem mntmElliptic = new JMenuItem("2 Elliptic");
		mntmElliptic.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{

				FileManager.loadConfigFromInputStream(getDemoInputStream("2elliptic.x0"));

			}
		});

		mnDemo.add(mntmOrbits);
		mnDemo.add(mntmElliptic);

		JMenuItem mntmEqualMass = new JMenuItem("2 Equal Mass");
		mntmEqualMass.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{

				FileManager.loadConfigFromInputStream(getDemoInputStream("2equalmass.x0"));

			}
		});
		mnDemo.add(mntmEqualMass);

		JMenuItem mntmDifferentMass = new JMenuItem("2 Different Mass");
		mntmDifferentMass.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{

				FileManager.loadConfigFromInputStream(getDemoInputStream("2differentmass.x0"));

			}
		});
		mnDemo.add(mntmDifferentMass);

	}
}
