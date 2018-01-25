package de.hdm.partnerboerse.client;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Auswahl;
import de.hdm.partnerboerse.shared.bo.Freitext;
import de.hdm.partnerboerse.shared.bo.Info;
import de.hdm.partnerboerse.shared.bo.Profil;
import de.hdm.partnerboerse.client.CreateWidget;

public class EditProfile extends VerticalPanel {

  private final PartnerboerseAdministrationAsync partnerAdmin =
      GWT.create(PartnerboerseAdministration.class);

  LoginInfo loginInfo = ClientSideSettings.getLoginInfo();

  Profil getProfilFromServer = ClientSideSettings.getProfil();
  
  CreateWidget cw = new CreateWidget();

  /*
   * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
   */

  Button deleteButton = new Button("Profil löschen");
  Button safeButton = new Button("Profil speichern");

  

  DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyy-mm-dd");

  /*
   * Beim Anzeigen werden die anderen Widgets erzeugt. Alle werden in einem Raster angeordnet,
   * dessen Größe sich aus dem Platzbedarf der enthaltenen Widgets bestimmt.
   */
  @Override
  public void onLoad() {

    /*
     * Grid für die Attribute
     * 
     */
    Grid profilIntGrid = new Grid(2, 3);
    profilIntGrid.setStyleName("itable");
    this.add(profilIntGrid);

    profilIntGrid.setWidget(1, 0, safeButton);
    profilIntGrid.setWidget(1, 2, deleteButton);

    Grid profilGrid = new Grid(6, 6);
    profilGrid.setStyleName("etable");
    this.add(profilGrid);

    // Spalte 1
    profilGrid.setWidget(1, 1, cw.getVnameLabel());
    profilGrid.setWidget(1, 2, cw.getVnameTextBox());
    cw.getVnameTextBox().setValue(getProfilFromServer.getVorname());

    profilGrid.setWidget(1, 3, cw.getLnameLabel());
    profilGrid.setWidget(1, 4, cw.getLnameTextBox());
    cw.getLnameTextBox().setValue(getProfilFromServer.getNachname());

    // Spalte 2

    profilGrid.setWidget(2, 1, cw.getBdayLabel());
    profilGrid.setWidget(2, 2, cw.getBdayTextBox());
    cw.getBdayTextBox().setValue(String.valueOf(getProfilFromServer.getGeburtsdatum()));

    
    // Spalte 4
    
    profilGrid.setWidget(2, 3, cw.getHcolorLabel());
    profilGrid.setWidget(2, 4, cw.setHcolorListBox());
    // hcolorListBox.setValue(getProfilFromServer.getHaarfarbe());

    profilGrid.setWidget(3, 1, cw.getHeightLabel());
    profilGrid.setWidget(3, 2, cw.getHeightTextBox());
    cw.getHeightTextBox().setValue(String.valueOf(getProfilFromServer.getKoerpergroesse()));

    // Spalte 5

    profilGrid.setWidget(3, 3, cw.getSmokerLabel());
    profilGrid.setWidget(3, 4, cw.setSmokerListBox());

    /*
     * https://stackoverflow.com/questions/3793650/convert-boolean-to-int-in -java Konvertiert bei
     * true zu 1 und bei false zu 0
     */
    // ToDo: Methode Funktioniert nicht, noch ausbessern
    int smokerToInt = (getProfilFromServer.isRaucher()) ? 1 : 0;
    switch (smokerToInt) {
      case 1:
        cw.getSmokerListBox().getValue(0);
        break;
      case 2:
        cw.getSmokerListBox().getValue(1);
        break;
    }

    // smokerListBox.setValue(1, getProfilFromServer.isRaucher());

    // Spalte 6
    
    profilGrid.setWidget(4, 1, cw.getReligionLabel());
    profilGrid.setWidget(4, 2, cw.setReligionListBox());
    // religionListBox.setValue(getProfilFromServer.getReligion());

    /*
     * Grid für die Eingenschaftsobjekte. Zur besseren Beschreibung eines Profils.
     * 
     */
    
    FlexTable descripton = new FlexTable();
    descripton.setStyleName("desctable");
    this.add(descripton);

    
    Grid infoGrid = new Grid(4, 6);
    infoGrid.setStyleName("etable");
    this.add(infoGrid);


//    infoGrid.setWidget(0, 1, hobbyLab);
//    infoGrid.setWidget(0, 2, hobby);
//    getHobbyStringFromServer(hobbyLab);


    
    /*
     * deleteButton.addClickHandler(new DeleteClickHandler()); deleteButton.setEnabled(false);
     * profilGrid.setWidget(0, 1, deleteButton);
     * 
     * newButton.addClickHandler(new NewClickHandler()); newButton.setEnabled(false);
     * profilGrid.setWidget(1, 1, newButton);
     * 
     * HorizontalPanel profilButtonsPanel = new HorizontalPanel(); profilGrid.setWidget(4, 1,
     * profilButtonsPanel);
     * 
     * Button changeButton = new Button("Name ändern"); changeButton.addClickHandler(new
     * ChangeClickHandler()); profilButtonsPanel.add(changeButton);
     * 
     * Button searchButton = new Button("Suchen"); profilButtonsPanel.add(searchButton);
     */

    /*
     * Button zum Speichern des eigenen geändertem Profils
     */
    safeButton.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {

        updateProfileOnServer();
       
      }

      private void updateProfileOnServer() {

        getProfilFromServer = getProfileValuesFromFormular();


        partnerAdmin.updateProfil(getProfilFromServer, new AsyncCallback<Void>() {

          @Override
          public void onFailure(Throwable caught) {
            Window.alert("Profil wurde nicht gespeichert.");

          }

          @Override
          public void onSuccess(Void result) {
            Window.alert(cw.getVnameTextBox().getValue() + " Profil wurde gespeichert.");


            ClientSideSettings.setProfil(getProfilFromServer);

            EigenProfilView epv = new EigenProfilView();

            HTMLPanel eigenProfilViewPanel =
                new HTMLPanel("<h3>" + "Hier können Sie ihr Profil sehen." + "</h3>");
            eigenProfilViewPanel.add(epv);

            RootPanel.get("contwrap").clear();
            RootPanel.get("contwrap").add(eigenProfilViewPanel);

          }
        });
      }

     
  

    });

    deleteButton.addClickHandler(new ClickHandler() {
    	
      @Override
      public void onClick(ClickEvent event) {
        deleteProfilOnServer();

      }

      private void deleteProfilOnServer() {

        Profil profileToDelete = ClientSideSettings.getProfil();

        partnerAdmin.deleteProfil(profileToDelete, new AsyncCallback<Void>() {

          @Override
          public void onFailure(Throwable caught) {
            Window.alert("Profil konnte nicht gelöscht werden");

          }

          @Override
          public void onSuccess(Void result) {
            // ToDo: Bei erfolgreicher Löschung wird dem Nutzer was
            // angezeigt?
            // Sobald das geklart ist mit der Gruppe, muss dies
            // implementiert werden
            // ToDo: alle Abhängigkeiten in der DB müssen auch
            // gelöscht werden
            Window.alert("Profil wurde gelöscht");

          }
        });

      }
    });
  }


/**
   * TODO: Datum muss noch aderster gemappt werden es ist falsch! Werte aus den geänderten
   * Formularen wird ausgelesen und in ein Profil gespeichert und zurück gegeben
   */
  private Profil getProfileValuesFromFormular() {


    Profil setProfil = new Profil();

    /*
     * Integer.parseInt wandelt String in int um
     */
    int heightConvert = Integer.parseInt(cw.getHeightTextBox().getValue());
   
    
    /*
	 * DateTimerFromat wandelt den Wert von bdayTextBox in Date um
	 */
	Date bDayConvert = DateTimeFormat.getFormat("yyyy-MM-dd").parse(cw.getBdayTextBox().getValue());

    setProfil.setId(getProfilFromServer.getId());
    setProfil.setVorname(cw.getVnameTextBox().getValue());
    setProfil.setNachname(cw.getLnameTextBox().getValue());
    setProfil.setGeburtsdatum(bDayConvert);
    setProfil.setEmail(loginInfo.getEmailAddress());
    setProfil.setKoerpergroesse(heightConvert);
    setProfil.setReligion(cw.getReligionListBox().getSelectedValue());
    setProfil.setHaarfarbe(cw.getHcolorListBox().getSelectedValue());

    /*
     * String-Wert von Raucher wird ausgelesen und durch eine Switch-Anweisung wird der Wert zu
     * einem Boolean konvertiert.
     */
    String raucherSelectedValue = cw.getSmokerListBox().getSelectedValue();

    switch (raucherSelectedValue) {
      case "YSmoker":
        setProfil.setRaucher(true);
        break;
      case "NSmoker":
        setProfil.setRaucher(false);
        break;
    }

    return setProfil;

  }

}
