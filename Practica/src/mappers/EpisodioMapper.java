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
import bd.ClaveEpisodio;
import tables.Episodio;

public class EpisodioMapper extends AbstractMapper<Episodio,ClaveEpisodio>{
	
	private static final String[] EPISODIO_KEY_COLUMN_NAMES = new String[] { "Id_Serie", "Num_Episodio","Num_Temporada" };
	private static final String[] EPISODIO_COLUMN_NAMES = new String[] { "Id_Serie", "Num_Episodio", "Num_Temporada", "Nombre_cap", "Sinopsis_cap", "Fecha_Est" };
	private static final String EPISODIO_TABLE_NAME = "Episodio";

	public EpisodioMapper(DataSource ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return EPISODIO_TABLE_NAME;
	}

	@Override
	protected String[] getColumnNames() {
		// TODO Auto-generated method stub
		return EPISODIO_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeObject(Episodio object) {
		// TODO Auto-generated method stub
		return new Object[] {object.getIdSerie(),object.getNumEpisodio(),object.getNumTemporada(),
				object.getNombreCap(),object.getSinopsis(),object.getFechaEst()};
	}

	@Override
	protected String[] getKeyColumnNames() {
		// TODO Auto-generated method stub
		return EPISODIO_KEY_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeKey(ClaveEpisodio key) {
		// TODO Auto-generated method stub
		return new Object[] {key.getClave(),key.getNumEpisodio(),key.getNumTemporada()};
	}

	@Override
	protected Episodio buildObject(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		Integer id = rs.getInt(EPISODIO_COLUMN_NAMES[0]);
		Integer numEpi = rs.getInt(EPISODIO_COLUMN_NAMES[1]);
		Integer numTemp = rs.getInt(EPISODIO_COLUMN_NAMES[2]);
		String name = rs.getString(EPISODIO_COLUMN_NAMES[3]);
		String sin = rs.getString(EPISODIO_COLUMN_NAMES[4]);
		Date fec = rs.getDate(EPISODIO_COLUMN_NAMES[5]);
		return new Episodio(id,numEpi,numTemp,name,sin,fec);
	}

	@Override
	protected ClaveEpisodio getKey(Episodio object) {
		// TODO Auto-generated method stub
		return new ClaveEpisodio(object.getIdSerie(),object.getNumEpisodio(),object.getNumTemporada());
	}

	@Override
	protected boolean tieneAutoIncrementColumn() {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Episodio> getListaEpisodios(Integer idSerie) {
		// TODO Auto-generated method stub
		Connection con        = null;
		PreparedStatement pst = null;
		ResultSet rs          = null;
		List<Episodio> result       = new LinkedList<Episodio>();
		try {
			con = ds.getConnection();
			String[] columnNames = getColumnNames();
			String columnNamesWithCommas = StringUtils.join(columnNames, ", ");
			String sql = "SELECT " + columnNamesWithCommas + " FROM " + getTableName() +  
			" WHERE id_serie=? ORDER BY num_episodio";
			pst = con.prepareStatement(sql);
			pst.setObject(1, idSerie);
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

	public List<Episodio> getEpisodiosNoVistos(Integer idSerie,String name) {
		// TODO Auto-generated method stub
		Connection con        = null;
		PreparedStatement pst = null;
		ResultSet rs          = null;
		List<Episodio> result       = new LinkedList<Episodio>();
		try {
			con = ds.getConnection();
			String[] columnNames = getColumnNames();
			String columnNamesWithCommas = StringUtils.join(columnNames, ", ");
			String sql = "SELECT " + columnNamesWithCommas + " FROM " + getTableName()
			+ " WHERE id_serie=? AND num_episodio NOT IN (SELECT num_episodio FROM episodio_visto WHERE nick=?)"
			+ " AND num_temporada NOT IN (SELECT num_temporada FROM episodio_visto WHERE nick=?)";
			pst = con.prepareStatement(sql);
			pst.setObject(1, idSerie);
			pst.setObject(2, name);
			pst.setObject(3, name);
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

	public int insertaEpisodio(Integer idSerie, String numEpi, String numTemp,
			String nombreC, String sinopC, String fEst) {
		// TODO Auto-generated method stub
		Integer nEpi = 0;
		Integer nTemp = 0;
		if (!numEpi.equals("")) nEpi = Integer.parseInt(numEpi);
		if (!numTemp.equals("")) nTemp = Integer.parseInt(numTemp);
		ClaveEpisodio clave = new ClaveEpisodio(idSerie,nEpi,nTemp);
		Episodio aux = super.findById(clave);
		if (aux==null)	{
			aux = new Episodio(idSerie,nEpi,nTemp,nombreC,sinopC,Date.valueOf(fEst));
			return super.insert(aux);
		}
		else return 0;
	}

}
