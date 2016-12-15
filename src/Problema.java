import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Problema {
	private ArrayList<Barcode> barcodes = new ArrayList<Barcode>();
	private ArrayList<Verga> verghe;
	private Float sfridoPadre;
	private Verga vergaInMacchina;
	private Verga spezzoneInMacchina; //lo spezzone è una verga non completa
	private Barcode barcodeXInMacchina; 
	private Barcode barcodeYInMacchina;
	private Problema problemaPadre;
	private Accoppiamento accoppiamento;
	private Integer cambiVerga;
	
	public Problema(ArrayList<Barcode> barcodes, ArrayList<Verga> verghe) {
		super();
		this.barcodes = new ArrayList<Barcode>(barcodes.size());
		for(Barcode b :  barcodes)
			this.barcodes.add(b.clone()); 
		
		this.verghe = new ArrayList<Verga> (verghe.size());
		for (Verga v : verghe){
			this.verghe.add(v.clone());
		}
		sfridoPadre = new Float(0);
		vergaInMacchina = null;
		spezzoneInMacchina = null;
		barcodeXInMacchina = null;
		barcodeYInMacchina = null;
		problemaPadre = null;
		accoppiamento = null;
		cambiVerga = new Integer(0);
	}

	public Problema(Problema p, Accoppiamento a) {
		super();
		//barcodes = (ArrayList<Barcode>) p.getBarcodes().clone();
		barcodes = new ArrayList<Barcode>(p.getBarcodes().size());
		for(Barcode b :  p.getBarcodes())
			barcodes.add(b.clone()); 
		
		//verghe = new ArrayList<Long> (p.getVerghe());
		verghe = new ArrayList<Verga> (p.getVerghe().size());
		for (Verga v : p.getVerghe()){
			verghe.add(v.clone());
		}
		
		
		//vergaInMacchina = a.getVerga().clone();
		sfridoPadre =p.getSfridoPadre() + a.getSfrido();
		cambiVerga = p.getCambiVerga();
		barcodeXInMacchina = null;
		barcodeYInMacchina = null;
		problemaPadre = p;
		accoppiamento = a;

		if(a.isVergaEsaurita()){
			rimuoviVerga(a.getVerga());
			vergaInMacchina = null;
		}
		else{
			aggiornaVerga(a.getVerga(),a.vergheUtilizzate());
		}
			
		if(a.isBarcodeXEsaurito())
			rimuoviBarcode(a.getBarcodeX());
		else{
			barcodeXInMacchina = aggiornaBarcode(a.getBarcodeX(),a.getTotQtaX());
		}

		if(a.isBarcodeYEsaurito())
			rimuoviBarcode(a.getBarcodeY());
		else
			barcodeYInMacchina = aggiornaBarcode(a.getBarcodeY(),a.getTotQtaY());

		if(a.getUltimoSpezzone().getLunghezza()>0)
			spezzoneInMacchina = a.getUltimoSpezzone().clone();
		else 
			spezzoneInMacchina = null;
		
		//rimuovi verga utilizzata nel padre se diversa dalla verga utilizzata nel figlio 
		if(vergaInMacchina != null && p.getVergaInMacchina() != null && !vergaInMacchina.getNome().equals(p.getVergaInMacchina().getNome())){
			rimuoviVerga(p.getVergaInMacchina());
		}
		else if(p.getVergaInMacchina() == null) //calcola il carico verga se macchina vuota
			cambiVerga++;
		
		
	}

	//questo costruttore genera un sottoproblema uguale al padre mma mette lo spezzone nello sfrido
	public Problema(Problema p) {
		super();
		//barcodes = (ArrayList<Barcode>) p.getBarcodes().clone();
		barcodes = new ArrayList<Barcode>(p.getBarcodes().size());
		for(Barcode b :  p.getBarcodes())
			barcodes.add(b.clone()); 
		
		//verghe = new ArrayList<Long> (p.getVerghe());
		verghe = new ArrayList<Verga> (p.getVerghe().size());
		for (Verga v : p.getVerghe()){
			verghe.add(v.clone());
		}
		
		
		sfridoPadre =p.getSfridoPadre() + p.getSpezzoneInMacchina().getLunghezza();
		if(p.getVergaInMacchina() != null)
			vergaInMacchina = p.getVergaInMacchina().clone();
		else  
			vergaInMacchina = null;
		
		if(p.getBarcodeXInMacchina() != null)
			barcodeXInMacchina = p.getBarcodeXInMacchina().clone();
		else 
			barcodeXInMacchina = null;
		
		if(p.getBarcodeYInMacchina() != null)
			barcodeYInMacchina = p.getBarcodeYInMacchina();
		else 
			barcodeYInMacchina = null;

		problemaPadre = p;
		accoppiamento = null;
		spezzoneInMacchina = null;

		cambiVerga = p.getCambiVerga();
		
		
	}

	public Verga getSpezzoneInMacchina() {
		return spezzoneInMacchina;
	}

	public Integer getCambiVerga() {
		return cambiVerga;
	}

	public Float getSfridoPadre() {
		return sfridoPadre;
	}

	public void setVergaInMacchina(Verga vergaInMacchina) {
		this.vergaInMacchina = vergaInMacchina;
	}
	
	@Override
	public String toString() {
/*		return "Problema [barcodes=" + barcodes + ", verghe=" + verghe
				+ ", sfridoPadre=" + sfridoPadre + ", vergaInMacchina="
				+ vergaInMacchina + ", barcodeXInMacchina=" + barcodeXInMacchina
				+ ", barcodeYInMacchina=" + barcodeYInMacchina
				//+ ", problemaPadre=" + problemaPadre + ", accoppiamento="	+ accoppiamento 
				+ ", cambiVerga=" + cambiVerga + "]";
*/		
		return "Problema [bcodes=" + barcodes + ", verghe=" + verghe
				+ ", sfrPadre=" + sfridoPadre + ", vergaInMacchina="
				+ vergaInMacchina + ", bcXInMacch.=" + barcodeXInMacchina
				+ ", bcYInMacch.=" + barcodeYInMacchina
				//+ ", problemaPadre=" + problemaPadre + ", accoppiamento="	+ accoppiamento 
				+ ", cambiVerga=" + cambiVerga + "]";

	}

	private void aggiornaVerga(Verga verga, Integer vergheUtilizzate) {
		for(Verga v : verghe){
			if(v.getNome().equals(verga.getNome())){
				v.setQta(v.getQta() - vergheUtilizzate);
				vergaInMacchina = v;
				break;
			}
		}
		return ;
		
	}

	private Barcode aggiornaBarcode(Barcode barcode, int qta) {
		for(Barcode b : barcodes){
			if(b.getNome().equals(barcode.getNome())){
				b.setQta(b.getQta() - qta);
				return b; 
			}
		}
		return null;
	}

	private void rimuoviBarcode(Barcode barcode) {
		for(Barcode b : barcodes){
			if(b.getNome().equals(barcode.getNome())){
				barcodes.remove(b);
				break;
			}
		}
		return ;
	}


	private void rimuoviVerga(Verga verga) {
		for (Verga v : verghe){
			if(v.getNome().equals(verga.getNome())){
				verghe.remove(v);
				cambiVerga++;
				break;
			}
		}		
	}
	
	public Verga getVergaInMacchina() {
		return vergaInMacchina;
	}
	

	public Barcode getBarcodeXInMacchina() {
		return barcodeXInMacchina;
	}

	public Barcode getBarcodeYInMacchina() {
		return barcodeYInMacchina;
	}

	public ArrayList<Barcode> getBarcodes() {
		return barcodes;
	}

	public ArrayList<Verga> getVerghe() {
		return verghe;
	}


	public Problema getPadre() {
		return problemaPadre;
	}

	public Accoppiamento getAccoppiamento() {
		return accoppiamento;
	}

	
}
