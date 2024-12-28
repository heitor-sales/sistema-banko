package modelo;

import java.util.ArrayList;

public class Conta { 
	protected int id;
	protected String data;
	protected double saldo;
	private ArrayList<Correntista> correntistas = new ArrayList<>();

	public Conta(int id, String data, double saldo) {
		super();
		this.id=id;
		this.data=data;
		this.saldo=saldo;
	}

	public void adicionar(Correntista c){
		correntistas.add(c);
	}
	
	public void remover(Correntista c){
		correntistas.remove(c);
	}
	
	public Correntista localizar(String cpf){
		for(Correntista c : correntistas){
			if(c.getCpf().equals(cpf))
				return c;
		}
		return null;
	}
	
	public boolean verificarTitular(String cpf) {
	    //verifica se a lista de correntistas não está vazia e se o primeiro correntista é o titular
	    if (!correntistas.isEmpty() && correntistas.get(0).getCpf().equals(cpf))
	        return true; //retorna true se o correntista é o titular da conta
	    return false; //retorna false se o correntista não é o titular da conta
	}
	
	public void creditar(double valor) {
		saldo=saldo+valor;
	}
	
	public void debitar(double valor) {
		saldo=saldo-valor;
	}
	
	public void transferir(double valor, Conta destino) {
		this.debitar(valor);
		destino.creditar(valor);
	}

	public double getSaldo() {
		return saldo;
	}

	@Override
	public String toString() {
		String texto =  "id=" + id + ", data=" + data + ",saldo=" + saldo;
		
		texto += ", correntistas:";
		for(Correntista c : correntistas)
			texto += c.getCpf() + ",";
		return texto;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<Correntista> getCorrentistas() {
		return correntistas;
	}

}
