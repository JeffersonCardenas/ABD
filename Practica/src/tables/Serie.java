package tables;

import java.sql.Date;
import java.util.Calendar;

public class Serie {
	private Integer id_serie;
	private String nombre;
	private String titular;
	private String sinopsis;
	private Date a�o_estreno;
	private Date a�o_fin;
	
	public Serie(Integer id,String name,String title,String sin,Date est){
		this.id_serie = id;
		this.nombre = name;
		this.titular = title;
		this.sinopsis = sin;
		this.a�o_estreno = est;
		this.a�o_fin = null;
	}
	
	public Serie(Integer id,String name,String title,String sin,Date est,Date fin){
		this.id_serie = id;
		this.nombre = name;
		this.titular = title;
		this.sinopsis = sin;
		this.a�o_estreno = est;
		this.a�o_fin = fin;
	}
	
	public Integer getIdSerie(){
		return this.id_serie;
	}
	
	public String getNombre(){
		return this.nombre;
	}
	
	public String getTitular(){
		return this.titular;
	}
	
	public String getSinopsis(){
		return this.sinopsis;
	}
	
	public Date getFechaEstreno(){
		return this.a�o_estreno;
	}
	
	public Date getFechaFin(){
		return this.a�o_fin;
	}
	
	public void setIdSerie(Integer id){
		this.id_serie = id;
	}
	
	public void setNombreSerie(String name){
		this.nombre = name;
	}
	
	public void setTitular(String title){
		this.titular = title;
	}
	
	public void setSinopsis(String sin){
		this.sinopsis = sin;
	}
	
	public void setFechaEst(Date est){
		this.a�o_estreno = est;
	}
	
	public void setFechaFin(Date fin){
		this.a�o_fin = fin;
	}
	
	@Override
	public String toString(){
		return this.nombre + " " +this.tiempoEmision(a�o_estreno) + "-" + this.tiempoEmision(a�o_fin);
	}
	
	public String tiempoEmision(Date f){
		Calendar cal = Calendar.getInstance();
		if (f != null){
			cal.setTime(f);
			int year = cal.get(Calendar.YEAR);
			return Integer.toString(year);
		}
		else return "?";
	}

}
