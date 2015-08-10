package tables;

import java.sql.Date;
import java.util.Calendar;

public class Usuario {
	private String nick;
	private String password;
	private Date fecha_nac;
	private byte[] imagen;
	
	public Usuario(){
		this.nick = null;
		this.password = null;
		this.fecha_nac = null;
	}
	
	public Usuario(String n,String pass,Date f){
		this.nick = n;
		this.password = pass;
		this.fecha_nac = f;
		this.imagen = null;
	}
	
	public Usuario(String n,String pass,Date f,byte[] image){
		this.nick = n;
		this.password = pass;
		this.fecha_nac = f;
		this.imagen = image;
	}
	
	public String getNick(){
		return this.nick;
	}
	
	public String getPass(){
		return this.password;
	}
	
	public Date getFechaNac(){
		return this.fecha_nac;
	}
	
	public byte[] getFoto(){
		return this.imagen;
	}
	
	public void setNick(String aka){
		this.nick = aka;
	}
	
	public void setFechaNac(Date fecha){
		this.fecha_nac = fecha;
	}
	
	public void setPass(String p){
		this.password = p;
	}
	
	public void setFoto(byte[] image){
		this.imagen = image;
	}
	
	@Override
	public String toString(){
		return "Usuario [Nick=" + this.nick + "Fecha de Nacimiento=" + this.fecha_nac + "]";
	}

	public String getAge() {
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha_nac);
		int year = cal.get(Calendar.YEAR);
		int anyoActual = Calendar.getInstance().get(Calendar.YEAR);
		year = anyoActual - year;
		return Integer.toString(year);
	}

}
