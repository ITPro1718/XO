package de.hdm.partnerboerse.client;

import java.util.Date;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Profil;

public class EditProfile extends VerticalPanel {

	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);

	/*
	 * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
	 */

	Button deleteButton = new Button("Profil löschen");
	Button safeButton = new Button("Profil speichern");

	Label mailLabel = new Label("E-Mail: ");
	Label pwLabel = new Label("Passwort: ");
	Label vnameLabel = new Label("Vorname: ");
	Label lnameLabel = new Label("Nachname: ");
	Label bdayLabel = new Label("Geburtstag: ");
	Label hcolorLabel = new Label("Haarfarbe: ");
	Label heightLabel = new Label("Größe "+ "(in cm): ");
	Label smokerLabel = new Label("Raucher: ");
	Label religionLabel = new Label("Religion: ");

	TextBox mailTextBox = new TextBox();
	TextBox pwTextBox = new TextBox();
	TextBox vnameTextBox = new TextBox();
	TextBox lnameTextBox = new TextBox();
	TextBox bdayTextBox = new TextBox();
	TextBox hcolorTextBox = new TextBox();
	TextBox heightTextBox = new TextBox();
	TextBox religionTextBox = new TextBox();



	
	/*
	 * Beim Anzeigen werden die anderen Widgets erzeugt. Alle werden in einem
	 * Raster angeordnet, dessen Größe sich aus dem Platzbedarf der enthaltenen
	 * Widgets bestimmt.
	 */
	@Override
	public void onLoad() {
		// super.onLoad();

		Grid profilIntGrid = new Grid(2, 3);
		profilIntGrid.setStyleName("itable");
		this.add(profilIntGrid);

		profilIntGrid.setWidget(1, 0, safeButton);
		profilIntGrid.setWidget(1, 2, deleteButton);

		Grid profilGrid = new Grid(7, 6);
		profilGrid.setStyleName("etable");
		this.add(profilGrid);

		// Spalte 1
		profilGrid.setWidget(1, 1, vnameLabel);
		profilGrid.setWidget(1, 2, vnameTextBox);

		profilGrid.setWidget(1, 3, lnameLabel);
		profilGrid.setWidget(1, 4, lnameTextBox);

		// Spalte 2
		profilGrid.setWidget(2, 1, bdayLabel);
		profilGrid.setWidget(2, 2, bdayTextBox);

		profilGrid.setWidget(2, 3, mailLabel);
		profilGrid.setWidget(2, 4, mailTextBox);

		// Spalte 3
		profilGrid.setWidget(3, 1, pwLabel);
		profilGrid.setWidget(3, 2, pwTextBox);

		// Spalte 4
		profilGrid.setWidget(4, 1, hcolorLabel);
		profilGrid.setWidget(4, 2, hcolorTextBox);

		profilGrid.setWidget(4, 3, heightLabel);
		profilGrid.setWidget(4, 4, heightTextBox);

		// Spalte 5
		ListBox smokerListBox = new ListBox();
		smokerListBox.addItem("Ja", "YSmoker");
		smokerListBox.addItem("Nein", "NSmoker");
		smokerListBox.addItem("Gelegentlich", "SSmoker");
		
		profilGrid.setWidget(5, 3, smokerLabel);
		profilGrid.setWidget(5, 4, smokerListBox);


		// Spalte 6
		profilGrid.setWidget(6, 1, religionLabel);
		profilGrid.setWidget(6, 2, religionTextBox);

		/*
		 * deleteButton.addClickHandler(new DeleteClickHandler());
		 * deleteButton.setEnabled(false); profilGrid.setWidget(0, 1,
		 * deleteButton);
		 * 
		 * newButton.addClickHandler(new NewClickHandler());
		 * newButton.setEnabled(false); profilGrid.setWidget(1, 1, newButton);
		 * 
		 * HorizontalPanel profilButtonsPanel = new HorizontalPanel();
		 * profilGrid.setWidget(4, 1, profilButtonsPanel);
		 * 
		 * Button changeButton = new Button("Name ändern");
		 * changeButton.addClickHandler(new ChangeClickHandler());
		 * profilButtonsPanel.add(changeButton);
		 * 
		 * Button searchButton = new Button("Suchen");
		 * profilButtonsPanel.add(searchButton);
		 */
		
        safeButton.addClickHandler(new ClickHandler() {
          
          @Override
          public void onClick(ClickEvent event) {
              
            updateProfileOnServer();
          }

          @SuppressWarnings("deprecation")
          private void updateProfileOnServer() {
            //ToDo Profil darf nicht Übergabeparameter sein, um sein Profil zu bekommen. Muss später Abgeändert werden
            
            /*
             * DateTimerFromat wandelt den Wert von bdayTextBox in Date um
             */
            //Date bDayConvert = DateTimeFormat.getFormat("yyyy-MM-dd").parse(bdayTextBox.getValue());
            /*
             *  Integer.parseInt wandelt String in int um
             */
            int heightConvert = Integer.parseInt(heightTextBox.getValue());
            
            Profil testProfil = new Profil();
            
            testProfil.setId(2);
            testProfil.setVorname(vnameTextBox.getValue());
            testProfil.setNachname(lnameTextBox.getValue());
            testProfil.setGeburtsdatum(new Date("2014-02-14"));
            testProfil.setEmail(mailTextBox.getValue());
            testProfil.setPasswort(pwTextBox.getValue());
            testProfil.setKoerpergroesse(heightConvert);
            testProfil.setReligion(religionTextBox.getValue());
            testProfil.setRaucher(false);
            testProfil.setHaarfarbe(hcolorTextBox.getValue());
            
            

            partnerAdmin.updateProfil(testProfil, new AsyncCallback<Void>() {

              @Override
              public void onFailure(Throwable caught) {
                Window.alert("Profil wurde nicht gespeichert.");
                
              }

              @Override
              public void onSuccess(Void result) {
                Window.alert(vnameTextBox.getValue() + "Profil wurde gespeichert.");
                
                
              }});
          }

            
        });
        
    }

	/*
	 * Click Handlers.
	 */

}
