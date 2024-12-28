package modelo;

import java.util.ArrayList;

public class Correntista {
	private String nome;
	private String cpf;
	private String senha;
	private ArrayList<Conta> contas= new ArrayList<>();

	public Correntista(String cpf,String nome,String senha) {
		super();
		this.cpf = cpf;
		this.nome= nome;
		this.senha =senha;
	}
	
	public void adicionar(Conta c){
		contas.add(c);
	}
	
	public void remover(Conta c){
		contas.remove(c);
	}
	
	public Conta localizar(int id){
		for(Conta c : contas){
			if(c.getId()==id) {
				return c;
			}
		}
		return null;
	}
		
	@Override
	public String toString() {
		String texto= "correntista[cpf=" + cpf + ", nome=" + nome+ ", senha=" +senha + "]";
		
		texto += "Contas:";
		for(Conta c : contas)
			texto += c.getId() + ",";
		return texto;
	}

	public double getSaldoTotal() {
		double total = 0;
		for(Conta c : contas) {
			total += c.getSaldo();
		}
		return total;
	}
	
	public ArrayList<Conta> getContasporData(String data) {
		ArrayList<Conta> lista = new ArrayList<>();

		for(Conta c : contas) {
			if (c.getData().equals(data))
				lista.add(c);
		}
		return lista;
	}
	
	public int contarContasEspeciais() {
		int cont=0;
		for(Conta c : contas) {
			if (c instanceof Especial)
				cont++;
		}
		return cont;
	}

	public ArrayList<Especial> getContasEspeciais() {
		ArrayList<Especial> especiais = new ArrayList<>();
		for(Conta c: contas) {
			if (c instanceof Especial e)
				especiais.add(e);
		}
		return especiais;
	}

	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf= cpf;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public ArrayList<Conta> getContas() {
		return contas;
	}
}
