package mappers;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import org.apache.commons.lang3.StringUtils;
import com.mysql.jdbc.Statement;
import java.util.LinkedList;
import java.util.List;
import bd.QueryCondition;

public abstract class AbstractMapper<T,K> {

	protected DataSource ds;

	/**
	 * Devuelve el nombre de la tabla asociada al mapper concreto. Esta
	 * tabla será la utilizada en todas las consultas SQL.
	 * 
	 * @return Cadena con el nombre de la tabla
	 */
	protected abstract String getTableName();

	/**
	 * Devuelve los nombres de las columnas de la tabla asociada al mapper
	 * concreto.
	 * 
	 * @return Array con los nombres de columnas de la tabla.
	 */
	protected abstract String[] getColumnNames();

	/**
	 * Divide un objeto dado en sus componentes. Las componentes del array
	 * devuelto deben estar en el orden correspondiente al dado por las
	 * columnas devueltas por getColumnNames() 
	 * 
	 * @param object Objeto a dividir
	 * @return Componentes del objeto dividido
	 */
	protected abstract Object[] serializeObject(T object);

	/**
	 * Devuelve los nombres de las columnas que forman la clave primaria de
	 * la tabla del mapper concreto.
	 * 
	 * @return Array con nombres de columnas clave
	 */
	protected abstract String[] getKeyColumnNames();
	
	/**
	 * Divide una clave primaria en sus componentes. Las componentes del array
	 * devuelto deben estar en el orden correspondiente al dado por las
	 * columnas devueltas por getKeyColumnNames() 
	 * 
	 * @param key Clave a dividir
	 * @return Componentes de la clave pasada como parámetro
	 */
	protected abstract Object[] serializeKey(K key);
	
	/**
	 * Construye un objeto a partir del resultado de una consulta.
	 * 
	 * @param rs ResultSet con el resultado actual de la consulta.
	 * @return Objeto (de tipo T) representado por la fila contenida en rs
	 * @throws SQLException
	 */
	protected abstract T buildObject(ResultSet rs) throws SQLException;

	/**
	 * Obtiene la clave primaria del objeto pasado como parámetro. 
	 * 
	 * @param object Objeto
	 * @return Clave primera del objeto pasado como parámetro.
	 */
	protected abstract K getKey(T object);

	
	public AbstractMapper(DataSource ds) {
		this.ds = ds;
	}
	
	/**
	 * Indica si la tabla tiene una columna con valor auto increment
	 * @return true si la tabla tiene una columna con un valor auto increment
	 * false en cualquier otro caso
	 */
	protected abstract boolean tieneAutoIncrementColumn();
	
	/**
	 * Devuelve la lista de objetos que satisfacen todas las condiciones
	 * del array pasado como parámetro
	 * 
	 * @param conditions Objetos de la clase QueryCondition que especifican las condiciones
	 *                   de los objetos a buscar
	 * @return Lista de objetos de la tabla que cumplen las condiciones dadas. 
	 *         Si ninguno de ellos las cumple, se devuelve una lista vacía.
	 */
	protected List<T> findByConditions(QueryCondition[] conditions){
		Connection con        = null;
		PreparedStatement pst = null;
		ResultSet rs          = null;
		List<T> resul = new LinkedList<T>();
		try{
			con = ds.getConnection();
			String[] columnNames = getColumnNames();
			String columnNamesWithCommas = StringUtils.join(columnNames, ", ");
			String sql = "SELECT " + columnNamesWithCommas + " FROM " + getTableName() +  
			" WHERE " + StringUtils.join(getWhereConditions(conditions)," AND ");
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				resul.add(buildObject(rs));
			}
			return resul;
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		finally {
			try {
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
		return null;		
	}


	public int update(T object){
		Connection con        = null;
		PreparedStatement pst = null;
		ResultSet rs          = null;
		int resul = 0;
		try{
			con = ds.getConnection();
			String[] columnName = getColumnNames();
			String[] assignments = new String[columnName.length-1];
			String[] keyColumns = getKeyColumnNames();
			String[] conditions = new String[keyColumns.length];
			for (int i=0;i<assignments.length;i++){
				assignments[i] = columnName[i+1] + "=?";
			}
			for (int i=0;i<keyColumns.length;i++){
				conditions[i] = keyColumns[i] + "=?";
			}
			String sql = "UPDATE " + getTableName() + " SET " + StringUtils.join(assignments,",")+
			" WHERE " + StringUtils.join(conditions," AND ");
			pst = con.prepareStatement(sql);
			Object[] objectField = serializeObject(object);
			int pos = 1;
			for (int j=1;j<columnName.length;j++){
				pst.setObject(j,objectField[j]);
				pos = j;
			}
			//Condiciones del where
			Object[] primaryKeys = serializeKey(getKey(object));
			pos++;
			for (int j=0;j<primaryKeys.length;j++){
				pst.setObject(pos, primaryKeys[j]);
				pos++;
			}
			resul = pst.executeUpdate();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		finally {
			try {
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
		return resul;
	}
	
	public int insert(T object){
		Connection con        = null;
		PreparedStatement pst = null;
		ResultSet rs          = null;
		int resul = 0;
		try{
			con = ds.getConnection();
			String[] columnName;
			String columnNamesWithCommas;
			String[] assignments;
			boolean autoIncrement = tieneAutoIncrementColumn();
			Object[] objectField;
			if (!autoIncrement){
				columnName = getColumnNames();
				columnNamesWithCommas = StringUtils.join(columnName, ", ");
				assignments = new String[getColumnNames().length];
				for (int i=0;i<assignments.length;i++){
					assignments[i] = "?";
				}
				String sql = "INSERT INTO " + getTableName() + " (" + columnNamesWithCommas + ")" 
						+ " VALUES (" + StringUtils.join(assignments,",") + ")";
				pst = con.prepareStatement(sql);
				objectField = serializeObject(object);
				for (int j=0;j<columnName.length;j++){
					pst.setObject(j+1,objectField[j]);
				}
				resul = pst.executeUpdate();
			}
			else {
				String [] columnNameId = getColumnNames();
				columnName = new String[columnNameId.length-1];
				assignments = new String[columnName.length];
				for (int i=0;i<assignments.length;i++){
					assignments[i] = "?";
				}
				for (int j=1;j<columnNameId.length;j++){
					columnName[j-1] = columnNameId[j];
				}
				columnNamesWithCommas = StringUtils.join(columnName, ", ");
				String sql = "INSERT INTO " + getTableName() + " (" + columnNamesWithCommas + ")" 
						+ " VALUES (" + StringUtils.join(assignments,",") + ")";
				pst = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				objectField = serializeObject(object);
				for (int j=1;j<objectField.length;j++){
					pst.setObject(j,objectField[j]);
				}
				resul = pst.executeUpdate();
				rs = pst.getGeneratedKeys();
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		finally {
			try {
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
		return resul;
	}
	
	public int delete(T object){
		Connection con        = null;
		PreparedStatement pst = null;
		ResultSet rs          = null;
		int resul = 0;
		try{
			con = ds.getConnection();
			String[] columnName = getColumnNames();
			String[] assignments = new String[columnName.length];
			for (int i=0;i<assignments.length;i++){
				assignments[i] = columnName[i] + "=?";
			}
			String sql = "DELETE FROM " + getTableName() + " WHERE " + StringUtils.join(assignments," AND ");
			pst = con.prepareStatement(sql);
			Object[] objectField = serializeObject(object);
			for (int j=0;j<columnName.length;j++){
				pst.setObject(j+1,objectField[j]);
			}
			resul = pst.executeUpdate();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		finally {
			try {
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
		return resul;
	}
	
	/**
	 * Devuelve el objeto de la tabla que coincide con la clave primaria
	 * @param id Clave
	 * @return el objeto encontrado en la tabla
	 */
	public T findById(K id) {
		Connection con        = null;
		PreparedStatement pst = null;
		ResultSet rs          = null;
		T result       = null;
		try {
			con = ds.getConnection();
			String[] columnNames = getColumnNames();
			String columnNamesWithCommas = StringUtils.join(columnNames, ", ");
			String[] keyColumnNames = getKeyColumnNames();
			String[] conditions = new String[keyColumnNames.length];
			for (int i=0;i<conditions.length;i++){
				conditions[i] = keyColumnNames[i] + "=?";
			}
			String sql = "SELECT " + columnNamesWithCommas + " FROM " + getTableName() +  
			" WHERE " + StringUtils.join(conditions," AND ");
			pst = con.prepareStatement(sql);
			Object[] keyComponents = serializeKey(id);
			for (int j=0;j<keyColumnNames.length;j++){
				pst.setObject(j+1, keyComponents[j]);
			}
			rs = pst.executeQuery();
			if (rs.next()) {
				result = buildObject(rs);
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
		return result;
	}
	
	private String[] getWhereConditions(QueryCondition[] cond){
		String[] conditionsStr = new String[cond.length];
		for (int i=0;i<cond.length;i++){
			if (cond[i].getOperator().ordinal() == 6){
				conditionsStr[i] = cond[i].getColumnName() + " " + cond[i].getOperator() + " '%" + cond[i].getValue() + "%'";
			}
			else{
				conditionsStr[i] = cond[i].getColumnName() + " " + cond[i].getOperator() + " '" + cond[i].getValue() + "'";
			}
		}
		return conditionsStr;
	}

}
