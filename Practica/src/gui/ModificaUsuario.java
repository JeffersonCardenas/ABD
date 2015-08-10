package gui;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import bd.Controlador;
import tables.Usuario;

public class ModificaUsuario extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contenedor;
	private Usuario usuario;
	private Controlador control;
	private JButton boton;
	
	public ModificaUsuario(Usuario us,Controlador c,JButton b){
		this.usuario = us;
		this.control = c;
		this.boton = b;
		initVentanaModificaUsuario();
	}

	private void initVentanaModificaUsuario() {
		// TODO Auto-generated method stub
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 250);
		contenedor = new JPanel();
		contenedor.setBorder(new TitledBorder("Actualización de datos"));
		setContentPane(contenedor);
		contenedor.setLayout(null);
		
		//Contraseña
		JLabel labelPass = new JLabel("Nueva Contraseña:");
		labelPass.setBounds(30, 35, 140, 20);
		contenedor.add(labelPass);
		
		JTextField nuevoPass = new JTextField();
		nuevoPass.setBounds(200, 30, 150, 20);
		contenedor.add(nuevoPass);
		
		//Fecha de nacimiento
		JLabel labelNaciemiento = new JLabel("Fecha de Nacimiento: ");
		labelNaciemiento.setBounds(30, 60, 150, 20);
		contenedor.add(labelNaciemiento);
		
		JTextField nuevaFecha = new JTextField();
		nuevaFecha.setBounds(200, 58, 80, 20);
		contenedor.add(nuevaFecha);
		
		JLabel infoFecha = new JLabel("YYYY-[M]M-[D]D");
		infoFecha.setBounds(300, 58, 100, 20);
		contenedor.add(infoFecha);
		
		//Botones
		JButton bCambiaFoto = new JButton("Cambiar Imagen");
		bCambiaFoto.setBounds(25, 140, 150, 23);
		contenedor.add(bCambiaFoto);
		
		cambiaFoto(bCambiaFoto);	
				
		JButton bAceptar = new JButton("Aceptar");
		bAceptar.setBounds(185, 140, 87, 23);
		contenedor.add(bAceptar);
		
		modificaDatos(nuevoPass, nuevaFecha, bAceptar);
				
		JButton bCancelar = new JButton("Cancelar");		
		bCancelar.setBounds(285, 140, 89, 23);
		contenedor.add(bCancelar);
		
		cancela(bCancelar,this);
				
		this.setVisible(true);
	}

	private void cambiaFoto(final JButton bFoto) {
		bFoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int resul = 0;
				JFileChooser seleccion=new JFileChooser();
				if(seleccion.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
					File file = seleccion.getSelectedFile();
					FileInputStream is;
					try {
						byte[] fileContent = new byte[(int)file.length()];
						is = new FileInputStream(file);
						is.read(fileContent);
						resul = control.actualizaFoto(usuario,fileContent);
					} catch (IOException e) {
						e.printStackTrace();
					}
					if (resul>0) {
						boton.setIcon(new ImageIcon(usuario.getFoto()));
						JOptionPane.showMessageDialog(bFoto, "Se ha actualizado con éxito la imagen");
					}
					else JOptionPane.showMessageDialog(bFoto,"No se pudo añadir la foto");
				}
				else JOptionPane.showMessageDialog(bFoto,"No se pudo añadir la foto");
			}
			});
	}

	private void modificaDatos(final JTextField fieldPass,final JTextField fieldFecha,
			final JButton bAcept) {
		bAcept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			     int resul = control.modificaDatosUsuario(usuario,fieldPass.getText(),fieldFecha.getText());
			     if (resul>0) JOptionPane.showMessageDialog(bAcept, "Se han actualizado con éxito los datos");
			     else JOptionPane.showMessageDialog(bAcept,"No se pudo actualizar los datos");
			}
		});
	}

	private void cancela(JButton b1,final JFrame v) {
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			   v.dispose();
			}
		});
	}

}
