package gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import bd.Controlador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaNuevaSerie extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controlador control;
	
	public VentanaNuevaSerie(Controlador c){
		this.control = c;
		initVentana();
	}
	
	private void initVentana(){
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 370);
		JPanel contenedor = new JPanel();
		contenedor.setBorder(new TitledBorder("Actualización de datos"));
		setContentPane(contenedor);
		contenedor.setLayout(null);
		
		//Nombre de la serie
		JLabel labelNombre = new JLabel("Nombre:");
		labelNombre.setBounds(30, 30, 80, 20);
		contenedor.add(labelNombre);
		
		JTextField nuevoNombre = new JTextField();
		nuevoNombre.setBounds(120, 30, 150, 20);
		contenedor.add(nuevoNombre);
		
		//Titular de la serie		
		JTextArea nuevoTitular = new JTextArea();
		JScrollPane scrollTitular = new JScrollPane(nuevoTitular);
		scrollTitular.setBorder(new TitledBorder("Titular"));
		scrollTitular.setBounds(30, 60, 420, 70);
		contenedor.add(scrollTitular);
		
		//Sinopsis de la serie		
		JTextArea nuevaSinopsis = new JTextArea();
		JScrollPane scrollSinopsis = new JScrollPane(nuevaSinopsis);
		scrollSinopsis.setBorder(new TitledBorder("Sinopsis"));
		scrollSinopsis.setBounds(30, 130, 420, 70);
		contenedor.add(scrollSinopsis);
		
		//Fecha de estreno de la serie
		JLabel labelFechaEst = new JLabel("Fecha Estreno: ");
		labelFechaEst.setBounds(30, 210, 150, 20);
		contenedor.add(labelFechaEst);
		
		JTextField nuevaFechaEst = new JTextField(20);
		nuevaFechaEst.setBounds(180, 210, 80, 20);
		contenedor.add(nuevaFechaEst);
		
		JLabel infoFecha1 = new JLabel("YYYY-[M]M-[D]D");
		infoFecha1.setBounds(270, 210, 100, 20);
		contenedor.add(infoFecha1);
		
		//Fecha de finalizacion de la serie
		JLabel labelFechaFin = new JLabel("Fecha Fin: ");
		labelFechaFin.setBounds(30, 240, 150, 20);
		contenedor.add(labelFechaFin);
		
		JTextField nuevaFechaFin = new JTextField(20);
		nuevaFechaFin.setBounds(180, 240, 80, 20);
		contenedor.add(nuevaFechaFin);
		
		JLabel infoFecha2 = new JLabel("YYYY-[M]M-[D]D");
		infoFecha2.setBounds(270, 240, 100, 20);
		contenedor.add(infoFecha2);
				
		JButton bAceptar = new JButton("Aceptar");
		bAceptar.setBounds(100, 280, 87, 23);
		contenedor.add(bAceptar);
		
		insertaSerie(nuevoNombre, nuevoTitular, nuevaSinopsis, nuevaFechaEst, nuevaFechaFin, bAceptar , this);
				
		JButton bCancelar = new JButton("Cancelar");		
		bCancelar.setBounds(240, 280, 89, 23);
		contenedor.add(bCancelar);
		
		cancela(bCancelar,this);
				
		this.setVisible(true);
		
	}

	private void insertaSerie(final JTextField nuevoNombre,final JTextArea nuevoTitular,final JTextArea nuevaSinopsis,
			final JTextField fEst,final JTextField fFin,final JButton bAceptar,final JFrame v) {
		// TODO Auto-generated method stub
		bAceptar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub 
				int resul = 0;
				String name = nuevoNombre.getText();
				String tit = nuevoTitular.getText();
				String sin = nuevaSinopsis.getText();
				String est = fEst.getText();
				String fin = fFin.getText();
				if (!name.isEmpty() && !tit.isEmpty() && !sin.isEmpty() && !est.isEmpty()){
					resul = control.insertaSerie(name,tit,sin,est,fin);
				}
				else JOptionPane.showMessageDialog(bAceptar, "Inserta los Datos");
				if (resul > 0) {
					JOptionPane.showMessageDialog(bAceptar, "Se ha insertado la serie con exito");
					v.dispose();
				}
				else JOptionPane.showMessageDialog(bAceptar, "Ha habido un error en tu petición");
			}
			
		});
	}

	private void cancela(JButton bCancelar,final JFrame v) {
		// TODO Auto-generated method stub
		bCancelar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				v.dispose();
			}
			
		});
	}

}
