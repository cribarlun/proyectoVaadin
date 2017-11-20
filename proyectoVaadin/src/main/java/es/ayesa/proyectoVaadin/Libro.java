package es.ayesa.proyectoVaadin;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Libro implements Serializable, Cloneable {

	private Long id;
	private String titulo;
	private String autor;
	private LibroEstado estado;
	private Cliente cliente;// a quien esta prestado el libro

	public Libro clone() throws CloneNotSupportedException {
		return (Libro) super.clone();

	}

	public int hashcode() {
		int hash = 5;
		hash = 43 * hash + (id == null ? 0 : id.hashCode());// si tiene id le suma el hash code del id
		return hash;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (this.id == null) {
			return false;
		}

		if (obj instanceof Libro && obj.getClass().equals(getClass())) {
			return this.id.equals(((Libro) obj).id);
		}
		return false;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public LibroEstado getEstado() {
		return estado;
	}

	public void setEstado(LibroEstado estado) {
		this.estado = estado;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}
