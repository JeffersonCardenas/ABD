package bd;

public enum Operator {
	EQ, LE, LT, GE, GT, NEQ, LIKE;
	
	/*
	 * Sería conveniente añadir un atributo a cada enum con la representación
	 * de cada operador (en forma de cadena)
	 */
	
	@Override
	public String toString() {
		String resul = new String("");
		switch(this) {
		      case EQ: resul = "="; break;
		      case LE: resul = "<="; break;
		      case LT: resul = "<"; break;
		      case GE: resul = ">="; break;
		      case GT: resul = ">"; break;
		      case NEQ: resul = "<>"; break;
		      case LIKE : resul = "LIKE"; break;
		    }
		return resul;		  
	}

}
