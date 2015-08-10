package bd;

import mappers.*;
import javax.sql.DataSource;
import java.sql.Date;
import java.util.List;
import tables.*;

public class Controlador {
	
	private SeriesMapper modeloSerie;
	private UsuarioMapper modeloUsuario;
	private EpisodioMapper modeloEpisodio;
	private SigueSerieMapper modeloSeguidor;
	private EpisodioVistoMapper modeloVistoEpisodio;
	
	public Controlador(DataSource ds){
		modeloSerie = new SeriesMapper(ds);
		modeloUsuario = new UsuarioMapper(ds);
		modeloEpisodio = new EpisodioMapper(ds);
		modeloSeguidor = new SigueSerieMapper(ds);
		modeloVistoEpisodio = new EpisodioVistoMapper(ds);
	}
	
	//Metodos del usuario mapper
	public Usuario existeUsuario(String idUser) {
		// TODO Auto-generated method stub
		return modeloUsuario.findById(idUser);
	}

	public int altaUsuarioNuevo(String idUser, String pass, String f) {
		// TODO Auto-generated method stub
		return modeloUsuario.nuevoUsuario(idUser, pass , f);
	}

	public int actualizaFoto(Usuario u,byte[] im) {
		// TODO Auto-generated method stub
		int resul = 0;
		u.setFoto(im);
		resul = this.modeloUsuario.update(u);
		return resul;
	}

	public int modificaDatosUsuario(Usuario u,String p,String f) {
		// TODO Auto-generated method stub
		if (!p.equals(""))	u.setPass(p);
		if (!f.equals("") && (f.length() == 10 || f.length() == 8) )	u.setFechaNac(Date.valueOf(f));
		return this.modeloUsuario.update(u);
	}
	
	//Metodos del serie mapper
	public List<Serie> buscaSerie(String nombre){
		return modeloSerie.findByName(nombre);
	}

	public int actualizaSerie(Serie s, String titular, String sinop,String fechaIni, String fechaFin) {
		// TODO Auto-generated method stub
		if (!titular.equals(""))	s.setTitular(titular);
		if (!sinop.equals(""))	s.setSinopsis(sinop);
		if (!fechaIni.equals("") && (fechaIni.length() == 10 || fechaIni.length() == 8) ) s.setFechaEst(Date.valueOf(fechaIni));
		if (!fechaFin.equals("") && (fechaFin.length() == 10 || fechaFin.length() == 8) ) s.setFechaFin(Date.valueOf(fechaFin));
		else s.setFechaFin(null);
		return this.modeloSerie.update(s);
	}

	public int insertaSerie(String name,String t,String sin,String est,String fin) {
		// TODO Auto-generated method stub
		return this.modeloSerie.insertaSerie(name,t,sin,est,fin);
	}

	public List<Serie> buscaSerieSeguidas(String nickUsuario) {
		// TODO Auto-generated method stub
		return this.modeloSerie.listaSeriesSeguidas(nickUsuario);
	}
	
	//Metodos del seguidor mapper
	public boolean sigueSerie(Serie serie,Usuario us) {
		// TODO Auto-generated method stub
		return modeloSeguidor.creaRelacion(new Seguidor(us.getNick(),serie.getIdSerie()));
	}
	
	//Metodos del episodio visto mapper
	public int marcaEpisodioVisto(Episodio aux, String nick) {
		// TODO Auto-generated method stub
		return modeloVistoEpisodio.insertaRelacion(aux,nick);
	}
	
	//Metodos del episodio mapper
	public List<Episodio> dameEpisodios(Integer idSerie) {
		// TODO Auto-generated method stub
		return modeloEpisodio.getListaEpisodios(idSerie);
	}

	public List<Episodio> dameEpisodiosNoVistos(Integer idSerie,String name) {
		// TODO Auto-generated method stub
		return modeloEpisodio.getEpisodiosNoVistos(idSerie,name);
	}

	public int borraEpisodio(Episodio episodio) {
		// TODO Auto-generated method stub
		return this.modeloEpisodio.delete(episodio);
	}

	public int actualizaEpisodio(Episodio aux, String title, String sinop,String fecha) {
		// TODO Auto-generated method stub
		if (!title.equals("")) aux.setNombreCap(title);
		if (!sinop.equals("")) aux.setSinopsis(sinop);
		if (!fecha.equals("")) aux.setFecha(Date.valueOf(fecha));
		return this.modeloEpisodio.update(aux);
	}

	public int insertaEpisodio(Integer idSerie, String numEpi, String numTemp,
			String nombreC, String sinopC, String fEst) {
		// TODO Auto-generated method stub
		return this.modeloEpisodio.insertaEpisodio(idSerie,numEpi,numTemp,nombreC,sinopC,fEst);
	}

}
