package regras_negocio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

import modelo.Conta;
import modelo.Correntista;
import modelo.Especial;
import repositorio.Repositorio;

public class Fachada {
	private Fachada() {}		
	private static Repositorio repositorio = new Repositorio();	
	
	public static ArrayList<Correntista> listarCorrentistas() 	{
		//lista todos os correntistas
	    ArrayList<Correntista> correntistas = repositorio.getCorrentistas();
	    
	    //ordena a lista pelo CPF
	    correntistas.sort(Comparator.comparing(Correntista::getCpf));
	    
	    return correntistas;
	}
	
	public static ArrayList<Conta> listarContas() {
		//lista todos as contas
		return repositorio.getContas();
	}
	
	/*public static Correntista localizarCorrentista(String cpf) {
		return repositorio.localizarCorrentista(cpf);
	}
	
	public static Conta localizarConta(int id) 	{
		return repositorio.localizarConta(id);
	}*/
	
	public static void criarCorrentista(String cpf, String nome, String senha) throws Exception {
		cpf = cpf.trim();
		nome = nome.trim();
		senha = senha.trim();

		//localizar correntista no repositorio, usando o cpf 
		Correntista c = repositorio.localizarCorrentista(cpf);
		if (c!=null)
			throw new Exception("criar correntista: " + cpf + " ja cadastrado(a).");
		
		//verificação da senha: precisa ser numérica e ter exatamente 4 dígitos
	    if (!senha.matches("\\d{4}"))
	        throw new Exception("criar correntista: a senha deve ser numérica e ter exatamente 4 dígitos.");

		//criar objeto Correntista
		c = new Correntista (cpf, nome, senha);

		//adicionar correntista no repositório
		repositorio.adicionar(c);
		//gravar repositório
		repositorio.salvarObjetos();
	}	
	
	public static void criarConta (String cpf) throws Exception {
		cpf = cpf.trim();

		//localizar correntista no repositório, usando o cpf
		Correntista co = repositorio.localizarCorrentista(cpf);
	    if (co == null)
	        throw new Exception("criar conta: correntista com CPF " + cpf + " não encontrado.");
	    
	    //verifica se o correntista já está associado a uma conta
	    Conta c = repositorio.localizarContaPorCorrentista(cpf);
	    if (c != null)
	        throw new Exception("criar conta: correntista com CPF " + cpf + " já possui uma conta associada.");

		//gerar id no repositório
		int id = repositorio.gerarIdConta();
		 
		//obter data atual e formatá-la como dia/mês/ano
	    LocalDate hoje = LocalDate.now();
	    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    String data = hoje.format(formato);
		
	    double saldo = 0;
	    
		Conta cont = new Conta(id, data, saldo);	
		
		//adicionar correntista a conta
		cont.adicionar(co);
		//adicionar conta ao correntista
		co.adicionar(cont);
		//adicionar conta no repositório
		repositorio.adicionar(cont);
		//gravar repositório
		repositorio.salvarObjetos();
	}
	
	public static void criarContaEspecial (String cpf, double limite) throws Exception {
		cpf = cpf.trim();

		//localizar correntista no repositório, usando o cpf
		Correntista co = repositorio.localizarCorrentista(cpf);
	    if (co == null)
	        throw new Exception("criar conta especial: correntista com CPF " + cpf + " não encontrado.");
	    
	    //verifica se o correntista já está associado a uma conta
	    Conta c = repositorio.localizarContaPorCorrentista(cpf);
	    if (c != null)
	        throw new Exception("criar conta especial: correntista com CPF " + cpf + " já possui uma conta associada.");

	    if (limite < 50)
	    	throw new Exception ("criar conta especial: o limite da conta precisa ser maior ou igual a R$ 50,00.");
	    
		//gerar id no repositório
		int id = repositorio.gerarIdConta();
		 
		//obter data atual e formatá-la como dia/mês/ano
	    LocalDate hoje = LocalDate.now();
	    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    String data = hoje.format(formato);
	    
	    double saldo = 0;
		
		Especial e = new Especial(id, data, saldo, limite);
		
		//adicionar correntista a conta especial
		e.adicionar(co);
		//adicionar conta especial ao correntista
		co.adicionar(e);
		//adicionar conta no repositório
		repositorio.adicionar(e);
		//gravar repositório
		repositorio.salvarObjetos();
	}

	public static void 	inserirCorrentistaConta(int id, String cpf) throws Exception {
		cpf = cpf.trim();
		
		//localizar correntista no repositório, usando o cpf
		Correntista co = repositorio.localizarCorrentista(cpf);
		if (co == null)
			throw new Exception("inserir correntista conta: correntista com CPF " + cpf + " não encontrado.");
		
		//localiza a conta pelo ID
		Conta conta = repositorio.localizarConta(id);
        if (conta == null)
            throw new Exception("inserir correntista conta: conta com ID " + id + " não encontrada.");

		//verifica se o correntista já é titular ou cotitular da conta
        Correntista c = conta.localizar(cpf);
		if(c != null) 
			throw new Exception("inserir correntista conta: correntista com CPF " + cpf + " já é titular ou cotitular desta conta.");
        
	    //adicionar correntista a conta
	    conta.adicionar(co);
	  	//adicionar conta ao correntista
	  	co.adicionar(conta);
		//gravar repositório
		repositorio.salvarObjetos();
	}

	public static void 	removerCorrentistaConta(int id, String cpf) throws Exception {
		cpf = cpf.trim();

		//localizar correntista no repositório, usando o cpf
		Correntista co = repositorio.localizarCorrentista(cpf);
		if (co == null)
			throw new Exception("remover correntista conta: correntista com CPF " + cpf + " não encontrado.");
		
		//localiza a conta pelo ID
		Conta conta = repositorio.localizarConta(id);
		if (conta == null)
			throw new Exception("remover correntista conta: conta com ID " + id + " não encontrada.");
		
		//verifica se o correntista já é titular ou cotitular da conta
        Correntista c = conta.localizar(cpf);
		if(c == null) 
			throw new Exception("remover correntista conta: correntista com CPF " + cpf + " não é titular ou cotitular da conta " + id + ".");
		
		//verificar se o correntista é titular da conta
		if(conta.verificarTitular(cpf))
			throw new Exception("remover correntista conta: não é possível remover o titular da conta " + id + ".");

		//desassociar conta de correntista
		co.remover(conta);
		//desassociar correntista de conta
		conta.remover(co);
		//gravar repositório
		repositorio.salvarObjetos();
	}

	public static void apagarConta(int id) throws Exception	{
		//localizar conta no repositorio, usando id 
		Conta c = repositorio.localizarConta(id);
		if (c == null)
			throw new Exception("apagar conta: conta " + id + " inexistente.");
		
		if(c.getSaldo() > 0)
			throw new Exception("apagar conta: conta " + id + " não pode ser apaga, porque o saldo é maior que R$ 0,00.");

		//remover todos os correntistas desta conta
		for(Correntista co : c.getCorrentistas()) {
			co.remover(c);
		}
		c.getCorrentistas().clear();
		
		//remover conta do repositório
		repositorio.remover(c);
		//gravar repositório
		repositorio.salvarObjetos();
	}
	
	public static void creditarValor(int id, String cpf, String senha, double valor) throws Exception {
		cpf = cpf.trim();
		senha = senha.trim();
		
		//verificar se o valor é válido
	    if (valor <= 0)
	        throw new Exception("creditar valor: valor deve ser maior que zero.");

	    //localizar o correntista no repositório, usando o CPF
	    Correntista correntista = repositorio.localizarCorrentista(cpf);
	    if (correntista == null)
	        throw new Exception("creditar valor: correntista com CPF " + cpf + " não encontrado.");
	    
	    //verificar se a senha do correntista é válida
	    if (!correntista.getSenha().equals(senha))
	        throw new Exception("creditar valor: senha incorreta para o CPF " + cpf + ".");

	    //localizar a conta pelo ID
	    Conta conta = repositorio.localizarConta(id);
	    if (conta == null)
	        throw new Exception("creditar valor: conta com ID " + id + " não encontrada.");
	    
	    //verificar se o correntista é titular ou cotitular da conta
	    Correntista associado = conta.localizar(cpf);
	    if (associado == null)
	        throw new Exception("creditar valor: correntista com CPF " + cpf + " não é titular ou cotitular da conta " + id + ".");
	    
	    //creditar o valor na conta
	    conta.creditar(valor);
	    
	    //gravar repositório
	    repositorio.salvarObjetos();
	}

	public static void debitarValor(int id, String cpf, String senha, double valor) throws Exception {
		cpf = cpf.trim();
		senha = senha.trim();
		
	    //verificar se o valor é válido
	    if (valor <= 0)
	        throw new Exception("debitar valor: valor deve ser maior que zero.");
	    
	    //localizar o correntista no repositório, usando o CPF
	    Correntista correntista = repositorio.localizarCorrentista(cpf);
	    if (correntista == null)
	        throw new Exception("debitar valor: correntista com CPF " + cpf + " não encontrado.");
	    
	    //verificar se a senha do correntista é válida
	    if (!correntista.getSenha().equals(senha))
	        throw new Exception("debitar valor: senha incorreta para o CPF " + cpf + ".");

	    //localizar a conta pelo ID
	    Conta conta = repositorio.localizarConta(id);
	    if (conta == null)
	        throw new Exception("debitar valor: conta com ID " + id + " não encontrada.");
	    
	    //verificar se o correntista é titular ou cotitular da conta
	    Correntista associado = conta.localizar(cpf);
	    if (associado == null)
	        throw new Exception("debitar valor: correntista com CPF " + cpf + " não é titular ou cotitular da conta " + id + ".");
	    
	    //verificar se há saldo suficiente
	    //double saldoAtual = conta.getSaldo();
	    //double novoSaldo = saldoAtual - valor;
	    double novoSaldo = conta.getSaldo() - valor;
	    
	    if (novoSaldo < 0) {
	        if (conta instanceof Especial) {
	            //para conta epecial, verifica o limite
	            Especial especial = (Especial) conta;
	            if (novoSaldo < -especial.getLimite()) {
	                throw new Exception("debitar valor: saldo insuficiente na conta" + id + ". O saldo não pode ser inferior ao limite da conta especial.");
	            }
	        } else {
	            //para contas simlpes, o saldo não pode ficar negativo
	            throw new Exception("debitar valor: saldo insuficiente na conta " + id + ".");
	        }
	    }

	    //debitar o valor da conta
	    conta.debitar(valor);
	    
	    //gravar repositório
	    repositorio.salvarObjetos();
	}
	
	public static void transferirValor(int id1, String cpf, String senha, double valor, int id2) throws Exception {
		cpf = cpf.trim();
		senha = senha.trim();
		
		//verificar se o valor é válido
	    if (valor <= 0)
	        throw new Exception("transferir valor: o valor da transferência deve ser maior que zero.");

	    //localizar o correntista no repositório, usando o CPF
	    Correntista correntista = repositorio.localizarCorrentista(cpf);
	    if (correntista == null)
	        throw new Exception("transferir valor: correntista com CPF " + cpf + " não encontrado.");
	    
	    //verificar se a senha do correntista é válida
	    if (!correntista.getSenha().equals(senha))
	        throw new Exception("transferir valor: senha incorreta para o CPF " + cpf + ".");
	    
	    //localizar a conta de origem (id1)
	    Conta contaOrigem = repositorio.localizarConta(id1);
	    if (contaOrigem == null)
	        throw new Exception("transferir valor: conta de origem com ID " + id1 + " não encontrada.");
	    
	    //verificar se o correntista é titular ou cotitular da conta de origem
	    Correntista associadoOrigem = contaOrigem.localizar(cpf);
	    if (associadoOrigem == null)
	        throw new Exception("transferir valor: correntista com CPF " + cpf + " não é titular ou cotitular da conta de origem " + id1 + ".");
	    
	    //verificar se há saldo suficiente na conta de origem
	    //double saldoAtual = contaOrigem.getSaldo();
	    //double novoSaldo = saldoAtual - valor;
	    double novoSaldo = contaOrigem.getSaldo() - valor;

	    if (novoSaldo < 0) {
	        if (contaOrigem instanceof Especial) {
	            //para conta epecial, verifica o limite
	            Especial especial = (Especial) contaOrigem;
	            if (novoSaldo < -especial.getLimite()) {
	                throw new Exception("debitar valor: saldo insuficiente na conta" + id1 + ". O saldo não pode ser inferior ao limite da conta especial.");
	            }
	        } else {
	            //para contas simlpes, o saldo não pode ficar negativo
	            throw new Exception("debitar valor: saldo insuficiente na conta " + id1 + ".");
	        }
	    }
	    /*if (contaOrigem.getSaldo() < valor)
	        throw new Exception("transferir valor: saldo insuficiente na conta de origem " + id1 + ".");*/
	    
	    //localizar a conta de destino (id2)
	    Conta contaDestino = repositorio.localizarConta(id2);
	    if (contaDestino == null)
	        throw new Exception("transferir valor: conta de destino com ID " + id2 + " não encontrada.");

	    //realizar a transferência
	    contaOrigem.debitar(valor); //debitar da conta de origem
	    contaDestino.creditar(valor); //creditar na conta de destino
	    
	    //gravar repositório
	    repositorio.salvarObjetos();
	}
}
