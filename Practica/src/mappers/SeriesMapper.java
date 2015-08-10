package mappers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.lang3.StringUtils;
import bd.Operator;
import bd.QueryCondition;
import tables.Serie;


public class SeriesMapper extends AbstractMapper<Serie,Integer>{
	
	private static final String[] SERIES_KEY_COLUMN_NAMES = new String[] { "Id_Serie" };
	private static final String[] SERIES_COLUMN_NAMES = new String[] { "Id_Serie", "Nombre", "Titular", "Sinopsis" ,"Año_Estreno","Año_Fin"};
	private static final String SERIES_TABLE_NAME = "Serie";
	

	public SeriesMapper(DataSource ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return SERIES_TABLE_NAME;
	}

	@Override
	protected String[] getColumnNames() {
		// TODO Auto-generated method stub
		return SERIES_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeObject(Serie object) {
		// TODO Auto-generated method stub
		return new Object[] {object.getIdSerie(),object.getNombre(),
				object.getTitular(),object.getSinopsis(),object.getFechaEstreno(),object.getFechaFin()};
	}

	@Override
	protected String[] getKeyColumnNames() {
		// TODO Auto-generated method stub
		return SERIES_KEY_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeKey(Integer key) {
		// TODO Auto-generated method stub
		return new Object[] { key };
	}

	@Override
	protected Serie buildObject(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		Integer id = rs.getInt(SERIES_COLUMN_NAMES[0]);
		String name = rs.getString(SERIES_COLUMN_NAMES[1]);
		String title = rs.getString(SERIES_COLUMN_NAMES[2]);;
		String sin = rs.getString(SERIES_COLUMN_NAMES[3]);
		Date año_est = rs.getDate(SERIES_COLUMN_NAMES[4]);
		Date año_f = rs.getDate(SERIES_COLUMN_NAMES[5]);
		if (rs.wasNull()) return new Serie(id,name,title,sin,año_est);
		else return new Serie(id,name,title,sin,año_est,año_f);
	}

	@Override
	protected Integer getKey(Serie object) {
		// TODO Auto-generated method stub
		return object.getIdSerie();
	}
	
	@Override
	protected boolean tieneAutoIncrementColumn() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public List<Serie> findByName(String name){
		QueryCondition cond = new QueryCondition(SERIES_COLUMN_NAMES[1],Operator.LIKE,name);
		QueryCondition[] condition = new QueryCondition[1];
		condition[0] = cond;
		return super.findByConditions(condition);
	}

	public int insertaSerie(String name,String t,String sin,String est,String fin) {
		// TODO Auto-generated method stub
		Integer id = 1;
		Serie aux = null;
		if (fin.isEmpty())	aux = new Serie(id,name,t,sin,Date.valueOf(est));
		else aux = new Serie(id,name,t,sin,Date.valueOf(est),Date.valueOf(fin));
		return this.insert(aux);
	}

	public List<Serie> listaSeriesSeguidas(String nickUser) {
		// TODO Auto-generated method stub
		Connection con        = null;
		PreparedStatement pst = null;
		ResultSet rs          = null;
		List<Serie> result    = new LinkedList<Serie>();
		try {
			con = ds.getConnection();
			String[] columnNames = getColumnNames();
			String columnNamesWithCommas = StringUtils.join(columnNames, ", ");
			String sql = "SELECT " + columnNamesWithCommas + " FROM " + getTableName() +  
			" NATURAL JOIN es_seguidor WHERE nick=?";
			pst = con.prepareStatement(sql);
			pst.setObject(1, nickUser);
			rs = pst.executeQuery();
			while (rs.next()) {
				result.add(buildObject(rs));
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (pst != null) pst.close();
				if (con != null) con.close();
			} catch (Exception e) {}
		}
		return null;
	}

}
