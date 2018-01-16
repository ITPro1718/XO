package de.hdm.partnerboerse.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;

public class ListViewSuchProfil extends VerticalPanel {

	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);

	Button createButton = new Button("Suchprofil erstellen");
	Button deleteButton = new Button("Suchprofil löschen");
	Button editButton = new Button("Suchprofil bearbeiten");
	Button searchButton = new Button("suchen, aufgehts!");

	/**
	 * Aufbau Suchprofilliste mit Bearbeiten, Löschen und Suchenfunktion
	 */

	@Override
	public void onLoad() {
		HTML splist = new HTML("<h3>" + "Suchprofilliste" + "</h3>");
		/**
		 * TODO in CSS einbinden
		 */
		splist.addStyleName("spwrap");

		FlexTable splistGrid = new FlexTable();
		
		/**
		 * TODO in CSS einbinden
		 */
		splistGrid.setStyleName("sptable");
		this.add(splistGrid);

		// Zeile 1

		splistGrid.setWidget(0, 0, createButton);

		// Zeile 2
		splistGrid.setText(1, 0, "Name des Suchprofils");
		splistGrid.setWidget(1, 1, editButton);
		splistGrid.setWidget(1, 2, deleteButton);
		splistGrid.setWidget(1, 3, searchButton);

		/**
		 * Button zum Erstellen eines Suchprofils
		 */
		createButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				loadCreateSuchprofilView();

			}

			private void loadCreateSuchprofilView() {

				CreateSuchprofil spl = new CreateSuchprofil();

				HTMLPanel createsuchprofilViewPanel = new HTMLPanel(
						"<h3>" + "Hier können sie ein Suchprofil erstellen!" + "</h3>");
				createsuchprofilViewPanel.add(spl);

				RootPanel.get("contwrap").clear();
				RootPanel.get("contwrap").add(createsuchprofilViewPanel);

			}
		});

		/**
		 * TODO Button zum löschen der Suchprofile
		 */
		deleteButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				loadCreateSuchprofilView();

			}

			private void loadCreateSuchprofilView() {

				CreateSuchprofil spl = new CreateSuchprofil();

				HTMLPanel createsuchprofilViewPanel = new HTMLPanel(
						"<h3>" + "Hier können sie ein Suchprofil erstellen!" + "</h3>");
				createsuchprofilViewPanel.add(spl);

				RootPanel.get("contwrap").clear();
				RootPanel.get("contwrap").add(createsuchprofilViewPanel);

			}
		});
		
		/**
		 * TODO Button zum Suchen nach den Fremdprofilen
		 */
		searchButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				loadCreateSuchprofilView();

			}

			private void loadCreateSuchprofilView() {

				CreateSuchprofil spl = new CreateSuchprofil();

				HTMLPanel createsuchprofilViewPanel = new HTMLPanel(
						"<h3>" + "Hier können sie ein Suchprofil erstellen!" + "</h3>");
				createsuchprofilViewPanel.add(spl);

				RootPanel.get("contwrap").clear();
				RootPanel.get("contwrap").add(createsuchprofilViewPanel);

			}
		});

	}
}
