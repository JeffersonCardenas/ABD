package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import bd.Controlador;
import tables.Episodio;
import tables.Serie;
import tables.Usuario;


public class UserWindow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel user;
	private JTabbedPane cuadro;
	private Controlador controlador;
	private JFrame login;
	private VentanaInfoSerie infoSerie;
	private List<Serie> listaSeries;
	private Usuario usuario;
	private JComboBox<Serie> comboSeries;
	private JButton icono;
	private MyTableModel tableModel;
	
	public UserWindow(JFrame log,Usuario user,Controlador c){
		this.login = log;
		this.listaSeries = new LinkedList<Serie>();
		this.usuario = user;
		this.controlador = c;
		initWindow();
	}
	
	private void initWindow(){
		this.setTitle("SeriesPy");
		this.setLayout(new BorderLayout());
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(120,50,1000,900);
		
		initUser();		
		initTabePane();
		
		JPanel salir = new JPanel();
		JButton bSalir = new JButton("Cerrar Sesión");		
		cerrarSesion(bSalir,this);
		salir.add(bSalir);
		
		this.add(user,BorderLayout.NORTH);
		this.add(cuadro,BorderLayout.CENTER);
		this.add(salir,BorderLayout.SOUTH);
		
		this.setVisible(true);
	}
	
	private void cerrarSesion(JButton s,final JFrame p){
		s.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				p.dispose();
				login.setVisible(true);
			}
			
		});
	}
	
	private void initUser(){
		user = new JPanel();
		user.setBorder(new TitledBorder("Datos Usuario"));
		user.setLayout(new BorderLayout());
		
		//imagen del usuario
		this.icono = new JButton();
		if (usuario.getFoto() != null)	icono.setIcon(new ImageIcon(usuario.getFoto()));
		
		modificaDatosUsuario(icono);
		JPanel bloqueFoto = new JPanel();
		bloqueFoto.add(icono);
		
		JPanel bloqueSup = new JPanel();				
		JLabel nick = new JLabel("Nick: " + usuario.getNick());
		String edad = usuario.getAge() + " Años";
		JLabel age = new JLabel(edad);
		
		bloqueSup.add(nick);
		bloqueSup.add(age);
		
		user.add(bloqueSup,BorderLayout.NORTH);
		user.add(bloqueFoto,BorderLayout.CENTER);
	}

	private void initTabePane(){
		cuadro = new JTabbedPane();
		
		//Cuadro de busqueda de series
		JComponent panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());
				
		initBusquedaSerie(panel1);
				
		//Cuadro de series que sigue el usuario
		JComponent panel2 = new JPanel();
		panel2.setLayout(new BorderLayout());
				
		initMisSeries(panel2);

		cuadro.addTab("Buscar series", panel1);
		cuadro.addTab("Mis Series", panel2);
	}
	
	private void initBusquedaSerie(JComponent p){
		JLabel u = new JLabel("Buscar serie:");
		JTextField busca = new JTextField(15);
		JButton search = new JButton("Buscar");
		
		JPanel panel1 = new JPanel();
		panel1.add(u);
		panel1.add(busca);
		panel1.add(search);
		
		p.add(panel1,BorderLayout.NORTH);
		
		//lista de series de la busqueda
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		JList<String> lista = new JList<String>(listModel);
		lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lista.setLayoutOrientation(JList.VERTICAL);
		lista.setVisibleRowCount(-1);
		
		JScrollPane listScroller = new JScrollPane(lista);
		listScroller.setPreferredSize(new Dimension(250, 80));
		
		p.add(listScroller,BorderLayout.CENTER);
		
		JButton info = new JButton("Ver Información");
		JPanel pane2 = new JPanel();
		pane2.add(info);
		
		p.add(pane2,BorderLayout.SOUTH);
		
		buscaSerie(search,busca,listModel);
		getInfoSerie(info,lista);
	}
	
	private void initMisSeries(JComponent p){
		JLabel s = new JLabel("Serie:");
		Serie[] listaSeriesUsuario = llenaLista();
		
		if (listaSeriesUsuario != null)	this.comboSeries = new JComboBox<Serie>(listaSeriesUsuario);		
		else	this.comboSeries = new JComboBox<Serie>();
		
		initEpisodiosTabla(this.comboSeries);
		
		JPanel p1 = new JPanel();
		p1.add(s);
		p1.add(this.comboSeries);
		
		p.add(p1,BorderLayout.NORTH);
		
		//Apartado 3
		tableModel = new MyTableModel();
        
        JTable table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        JScrollPane scrollPaneTable = new JScrollPane(table);
        scrollPaneTable.setBorder(new TitledBorder("Episodios No Vistos"));
        p.add(scrollPaneTable);
        
        JButton visto = new JButton("Marcar Como Visto");
        
        marcarEpisodioVisto(visto,table);
        
        JPanel p2 = new JPanel();
        p2.add(visto);
        
        p.add(p2,BorderLayout.SOUTH);
	}
	
	private void marcarEpisodioVisto(JButton visto,final JTable t) {
		// TODO Auto-generated method stub
		visto.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int i = t.getSelectedRow();
				int resul = 0;
				if (i != -1){
					Episodio aux = tableModel.getEpisodio(i);
					resul = controlador.marcaEpisodioVisto(aux,usuario.getNick());
					if (resul > 0)	{
						tableModel.removeTable();
						List<Episodio> lista = controlador.dameEpisodiosNoVistos(aux.getIdSerie(),usuario.getNick());
						if (!lista.isEmpty()){
							Iterator<Episodio> it = lista.iterator();
							while (it.hasNext()){
								tableModel.anyadeEpisodio(it.next());
							}
						}
						JOptionPane.showMessageDialog(null, "Has marcado como visto el episodio");
					}
					else JOptionPane.showMessageDialog(null, "Ha Habido un error en tu petición");					
				}
				else JOptionPane.showMessageDialog(null, "No Has Seleccionado Ningún Capitulo");
			}
			
		});
	}

	private Serie[] llenaLista() {
		// TODO Auto-generated method stub
		List<Serie> aux = this.controlador.buscaSerieSeguidas(usuario.getNick());
		if (aux != null){
			Serie[] resul = new Serie[aux.size()];
			Iterator<Serie> it = aux.iterator();
			int i=0;
			while(it.hasNext()){
				resul[i] = it.next();
				i++;
			}
			return resul;
		}
		else return null;
	}
	
	private void modificaDatosUsuario(JButton icon) {
		// TODO Auto-generated method stub
		icon.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				ModificaUsuario vMod = new ModificaUsuario(usuario,controlador,icono);
			}
			
		});		
	}
	
	private void initEpisodiosTabla(final JComboBox<Serie> cSerie) {
		// TODO Auto-generated method stub
		cSerie.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int indice = cSerie.getSelectedIndex();
				if (indice != -1){
					tableModel.removeTable();
					Serie s = cSerie.getItemAt(indice);
					List<Episodio> aux = controlador.dameEpisodiosNoVistos(s.getIdSerie(),usuario.getNick());
					if (!aux.isEmpty()){
						Iterator<Episodio> it = aux.iterator();
						while (it.hasNext()){
							tableModel.anyadeEpisodio(it.next());
						}
					}
				}
			}
			
		});
	}

	private void buscaSerie(final JButton b,final JTextField field,final DefaultListModel<String> lModel){
		b.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String nombre = field.getText();
				if (!nombre.isEmpty()){
					listaSeries.clear();
					listaSeries = controlador.buscaSerie(nombre);
					if (!listaSeries.isEmpty()){
						lModel.clear();
						Iterator<Serie> it = listaSeries.iterator();
						while(it.hasNext()){
							lModel.addElement(it.next().getNombre());
						}
					}
					else JOptionPane.showMessageDialog(b, "No hay series coincidentes");
				}
				else JOptionPane.showMessageDialog(b, "Escribe el nombre de la serie");
			}
			
		});
	}
	
	private void getInfoSerie(JButton b,final JList<String> l){
		b.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int index = l.getSelectedIndex();
				if (!listaSeries.isEmpty() && index != -1){
					infoSerie = new VentanaInfoSerie(listaSeries.get(index),usuario,controlador,comboSeries);
				}
			}
		});
	}

}
