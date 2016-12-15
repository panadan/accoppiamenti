import java.util.ArrayList;


public class Soluzione implements Comparable{
	private ArrayList<Problema> passaggi = new ArrayList<Problema>();
	private Float sfrido;
	private Float costo;
	private Integer cambiVerga;
	
	public Float getSfrido() {
		return sfrido;
	}

	public Float getCosto() {
		return costo;
	}

	public Integer getCambiVerga() {
		return cambiVerga;
	}

	private void creaPassaggi(Problema p) {
		if(p == null) return; 
	
		creaPassaggi(p.getPadre());
		passaggi.add(p);
		
	}
	
//	public Soluzione(Problema p) {
//		super();
//		creaPassaggi(p);
//		sfrido = p.getSfridoPadre();
//		cambiVerga = p.getCambiVerga();
//		costo = p.getSfridoPadre() + p.getCambiVerga();
//		
//	}

	public Soluzione(Problema p, Float costoM, Float costoPiazzamento) {
		super();
		creaPassaggi(p);
		sfrido = p.getSfridoPadre();
		cambiVerga = p.getCambiVerga();
		costo = p.getSfridoPadre() * costoM/1000 + p.getCambiVerga() * costoPiazzamento;
	}

	@Override
	public int compareTo(Object o) {
		return costo.compareTo(((Soluzione)o).getCosto());
		//return 0;
	}

	@Override
	public String toString() {
		String text = "";
		
		//text += "   Sfrido: " + sfrido + ", cambiVerga: " + cambiVerga + "\n";
		//text += "Problema";
		
		for(int iPass=0; iPass < passaggi.size(); iPass++){
			Problema p = passaggi.get(iPass);
			
			if(iPass > 0)
				text += " Passaggio " + iPass;
			
			if(p.getAccoppiamento() != null){
				text += "\n";
				text += p.getAccoppiamento().toString();
				
			}
			text += "\n";
			text += p.toString();
			text += "\n";
		}
		
		return text;
	}

	public ArrayList<Problema> getPassaggi() {
		return passaggi;
	}
	
	
	
	
}
