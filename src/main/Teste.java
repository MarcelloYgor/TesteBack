package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import dao.CustomerDAO;
import model.Customer;

public class Teste {

	private CustomerDAO custDAO = new CustomerDAO();

	public static void main(String[] args) {
		Teste teste = new Teste();
		teste.insert();
		teste.select();
	}

	public void insert() {
		List<Customer> listCustomer = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			Customer customer = new Customer();
			customer.setCpfCnpj(getSaltString());
			customer.setNome(getSaltString());
			customer.setActive(true);
			customer.setVlTotal(ThreadLocalRandom.current().nextInt(1, 5000 + 1));
			listCustomer.add(customer);
		}
		custDAO.insertCustomer(listCustomer);
	}

	public void select() {
		double media = 0;
		int c = 0;
		List<Customer> listCustomer = custDAO.selectCustomer();
		List<Customer> listAux = new ArrayList<>();
		for (Customer customer : listCustomer) {
			if ((customer.getVlTotal() > 560) && (customer.getId() > 1500 && customer.getId() < 2700)) {
				listAux.add(customer);
				media = media + customer.getVlTotal();
				c++;
			}
		}
		//Ordenação dos clientes por valor total
		Collections.sort(listAux, new Comparator() {
			public int compare(Object o1, Object o2) {
				Customer c1 = (Customer) o1;
				Customer c2 = (Customer) o2;
				return c1.getVlTotal() < c2.getVlTotal() ? -1 : (c1.getVlTotal() > c2.getVlTotal() ? +1 : 0);
			}
		});
		for (Customer customer : listAux) {
			System.out.println("Id: " + customer.getId() + ", Customer: " + customer.getNome() + ", Valor total: " + customer.getVlTotal());
		}
		media = media / c;
		System.out.println("Média final de valores: " + media);
	}
	
	// Gera String randomicamente
	protected String getSaltString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 15) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;
	}
}
