package de.hdm.partnerboerse.client.editor.suchprofil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.partnerboerse.client.ClientSideSettings;
import de.hdm.partnerboerse.client.ClientValidation;
import de.hdm.partnerboerse.client.editor.eigenschaften.EigenschaftsView;
import de.hdm.partnerboerse.client.editor.forms.CreateWidget;
import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Profil;
import de.hdm.partnerboerse.shared.bo.Suchprofil;

public class CreateSuchprofil extends VerticalPanel {

	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);

	Button safeButton = new Button("speichern");

	CreateWidget cw = new CreateWidget();

	@Override
	public void onLoad() {

		// Grid erstellen zur besseren Darstellung

		FlexTable sprofilGrid = new FlexTable();
		sprofilGrid.setStyleName("etable");
		this.add(sprofilGrid);

		// Spalte 2
		sprofilGrid.setWidget(0, 0, cw.getAlterLabel());
		sprofilGrid.setWidget(0, 1, cw.setAlterListBox());

		// Spalte 4
		sprofilGrid.setWidget(1, 0, cw.getHcolorLabel());
		sprofilGrid.setWidget(1, 1, cw.setHcolorListBox());

		sprofilGrid.setWidget(2, 0, cw.getSpheightLabel());
		sprofilGrid.setWidget(2, 1, cw.setHeightListBox());

		// Spalte 5
		sprofilGrid.setWidget(0, 2, cw.getSmokerLabel());
		sprofilGrid.setWidget(0, 3, cw.setSmokerListBox());

		// Spalte 6
		sprofilGrid.setWidget(1, 2, cw.getReligionLabel());
		sprofilGrid.setWidget(1, 3, cw.setReligionListBox());

		// Spalte 7
		sprofilGrid.setWidget(2, 2, cw.getTitleLabel());
		sprofilGrid.setWidget(2, 3, cw.getTitleTextBox());

		this.add(safeButton);

		safeButton.addClickHandler(new SafeButtonClickhandler());

	}
	/**
	 * ClickHandler zum Speichern des neuen Suchprofils
	 *
	 */
	
	private class SafeButtonClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			createSuchprofilCallback();
		}

		private void createSuchprofilCallback() {

			Profil source = ClientSideSettings.getProfil();
			Suchprofil search = getSuchprofilWerte();
			ClientValidation cv = new ClientValidation();
			if (cv.isSuchprofilValid(search)) {
				partnerAdmin.createSuchprofil(source, search.getTitle(), search.getHaarFarbe(),
						(float) search.getKoerpergroesse(), search.isRaucher(), search.getReligion(),
						search.getAlter(), new AsyncCallback<Suchprofil>() {

							@Override
							public void onFailure(Throwable caught) {
								Window.alert("Suchprofil wurde nicht gespeichert.");

							}

							@Override
							public void onSuccess(Suchprofil result) {

								EigenschaftsView ev = new EigenschaftsView();
								ev.egFor(result);

								HTMLPanel evPanel = new HTMLPanel(
										"<h3>" + "Geben Sie Eigenschaften an, nach welchen Sie suchen möchten! "
												+ "Was sollte Ihren Traumpartner ausmachen?" + "</h3>");
								evPanel.add(ev);

								RootPanel.get("contwrap").clear();
								RootPanel.get("contwrap").add(evPanel);
							}

						});
			} else {
				return;
			}

		}

	}
	
	/**
	 * 
	 * METHODEN 
	 * TODO Kommentieren!
	 * 
	 */

	private Suchprofil getSuchprofilWerte() {

		Suchprofil s = new Suchprofil();
		s.setId(1);
		int alter = Integer.parseInt(cw.getAlterListBox().getSelectedValue());
		s.setAlter(alter);
		s.setHaarFarbe(cw.getHcolorListBox().getSelectedValue());
		float kgr = Float.parseFloat(cw.getHeightListBox().getSelectedValue());
		s.setKoerpergroesse(kgr);

		String raucherSelectedValue = cw.getSmokerListBox().getSelectedValue();
		switch (raucherSelectedValue) {
		case "Ja":
			s.setRaucher(true);
			break;
		case "Nein":
			s.setRaucher(false);
			break;
		default:
			break;
		}

		s.setReligion(cw.getReligionListBox().getSelectedValue());
		s.setTitle(cw.getTitleTextBox().getValue());

		return s;
	}

}
