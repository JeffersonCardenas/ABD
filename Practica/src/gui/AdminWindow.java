package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import bd.Controlador;
import tables.Serie;

public class AdminWindow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTabbedPane panel;
	private JButton infoSerie;
	private JButton newSerie;
	private Controlador control;
	private List<Serie> listaSeries;
	
	public AdminWindow(Controlador c){
		this.control = c;
		this.listaSeries = new LinkedList<Serie>();
		initWindow();
	}
	
	private void initWindow(){
		this.setTitle("Administrador");		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(200,150,500,250);
		
		panel = new JTabbedPane();
		
		JComponent panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());
		initSerie(panel1);
		
		panel.addTab("Series", panel1);
		
		this.add(panel);
		this.setVisible(true);
	}
	
	private void initSerie(JComponent p){
		JLabel u = new JLabel("Buscar serie:");
		JTextField busca = new JTextField(15);
		JButton boton = new JButton("Buscar");
		
		JPanel pane = new JPanel();
		pane.add(u);
		pane.add(busca);
		pane.add(boton);
		
		p.add(pane,BorderLayout.NORTH);
		
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		JList<String> lista = new JList<String>(listModel);
		lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lista.setLayoutOrientation(JList.VERTICAL);
		lista.setVisibleRowCount(-1);
		
		JScrollPane listScroller = new JScrollPane(lista);
		listScroller.setPreferredSize(new Dimension(250, 80));
		
		buscaSerie(boton,busca,listModel);
		
		p.add(listScroller,BorderLayout.CENTER);
		
		infoSerie = new JButton("Ver Información");
		newSerie = new JButton("Nueva Serie");
		JPanel pane2 = new JPanel();
		pane2.add(infoSerie);
		pane2.add(newSerie);
		
		actualizaSerie(infoSerie,lista);
		
		nuevaSerie(newSerie);
		
		p.add(pane2,BorderLayout.SOUTH);
	}

	private void nuevaSerie(JButton newSerie2) {
		// TODO Auto-generated method stub
		newSerie2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				VentanaNuevaSerie nueva = new VentanaNuevaSerie(control);
			}
			
		});
	}

	private void actualizaSerie(JButton bInfo,final JList<String> l) {
		// TODO Auto-generated method stub
		bInfo.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int index = l.getSelectedIndex();
				if (!listaSeries.isEmpty() && index != -1){
					ModificaSerie v = new ModificaSerie(listaSeries.get(index),control);					
				}
			}
			
		});
	}

	private void buscaSerie(final JButton b,final JTextField field,final DefaultListModel<String> lM) {
		// TODO Auto-generated method stub
		b.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String nombre = field.getText();
				if (!nombre.isEmpty()){
					listaSeries.clear();
					listaSeries = control.buscaSerie(nombre);
					if (!listaSeries.isEmpty()){
						lM.clear();
						Iterator<Serie> it = listaSeries.iterator();
						while(it.hasNext()){
							lM.addElement(it.next().getNombre());
						}
					}
					else JOptionPane.showMessageDialog(b, "No hay series coincidentes");
				}
				else JOptionPane.showMessageDialog(b, "Escribe el nombre de la serie");
			}
			
		});
		
	}

}
