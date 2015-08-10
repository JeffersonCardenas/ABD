package tables;

public class Seguidor {
	
	private String nick;
	private Integer id_serie;
	
	public Seguidor (String n,Integer id){
		this.nick = n;
		this.id_serie = id;
	}
	
	public String getNick(){
		return this.nick;
	}
	
	public Integer getIdSerie(){
		return this.id_serie;
	}
	
	public void setNick(String n){
		this.nick = n;
	}
	
	public void setIdSerie(Integer id){
		this.id_serie = id;
	}
	
	@Override
	public String toString(){
		return new String("El usuario:" + this.nick + "sigue la serie: " + this.id_serie);		
	}

}
