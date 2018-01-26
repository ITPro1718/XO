package de.hdm.partnerboerse.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Profil;
import de.hdm.partnerboerse.shared.bo.Suchprofil;

public class ListViewSuchProfil extends VerticalPanel {

	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);

	Profil profil = ClientSideSettings.getProfil();

	Button createButton = new Button("erstellen");
	FlexTable splistGrid = new FlexTable();

	/**
	 * Aufbau Suchprofilliste der Anzeige des Suchprofils
	 */

	@Override
	public void onLoad() {
		HTML splist = new HTML("<h3>" + "Suchprofilliste" + "</h3>");
		splist.addStyleName("spwrap");

		splistGrid.setStyleName("sptable");
		this.add(splistGrid);

		splistGrid.setWidget(0, 0, createButton);
		/**
		 * TODO CSS einbinden
		 */
		splistGrid.setText(1, 0, "Name des Suchprofils");

		loadSuchprofileFromServer();

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

	}

	// ToDo: Methode muss geändert
	private void loadSuchprofileFromServer() {

		partnerAdmin.findSuchprofileOf(profil, new AsyncCallback<ArrayList<Suchprofil>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Suchprofile konnten nicht geladen werden");

			}

			@Override
			public void onSuccess(ArrayList<Suchprofil> result) {
				loadListViewSuchProfile(result);
				// Window.alert(result.toString());

			}
		});
	}

	private void loadListViewSuchProfile(ArrayList<Suchprofil> result) {

		for (final Suchprofil sp : result) {

			Button showButton = new Button("anzeigen");
			Button searchButton = new Button("suchen");
			int row = splistGrid.getRowCount();

			splistGrid.setText(row, 0, sp.getTitle());
			splistGrid.setWidget(row, 1, showButton);
			splistGrid.setWidget(row, 2, searchButton);

			final Suchprofil search = sp;

			showButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {

				  ClientSideSettings.setSuchprofil(sp);
					SuchprofilView spv = new SuchprofilView();

					HTMLPanel spvPanel = new HTMLPanel("<h3>" + "Das ist ihr Suchprofil" + "</h3>");
					spvPanel.add(spv);
					spv.setNewSuchprofil(search);
					RootPanel.get("contwrap").clear();
					RootPanel.get("contwrap").add(spvPanel);

				}
			});

		}

	}

}
