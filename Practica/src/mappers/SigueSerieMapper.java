package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import bd.ClaveSeguidor;
import tables.Seguidor;
import javax.sql.DataSource;

public class SigueSerieMapper extends AbstractMapper<Seguidor,ClaveSeguidor>{
	
	private static final String[] SEGUIDOR_KEY_COLUMN_NAMES = new String[] { "nick", "id_serie" };
	private static final String[] SEGUIDOR_COLUMN_NAMES = new String[] { "nick", "id_serie" };
	private static final String SEGUIDOR_TABLE_NAME = "es_seguidor";
	
	public SigueSerieMapper(DataSource ds){
		super(ds);
	}

	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return SEGUIDOR_TABLE_NAME;
	}

	@Override
	protected String[] getColumnNames() {
		// TODO Auto-generated method stub
		return SEGUIDOR_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeObject(Seguidor object) {
		// TODO Auto-generated method stub
		return new Object[]{object.getNick(),object.getIdSerie()};
	}

	@Override
	protected String[] getKeyColumnNames() {
		// TODO Auto-generated method stub
		return SEGUIDOR_KEY_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeKey(ClaveSeguidor key) {
		// TODO Auto-generated method stub
		return new Object[]{key.getNick(),key.getIdSerie()};
	}

	@Override
	protected Seguidor buildObject(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		String n = rs.getString(SEGUIDOR_COLUMN_NAMES[0]);
		Integer id = rs.getInt(SEGUIDOR_COLUMN_NAMES[1]);
		return new Seguidor(n,id);
	}

	@Override
	protected ClaveSeguidor getKey(Seguidor object) {
		// TODO Auto-generated method stub
		return new ClaveSeguidor(object.getNick(),object.getIdSerie());
	}
	
	@Override
	protected boolean tieneAutoIncrementColumn() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean creaRelacion(Seguidor o) {
		// TODO Auto-generated method stub
		Seguidor seg = this.findById(new ClaveSeguidor(o.getNick(),o.getIdSerie()));
		boolean siguiendo = false;
		if (seg==null)	{
			this.insert(o);
			siguiendo = true;
		}
		else {
			this.delete(seg);
			siguiendo = false;
		}
		return siguiendo;
	}

}
