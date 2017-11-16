package es.ayesa.proyectoVaadin;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.Grid.SelectionMode;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of an HTML page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {
	private ClienteService clienteService = ClienteService.getInstancia();
	private Grid<Cliente> grid = new Grid<>(Cliente.class); // una tabla
	final TextField filterText = new TextField();

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		final VerticalLayout layout = new VerticalLayout();

		grid.setSelectionMode(SelectionMode.SINGLE);// solo puede seleccionar un elemento de la tabla a la vez
		grid.setColumns("nombre", "apellido", "email");// que atributos del cliente queremos que muestre. tienen que
														// coincidir con los nombres de las propiedades del cliente

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

		layout.addComponents(filtrado, grid);

		setContent(layout);
	}

	private void actualizarTabla() {
		grid.setItems(clienteService.findAll(filterText.getValue()));

	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}
}
