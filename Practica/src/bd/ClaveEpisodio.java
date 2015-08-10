package bd;

public class ClaveEpisodio {
	private Integer claveEpisodio;
	private Integer num_episodio;
	private Integer num_temporada;
	
	public ClaveEpisodio(Integer key,Integer num,Integer temp){
		this.claveEpisodio = key;
		this.num_episodio = num;
		this.num_temporada = temp;
	}
	
	public Integer getClave(){
		return this.claveEpisodio;
	}
	
	public Integer getNumEpisodio(){
		return this.num_episodio;
	}
	
	public Integer getNumTemporada(){
		return this.num_temporada;
	}

}
