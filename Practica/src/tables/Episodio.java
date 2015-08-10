package tables;

import java.sql.Date;

public class Episodio {
	private Integer id_serie;
	private Integer num_episodio;
	private Integer num_temporada;
	private String nombre_cap;
	private String sinopsis_cap;
	private Date fecha_est;
	
	public Episodio(Integer id,Integer epi,Integer temp,String name,String sin,Date f){
		this.id_serie = id;
		this.num_episodio = epi;
		this.num_temporada = temp;
		this.nombre_cap = name;
		this.sinopsis_cap = sin;
		this.fecha_est = f;
	}
	
	
	public Integer getIdSerie(){
		return this.id_serie;
	}
	
	public Integer getNumEpisodio(){
		return this.num_episodio;
	}
	
	public Integer getNumTemporada(){
		return this.num_temporada;
	}
	
	public String getNombreCap(){
		return this.nombre_cap;
	}
	
	public String getSinopsis(){
		return this.sinopsis_cap;
	}
	
	public Date getFechaEst(){
		return this.fecha_est;
	}
	
	
	public void setIdSerie(Integer id){
		this.id_serie = id;
	}
	
	public void setNumEpisodio(Integer epi){
		this.num_episodio = epi;
	}
	
	public void setNumTemporada(Integer temp){
		this.num_temporada = temp;
	}
	
	public void setNombreCap(String name){
		this.nombre_cap = name;
	}
	
	public void setSinopsis(String sin){
		this.sinopsis_cap = sin;
	}
	
	public void setFecha(Date date){
		this.fecha_est = date;
	}
	
	
	@Override
	public String toString(){
		return "Episodio [id=" + this.id_serie 
				+ "Numero de Episodio=" + this.num_episodio 
				+ "Numero de Temporada=" + this.num_temporada
				+ "Nombre del Capitulo=" + this.nombre_cap
				+ "Sinopsis=" + this.sinopsis_cap + "Fecha de Estreno=" + this.fecha_est 
				+ "]";		
	}

}
