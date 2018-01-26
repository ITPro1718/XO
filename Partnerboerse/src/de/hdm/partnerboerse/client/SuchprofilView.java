package de.hdm.partnerboerse.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import de.hdm.partnerboerse.shared.bo.Profil;
import de.hdm.partnerboerse.shared.bo.Suchprofil;

public class SuchprofilView extends VerticalPanel {

	Profil profil = ClientSideSettings.getProfil();

	Button editButton = new Button("bearbeiten");
	Button deleteButton = new Button("löschen");
	Button searchButton = new Button("suchen");
	
	CreateWidget cw = new CreateWidget();

	Suchprofil newsuchprofil = new Suchprofil();
	
    LoadEigenschaften loadEigenschaften = new LoadEigenschaften();


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
		new HTML("<h3>" + "Suchprofil" + "</h3>");
		updateSpTable(newsuchprofil);

		editButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				loadEditSuchprofilView(newsuchprofil);

			}
		});
		
		/**
		 * Lädt das Alle Eigenschaften mit den zugehörigen Infos
		 * zu einem Suchprofil.
		 */
	    Grid info = loadEigenschaften.loadEigenRead(newsuchprofil);
		this.add(info);
	}

	private void updateSpTable(Suchprofil result) {
		Suchprofil suchProfil = result;

		FlexTable sProfilIntGrid = new FlexTable();
		sProfilIntGrid.setStyleName("itable");
		this.add(sProfilIntGrid);

		sProfilIntGrid.setWidget(1, 0, editButton);
		sProfilIntGrid.setWidget(1, 1, deleteButton);

		Grid sProfilGrid = new Grid(7, 6);
		sProfilGrid.setStyleName("etable");
		this.add(sProfilGrid);

		
		/**
		 * Attribute von Suchprofil
		 */
		// Spalte 1
		sProfilGrid.setWidget(1, 1, cw.getAlterLabel());

		sProfilGrid.setText(1, 2, Integer.toString(suchProfil.getAlter()));

		sProfilGrid.setWidget(1, 3, cw.getHcolorLabel());
		sProfilGrid.setText(1, 4, suchProfil.getHaarFarbe());

		// Spalte 2
		sProfilGrid.setWidget(2, 1, cw.getHeightLabel());
		sProfilGrid.setText(2, 2, String.valueOf(suchProfil.getKoerpergroesse()));

		sProfilGrid.setWidget(2, 3, cw.getSmokerLabel());
		sProfilGrid.setText(2, 4, GuiUtils.getJaNein(suchProfil.isRaucher()));

		// Spalte 3
		sProfilGrid.setWidget(3, 1, cw.getReligionLabel());
		sProfilGrid.setText(3, 2, suchProfil.getReligion());
		
		FlexTable sProfilIntGrid2 = new FlexTable();
		sProfilIntGrid2.setStyleName("itable");
		this.add(sProfilIntGrid2);
		sProfilIntGrid2.setWidget(0, 0, searchButton);

	}

	public Suchprofil getNewSuchprofil() {
		return newsuchprofil;
	}

	public void setNewSuchprofil(Suchprofil newsuchprofil) {
		this.newsuchprofil = newsuchprofil;
	}

	private void loadEditSuchprofilView(Suchprofil result) {

		EditSuchprofil editsp = new EditSuchprofil();

		// Profile Edit - Panel wird erzeugt und eingefügt.
		HTMLPanel editSuchprofilPanel = new HTMLPanel(
				"<h3>" + "Hier können Sie ihre Profilinformationen bearbeiten." + "</h3>");

		editSuchprofilPanel.add(editsp);

		RootPanel.get("contwrap").clear();
		RootPanel.get("contwrap").add(editSuchprofilPanel);

	}

}
