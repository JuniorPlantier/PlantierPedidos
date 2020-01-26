package com.plantier.pedidos;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.plantier.pedidos.domain.Categoria;
import com.plantier.pedidos.domain.Cidade;
import com.plantier.pedidos.domain.Cliente;
import com.plantier.pedidos.domain.Endereco;
import com.plantier.pedidos.domain.Estado;
import com.plantier.pedidos.domain.Pagamento;
import com.plantier.pedidos.domain.PagamentoComBoleto;
import com.plantier.pedidos.domain.PagamentoComCartao;
import com.plantier.pedidos.domain.Pedido;
import com.plantier.pedidos.domain.Produto;
import com.plantier.pedidos.domain.enums.EstadoPagamento;
import com.plantier.pedidos.domain.enums.TipoCliente;
import com.plantier.pedidos.repositories.CategoriaRepository;
import com.plantier.pedidos.repositories.CidadeRepository;
import com.plantier.pedidos.repositories.ClienteRepository;
import com.plantier.pedidos.repositories.EnderecoRepository;
import com.plantier.pedidos.repositories.EstadoRepository;
import com.plantier.pedidos.repositories.PagamentoRepository;
import com.plantier.pedidos.repositories.PedidoRepository;
import com.plantier.pedidos.repositories.ProdutoRepository;

@SpringBootApplication
public class PlantierPedidosApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(PlantierPedidosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		// Adiciona as categorias a lista de produtos 
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		// Os produtos precisam conhecer sua categoria
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		// ###########
		Estado est1 = new Estado(null, "Minas Gerias");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlâncida", est1);
		Cidade c2 = new Cidade(null, "Bauru", est2);
		Cidade c3 = new Cidade(null, "São Paulo", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		// ###########
		Cliente cli1 = new Cliente(null, "Rogério Ceni", "ceni@spfc.com.br", "12346789", TipoCliente.PESSOAJURIDICA);
		cli1.getTelefones().addAll(Arrays.asList("32145847", "45698985"));
		
		Endereco e1 = new Endereco(null, "Av. Morumbi", "444", "Apto 712", "Morumbi", "123-546", cli1, c3);
		Endereco e2 = new Endereco(null, "Av. Matos", "548", "Casa 8", "Vila Sabrinca", "54545-56", cli1, c1);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		// ###########
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
		
		Pedido ped1 = new Pedido(null, sdf.parse("25/01/2020 23:18"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("02/03/2020 18:20"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("04/02/2020 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
	}	

}
