package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import bd.Controlador;
import tables.Episodio;
import tables.Serie;
import tables.Usuario;

public class VentanaInfoSerie extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton seguir;
	private Serie serie;
	private Usuario usuario;
	private Controlador control;
	private JComboBox<Serie> combo;
	private MyTableModel modelTablaEpisodios;
	
	public VentanaInfoSerie(Serie s,Usuario us,Controlador c,JComboBox<Serie> com){
		this.serie = s;
		this.usuario = us;
		this.control = c;
		this.combo = com;
		initVentana(serie);
	}
	
	private void initVentana(Serie s){
		this.setTitle(s.getNombre());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(200,150,850,650);
		
		JPanel contenedor = new JPanel();
		contenedor.setBorder(new TitledBorder("Información de la Serie"));
		setContentPane(contenedor);
		contenedor.setLayout(null);
		
		//titular		
		JTextArea textTitular = new JTextArea(s.getTitular());
		textTitular.setEditable(false);
		JScrollPane scrollTitular = new JScrollPane(textTitular);
		scrollTitular.setBorder(new TitledBorder("Titular"));
		scrollTitular.setBounds(40, 30, 650, 80);
		contenedor.add(scrollTitular);
		
		//sinopsis
		JTextArea info = new JTextArea(s.getSinopsis());
		info.setEditable(false);
		JScrollPane scrollpane = new JScrollPane(info);
		scrollpane.setBorder(new TitledBorder("Sinopsis"));
		scrollpane.setBounds(50,125,600,120);
		contenedor.add(scrollpane);
		
        //fecha estreno
        JLabel fechaEst = new JLabel("Estreno: "+s.getFechaEstreno().toString());
      	fechaEst.setBounds(80,260,150,20);
      	contenedor.add(fechaEst);

      	//fecha finalizacion
      	JLabel fechaFin;
      	if (s.getFechaFin() != null){
      		fechaFin = new JLabel("Finalización: "+s.getFechaFin().toString());
      		fechaFin.setBounds(400, 260,150, 20);
      	}
      	else {
      		fechaFin = new JLabel("No Finalizada Aún");
      		fechaFin.setBounds(400, 260, 150, 20);
      	}
      	contenedor.add(fechaFin);
      	
      	//Tabla de Episodios
   		modelTablaEpisodios = new MyTableModel();
              
        JTable tablaEpisodios = new JTable(modelTablaEpisodios);
        tablaEpisodios.setFillsViewportHeight(true);
        tablaEpisodios.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        
        List<Episodio> aux = control.dameEpisodios(serie.getIdSerie());
		if (!aux.isEmpty()){
			Iterator<Episodio> it = aux.iterator();
			while (it.hasNext()){
				modelTablaEpisodios.anyadeEpisodio(it.next());
			}
		}
        
        JScrollPane scrollPaneTable = new JScrollPane(tablaEpisodios);
        scrollPaneTable.setBorder(new TitledBorder("Episodios Serie"));
        scrollPaneTable.setBounds(60, 300, 600, 200);
        contenedor.add(scrollPaneTable);
        
		//Botones		
		seguir = new JButton("Seguir");
		seguir.setBounds(200,560,80,40);		
		sigueSerie(seguir);		
		contenedor.add(seguir);
		
		JButton btnInfoEpisodio = new JButton("Información");
		btnInfoEpisodio.setBounds(300, 560, 140, 40);
		getInfoEpisodio(btnInfoEpisodio,tablaEpisodios);
		contenedor.add(btnInfoEpisodio);
		
		this.setVisible(true);
	}
	
	private void sigueSerie(JButton seg){
		seg.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				boolean siguiendo = control.sigueSerie(serie,usuario);
				if (siguiendo) JOptionPane.showMessageDialog(null, "Estas siguiendo " + serie.getNombre());
				else JOptionPane.showMessageDialog(null, "Ya no sigues " + serie.getNombre());
					List<Serie> aux = control.buscaSerieSeguidas(usuario.getNick());
					combo.removeAllItems();
					if (!aux.isEmpty()){
						Iterator<Serie> it = aux.iterator();
						while (it.hasNext()){
							combo.addItem(it.next());
						}
					}
				}			
		});
	}
	
	private void getInfoEpisodio(JButton btnInfoEpi,final JTable t) {
		// TODO Auto-generated method stub
		btnInfoEpi.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int fila = t.getSelectedRow();
				if (fila != -1){
					Episodio aux = modelTablaEpisodios.getEpisodio(fila);
					JFrame frameInfoEpisodio = new JFrame(aux.getNombreCap());
					frameInfoEpisodio.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
					frameInfoEpisodio.setBounds(300, 300, 450, 250);
					
					JTextArea textSinopsis = new JTextArea(aux.getSinopsis());
					textSinopsis.setEditable(false);
					textSinopsis.setLineWrap(true);
					JScrollPane scrollSinopsis = new JScrollPane(textSinopsis);
					scrollSinopsis.setBorder(new TitledBorder("Sinopsis"));
					frameInfoEpisodio.add(scrollSinopsis);
					frameInfoEpisodio.setVisible(true);
				}
				else JOptionPane.showMessageDialog(null, "No Has Seleccionado Ningún Capítulo");
			}			
		});
	}

}
