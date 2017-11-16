package es.ayesa.proyectoVaadin;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutAction.KeyCode;

@SuppressWarnings("serial")
public class ClienteFormBack extends ClienteForm {

	private ClienteService clienteService = ClienteService.getInstancia();
	private Binder<Cliente> binder = new Binder<>(Cliente.class);
	private Cliente cliente;
	private MyUI myUI;

	public ClienteFormBack(MyUI myui) {
		this.myUI = myui;

		guardar.setClickShortcut(KeyCode.ENTER); // pulsar este boton'guardar' es lo mismo que darle a enter
		estado.setItems(ClienteEstado.values());

		binder.bindInstanceFields(this);// saca los datos de esta clase(this) y como hereda de clienteForm, se trae
										// todos los datos

		guardar.addClickListener(e -> this.guardar());
		borrar.addClickListener(e -> this.borrar());

	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
		binder.setBean(cliente);
		borrar.setVisible(cliente.getId() != null);
		setVisible(true);
		nombre.selectAll();
	}

	private void guardar() {
		clienteService.guardar(cliente);
		myUI.actualizarTabla();
		setVisible(false);
	}

	private void borrar() {
		clienteService.borrar(cliente);
		myUI.actualizarTabla();
		setVisible(false);
	}
}
