package org.xeroserver.GravitySimulator.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import org.xeroserver.GravitySimulator.Objects.Obj;
import org.xeroserver.GravitySimulator.Objects.Vec2D;
import org.xeroserver.GravitySimulator.Support.FileManager;
import org.xeroserver.GravitySimulator.Support.GUIEvents;
import org.xeroserver.GravitySimulator.Support.Vars;
import org.xeroserver.x0library.gui.InputField;

public class MainFrame extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;

	// Panels
	private JPanel masterPanel;
	private JPanel controlPanel;
	public JPanel renderPanel;

	// t - Text Field
	public InputField t_steps;
	public InputField t_pathSize;
	public InputField t_timestep;
	public InputField t_massstabInput;
	private InputField t_xPos;
	private InputField t_yPos;
	private InputField t_mass;
	private InputField t_xVelocity;
	private InputField t_yVelocity;
	private InputField t_nameField;

	// rl - Render Label
	public JLabel rl_massstab;
	public JLabel rl_Time;
	public JLabel rl_Objects;
	public JLabel rl_Timestep;

	// l - Label
	private JLabel l_timestep;
	private JLabel l_pathsize;
	private JLabel l_steps;

	// b - Button
	public JButton b_StartStop;
	public JButton b_nextStep;

	// cb - Check Box
	public JCheckBox cb_drawPath;
	public JCheckBox cb_showNames;

	// Mouse Click related
	public double lastMouseWheelState = 1;
	public Vec2D mouseClickPos = new Vec2D(0, 0);
	public Vec2D mouseReleasePos = new Vec2D(0, 0);

	public MainFrame() {

		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/img/gravsim64.png")));

		GUIEvents.initGlobalKeyEvents();

		initPanels();

		populateRenderPanel();
		populateMasterPanel();
		populateControlPanel();

	}

	private void initPanels() {
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
	}

	@Override
	public void run() {
		setVisible(true);
	}

	@SuppressWarnings("serial")
	private void populateControlPanel() {

		b_StartStop = new JButton("Start Simulation");
		b_StartStop.setToolTipText("Start");
		b_StartStop.setBounds(10, 11, 178, 71);
		b_StartStop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				GUIEvents.startStop();
			}
		});

		JLabel l_newObject = new JLabel("Object Presets:");
		l_newObject.setFont(new Font("Tahoma", Font.BOLD, 11));
		l_newObject.setBounds(10, 338, 178, 14);

		t_mass = new InputField(true, InputField.DOUBLE) {
			@Override
			public void update() {
				Vars.preset_Mass = this.getDoubleValue();
				Vars.logger.info("Updated Mass to " + Vars.preset_Mass);
			}
		};

		t_mass.setText(Vars.preset_Mass + "");

		t_mass.setBounds(85, 363, 103, 20);
		t_mass.setColumns(10);

		t_xVelocity = new InputField(true, InputField.DOUBLE) {
			@Override
			public void update() {
				Vars.preset_Velocity.setX(this.getDoubleValue());
				Vars.logger.info("Updated x-Vel to " + Vars.preset_Velocity.getX());
			}
		};

		t_xVelocity.setText(Vars.preset_Velocity.getX() + "");

		t_xVelocity.setBounds(85, 394, 103, 20);

		t_yVelocity = new InputField(true, InputField.DOUBLE) {
			@Override
			public void update() {
				Vars.preset_Velocity.setY(this.getDoubleValue());
				Vars.logger.info("Updated y-Vel to " + Vars.preset_Velocity.getY());
			}
		};

		t_yVelocity.setText(Vars.preset_Velocity.getY() + "");

		t_yVelocity.setBounds(85, 425, 103, 20);
		t_yVelocity.setColumns(10);

		JLabel l_mass = new JLabel("Mass:");
		l_mass.setBounds(10, 366, 65, 14);

		JLabel l_xVelocity = new JLabel("x Velocity:");
		l_xVelocity.setBounds(10, 397, 65, 14);

		JLabel l_yVelocity = new JLabel("y Velocity:");
		l_yVelocity.setBounds(10, 428, 65, 14);

		l_timestep = new JLabel("Timestep:");
		l_timestep.setFont(new Font("Tahoma", Font.PLAIN, 12));
		l_timestep.setBounds(10, 109, 65, 14);

		JLabel l_maﬂstabLabel = new JLabel("Scale = 1:");
		l_maﬂstabLabel.setHorizontalAlignment(SwingConstants.LEFT);
		l_maﬂstabLabel.setBounds(10, 300, 91, 14);

		b_nextStep = new JButton(">");
		b_nextStep.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Vars.nextStep = true;
			}
		});
		b_nextStep.setFont(new Font("Tahoma", Font.BOLD, 14));
		b_nextStep.setBounds(10, 208, 178, 23);

		l_steps = new JLabel("Steps:");
		l_steps.setFont(new Font("Tahoma", Font.PLAIN, 12));
		l_steps.setBounds(10, 179, 49, 14);

		t_steps = new InputField(true, InputField.INT, InputField.NOT_ZERO, InputField.POSITIVE) {
			@Override
			public void update() {
				Vars.steps = this.getIntegerValue();
				Vars.logger.info("Updated Steps to " + Vars.steps);
			}
		};

		t_steps.setText(Vars.steps + "");

		t_steps.setBounds(85, 177, 103, 20);
		t_steps.setColumns(10);

		cb_drawPath = new JCheckBox("Draw Path");
		cb_drawPath.setSelected(true);
		cb_drawPath.setBackground(Color.WHITE);
		cb_drawPath.setBounds(10, 242, 182, 23);

		l_pathsize = new JLabel("Pathsize:");
		l_pathsize.setFont(new Font("Tahoma", Font.PLAIN, 12));
		l_pathsize.setBounds(10, 134, 65, 16);

		t_pathSize = new InputField(true, InputField.INT, InputField.POSITIVE) {
			@Override
			public void update() {
				Vars.pathSize = this.getIntegerValue();
				Vars.logger.info("Updated Pathsize to " + Vars.pathSize);
			}
		};

		t_pathSize.setText(Vars.pathSize + "");
		t_pathSize.setBounds(85, 133, 103, 20);
		t_pathSize.setColumns(10);

		t_timestep = new InputField(true, InputField.DOUBLE, InputField.NOT_ZERO) {
			@Override
			public void update() {
				Vars.timeStep = this.getDoubleValue();
				Vars.logger.info("Updated TimeStep to " + Vars.timeStep);
			}
		};
		t_timestep.setText(Vars.timeStep + "");

		t_timestep.setBounds(85, 106, 103, 22);
		t_timestep.setColumns(10);

		cb_showNames = new JCheckBox("Show names");
		cb_showNames.setSelected(true);
		cb_showNames.setBackground(Color.WHITE);
		cb_showNames.setBounds(10, 268, 178, 25);

		t_yPos = new InputField(true, InputField.DOUBLE) {
			@Override
			public void update() {
				Vars.preset_Position.setY(this.getDoubleValue());
				Vars.logger.info("Updated y-Pos to " + Vars.preset_Position.getY());
			}
		};

		t_yPos.setText(Vars.preset_Position.getY() + "");

		t_yPos.setBounds(85, 530, 103, 20);
		t_yPos.setColumns(10);

		t_xPos = new InputField(true, InputField.DOUBLE) {
			@Override
			public void update() {
				Vars.preset_Position.setX(this.getDoubleValue());
				Vars.logger.info("Updated x-Pos to " + Vars.preset_Position.getX());
			}
		};

		t_xPos.setText(Vars.preset_Position.getX() + "");

		t_xPos.setBounds(85, 499, 103, 20);
		t_xPos.setColumns(10);

		final JLabel l_xPos = new JLabel("x Position:");
		l_xPos.setBounds(10, 502, 65, 14);

		final JLabel l_yPos = new JLabel("y Position:");
		l_yPos.setBounds(10, 533, 65, 14);

		JButton btnPlaceObject = new JButton("Place Object");
		btnPlaceObject.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				GUIEvents.addObject(Vars.preset_Position.getX(), Vars.preset_Position.getY());

			}
		});
		btnPlaceObject.setBounds(10, 561, 178, 30);

		t_massstabInput = new InputField(true, InputField.DOUBLE, InputField.NOT_ZERO, InputField.POSITIVE) {
			@Override
			public void update() {

				// Reset Color
				Vars.scaling_ZoomFactor = 1 / this.getDoubleValue();
				Vars.clearPoints = true;

				Vars.logger.info("Updated Scaling  to 1:" + this.getDoubleValue());

			}
		};

		t_massstabInput.setText(Vars.timeStep + "");

		t_massstabInput.setBounds(85, 297, 103, 20);
		t_massstabInput.setColumns(10);

		rl_Time = new JLabel("Time: " + Vars.time);
		rl_Time.setForeground(Color.WHITE);
		rl_Time.setBounds(10 - (renderPanel.getWidth() / 2), 11 - (renderPanel.getHeight() / 2),
				renderPanel.getWidth() - 10, 14);
		renderPanel.add(rl_Time);

		rl_Timestep = new JLabel("Timestep: " + Vars.time);
		rl_Timestep.setForeground(Color.WHITE);
		rl_Timestep.setBounds(10 - (renderPanel.getWidth() / 2), 27 - (renderPanel.getHeight() / 2),
				renderPanel.getWidth() - 10, 14);
		renderPanel.add(rl_Timestep);

		rl_Objects = new JLabel("Objects: " + Vars.activeObjects.size());
		rl_Objects.setForeground(Color.WHITE);
		rl_Objects.setBounds(10 - (renderPanel.getWidth() / 2), 43 - (renderPanel.getHeight() / 2),
				renderPanel.getWidth() - 10, 14);
		renderPanel.add(rl_Objects);

		rl_massstab = new JLabel("Ma\u00DFstab 1:1");
		rl_massstab.setForeground(Color.WHITE);
		rl_massstab.setBounds(10 - (renderPanel.getWidth() / 2), 59 - (renderPanel.getHeight() / 2),
				renderPanel.getWidth() - 10, 14);
		renderPanel.add(rl_massstab);

		// MenuBar:
		setUpMenuBar();

		// JFrame:
		setBackground(Color.BLACK);
		setResizable(false);
		setTitle("x0 Gravity Simulator v" + Vars.version);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1080, 720);

		controlPanel.add(l_maﬂstabLabel);
		controlPanel.add(b_StartStop);
		controlPanel.add(l_newObject);
		controlPanel.add(t_mass);
		controlPanel.add(t_xVelocity);
		controlPanel.add(t_yVelocity);
		controlPanel.add(l_mass);
		controlPanel.add(l_xVelocity);
		controlPanel.add(l_yVelocity);
		controlPanel.add(l_timestep);
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
		lblName.setBounds(10, 461, 65, 14);
		controlPanel.add(lblName);

		t_nameField = new InputField(true) {
			@Override
			public void update() {
				Vars.preset_Name = this.getStringValue();
				Vars.logger.info("Updated name to " + Vars.preset_Name);
			}
		};

		t_nameField.setText(Vars.preset_Name + "");

		t_nameField.setBounds(85, 458, 103, 20);

		controlPanel.add(t_nameField);
		t_nameField.setColumns(10);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 486, 178, 2);
		controlPanel.add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 325, 178, 2);
		controlPanel.add(separator_1);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 164, 178, 2);
		controlPanel.add(separator_2);

		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(10, 93, 178, 2);
		controlPanel.add(separator_3);

		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(10, 242, 178, 2);
		controlPanel.add(separator_4);

	}

	private InputStream getDemoInputStream(String name) {
		return getClass().getResourceAsStream("/demofiles/" + name);
	}

	private void populateMasterPanel() {
		masterPanel.add(controlPanel);
		masterPanel.add(renderPanel);
	}

	private void populateRenderPanel() {
		renderPanel.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent arg0) {

				// Clear Points:
				Vars.clearPoints = true;

				if (!Vars.isActive) {
					for (Obj o : Vars.activeObjects) {
						o.clearPoints();
					}
				}

				if (arg0.isShiftDown()) {
					lastMouseWheelState += (arg0.getUnitsToScroll() * 1000000);
				} else {
					lastMouseWheelState += arg0.getUnitsToScroll();
				}

				if (lastMouseWheelState < 1) {
					lastMouseWheelState = 1;
				}

				double d = lastMouseWheelState;

				Vars.scaling_ZoomFactor = 1 / d;
				t_massstabInput.setText(String.format("%.0f", (1 / Vars.scaling_ZoomFactor)));

			}
		});

		renderPanel.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent arg0) {

				if (SwingUtilities.isLeftMouseButton(arg0)) {
					if (arg0.isControlDown()) {
						mouseClickPos.setX(arg0.getX());
						mouseClickPos.setY(arg0.getY());

					} else
						GUIEvents.addObject(arg0.getX(), arg0.getY());
				}

			}

			@Override
			public void mouseReleased(MouseEvent arg0) {

				if (arg0.isControlDown()) {

					// Clear Points:
					Vars.clearPoints = true;

					if (!Vars.isActive) {
						for (Obj o : Vars.activeObjects) {
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

	private void setUpMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmLoadConfiguration = new JMenuItem("Load Configuration");
		mntmLoadConfiguration.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				GUIEvents.loadConf();
			}
		});
		mnFile.add(mntmLoadConfiguration);

		JMenuItem mntmSaveConfiguration = new JMenuItem("Save Configuration");
		mntmSaveConfiguration.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FileManager.saveConfiguration();
			}
		});
		mnFile.add(mntmSaveConfiguration);

		JMenuItem mntmCloseSimulator = new JMenuItem("Close Simulator");
		mntmCloseSimulator.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "x0 Gravity Simulator version " + Vars.version
						+ "\n developed by Daniel 'Xer0' Englisch \n http://xeroserver.org/");
			}
		});

		JMenuItem mntmReloadLastFile = new JMenuItem("Reload Configuration (Strg-R)");
		mntmReloadLastFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (Vars.lastFile == null) {
					GUIEvents.loadConf();
				} else {
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
		mntmResetSimulator.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
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
		mntmTakeScreenshot.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GUIEvents.takeScreenShot(true);

			}
		});
		mnActions.add(mntmTakeScreenshot);

		JMenu mnDemo = new JMenu("Demo");
		menuBar.add(mnDemo);

		JMenuItem mntmOrbits = new JMenuItem("3 Orbits");
		mntmOrbits.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				FileManager.loadConfigFromInputStream(getDemoInputStream("3orbits.x0"));
			}
		});

		setContentPane(masterPanel);

		JMenuItem mntmElliptic = new JMenuItem("2 Elliptic");
		mntmElliptic.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				FileManager.loadConfigFromInputStream(getDemoInputStream("2elliptic.x0"));

			}
		});

		mnDemo.add(mntmOrbits);
		mnDemo.add(mntmElliptic);

		JMenuItem mntmEqualMass = new JMenuItem("2 Equal Mass");
		mntmEqualMass.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				FileManager.loadConfigFromInputStream(getDemoInputStream("2equalmass.x0"));

			}
		});
		mnDemo.add(mntmEqualMass);

		JMenuItem mntmDifferentMass = new JMenuItem("2 Different Mass");
		mntmDifferentMass.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				FileManager.loadConfigFromInputStream(getDemoInputStream("2differentmass.x0"));

			}
		});
		mnDemo.add(mntmDifferentMass);

	}
}
