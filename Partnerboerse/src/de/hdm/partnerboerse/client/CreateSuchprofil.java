package de.hdm.partnerboerse.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Auswahl;
import de.hdm.partnerboerse.shared.bo.Eigenschaft;
import de.hdm.partnerboerse.shared.bo.Info;
import de.hdm.partnerboerse.shared.bo.Profil;
import de.hdm.partnerboerse.shared.bo.Suchprofil;
import de.hdm.partnerboerse.client.CreateWidget;

public class CreateSuchprofil extends VerticalPanel {

	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);

	Button safeButton = new Button("speichern");

	CreateWidget cw = new CreateWidget();

	/**
	 *  Suchprofil wird erstellt, das Grid wird gezeichnet und 
	 *  die jeweiligen Informationen aus der CreatWidget Klasse in das Grid eingetragen.
	 */
	
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
		 *  Ein Safebutton wird hinzugefügt
		 */
		
		this.add(safeButton);

		/**
		 *  Der Klickhandler für den Safebutton wird erstellt und 
		 *  die eingetragenen Werte in die Datenbank eingetragen.
		 *  Es wird vorab geprüft ob die Speicherung erfolgreich war, 
		 *  Danach wird das Sucprofil ausgegeben (Wenn die Speicherung erfolgreich war).
		 */
		
		safeButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				createSuchprofilCallback();
			}

			private void createSuchprofilCallback() {

				Profil source = ClientSideSettings.getProfil();
				Suchprofil search = getSuchprofilWerte();
				ClientValidation cv = new ClientValidation();
				if(cv.isSuchprofilValid(search)) {
    				partnerAdmin.createSuchprofil(source, search.getTitle(), search.getHaarFarbe(),
    						(float) search.getKoerpergroesse(), search.isRaucher(), search.getReligion(), search.getAlter(),
    						new AsyncCallback<Suchprofil>() {
    
    							@Override
    							public void onFailure(Throwable caught) {
    								Window.alert("Suchprofil wurde nicht gespeichert.");
    
    							}
    
    							@Override
    							public void onSuccess(Suchprofil result) {
    								
    								EigenschaftsView ev = new EigenschaftsView();
    								ev.egFor(result);
    
    								HTMLPanel evPanel = new HTMLPanel(
    										"<h3>" + "Hier können Sie einige relevante Infos angeben!" + "</h3>");
    								evPanel.add(ev);
    
    								RootPanel.get("contwrap").clear();
    								RootPanel.get("contwrap").add(evPanel);
    							}
    
    						});
				} else {
				  return;
				}

			}

		});

	}
	
	/**
	 *  Falls das Suchprofil nicht neu ist und bereits existiert, 
	 *  werden die vorabdefinierten Werte für den User geladen.
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
