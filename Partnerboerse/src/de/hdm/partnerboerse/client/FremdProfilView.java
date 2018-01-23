package de.hdm.partnerboerse.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Profil;

public class FremdProfilView extends VerticalPanel {

	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);

	Button merkButton = new Button("Profil merken");
	Button sperrButton = new Button("Profil sperren");

	String vnameLabel = new String("Vorname: ");
	String lnameLabel = new String("Nachname: ");
	String bdayLabel = new String("Geburtstag: ");
	String hcolorLabel = new String("Haarfarbe: ");
	String heightLabel = new String("Größe (in cm): ");
	String smokerLabel = new String("Raucher: ");
	String religionLabel = new String("Religion: ");

	@Override
	public void onLoad() {

		loadProfileFromServer();

		merkButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				partnerAdmin.getProfilByID(1, new AsyncCallback<Profil>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Daten wurden nicht geladen!");

					}

					@Override
					public void onSuccess(Profil result) {
						Window.alert("Muss noch implementiert werden");
					}

				});
			}
		});
		sperrButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				partnerAdmin.getProfilByID(1, new AsyncCallback<Profil>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Daten wurden nicht geladen!");

					}

					@Override
					public void onSuccess(Profil result) {
						Window.alert("Muss noch implementiert werden");
					}

				});

			}
		});
	}

	private void loadProfileFromServer() {

		partnerAdmin.getProfilByID(1, new AsyncCallback<Profil>() {

			@Override
			public void onFailure(Throwable caught) {

				Window.alert("Daten wurden nicht geladen!");

			}

			@Override
			public void onSuccess(Profil result) {

				updateProfilTable(result);

			}

		});

	}

	// ToDo: Parameter muss umgeändert werden von "Profil result" in Google+
	// Email später
	// sobald Login da ist.
	private void updateProfilTable(Profil result) {
		Profil fremdProfil = result;

		Grid profilIntGrid = new Grid(2, 3);
		profilIntGrid.setStyleName("itable");
		this.add(profilIntGrid);

		profilIntGrid.setWidget(0, 0, merkButton);
		profilIntGrid.setWidget(0, 1, sperrButton);

		Grid profilGrid = new Grid(7, 6);
		profilGrid.setStyleName("etable");
		this.add(profilGrid);

		// Spalte 1
		profilGrid.setText(1, 1, vnameLabel);
		profilGrid.setText(1, 2, fremdProfil.getVorname());

		profilGrid.setText(1, 3, lnameLabel);
		profilGrid.setText(1, 4, fremdProfil.getNachname());

		// Spalte 2
		profilGrid.setText(2, 1, bdayLabel);
		profilGrid.setText(2, 2, fremdProfil.getGeburtsdatum().toString());

		profilGrid.setText(2, 3, hcolorLabel);
		profilGrid.setText(2, 4, fremdProfil.getHaarfarbe());

		// Spalte 3
		profilGrid.setText(3, 1, heightLabel);
		profilGrid.setText(3, 2, String.valueOf(fremdProfil.getKoerpergroesse()));

		profilGrid.setText(3, 3, smokerLabel);
		profilGrid.setText(3, 4, GuiUtils.getJaNein(fremdProfil.isRaucher()));

		// Spalte 4
		profilGrid.setText(4, 1, religionLabel);
		profilGrid.setText(4, 2, fremdProfil.getReligion());

	}

}
