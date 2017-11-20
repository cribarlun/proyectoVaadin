package es.ayesa.proyectoVaadin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class LibroService {

	private static LibroService instancia; // crea una instancia
	private final Map<Long, Libro> contactos = new HashMap<>(); // simula una base de datos
	private long siguienteId = 0;// Contador de los id que generamos

	private LibroService() {
	} // constructor privado para que nadie instancia nuestra clase

	public static LibroService getInstancia() {
		if (instancia == null) {// simula un singleton
			instancia = new LibroService();
			instancia.generarDatos();
		}
		return instancia;
	}

	private void generarDatos() {
		if (findAll().isEmpty()) {
			final String[] nombres = new String[] { "Libro1, Autor1", "Libro2, Autor2", "Libro3, Autor3",
					"Libro4, Autor4", "Libro5, Autor5", "Libro6, Autor6", "Libro7, Autor7", "Libro8, Autor8" };
			Random random = new Random(0);
			List<Cliente> clientes = new ArrayList<>();

			clientes = ClienteService.getInstancia().findAll();

			for (String nombreCompleto : nombres) {
				String[] separados = nombreCompleto.split(",");
				Libro libro = new Libro();
				libro.setTitulo(separados[0]);
				libro.setAutor(separados[1]);

				libro.setEstado(LibroEstado.values()[random.nextInt(LibroEstado.values().length)]);// elige un
																									// estado
				// aleatorio
				// entre 0 y
				// el numero
				// de
				// estados
				// que hay

				if (libro.getEstado().equals(LibroEstado.No_Disponible)) {
					libro.setCliente(clientes.get(random.nextInt(clientes.size())));
				}
				guardar(libro);
			}
		}
	}

	public void guardar(Libro libro) {
		if (libro == null) {
			return;
		} else {
			if (libro.getId() == null) {
				libro.setId(siguienteId++);
			}
		}

		try {
			libro = libro.clone();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		contactos.put(libro.getId(), libro);
		if (libro.getCliente() == null) {
			libro.setEstado(LibroEstado.Disponible);
		} else {
			libro.setEstado(LibroEstado.No_Disponible);
		}

	}

	public List<Libro> findAll() {
		return findAll(null);
	}

	public List<Libro> findAll(String cadena) {

		return contactos.values().stream().filter(contacto -> {
			return (cadena == null || cadena.isEmpty())
					|| (contacto.getTitulo().toLowerCase().contains(cadena.toLowerCase())
							|| contacto.getAutor().toLowerCase().contains(cadena.toLowerCase()));
		}).collect(Collectors.toList());
	}

	public long count() {
		return contactos.size();
	}

	public void borrar(Libro libro) {
		contactos.remove(libro.getId());
	}

	public void prestar(Libro libro, Cliente cliente) {
		libro.setCliente(cliente);
		libro.setEstado(LibroEstado.No_Disponible);

	}

}
