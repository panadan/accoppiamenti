
public class Verga implements Cloneable{
	private String nome;
	private Float lunghezza;
	private Integer qta;
	private Float costo;
		
	public Verga(Float lunghezza, Integer qta, Float costo) {
		super();
		this.nome = lunghezza.toString();
		this.lunghezza = lunghezza;
		this.qta = qta;
		this.costo = costo;
	}
	
	public Verga() {
		super();
		this.nome = "spezzone";
		this.lunghezza = new Float(0);
		this.qta = new Integer(0);
		this.costo = new Float(0);
	}

	public Verga(String nome, Float lunghezza, Integer qta) {
		super();
		this.nome = nome;
		this.lunghezza = lunghezza;
		this.qta = qta;
		this.costo = new Float(0);
	}

	public Float getLunghezza() {
		return lunghezza;
	}

	public void setLunghezza(Float lunghezza) {
		this.lunghezza = lunghezza;
	}

	public Integer getQta() {
		return qta;
	}

	public void setQta(Integer qta) {
		this.qta = qta;
	}

	public Float getCosto() {
		return costo;
	}

	public void setCosto(Float costo) {
		this.costo = costo;
	}

	public String getNome() {
		return nome;
	}

	@Override
	public String toString() {
//		return "Verga [lunghezza=" + lunghezza + ", qta=" + qta + ", costo="
//				+ costo + "]";
		//tolto il costo adesso non gestito
		return nome+"(" + lunghezza + "*" + qta + ")" ;
	}

	@Override
	protected Verga clone() {
		return new Verga(nome, lunghezza, qta);
	};
	
	
}
