
public class Barcode implements Cloneable{
	private String nome;
	private float qttb;
	private int qta;
	
	public Barcode(String nome, float qttb, int qta) {
		super();
		this.nome = nome;
		this.qttb = qttb;
		this.qta = qta;
	}

	public Barcode() {
		super();
		nome = "vuoto";
		this.qttb = 0;
		this.qta = 0;
	}

	public float getQttb() {
		// TODO Auto-generated method stub
		return qttb;
	}

	public String getNome() {
		return nome;
	}

	public int getQta() {
		return qta;
	}

	public void setQta(int qta) {
		this.qta = qta;
		
	}

	
	public String toString2() {
		/*return "Barcode [nome=" + nome + ", qttb=" + qttb + ", qta=" + qta
				+ "]";*/
		return nome + "(" + qttb + "*" + qta + ")";
		
	}

	@Override
	public String toString() {
		/*return "Barcode [nome=" + nome + ", qttb=" + qttb + ", qta=" + qta
				+ "]";*/
		return nome + " x"  + qta;
		
	}

	@Override
	protected Barcode clone() {
		return new Barcode(nome, qttb, qta);
	};
	
	
}
