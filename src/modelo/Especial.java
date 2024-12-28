package modelo;

public class Especial extends Conta {
	private double limite;

	public Especial(int id,String data, double saldo, double limite) {
		super(id,data,saldo);
		this.limite = limite;
	}

	public void debitar(double valor) {
		saldo=saldo-valor;
	}
	
	@Override
	public String toString() {
		String texto =  "id=" + id + ", data=" + data + ", saldo=" + saldo + ",limite="+limite;
		
		texto += ", correntistas:";
		for(Correntista c : getCorrentistas())
			texto += c.getCpf() + ",";
		return texto;
	}

	public double getLimite() {
		return limite;
	}

	public void setLimite(double limite) {
		this.limite = limite;
	}	
}
