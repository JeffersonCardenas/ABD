package tables;

public class EpisodioVisto {
	private String nick;
	private Integer id_serie;
	private Integer num_epidodio;
	private Integer num_temporada;
	
	public EpisodioVisto(String n,Integer id,Integer num_epi,Integer num_temp){
		this.nick = n;
		this.id_serie = id;
		this.num_epidodio = num_epi;
		this.num_temporada = num_temp;
	}
	
	public String getNick(){
		return this.nick;
	}
	
	public Integer getIdSerie(){
		return this.id_serie;
	}
	
	public Integer getNumEpisodio(){
		return this.num_epidodio;
	}
	
	public Integer getNumTemporada(){
		return this.num_temporada;
	}
	
	public void setNick(String n){
		this.nick = n;
	}
	
	public void setIdSerie(Integer id){
		this.id_serie = id;
	}
	
	public void setNumEpisodio(Integer nEpi){
		this.num_epidodio = nEpi;
	}
	
	public void setNumTemporada(Integer nTemp){
		this.num_temporada = nTemp;
	}
	
	@Override
	public String toString(){
		return "Has visto el episodio "+this.getNumEpisodio();
	}

}
