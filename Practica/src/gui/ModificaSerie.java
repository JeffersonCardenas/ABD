package gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import bd.Controlador;
import tables.Episodio;
import tables.Serie;

public class ModificaSerie extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Serie serie;
	private Controlador control;
	private MyTableModel modelEpisodios;
	
	public ModificaSerie(Serie s,Controlador c){
		this.serie = s;
		this.control = c;
		initVentanaModificaSerie();
	}

	private void initVentanaModificaSerie() {
		// TODO Auto-generated method stub
		this.setTitle("Modificar "+serie.getNombre());
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(200,150,700,600);
		
		JPanel contenedor = new JPanel();
		contenedor.setBorder(new TitledBorder("Información de la Serie"));
		setContentPane(contenedor);
		contenedor.setLayout(null);
		
		//Titular de la serie
		JTextArea textTitular = new JTextArea(serie.getTitular());
		JScrollPane scrollTitular = new JScrollPane(textTitular);
		scrollTitular.setBorder(new TitledBorder("Titular"));
		scrollTitular.setBounds(50, 30, 560, 60);
		textTitular.setEditable(true);
		contenedor.add(scrollTitular);
		
		//Sinopsis de la serie
		JTextArea info = new JTextArea(serie.getSinopsis());
		JScrollPane scrollpane = new JScrollPane(info);
		scrollpane.setBorder(new TitledBorder("Sinopsis"));
		scrollpane.setBounds(50,100,560,100);
		info.setEditable(true);
		contenedor.add(scrollpane);
		
		//Fecha de estreno
		JLabel labelFechaEst = new JLabel("Fecha de Estreno:");
		labelFechaEst.setBounds(15, 215, 120, 20);
		contenedor.add(labelFechaEst);
		
		JTextField textFechaEst = new JTextField(serie.getFechaEstreno().toString());
		textFechaEst.setBounds(135, 215, 80, 25);
		contenedor.add(textFechaEst);
		
		JLabel infoFecha1 = new JLabel("YYYY-[M]M-[D]D");
		infoFecha1.setBounds(225, 215, 90, 20);
		contenedor.add(infoFecha1);
		
		//Fecha de finalizacion
		JLabel labelFechaFin = new JLabel("Fecha de finalización:");
		labelFechaFin.setBounds(350, 215, 140, 20);
		contenedor.add(labelFechaFin);
		
		JTextField textFechaFin = new JTextField();
		textFechaFin.setBounds(480, 215, 80, 25);
		contenedor.add(textFechaFin);
		
		JLabel infoFecha2 = new JLabel("YYYY-[M]M-[D]D");
		infoFecha2.setBounds(570, 215, 95, 20);
		contenedor.add(infoFecha2);
		
		//tabla de episodios
		modelEpisodios = new MyTableModel();
        
        JTable tablaEpisodios = new JTable(modelEpisodios);
        tablaEpisodios.setFillsViewportHeight(true);
        tablaEpisodios.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        
        List<Episodio> aux = control.dameEpisodios(serie.getIdSerie());
		if (!aux.isEmpty()){
			Iterator<Episodio> it = aux.iterator();
			while (it.hasNext()){
				modelEpisodios.anyadeEpisodio(it.next());
			}
		}
        
        JScrollPane scrollTablaEpisodios = new JScrollPane(tablaEpisodios);
        scrollTablaEpisodios.setBorder(new TitledBorder("Episodios Serie"));
        scrollTablaEpisodios.setBounds(20, 260, 640, 200);
        contenedor.add(scrollTablaEpisodios);
		
		//botones
		JButton editaSerie = new JButton("Editar Serie");
		editaSerie.setBounds(80,500,100,40);
		
		JButton btnAddEpi = new JButton("Añade Episodio");
		btnAddEpi.setBounds(200, 500, 125, 40);
		
		JButton btnEditaEpi = new JButton("Edita Episodio");
		btnEditaEpi.setBounds(340, 500, 120, 40);
		
		JButton btnBorraEpi = new JButton("Borra Episodio");
		btnBorraEpi.setBounds(480, 500, 120, 40);
		
		editaSerie(editaSerie,this,textTitular,info,textFechaEst,textFechaFin);
		vistaNuevoEpisodio(btnAddEpi);
		borraEpisodio(btnBorraEpi,tablaEpisodios);
		vistaModEpi(btnEditaEpi,tablaEpisodios);
		
		contenedor.add(editaSerie);
		contenedor.add(btnAddEpi);
		contenedor.add(btnEditaEpi);
		contenedor.add(btnBorraEpi);
		
		this.setVisible(true);
	}
	
	private void editaSerie(final JButton btnedita,final JFrame v,final JTextArea titularSerie,
			final JTextArea sinopsisSerie,final JTextField fechaIni,final JTextField fechaFin) {
		// TODO Auto-generated method stub
		btnedita.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int resul = 0;
				resul = control.actualizaSerie(serie,titularSerie.getText(),
						sinopsisSerie.getText(),fechaIni.getText(),fechaFin.getText());
				if (resul > 0) {
					JOptionPane.showMessageDialog(btnedita, "Serie actualizada con éxito");
					v.dispose();
				}
				else JOptionPane.showMessageDialog(btnedita, "Ha habido un error en tu petición");
			}
			
		});
	}

	private void vistaModEpi(JButton btnEditaEpi,final JTable tablaEpisodios) {
		// TODO Auto-generated method stub
		btnEditaEpi.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				final int i = tablaEpisodios.getSelectedRow();
				if (i>-1){
					JFrame modEpi = new JFrame("Modificar Episodio");				
					modEpi.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
					modEpi.setBounds(200,150,700,350);
					
					JPanel contModEpisodio = new JPanel();
					contModEpisodio.setBorder(new TitledBorder("Información del Episodio"));
					modEpi.setContentPane(contModEpisodio);
					contModEpisodio.setLayout(null);
					
					//Nombre Episodio
					final JTextArea textTitle = new JTextArea(modelEpisodios.getEpisodio(i).getNombreCap());
					JScrollPane scrollTitular = new JScrollPane(textTitle);
					scrollTitular.setBorder(new TitledBorder("Nombre del Episodio"));
					scrollTitular.setBounds(50, 30, 560, 60);
					textTitle.setEditable(true);
					contModEpisodio.add(scrollTitular);
					
					//Sinopsis del Capitulo
					final JTextArea info = new JTextArea(modelEpisodios.getEpisodio(i).getSinopsis());
					JScrollPane scrollpane = new JScrollPane(info);
					scrollpane.setBorder(new TitledBorder("Sinopsis del Episodio"));
					scrollpane.setBounds(50,100,560,100);
					info.setEditable(true);
					contModEpisodio.add(scrollpane);
					
					//Fecha de estreno
					JLabel labelFechaEst = new JLabel("Fecha de Estreno:");
					labelFechaEst.setBounds(50, 215, 120, 20);
					contModEpisodio.add(labelFechaEst);
					
					final JTextField textFechaEst = new JTextField(modelEpisodios.getEpisodio(i).getFechaEst().toString());
					textFechaEst.setBounds(180, 215, 80, 25);
					contModEpisodio.add(textFechaEst);
					
					JLabel infoFecha1 = new JLabel("YYYY-[M]M-[D]D");
					infoFecha1.setBounds(280, 215, 90, 20);
					contModEpisodio.add(infoFecha1);
					
					JButton btnModEpi = new JButton("Aceptar");
					btnModEpi.setBounds(285, 250, 100, 40);
					
					//funcionalidad del boton
					btnModEpi.addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub							
							int resul = 0;
							resul = control.actualizaEpisodio(modelEpisodios.getEpisodio(i),textTitle.getText(),info.getText(),textFechaEst.getText());
							if (resul > 0){
								List<Episodio> aux = control.dameEpisodios(serie.getIdSerie());
								if (!aux.isEmpty()){
									modelEpisodios.removeTable();
									Iterator<Episodio> it = aux.iterator();
									while (it.hasNext()){
										modelEpisodios.anyadeEpisodio(it.next());
									}
								}
								JOptionPane.showMessageDialog(null, "Se ha modificado con éxito el episodio");
							}
							else JOptionPane.showMessageDialog(null, "Ha habido un error en tu petición");
						}
						
					});
					
					contModEpisodio.add(btnModEpi);
					
					modEpi.setVisible(true);					
				}
				else JOptionPane.showMessageDialog(null, "No Has Seleccionado ningún episodio");
				
			}
			
		});
	}

	private void vistaNuevoEpisodio(JButton btnAddEpi) {
		// TODO Auto-generated method stub
		btnAddEpi.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame nuevoEpi = new JFrame("Nuevo Episodio");
				nuevoEpi.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				nuevoEpi.setBounds(100, 100, 700, 450);
				
				JPanel contEpiNuevo = new JPanel();
				contEpiNuevo.setBorder(new TitledBorder("Información de la Nueva Serie"));
				nuevoEpi.setContentPane(contEpiNuevo);
				contEpiNuevo.setLayout(null);
				
				//Nombre Episodio
				final JTextArea textTitle = new JTextArea();
				JScrollPane scrollTitular = new JScrollPane(textTitle);
				scrollTitular.setBorder(new TitledBorder("Título"));
				scrollTitular.setBounds(50, 30, 560, 60);
				textTitle.setEditable(true);
				contEpiNuevo.add(scrollTitular);
				
				//Sinopsis del Capitulo
				final JTextArea info = new JTextArea();
				JScrollPane scrollpane = new JScrollPane(info);
				scrollpane.setBorder(new TitledBorder("Sinopsis"));
				scrollpane.setBounds(50,100,560,100);
				info.setEditable(true);
				contEpiNuevo.add(scrollpane);
				
				//Fecha de estreno
				JLabel labelFechaEst = new JLabel("Fecha de Estreno:");
				labelFechaEst.setBounds(50, 215, 120, 20);
				contEpiNuevo.add(labelFechaEst);
				
				final JTextField textFechaEst = new JTextField();
				textFechaEst.setBounds(180, 215, 80, 25);
				contEpiNuevo.add(textFechaEst);
				
				JLabel infoFecha1 = new JLabel("YYYY-[M]M-[D]D");
				infoFecha1.setBounds(280, 215, 90, 20);
				contEpiNuevo.add(infoFecha1);
				
				//Numero de Episodio
				JLabel labelNumEpi = new JLabel("Número de Episodio:");
				labelNumEpi.setBounds(50, 255, 120, 20);
				contEpiNuevo.add(labelNumEpi);
				
				final JTextField textNumEpi = new JTextField();
				textNumEpi.setBounds(180, 255, 40, 20);
				contEpiNuevo.add(textNumEpi);
				
				//Numero de Temporada
				JLabel labelNumTemp = new JLabel("Número de Temporada:");
				labelNumTemp.setBounds(300, 255, 140, 20);
				contEpiNuevo.add(labelNumTemp);
				
				final JTextField textNumTemp = new JTextField();
				textNumTemp.setBounds(450, 255, 40, 20);
				contEpiNuevo.add(textNumTemp);
				
				//boton
				JButton btnNuevoEpi = new JButton("Aceptar");
				btnNuevoEpi.setBounds(280, 310, 90, 40);
				
				//funcionalida del boton
				btnNuevoEpi.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						int resul = 0;
						resul = control.insertaEpisodio(serie.getIdSerie(),textNumEpi.getText(),textNumTemp.getText(),textTitle.getText(),info.getText(),textFechaEst.getText());
						if (resul > 0){
							List<Episodio> aux = control.dameEpisodios(serie.getIdSerie());
							if (!aux.isEmpty()){
								modelEpisodios.removeTable();
								Iterator<Episodio> it = aux.iterator();
								while (it.hasNext()){
									modelEpisodios.anyadeEpisodio(it.next());
								}
							}
							JOptionPane.showMessageDialog(null, "Se ha insertado con éxito el episodio");
						}
						else JOptionPane.showMessageDialog(null, "Ya Existe el Episodio");
					}
					
				});
				
				contEpiNuevo.add(btnNuevoEpi);
				
				nuevoEpi.setVisible(true);
			}
			
		});
	}
	
	private void borraEpisodio(JButton btnBorraEpi,final JTable tablaEpisodios) {
		// TODO Auto-generated method stub
		btnBorraEpi.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int ind = tablaEpisodios.getSelectedRow();
				int resul = 0;
				if (ind > -1){
					resul = control.borraEpisodio(modelEpisodios.getEpisodio(ind));
					if (resul != 0){
						List<Episodio> aux = control.dameEpisodios(serie.getIdSerie());
						modelEpisodios.removeTable();
						if (!aux.isEmpty()){
							Iterator<Episodio> it = aux.iterator();
							while (it.hasNext()){
								modelEpisodios.anyadeEpisodio(it.next());
							}
						}
						JOptionPane.showMessageDialog(null, "Se ha eliminado con éxito el episodio");
					}
					else JOptionPane.showMessageDialog(null, "No se ha podido borrar");
				}
				else JOptionPane.showMessageDialog(null, "No Has seleccionado el capítulo");
			}
			
		});
	}

}
