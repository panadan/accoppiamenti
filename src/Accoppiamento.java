
public class Accoppiamento {
	
	private Verga verga;
	private Barcode barcodeX;
	private Barcode barcodeY;
	private int mulX;
	private int mulY;
	private int totQtaX;
	private int totQtaY;
	private float sfridoSingolo;
	private int vergheIntere;
	private Verga ultimoSpezzone;


	public Accoppiamento(Verga verga, Barcode barcodeX, Barcode barcodeY,
			int mulX, int mulY) {
		super();
		this.verga = verga;
		this.barcodeX = barcodeX;
		this.barcodeY = barcodeY;
		this.mulX = mulX;
		this.mulY = mulY;
		if(barcodeY.getQta() == 0)
			calcoloParametriUtilizzoSingol();
		else
			calcoloParametriUtilizzo();
		
	}


	@Override
	public String toString() {
		/*return "Accoppiamento [verga=" + verga + ", barcodeX=" + barcodeX
				+ ", barcodeY=" + barcodeY + ", mulX=" + mulX + ", mulY="
				+ mulY + ", totQtaX=" + totQtaX + ", totQtaY=" + totQtaY
				+ ", sfridoSingolo=" + sfridoSingolo + ", vergheIntere="
				+ vergheIntere + ", ultimoSpezzone=" + ultimoSpezzone + "]";
				*/
		return "Accopp. [v=" + verga + ", bcX=" + barcodeX
		+ ", bcY=" + barcodeY + ", mulX=" + mulX + ", mulY="
		+ mulY + ", totQtaX=" + totQtaX + ", totQtaY=" + totQtaY
		+ ", sfridoSingolo=" + sfridoSingolo + ", vergheIntere="
		+ vergheIntere + ", ultimoSpezzone=" + ultimoSpezzone + "]";
		
	}


	public Accoppiamento(Verga v, Barcode bX) {
		this(v, bX, new Barcode(), (int)(v.getLunghezza()/bX.getQttb()), 1);
	}

	public Verga getVerga() {
		return verga;
	}

	public Barcode getBarcodeX() {
		return barcodeX;
	}

	public Barcode getBarcodeY() {
		return barcodeY;
	}

	public int getMulX() {
		return mulX;
	}

	public int getMulY() {
		return mulY;
	}

	public float getSfridoSingolo(){
		return sfridoSingolo;
	}
	
	public float getSfido(){
		return sfridoSingolo * vergheIntere;
	}
	
	private void calcoloParametriUtilizzoSingol() {
		sfridoSingolo = verga.getLunghezza() - (barcodeX.getQttb() * mulX);
		
		//controlla se non ci sono abbastanza verghe
		int vergheNecessarie = (int)Math.ceil(barcodeX.getQta()/mulX);
		
		//se non ce ne sono abbastanza
		if(verga.getQta() > 0 && vergheNecessarie > verga.getQta()){
			vergheIntere = verga.getQta();
			totQtaX = vergheIntere * mulX;
			ultimoSpezzone = new Verga();
		}
		//altrimenti
		else{
			vergheIntere = (int)(barcodeX.getQta()/mulX);
			int restoX = barcodeX.getQta() % mulX;
			
			totQtaX = barcodeX.getQta(); 
			//calcola l'ultimo spezzone
			Float lunghezzaUltimoSpezzone = (verga.getLunghezza() - (restoX * barcodeX.getQttb())) % verga.getLunghezza();
			ultimoSpezzone = new Verga("Spezzone",lunghezzaUltimoSpezzone,new Integer(1));
		}			
		
		totQtaY = 0;

	}
	
	
	private void calcoloParametriUtilizzo() {
		sfridoSingolo = verga.getLunghezza() - (barcodeX.getQttb() * mulX + barcodeY.getQttb() * mulY);
		
		int vergheNecessarie = Math.min((int)Math.ceil(barcodeX.getQta()/mulX),(int)Math.ceil(barcodeY.getQta()/mulY)) ;
		
		//se non ce ne sono abbastanza
		if(verga.getQta() > 0 && vergheNecessarie > verga.getQta()){
			vergheIntere = verga.getQta();
			totQtaX = vergheIntere * mulX;
			totQtaY = vergheIntere * mulY;
			ultimoSpezzone = new Verga();
		}
		
		
		//altrimenti
		else{
			vergheIntere = Math.min( (int)(barcodeX.getQta()/mulX),(int)(barcodeY.getQta()/mulY)) ;
			int differenzaXYInVerghe = (int)(barcodeX.getQta()/mulX) - (int)(barcodeY.getQta()/mulY);
	
			//la macchina si ferma quando ha finito la prima quantità
			//quindi sia che == 0 che minore 0 consideriamo TuttaQtaX
			int restoX = barcodeX.getQta() % mulX;
			int restoY = barcodeY.getQta() % mulY;
			
			Float lunghezzaUltimoSpezzone;
			
			if(differenzaXYInVerghe <= 0){
				totQtaX = barcodeX.getQta(); //la macchina si ferma quando conclude Bx, non incomincia la parte accoppiata By
				totQtaY = vergheIntere * mulY;
				//calcola l'ultimo spezzone (la macchina si è fermata perchè ha concluso qta1)
				lunghezzaUltimoSpezzone = (verga.getLunghezza() - (restoX * barcodeX.getQttb())) % verga.getLunghezza(); 
				  
			}
			else{
				totQtaY = barcodeY.getQta(); //la macchina si ferma quando conclude By, ha già fatto la parte accoppiata Bx
				totQtaX = (vergheIntere + 1) * mulX ;
				
				//calcola l'ultimo spezzone (la macchina si è fermata perchè ha concluso qta2 ma ha gia fatto tutta qta1)
				lunghezzaUltimoSpezzone = (verga.getLunghezza() - (restoY * barcodeY.getQttb()) - (mulX * barcodeX.getQttb())) % verga.getLunghezza()  ; 
			}
			
			ultimoSpezzone = new Verga(lunghezzaUltimoSpezzone,new Integer(1),new Float(verga.getCosto()));
		}		
	}

		
	//Ritorna il numero di verghe intere utilizzate
	public int getTotVergheInetere(){
		return vergheIntere;
	}

	public int getTotQtaX() {
		return totQtaX;
	}

	public int getTotQtaY() {
		return totQtaY;
	}

	public Verga getUltimoSpezzone() {
		return ultimoSpezzone;
	}
	
	public boolean isBarcodeXEsaurito(){
		if(totQtaX == barcodeX.getQta())	return true;
		return false;
	}

	public boolean isBarcodeYEsaurito(){
		if(totQtaY == barcodeY.getQta())	return true;
		return false;
	}

	public Integer vergheUtilizzate(){
		if(ultimoSpezzone.getLunghezza()>0)
			return 1+vergheIntere;
		else
			return vergheIntere;
	}
	
	public boolean isVergaEsaurita(){
		if(vergheUtilizzate() == verga.getQta())	return true;
		return false;
	}
	

}
