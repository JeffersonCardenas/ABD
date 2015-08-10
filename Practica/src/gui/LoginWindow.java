package gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import tables.Usuario;
import bd.Controlador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow extends JFrame{

	private static final long serialVersionUID = 1L;	
	private JPanel login;
	private JButton aceptar;
	private JButton nuevoUsuario;
	private Controlador controlador;
	private UserWindow ventanaPrincipal;
	
	public LoginWindow(Controlador c){
		this.controlador = c;
		this.iniciaVentana();
	}
	
	private void iniciaVentana(){		
		this.setTitle("Ventana Usuario");
		this.setLayout(new BorderLayout());
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(300,250,400,200);
		
		aceptar = new JButton("Login");
		nuevoUsuario = new JButton("Nuevo Usuario");
		
		//Login
		login = new JPanel();
		login.setBorder(new TitledBorder("Login"));
		login.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel u1 = new JLabel("Usuario");
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		login.add(u1,c);
		
		JTextField user = new JTextField(10);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 1;
		c.gridy = 0;
		login.add(user,c);
		
		JLabel p1 = new JLabel("Contraseña");
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		login.add(p1,c);
		
		JPasswordField pass = new JPasswordField(10);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 1;
		c.gridy = 1;
		login.add(pass,c);
		
		this.altaUsuario(this,nuevoUsuario, user, pass);
		this.loginUsuario(this, aceptar, user, pass);
		
		//Panel de botones inferiores
		JPanel boton = new JPanel();
		boton.setBorder(new TitledBorder(""));
		boton.add(aceptar);
		boton.add(nuevoUsuario);
		
		
		this.add(login,BorderLayout.CENTER);
		this.add(boton,BorderLayout.SOUTH);
		
		this.setVisible(true);
		
	}
	
	private void altaUsuario(final JFrame v,final JButton nuevo,final JTextField user,final JPasswordField password){
		nuevo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String idUser = user.getText();
				char[] pass = password.getPassword();
				Usuario us = null;
				if (!idUser.isEmpty() && pass.length > 0){
					//ver si existe el usuario en la BD
					//si no existe crearlo con el nick y la contraseña introducidos
					us = controlador.existeUsuario(idUser);
					if (us==null) {
						String p = new String(pass);
						String fecha_nac = JOptionPane.showInputDialog("Escribe tu fecha de nacimiento: (aaaa-mm-dd)");
						int resul = controlador.altaUsuarioNuevo(idUser,p,fecha_nac);
						if (resul !=0 ){
							JOptionPane.showMessageDialog(nuevo, "Usuario insertado con éxito");
							v.setVisible(false);
							us = controlador.existeUsuario(idUser);
							ventanaPrincipal = new UserWindow(v,us,controlador);
							borraPass(pass);
							user.setText(null);
							password.setText(null);
						}
						else JOptionPane.showMessageDialog(nuevo, "No se ha podido insertar el usuario");
					}
					else JOptionPane.showMessageDialog(nuevo, "El usuario ya existe");
				}
			}

			private void borraPass(char[] p) {
				// TODO Auto-generated method stub
				for (int i=0;i<p.length;i++){
					p[i]=0;
				}
			}
		});
	}
	
	private void loginUsuario(final JFrame v,final JButton log,final JTextField user,final JPasswordField password){
		log.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String idUser = user.getText();
				char[] pass = password.getPassword();
				Usuario us = null;				
				if (!idUser.isEmpty() && pass.length > 0){
					//ver si existe el usuario en la BD
					//si existe abrir la ventana principal
					us = controlador.existeUsuario(idUser);
					if (us!=null) {
						String p = new String(pass);
						if (us.getPass().equals(p)){
							v.setVisible(false);
							ventanaPrincipal = new UserWindow(v,us,controlador);
							borraPass(pass);
							user.setText(null);
							password.setText(null);
						}
						else JOptionPane.showMessageDialog(log, "Contraseña Incorrecta");
					}
					else JOptionPane.showMessageDialog(log, "Nombre Incorrecto");
				}
				else JOptionPane.showMessageDialog(log, "Datos Erroneos");
			}

			private void borraPass(char[] p) {
				// TODO Auto-generated method stub
				for (int i=0;i<p.length;i++){
					p[i]=0;
				}
			}

		});
	}

}
