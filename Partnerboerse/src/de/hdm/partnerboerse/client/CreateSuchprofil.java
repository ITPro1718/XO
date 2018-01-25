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
	 * TODO wenn EigenschaftsModell steht // Eigenschaften für InfoGrid Label
	 * sdescriptLab = new Label("Beschreibe dich kurz: "); Label hobbyLab = new
	 * Label("Deine Hobbies: "); Label musicLab = new Label("Deine lieblings
	 * Musik: "); Label searchForLab = newLabel("Du Bist auf der Suche Nach? ");
	 * Label sexOrientLab = new Label("Deine sexuelle Ausrichtung: ");
	 * 
	 * TextBox hobby = new TextBox(); TextBox music = new TextBox(); TextArea
	 * sdescript = new TextArea(); ListBox sexOrient = new ListBox(); ListBox
	 * searchFor = new ListBox();
	 **/

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

		SprofilGrid.setWidget(2, 0, cw.getHeightLabel());
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

		this.add(safeButton);

		
		loadEigenschaften();
		
		this.add(safeButton);

		safeButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				createSuchprofilCallback();
			}

			private void createSuchprofilCallback() {

				Profil source = ClientSideSettings.getProfil();

				Suchprofil search = getSuchprofilWerte();

				partnerAdmin.createSuchprofil(source, search.getTitle(), search.getHaarFarbe(),
						(float) search.getKoerpergroesse(), search.isRaucher(), search.getReligion(), search.getAlter(),
						new AsyncCallback<Void>() {

							@Override
							public void onFailure(Throwable caught) {
								Window.alert("Suchprofil wurde nicht gespeichert.");

							}

							@Override
							public void onSuccess(Void result) {
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

	}

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
		case "YSmoker":
			s.setRaucher(true);
			break;
		case "NSmoker":
			s.setRaucher(false);
			break;
		default:
			break;
		}

		s.setReligion(cw.getReligionListBox().getSelectedValue());
		s.setTitle(cw.getTitleTextBox().getValue());

		return s;
	}
	
	Grid infoGrid = new Grid(10,5);
	int row = 1;
	int column = 2;
	
	private void loadEigenschaften(){
		
		this.add(infoGrid);
		
		partnerAdmin.getAllEigenschaften(new AsyncCallback<ArrayList<Eigenschaft>>(){

			@Override
			public void onFailure(Throwable caught) {
				
			}

			@Override
			public void onSuccess(ArrayList<Eigenschaft> result) {
				
				for (Eigenschaft e : result){
					
					final Eigenschaft eg = e;
					
					if (eg.getIs_a().equals("freitext")){
						final TextBox tb = new TextBox();
						infoGrid.setText(row, column, eg.getErlaeuterung());
						infoGrid.setWidget(row, column + 1, tb);
						Button safe = new Button("Safe");
						infoGrid.setWidget(row, column + 2, safe);
						safe.addClickHandler(new ClickHandler(){

							@Override
							public void onClick(ClickEvent event) {
								partnerAdmin.createInfo(ClientSideSettings.getProfil(), tb.getText(), eg, new AsyncCallback<Info>(){

									@Override
									public void onFailure(Throwable caught) {
									}

									@Override
									public void onSuccess(Info result) {
									}
									
								});
							}
							
						});
						row++;
					}
					
					if (eg.getIs_a().equals("auswahl")){
						
						final ListBox lb = new ListBox();
						
						infoGrid.setText(row, column, eg.getErlaeuterung());
						infoGrid.setWidget(row, column + 1, lb);
						
						partnerAdmin.getAuswahl(new AsyncCallback<ArrayList<Auswahl>>(){

							@Override
							public void onFailure(Throwable caught) {
							}

							@Override
							public void onSuccess(ArrayList<Auswahl> result) {
								for (Auswahl a : result){
									if (a.getEigenschaftId() == eg.getId()){
										lb.addItem(a.getTitel());										
									}
								}
							}
						});
						
						Button safe = new Button("Safe");
						infoGrid.setWidget(row, column + 2, safe);
						safe.addClickHandler(new ClickHandler(){

							@Override
							public void onClick(ClickEvent event) {
								partnerAdmin.createInfo(ClientSideSettings.getProfil(), lb.getSelectedValue(), eg, new AsyncCallback<Info>(){

									@Override
									public void onFailure(Throwable caught) {
									}

									@Override
									public void onSuccess(Info result) {
									}
									
								});
							}
							
						});
						row++;
					}
				}
			}
		});	
	} // End load eigenschaften
}
