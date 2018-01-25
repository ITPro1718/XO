package de.hdm.partnerboerse.client;

import java.util.ArrayList;

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
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;
import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Auswahl;
import de.hdm.partnerboerse.shared.bo.Freitext;
import de.hdm.partnerboerse.shared.bo.Info;
import de.hdm.partnerboerse.shared.bo.Profil;

public class EditProfile extends VerticalPanel {

  private final PartnerboerseAdministrationAsync partnerAdmin =
      GWT.create(PartnerboerseAdministration.class);

  LoginInfo loginInfo = ClientSideSettings.getLoginInfo();

  Profil getProfilFromServer = ClientSideSettings.getProfil();

  /*
   * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
   */

  Button deleteButton = new Button("Profil löschen");
  Button safeButton = new Button("Profil speichern");

  // Label, Textboxen und ListBoxen für das ProfilGrid
  Label vnameLabel = new Label("Vorname: ");
  Label lnameLabel = new Label("Nachname: ");
  Label bdayLabel = new Label("Geburtstag: ");
  Label hcolorLabel = new Label("Haarfarbe: ");
  Label heightLabel = new Label("Größe (im cm)");
  Label smokerLabel = new Label("Raucher: ");
  Label religionLabel = new Label("Religion: ");

  TextBox vnameTextBox = new TextBox();
  TextBox lnameTextBox = new TextBox();
  DateBox bdayTextBox = new DateBox();
  ListBox hcolorListBox = new ListBox();
  TextBox heightTextBox = new TextBox();
  ListBox religionListBox = new ListBox();
  ListBox smokerListBox = new ListBox();

  

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
    profilGrid.setWidget(1, 1, vnameLabel);
    profilGrid.setWidget(1, 2, vnameTextBox);
    vnameTextBox.setValue(getProfilFromServer.getVorname());

    profilGrid.setWidget(1, 3, lnameLabel);
    profilGrid.setWidget(1, 4, lnameTextBox);
    lnameTextBox.setValue(getProfilFromServer.getNachname());

    // Spalte 2
    bdayTextBox.setFormat(new DateBox.DefaultFormat(dateFormat));

    profilGrid.setWidget(2, 1, bdayLabel);
    profilGrid.setWidget(2, 2, bdayTextBox);
    bdayTextBox.setValue(getProfilFromServer.getGeburtsdatum());

    
    // Spalte 4
    hcolorListBox.addItem("schwarz", "schwarz");
    hcolorListBox.addItem("braun", "braun");
    hcolorListBox.addItem("blond", "blond");
    hcolorListBox.addItem("grau", "grau");
    hcolorListBox.addItem("sonstige", "sonstige");
    
    profilGrid.setWidget(2, 3, hcolorLabel);
    profilGrid.setWidget(2, 4, hcolorListBox);
    // hcolorListBox.setValue(getProfilFromServer.getHaarfarbe());

    profilGrid.setWidget(3, 1, heightLabel);
    profilGrid.setWidget(3, 2, heightTextBox);
    heightTextBox.setValue(String.valueOf(getProfilFromServer.getKoerpergroesse()));

    // Spalte 5
    smokerListBox.addItem("Ja", "YSmoker");
    smokerListBox.addItem("Nein", "NSmoker");

    profilGrid.setWidget(3, 3, smokerLabel);
    profilGrid.setWidget(3, 4, smokerListBox);

    /*
     * https://stackoverflow.com/questions/3793650/convert-boolean-to-int-in -java Konvertiert bei
     * true zu 1 und bei false zu 0
     */
    // ToDo: Methode Funktioniert nicht, noch ausbessern
    int smokerToInt = (getProfilFromServer.isRaucher()) ? 1 : 0;
    switch (smokerToInt) {
      case 1:
        smokerListBox.getValue(0);
        break;
      case 2:
        smokerListBox.getValue(1);
        break;
    }

    // smokerListBox.setValue(1, getProfilFromServer.isRaucher());

    // Spalte 6
    religionListBox.addItem("christlich", "christlich");
    religionListBox.addItem("muslimisch", "muslimisch");
    religionListBox.addItem("jüdisch", "jüdisch");
    religionListBox.addItem("buddhistisch", "buddhistisch");
    religionListBox.addItem("atheist", "atheist");
    
    profilGrid.setWidget(4, 1, religionLabel);
    profilGrid.setWidget(4, 2, religionListBox);
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
            Window.alert(vnameTextBox.getValue() + " Profil wurde gespeichert.");


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
     * DateTimerFromat wandelt den Wert von bdayTextBox in Date um. Wird nicht mehr benötigt, da wir
     * jetzt eine Datebox nutzen.
     */
    // Date bDayConvert = DateTimeFormat.getFormat("yyyy-MM-dd").parse(bdayTextBox.getValue());

    /*
     * Integer.parseInt wandelt String in int um
     */
    int heightConvert = Integer.parseInt(heightTextBox.getValue());
    // String stringDate = dateFormat.format(bdayTextBox.getValue());
    // @SuppressWarnings("deprecation")
    // Date date = new Date(stringDate)
    java.util.Date utilDate = bdayTextBox.getValue();
    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

    setProfil.setId(getProfilFromServer.getId());
    setProfil.setVorname(vnameTextBox.getValue());
    setProfil.setNachname(lnameTextBox.getValue());
    setProfil.setGeburtsdatum(sqlDate);
    setProfil.setEmail(loginInfo.getEmailAddress());
    setProfil.setKoerpergroesse(heightConvert);
    setProfil.setReligion(religionListBox.getSelectedValue());
    setProfil.setHaarfarbe(hcolorListBox.getSelectedValue());

    /*
     * String-Wert von Raucher wird ausgelesen und durch eine Switch-Anweisung wird der Wert zu
     * einem Boolean konvertiert.
     */
    String raucherSelectedValue = smokerListBox.getSelectedValue();

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
