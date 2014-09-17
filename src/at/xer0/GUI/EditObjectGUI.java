package at.xer0.GUI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import at.xer0.Support.Obj;
import at.xer0.Support.Vars;


public class EditObjectGUI extends JFrame
{


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tf_mass;
	private Obj obj;

	/**
	 * Create the frame.
	 */
	public EditObjectGUI(Obj o)
	{
		obj = o;
		
		setTitle("Edit Object");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 284, 104);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnDeleteObject = new JButton("Delete Object");
		btnDeleteObject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Vars.objectToDelete.add(obj);
				dispose();
				
			}
		});
		btnDeleteObject.setBounds(10, 36, 142, 23);
		contentPane.add(btnDeleteObject);
		
		JButton btnApply = new JButton("Apply");
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				double mass = obj.getMass();
				
				try
				{
					mass = Double.parseDouble(tf_mass.getText());


				} catch (Exception de)
				{
					JOptionPane.showMessageDialog(null, "Keine gültigen Werte!");
				}
				
				obj.setMass(mass);
				dispose();
				
			}
		});
		btnApply.setBounds(162, 36, 100, 23);
		contentPane.add(btnApply);
		
		JLabel lblMass = new JLabel("Mass:");
		lblMass.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMass.setBounds(10, 11, 32, 14);
		contentPane.add(lblMass);
		
		tf_mass = new JTextField();
		tf_mass.setBounds(52, 8, 210, 20);
		tf_mass.setText(obj.getMass() + "");
		contentPane.add(tf_mass);
		tf_mass.setColumns(10);
	}
}
