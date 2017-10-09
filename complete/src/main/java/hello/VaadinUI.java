package hello;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.data.Binder;
import com.vaadin.server.*;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.Registration;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringUI
public class VaadinUI extends UI {

	private final CustomerRepository repo;

	private final CustomerEditor editor;
	
	//A grid component for displaying tabular data.
	final Grid<Customer> grid;

	final TextField filter;
	final ComboBox<String> select;

	private final Button addNewBtn;
	
	
	/*private final Button help;*/

	@Autowired
	public VaadinUI(CustomerRepository repo, CustomerEditor editor) {
		this.repo = repo;
		this.editor = editor;
		this.grid = new Grid<>(Customer.class);
		this.filter = new TextField();
		this.select= new ComboBox<String>();
		this.addNewBtn = new Button("New customer", FontAwesome.PLUS);
	}

	@Override
	protected void init(VaadinRequest request) {
		// build layout
		
	
		
		/*List<String> fields = new ArrayList<>();
		fields.add("firstName");
		fields.add("lastName");
		ComboBox<String> select=new ComboBox<>();
		select.setItems(fields);*/
		ComboBox<String> select = new ComboBox<>();
		select.setItems("firstName", "lastName");
		
		
		HorizontalLayout actions = new HorizontalLayout(select,filter, addNewBtn);
		
		
		//Opening window
		BrowserWindowOpener opener = new BrowserWindowOpener("https://github.com/riteshtiwary/VaadinSample/wiki");
		opener.setFeatures("height=300,width=600,resizable");
		opener.setWindowName("_blank");
		Button button = new Button("Help");
		opener.extend(button);
		actions.addComponent(button);
		
		VerticalLayout mainLayout = new VerticalLayout(actions, grid, editor);
		setContent(mainLayout);

		
		
		grid.setHeight(300, Unit.PIXELS);
		grid.setWidth(800, Unit.PIXELS);
		grid.setColumns("id", "firstName", "lastName", "age", "mobNo", "email");
		
		
		select.setPlaceholder("Select a field");
		filter.setPlaceholder("Filter by name");
 
		// Hook logic to components
		
		//select.addSelectionListener(e ->e.getSelectedItem());
		//Object selected=select.addSelectionListener(e ->e.getSelectedItem());
		
		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listCustomers(e.getValue()));
		//select.addValueChangeListener(event -> listCustomers(select.setValue(event.getValue())));
	

		// Connect selected Customer to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editCustomer(e.getValue());
		});

		// Instantiate and edit new Customer the new button is clicked
		addNewBtn.addClickListener(e -> editor.editCustomer(new Customer("", "","","","")));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listCustomers(filter.getValue());
		});

		// Initialize listing
		listCustomers(null);
	}

	// tag::listCustomers[]
	void listCustomers(String filterText) {
		
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(repo.findAll());
		}
		else {
			grid.setItems(repo.findByLastNameStartsWithIgnoreCase(filterText));
		}
	}
	// end::listCustomers[]

}
