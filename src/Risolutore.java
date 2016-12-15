import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Risolutore {

	private ArrayList<Soluzione> soluzioni;
	private Problema pOrigine;
	private Problema p;
	//private int iterazione;
	private Float costoPaizzamenti;
	private Float costoMetro;
	private Float offsetPinza;
	private Float offsetTaglio;
	private double maxSfrido;
	private long startTime;
	private long endTime;
	
	public static void main(String[] args) {

		//test
		ArrayList<Barcode> barcodes = new ArrayList<Barcode>();
		barcodes.add(new Barcode("primo", 1750, 22));
		barcodes.add(new Barcode("secondo", 2250, 13));
		barcodes.add(new Barcode("terzo", 1250, 31));
		barcodes.add(new Barcode("quarto", 1050, 20));
		barcodes.add(new Barcode("quinto", 1253, 60));
		barcodes.add(new Barcode("sesto", 1153, 160));
		//barcodes.add(new Barcode("settimo", 1103, 110));
		ArrayList<Verga> verghe = new ArrayList<Verga>();
		verghe.add(new Verga("v1",new Float(6000),Integer.MAX_VALUE));
		verghe.add(new Verga("v2",new Float(5300),Integer.MAX_VALUE));
		//verghe.add(new Verga("v3",new Float(6800),Integer.MAX_VALUE));
		//verghe.add(new Verga("v4",new Float(5000),Integer.MAX_VALUE));
		//verghe.add(new Verga("v5",new Float(5500),Integer.MAX_VALUE));
		//verghe.add(new Verga("v6",new Float(7000),Integer.MAX_VALUE));
		
		Float offsetPinza = new Float(32);
		Float offsetTaglio = new Float(2);
		Float costoMetro = new Float(2);
		Float costoPiazzamento = new Float(5);
		
		Risolutore r = new Risolutore(new Problema(barcodes,verghe), offsetPinza, offsetTaglio, costoMetro, costoPiazzamento);
		//r.generaTest();
		r.risolvi();
		
		//r.test();
	}

	public void risolvi() {
		startTime = System.currentTimeMillis();
		creaLimiteSfrindo(0.3);
		generaCombinazioni(p);
		Collections.sort(soluzioni);
		endTime = System.currentTimeMillis();
		stampaSoluzioni(10);
			
	}

	private void creaLimiteSfrindo(double d) {
		maxSfrido=0;
		for(Barcode b : p.getBarcodes()){
			maxSfrido+=b.getQta()*b.getQttb()*d;
		}
		
	}

	public Risolutore(Problema problema, Float offsetPinza, Float offsetTaglio, Float costoMetro, Float costoPaizzamenti ) {
		this.costoPaizzamenti = costoPaizzamenti;
		this.costoMetro = costoMetro;
		this.offsetPinza = offsetPinza;
		this.offsetTaglio = offsetTaglio;
		pOrigine = problema;
		p = generaProblemaRimodellato();
		soluzioni = new ArrayList<Soluzione>();

	}

	private Problema generaProblemaRimodellato() {
		ArrayList<Barcode> barcodes = new ArrayList<Barcode>();
		for(Barcode b : pOrigine.getBarcodes())
			barcodes.add(new Barcode(b.getNome(),b.getQttb()+offsetTaglio,b.getQta()));

		ArrayList<Verga> verghe = new ArrayList<Verga>();
		for(Verga v : pOrigine.getVerghe())
			verghe.add(new Verga(v.getLunghezza().toString(), v.getLunghezza()-offsetPinza,v.getQta(),v.getCosto()));
		
		p = new Problema(barcodes,verghe);
		
		return p;
	}
	
	private void generaCombinazioni(Problema p){
		//taglio un po di soluzioni per velocizzare 
		if(p.getSfridoPadre()>maxSfrido) return;
		
		ArrayList<Barcode> barcodes = p.getBarcodes();
		ArrayList<Verga> verghe = p.getVerghe();

		//stampaDebugP(p);

		
		//se c'è uno spezzone imposto quello come unica verga
		if(p.getSpezzoneInMacchina() != null){
			verghe = new ArrayList<Verga>(1);
			verghe.add(p.getSpezzoneInMacchina());
		
		//gestisco lo scarico completo dello spezzone

		Problema pFiglio2;
		//questo costruttore scarica lo spezzone
		
		//System.out.println("Scarico Spezzone");

		pFiglio2 = new Problema(p);
		if(pFiglio2.getBarcodes().isEmpty())
			salvaSoluzione(pFiglio2);
		else
			generaCombinazioni(pFiglio2);
		
		
		}

		//--System.out.println("start");
		
		ArrayList<Accoppiamento> accoppiamenti = new ArrayList<Accoppiamento>();
		//accoppiamenti = new ArrayList<Accoppiamento>();
		
		
		for(Verga v : verghe){
			//combinaBarcode(barcodes, v);
			
			for(Barcode bX : barcodes){
				//non cercare di combinare barcode più lunghi della verga 
				if(bX.getQttb() > v.getLunghezza()) continue;
				
				//se c'è un barcode in macchina deve usare quello
				if(p.getBarcodeXInMacchina() != null && !p.getBarcodeXInMacchina().getNome().equals(bX.getNome())) 
					continue; 
				
				//accoppiamento su se stesso
				//System.out.println("Accoppiamento su se stesso");
				//stampaDebugP(p);
				//System.out.println(bX);
				//System.out.println(v);
				Accoppiamento a1 = new Accoppiamento(v, bX); 
				//stampaDebugA(a1);
				Problema pFiglio1;
				pFiglio1 = new Problema(p, a1);

				if(pFiglio1.getBarcodes().isEmpty())
					//stampaSoluzione(pFiglio);
					salvaSoluzione(pFiglio1);
				else
					generaCombinazioni(pFiglio1);
				
				for(Barcode bY : barcodes){
					//se c'è un barcode in macchina deve usare quello
					if(p.getBarcodeYInMacchina() != null && !p.getBarcodeYInMacchina().getNome().equals(bY.getNome())) 
						continue; 
					
					//se è lo stesso barcode non lo accoppio
					if(bX.getNome().equals(bY.getNome()))	continue;

					accoppiamenti = new ArrayList<Accoppiamento>();
					accoppiamenti.addAll(generaAccoppiamenti(v,bX,bY));

					for(Accoppiamento a : accoppiamenti){
						Problema pFiglio;
						//System.out.println("Accoppiamento std");
						//stampaDebugA(a);
						pFiglio = new Problema(p, a);

						//--System.out.println(a.toString());
						//--System.out.println(pFiglio.toString());
						
						if(pFiglio.getBarcodes().isEmpty())
							//stampaSoluzione(pFiglio);
							salvaSoluzione(pFiglio);
						else
							generaCombinazioni(pFiglio);
					}
				}
			}

		}
	}

	private ArrayList<Accoppiamento> generaAccoppiamenti(Verga verga, Barcode bX, Barcode bY) {
		Float qttbX = bX.getQttb();
		Float qttbY = bY.getQttb();
		Float v = verga.getLunghezza();
		
		int mX;
		//quanti ce ne stanno in una verga
		mX = (int)(v / qttbX);
		int mY;
		//quanti ce ne stanno in una verga
		mY = (int)(v / qttbY);
		
		ArrayList<Accoppiamento> accoppiamenti = new ArrayList<Accoppiamento>();
		
		Float sfrido= new Float(0);
		for(int iX=mX; iX>0; iX--){
			for(int iY=1; iY <=mY; iY++){
				sfrido = new Float(v - (iX*qttbX + iY*qttbY));
				//verifica se è un accoppiamento valido
				if(sfrido < 0 )
					break;//inutile continuare
				if(sfrido < qttbX && sfrido < qttbY){
					if (qttbX != qttbY || iY != iX+1) //evita accoppiamenti doppioni che possono capitare se qttb uguali (es: 1100*3,1100*2 == 1100*2,1100*3) 
							accoppiamenti.add(new Accoppiamento(verga, bX, bY, iX, iY));
				}
			}
		}
		return accoppiamenti;
		
	}

	private void salvaSoluzione(Problema p) {
		soluzioni.add(new Soluzione(p, costoMetro, costoPaizzamenti));
		if (soluzioni.size()>10){
			Collections.sort(soluzioni);
			soluzioni.remove(10);
			maxSfrido = soluzioni.get(9).getSfrido();
		}
	
			
	}

	
	private void stampaSoluzione(Problema p) {
		if(p == null) return; 
		
		stampaSoluzione(p.getPadre());
		System.out.println(p.getAccoppiamento());
		System.out.println(p);	
		
	}

	public String stampaSoluzioni(int numero) {
		String soluzioneText="";
		
		//soluzioneText="Problema Origine \n";
		
		//System.out.println("Problema Origine \n" + pOrigine);
		
		
		int iSol=0;
		for(Soluzione s : soluzioni){
			if (iSol >= numero) break;
			iSol++;
			soluzioneText+="   SOLUZIONE " + iSol + " Sfrido: " + s.getSfrido()/1000 + " Mt (costo Metro "+ costoMetro +")"
					+ " Piazzamenti: " + s.getCambiVerga() + " (costo cad: " + costoPaizzamenti + ")"
							+ " Totale Costo: " + s.getCosto() ;
			
			System.out.println("   SOLUZIONE " + iSol + " Sfrido: " + s.getSfrido()/1000 + " Mt (costo Metro "+ costoMetro +")"
					+ " Piazzamenti: " + s.getCambiVerga() + " (costo cad: " + costoPaizzamenti + ")"
							+ " Totale Costo: " + s.getCosto()) ;
			
			soluzioneText+=s;
			System.out.println(s);
		}
		System.out.println("secondi = "+(endTime - startTime)/1000);
		
		return soluzioneText;
		
	}

	public ArrayList<Soluzione> getSoluzioni() {
		// TODO Auto-generated method stub
		return soluzioni;
	}

	public long getStartTime() {
		return startTime;
	}

	public long getEndTime() {
		// TODO Auto-generated method stub
		return endTime;
	}

//	private void stampaDebug(Problema p, Accoppiamento a){
//		System.out.println(iterazione++);
//		System.out.println(p);	
//		System.out.println(a);	
//		
//	}
//
//	private void stampaDebugP(Problema p){
//		System.out.println(iterazione++);
//		System.out.println(p);	
//		
//	}
//
//	private void stampaDebugA(Accoppiamento a){
//		System.out.println(iterazione++);
//		System.out.println(a);	
//		
//	}

//	private void generaTest() {
//		ArrayList<Barcode> barcodes = new ArrayList<Barcode>();
//		barcodes.add(new Barcode("primo", 1750, 22));
//		barcodes.add(new Barcode("secondo", 2250, 13));
//		barcodes.add(new Barcode("terzo", 1250, 31));
//		barcodes.add(new Barcode("quarto", 1050, 20));
//		ArrayList<Verga> verghe = new ArrayList<Verga>();
//		verghe.add(new Verga(new Float(6000),Integer.MAX_VALUE,new Float(1)));
//		//verghe.add(new Verga(new Float(6000),15,new Float(1)));
//		verghe.add(new Verga(new Float(5300),Integer.MAX_VALUE,new Float(1)));
//		verghe.add(new Verga(new Float(6800),Integer.MAX_VALUE,new Float(1)));
//		p = new Problema(barcodes,verghe);
//	}
//
//
//	private void test(){
//		ArrayList<Barcode> barcodes = new ArrayList<Barcode>();
//		barcodes.add(new Barcode("primo", 1750, 22));
//		barcodes.add(new Barcode("secondo", 2250, 13));
//		barcodes.add(new Barcode("terzo", 1250, 31));
//		barcodes.add(new Barcode("quarto", 1050, 20));
//		ArrayList<Verga> verghe = new ArrayList<Verga>();
//		verghe.add(new Verga(new Float(6000),Integer.MAX_VALUE,new Float(1)));
//		//verghe.add(new Verga(new Float(6000),15,new Float(1)));
//		verghe.add(new Verga(new Float(5300),Integer.MAX_VALUE,new Float(1)));
//		verghe.add(new Verga(new Float(6800),Integer.MAX_VALUE,new Float(1)));
//		generaProblema(barcodes,verghe);
//	}
//		
//	private void generaProblema(ArrayList<Barcode> barcodes,
//		ArrayList<Verga> verghe) {
//
//		p = new Problema(barcodes,verghe);
//		soluzioni = new ArrayList<Soluzione>();
//		
//		generaCombinazioni(p);
//		
//		Collections.sort(soluzioni);
//		stampaSoluzioni(10);
//	}
//	private void combinaBarcode(ArrayList<Barcode> barcodes, Verga v){
//	for(Barcode bX : barcodes){
//		//se c'è un barcode in macchina deve usare quello
//		if(p.getBarcodeXInMacchina() != null && !p.getBarcodeXInMacchina().getNome().equals(bX.getNome())) 
//			continue; 
//		
//		//accoppiamento su se stesso
//		Accoppiamento a1 = new Accoppiamento(v, bX); 
//		Problema pFiglio1;
//		pFiglio1 = new Problema(p, a1);
//
//		if(pFiglio1.getBarcodes().isEmpty())
//			//stampaSoluzione(pFiglio);
//			salvaSoluzione(pFiglio1);
//		else
//			generaCombinazioni(pFiglio1);
//		
//		for(Barcode bY : barcodes){
//			//se c'è un barcode in macchina deve usare quello
//			if(p.getBarcodeYInMacchina() != null && !p.getBarcodeYInMacchina().getNome().equals(bY.getNome())) 
//				continue; 
//			
//			//se è lo stesso barcode non lo accoppio
//			if(bX.getNome().equals(bY.getNome()))	continue;
//
//			accoppiamenti = new ArrayList<Accoppiamento>();
//			accoppiamenti.addAll(generaAccoppiamenti(v,bX,bY));
//
//			for(Accoppiamento a : accoppiamenti){
//				Problema pFiglio;
//				pFiglio = new Problema(p, a);
//
//				//--System.out.println(a.toString());
//				//--System.out.println(pFiglio.toString());
//				
//				if(pFiglio.getBarcodes().isEmpty())
//					//stampaSoluzione(pFiglio);
//					salvaSoluzione(pFiglio);
//				else
//					generaCombinazioni(pFiglio);
//			}
//		}
//	}
//	
//}

	
}
