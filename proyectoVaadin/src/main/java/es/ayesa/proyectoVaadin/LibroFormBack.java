package es.ayesa.proyectoVaadin;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction.KeyCode;

@SuppressWarnings("serial")
public class LibroFormBack extends LibroForm {

	private LibroService libroService = LibroService.getInstancia();
	private Binder<Libro> binder = new Binder<>(Libro.class);
	private Libro libro;
	private VistaLibro vistaLibro;

	public LibroFormBack(VistaLibro vistaLibro) {
		this.vistaLibro = vistaLibro;

		guardar.setClickShortcut(KeyCode.ENTER); // pulsar este boton'guardar' es lo mismo que darle a enter
		cliente.setItems(ClienteService.getInstancia().findAll());

		binder.bindInstanceFields(this);// saca los datos de esta clase(this) y como hereda de libroForm, se trae
										// todos los datos

		guardar.addClickListener(e -> this.guardar());
		borrar.addClickListener(e -> this.borrar());

	}

	public void setLibro(Libro libro) {
		this.libro = libro;
		binder.setBean(libro);
		borrar.setVisible(libro.getId() != null);
		setVisible(true);
		titulo.selectAll();
	}

	private void guardar() {
		libroService.guardar(libro);
		vistaLibro.actualizarTabla();
		setVisible(false);
	}

	private void borrar() {
		libroService.borrar(libro);
		vistaLibro.actualizarTabla();
		setVisible(false);
	}


}
