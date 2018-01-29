package de.hdm.partnerboerse.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Profil;
import de.hdm.partnerboerse.shared.bo.Suchprofil;

public class EditSuchprofil extends VerticalPanel {

	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);
	private LoadEigenschaften loadEigenschaften = new LoadEigenschaften();
    Suchprofil suchprofil = ClientSideSettings.getSuchprofil();

	Button deleteButton = new Button("Suchprofil löschen");
	Button safeButton = new Button("Suchprofil speichern");
	CreateWidget cw = new CreateWidget();
	
	@Override
	public void onLoad() {

		// Grid erstellen zur besseren Darstellung

		FlexTable SprofilGrid = new FlexTable();
		SprofilGrid.setStyleName("etable");
		this.add(SprofilGrid);

		// Spalte 2
		SprofilGrid.setWidget(0, 0, cw.getAlterLabel());
		SprofilGrid.setWidget(0, 1, cw.setAlterListBox());

		// Spalte 4
		SprofilGrid.setWidget(1, 0, cw.getHcolorLabel());
		SprofilGrid.setWidget(1, 1, cw.setHcolorListBox());

		SprofilGrid.setWidget(2, 0, cw.getSpheightLabel());
		SprofilGrid.setWidget(2, 1, cw.setHeightListBox());

		// Spalte 5
		SprofilGrid.setWidget(0, 2, cw.getSmokerLabel());
		SprofilGrid.setWidget(0, 3, cw.setSmokerListBox());

		// Spalte 6
		SprofilGrid.setWidget(1, 2, cw.getReligionLabel());
		SprofilGrid.setWidget(1, 3, cw.setReligionListBox());

		// Spalte 7
		SprofilGrid.setWidget(2, 2, cw.getTitleLabel());
		SprofilGrid.setWidget(2, 3, cw.getTitleTextBox());
/**
 * Eventuell nicht nur Speichern sondern auch gleichzeitig suche? falls möglich
 */
		this.add(safeButton);

		safeButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				createSuchprofilCallback();
			}

			private void createSuchprofilCallback() {

				Profil source = new Profil();
				source.setId(ClientSideSettings.getProfil().getId());

				Suchprofil search = getSuchprofilWerte();

				partnerAdmin.createSuchprofil(source, search.getTitle(), search.getHaarFarbe(),
						(float) search.getKoerpergroesse(), search.isRaucher(), search.getReligion(), search.getAlter(),
						new AsyncCallback<Suchprofil>() {

							@Override
							public void onFailure(Throwable caught) {

							}

							@Override
							public void onSuccess(Suchprofil result) {
								ListViewSuchProfil lvsp = new ListViewSuchProfil();

								HTMLPanel splistViewPanel = new HTMLPanel(
										"<h3>" + "Hier können sie ein Suchprofil erstellen!" + "</h3>");
								splistViewPanel.add(lvsp);

								RootPanel.get("contwrap").clear();
								RootPanel.get("contwrap").add(splistViewPanel);
							}

						});

			}

		});

		Grid infoGrid = loadEigenschaften.loadEigen(suchprofil);
		this.add(infoGrid);
		
	}

	private Suchprofil getSuchprofilWerte() {

	    // TODO noch unvollständig da die Daten noch nocht aus der DB geladen werden 
		suchprofil.setId(ClientSideSettings.getSuchprofil().getId());
		int alter = Integer.parseInt(cw.getAlterListBox().getSelectedValue());
		suchprofil.setAlter(alter);
		suchprofil.setHaarFarbe(cw.getHcolorListBox().getSelectedValue());
		float kgr = Float.parseFloat(cw.getHeightListBox().getSelectedValue());
		suchprofil.setKoerpergroesse(kgr);

		String raucherSelectedValue = cw.getSmokerListBox().getSelectedValue();
		switch (raucherSelectedValue) {
		case "YSmoker":
			suchprofil.setRaucher(true);
			break;
		case "NSmoker":
			suchprofil.setRaucher(false);
			break;
		}

		suchprofil.setReligion(cw.getReligionListBox().getSelectedValue());
		suchprofil.setTitle(cw.getTitleTextBox().getValue());

		return suchprofil;
	}
}