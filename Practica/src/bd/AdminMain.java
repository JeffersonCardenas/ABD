package bd;

import java.beans.PropertyVetoException;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import gui.AdminWindow;

public class AdminMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {			
			ComboPooledDataSource cpds = new ComboPooledDataSource();
			cpds.setDriverClass("org.gjt.mm.mysql.Driver");
			cpds.setJdbcUrl("jdbc:mysql://localhost/Practica");
			cpds.setUser("AdminP1");
			cpds.setPassword("AdminP1");
			cpds.setAcquireRetryAttempts(1);
			cpds.setAcquireRetryDelay(1);
			cpds.setBreakAfterAcquireFailure(true);
			
 			Controlador controla = new Controlador(cpds);
 			AdminWindow ventana = new AdminWindow(controla);			
			
		}
		catch (PropertyVetoException e) {
			e.printStackTrace();
		}

	}

}
