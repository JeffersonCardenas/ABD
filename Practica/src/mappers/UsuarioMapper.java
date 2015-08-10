package mappers;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Blob;
import javax.sql.DataSource;
import tables.Usuario;

public class UsuarioMapper extends AbstractMapper<Usuario,String>{
	
	private static final String[] USUARIO_KEY_COLUMN_NAMES = new String[] { "Nick" };
	private static final String[] USUARIO_COLUMN_NAMES = new String[] { "Nick", "Password", "fecha_nac", "imagen"};
	private static final String USUARIO_TABLE_NAME = "Usuario";

	public UsuarioMapper(DataSource ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return USUARIO_TABLE_NAME;
	}

	@Override
	protected String[] getColumnNames() {
		// TODO Auto-generated method stub
		return USUARIO_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeObject(Usuario object) {
		// TODO Auto-generated method stub
		return new Object[] {object.getNick(),object.getPass(),object.getFechaNac(),object.getFoto()};
	}

	@Override
	protected String[] getKeyColumnNames() {
		// TODO Auto-generated method stub
		return USUARIO_KEY_COLUMN_NAMES;
	}

	@Override
	protected Object[] serializeKey(String key) {
		// TODO Auto-generated method stub
		return new Object[] {key};
	}

	@Override
	protected Usuario buildObject(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		String n = rs.getString(USUARIO_COLUMN_NAMES[0]);
		String pass = rs.getString(USUARIO_COLUMN_NAMES[1]);
		Date f = rs.getDate(USUARIO_COLUMN_NAMES[2]);
		Blob blob = rs.getBlob(USUARIO_COLUMN_NAMES[3]);
		if (!rs.wasNull()){
			byte[] im = blob.getBytes(1, (int)blob.length());
			return new Usuario(n,pass,f,im);
		}
		else return new Usuario(n,pass,f);
	}

	@Override
	protected String getKey(Usuario object) {
		// TODO Auto-generated method stub
		return object.getNick();
	}
	
	@Override
	protected boolean tieneAutoIncrementColumn() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public int nuevoUsuario(String n,String p,String f){
		return this.insert(new Usuario(n,p,Date.valueOf(f)));
	}

}
