package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import bd.ClaveEpisodioVisto;
import tables.Episodio;
import tables.EpisodioVisto;

public class EpisodioVistoMapper extends AbstractMapper<EpisodioVisto,ClaveEpisodioVisto>{
	private static final String[] VISTO_KEY_COLUMN_NAMES = new String[] { "nick", "id_serie", "num_episodio", "num_temporada" };
	private static final String[] VISTO_COLUMN_NAMES = new String[] { "nick", "id_serie", "num_episodio", "num_temporada" };
	private static final String VISTO_TABLE_NAME = "episodio_visto";
	
	public EpisodioVistoMapper(DataSource ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return VISTO_TABLE_NAME;
	}

	@Override
	protected String[] getColumnNames() {
		// TODO Auto-generated method stub
		return VISTO_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeObject(EpisodioVisto object) {
		// TODO Auto-generated method stub
		return new Object[]{object.getNick(),object.getIdSerie(),object.getNumEpisodio(),object.getNumTemporada()};
	}

	@Override
	protected String[] getKeyColumnNames() {
		// TODO Auto-generated method stub
		return VISTO_KEY_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeKey(ClaveEpisodioVisto key) {
		// TODO Auto-generated method stub
		return new Object[]{key.getNick(),key.getIdSerie(),key.getNumEpisodio(),key.getNumTemporada()};
	}

	@Override
	protected EpisodioVisto buildObject(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		String n = rs.getString(VISTO_COLUMN_NAMES[0]);
		Integer id = rs.getInt(VISTO_COLUMN_NAMES[1]);
		Integer nE = rs.getInt(VISTO_COLUMN_NAMES[2]);
		Integer nT = rs.getInt(VISTO_COLUMN_NAMES[3]);
		return new EpisodioVisto(n,id,nE,nT);
	}

	@Override
	protected ClaveEpisodioVisto getKey(EpisodioVisto object) {
		// TODO Auto-generated method stub
		return new ClaveEpisodioVisto(object.getNick(),object.getIdSerie(),object.getNumEpisodio(),object.getNumTemporada());
	}

	@Override
	protected boolean tieneAutoIncrementColumn() {
		// TODO Auto-generated method stub
		return false;
	}

	public int insertaRelacion(Episodio aux, String nick) {
		// TODO Auto-generated method stub
		return super.insert(new EpisodioVisto(nick,aux.getIdSerie(),aux.getNumEpisodio(),aux.getNumTemporada()));
	}

}
