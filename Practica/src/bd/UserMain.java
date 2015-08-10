package bd;

import java.beans.PropertyVetoException;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import gui.*;

public class UserMain {
	
	//throws PropertyVetoException
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			
			ComboPooledDataSource cpds = new ComboPooledDataSource();
			cpds.setDriverClass("org.gjt.mm.mysql.Driver");
			cpds.setJdbcUrl("jdbc:mysql://localhost/Practica");
			cpds.setUser("UsuarioP1");
			cpds.setPassword("UsuarioP1");
			cpds.setAcquireRetryAttempts(1);
			cpds.setAcquireRetryDelay(1);
			cpds.setBreakAfterAcquireFailure(true);
			
 			Controlador control = new Controlador(cpds);
			
			LoginWindow newUser = new LoginWindow(control);			
			
		}
		catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}
}
