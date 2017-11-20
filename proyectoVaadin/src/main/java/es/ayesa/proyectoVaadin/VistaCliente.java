package es.ayesa.proyectoVaadin;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings({ "serial", "deprecation" })
public class VistaCliente extends VerticalLayout implements View {

	private ClienteService clienteService = ClienteService.getInstancia();
	private Grid<Cliente> grid = new Grid<>(Cliente.class); // una tabla
	final TextField filterText = new TextField();
	private ClienteFormBack form = new ClienteFormBack(this);
	private Navigator navigator;
	public static final String NAME = "clientes";

	public VistaCliente(Navigator navigator) {
		this.navigator = navigator;

		form.setVisible(false);

		grid.setSelectionMode(SelectionMode.SINGLE);// solo puede seleccionar un elemento de la tabla a la vez
		grid.setColumns("nombre", "apellido", "email");// que atributos del cliente queremos que muestre. tienen que
														// coincidir con los nombres de las propiedades del cliente
		grid.setSizeFull();

		actualizarTabla();// llenamos la tabla

		Button borrarFiltro = new Button(FontAwesome.TIMES);// este botón va a tener un icono. el que se le ha pasado
		borrarFiltro.setDescription("Borrar filtro");// se muestra cuando se pasa el ratón encima del botón
		borrarFiltro.addClickListener(e -> filterText.clear());// que evento salta cuando se hace click en el botón

		filterText.setPlaceholder("Filtrar por nombre:");
		filterText.addValueChangeListener(e -> actualizarTabla());// lo que queremos que haga cuando actualiza el valor
		filterText.setValueChangeMode(ValueChangeMode.LAZY);// si pasa un tiempo sin que cambie el valor, lanza el
															// evento.

		CssLayout filtrado = new CssLayout();
		filtrado.addComponents(filterText, borrarFiltro);
		filtrado.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);// un tema para el layout. agrupa los componentes que se
																// han añadido
		HorizontalLayout main = new HorizontalLayout();
		main.addComponents(grid, form);
		main.setSizeFull();// que ocupe todo el espacio que pueda en la pantalla
		form.setSizeFull();
		main.setExpandRatio(grid, 2);// Divide la pantalla en tres partes. dos partes son para el grid
		main.setExpandRatio(form, 1);// Divide la pantalla en tres partes. una parte es para el grid

		grid.asSingleSelect().addValueChangeListener(evento -> {
			if (evento.getValue() == null) {// si no hay fila seleccionada el formulario es invisible
				form.setVisible(false);
			} else {
				form.setCliente(evento.getValue());// si se selecciona una fila se llama a esta función
			}
		});

		HorizontalLayout botonera = new HorizontalLayout();
		Button nuevoCliente = new Button("Nuevo cliente");
		nuevoCliente.addClickListener(evento -> {
			grid.asSingleSelect().clear();
			form.setCliente(new Cliente());
		});

		Button navegar = new Button("Prueba");
		navegar.addClickListener(event -> this.navigator.navigateTo(VistaPrueba.NAME));

		botonera.addComponents(filtrado, nuevoCliente, navegar);

		addComponents(botonera, main);

	}

	public void actualizarTabla() {
		grid.setItems(clienteService.findAll(filterText.getValue()));

	}

}
