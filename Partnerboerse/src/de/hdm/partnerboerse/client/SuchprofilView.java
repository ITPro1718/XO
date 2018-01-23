package de.hdm.partnerboerse.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Profil;
import de.hdm.partnerboerse.shared.bo.Suchprofil;

public class SuchprofilView extends VerticalPanel {

	Suchprofil newsuchprofil = new Suchprofil();

	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);

	Profil profil = ClientSideSettings.getProfil();

	Button editButton = new Button("bearbeiten");
	Button deleteButton = new Button("löschen");
	String alterLabel = new String("Alter: ");
	String hcolorLabel = new String("Haarfarbe: ");
	String heightLabel = new String("Größe (in cm): ");
	String smokerLabel = new String("Raucher: ");
	String religionLabel = new String("Religion: ");

	/**
	 * TODO Wenn EigenschaftsModell steht Eigenschaften vom Suchprofil
	 * 
	 * Label sdescriptLab = new Label("Beschreibe dich kurz: "); Label hobbyLab
	 * = new Label("Deine Hobbies: "); Label musicLab = new Label("Deine
	 * Lieblings Musik: "); Label searchForLab = new Label("Du bist auf der
	 * Suche nach? "); Label sexOrientLab = new Label("Deine sexuelle
	 * Ausrichtung: ");
	 **/

	@Override
	public void onLoad() {
		HTML spview = new HTML("<h3>" + "Suchprofil" + "</h3>");
		Suchprofil sp = new Suchprofil();
		updateSpTable(newsuchprofil);

		editButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				// loadEditSuchprofilView(ClientSideSettings.getProfil());

			}
		});
	}

	private void updateSpTable(Suchprofil result) {
		Suchprofil suchProfil = result;

		FlexTable sProfilIntGrid = new FlexTable();
		sProfilIntGrid.setStyleName("itable");
		this.add(sProfilIntGrid);

		sProfilIntGrid.setWidget(1, 0, editButton);

		Grid sProfilGrid = new Grid(7, 6);
		sProfilGrid.setStyleName("etable");
		this.add(sProfilGrid);

		/**
		 * Attribute von Suchprofil
		 */
		// Spalte 1
		sProfilGrid.setText(1, 1, alterLabel);

		sProfilGrid.setText(1, 2, Integer.toString(suchProfil.getAlter()));

		sProfilGrid.setText(1, 3, hcolorLabel);
		sProfilGrid.setText(1, 4, suchProfil.getHaarFarbe());

		// Spalte 2
		sProfilGrid.setText(2, 1, heightLabel);
		sProfilGrid.setText(2, 2, String.valueOf(suchProfil.getKoerpergroesse()));

		sProfilGrid.setText(2, 3, smokerLabel);
		sProfilGrid.setText(2, 4, GuiUtils.getJaNein(suchProfil.isRaucher()));

		// Spalte 3
		sProfilGrid.setText(3, 1, religionLabel);
		sProfilGrid.setText(3, 2, suchProfil.getReligion());

	}

	public Suchprofil getNewSuchprofil() {
		return newsuchprofil;
	}

	public void setNewSuchprofil(Suchprofil newsuchprofil) {
		this.newsuchprofil = newsuchprofil;
	}

}
