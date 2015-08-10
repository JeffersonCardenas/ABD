package gui;

import java.sql.Date;
import java.util.LinkedList;
import javax.swing.table.*;
import tables.Episodio;

public class MyTableModel extends AbstractTableModel{
	static final long serialVersionUID = 1L;
	private String[] columnNames = {"Núm", "Temporada", "Título", "Fecha de Estreno"};
	private LinkedList<Episodio> data = new LinkedList<Episodio>();
	
    /**
     * Devuelve el numero de columnas
     */
	public int getColumnCount() {
		return columnNames.length;
	}
	
	/**
	 * devuelve el numero de filas
	 */
	public int getRowCount() {
		return data.size();
	}
	
	/**
	 * Devuelve el nombre de las columnas
	 */
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	/**
	 * Devuelve el valor de una posicion en la tabla
	 */
	public Object getValueAt(int row, int col) {
		Episodio aux = data.get(row);
		
		switch(col){
			case 0: return aux.getNumEpisodio();
			case 1: return aux.getNumTemporada();
			case 2: return aux.getNombreCap();
			case 3: return aux.getFechaEst();
			default: return null;
		}
	}
	
	/**
	 * Devuelve la clase de la columna
	 */
	public Class<?> getColumnClass(int c) {
		switch(c){
			case 0: return Integer.class;			
			case 1: return Integer.class;
			case 2: return String.class;
			case 3: return Date.class;
			default: return Object.class;
		}
	}


  /**
   * Asigna un valor en la posicion de la tabla
   */
  public void setValueAt(Object value, int row, int col) {
	  data.add((Episodio) value);
	  fireTableCellUpdated(row, col);
  }
  
  /**
   * Elimina todos los elementos de la lista
   */
  public void removeTable(){
  	data.clear();
  	this.fireTableDataChanged();
  }
  
  /**
   * Borra del modelo el episodio en la fila indicada 
   */
  public void borraEpisodio (int fila){
      // Se borra la fila 
      data.remove(fila);
  }
  
  /**
   * Añade un Episodio al final de la tabla
   */
  public void anyadeEpisodio (Episodio nuevoEpisodio){
      // Añade el episodio al modelo 
      data.add(nuevoEpisodio);
  }
  
  /**
   * Devuelve el episodio de la tabla
   * @param index indice del episodio elegido
   * @return el episodio seleccionado
   */
  public Episodio getEpisodio(int index){
	  return data.get(index);
  }

}
