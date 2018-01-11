package de.hdm.partnerboerse.client;

import java.util.Date;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Profil;

public class EditProfile extends VerticalPanel {

  private final PartnerboerseAdministrationAsync partnerAdmin =
      GWT.create(PartnerboerseAdministration.class);
  
  Profil getProfilFromServer;

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
  Label heightLabel = new Label("Größe " + "(in cm): ");
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

  ListBox smokerListBox = new ListBox();



  /*
   * Beim Anzeigen werden die anderen Widgets erzeugt. Alle werden in einem Raster angeordnet,
   * dessen Größe sich aus dem Platzbedarf der enthaltenen Widgets bestimmt.
   */
  @Override
  public void onLoad() {
    

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
    vnameTextBox.setValue(getProfilFromServer.getVorname());

    profilGrid.setWidget(1, 3, lnameLabel);
    profilGrid.setWidget(1, 4, lnameTextBox);
    lnameTextBox.setValue(getProfilFromServer.getNachname());

    // Spalte 2
    profilGrid.setWidget(2, 1, bdayLabel);
    profilGrid.setWidget(2, 2, bdayTextBox);
    bdayTextBox.setValue(getProfilFromServer.getGeburtsdatum().toString());

    profilGrid.setWidget(2, 3, mailLabel);
    profilGrid.setWidget(2, 4, mailTextBox);
    mailTextBox.setValue(getProfilFromServer.getEmail());

    // Spalte 3
    profilGrid.setWidget(3, 1, pwLabel);
    profilGrid.setWidget(3, 2, pwTextBox);
    pwTextBox.setValue(getProfilFromServer.getPasswort());

    // Spalte 4
    profilGrid.setWidget(4, 1, hcolorLabel);
    profilGrid.setWidget(4, 2, hcolorTextBox);
    hcolorTextBox.setValue(getProfilFromServer.getHaarfarbe());

    profilGrid.setWidget(4, 3, heightLabel);
    profilGrid.setWidget(4, 4, heightTextBox);
    heightTextBox.setValue(String.valueOf(getProfilFromServer.getKoerpergroesse()));

    
    // Spalte 5
    smokerListBox.addItem("Ja", "YSmoker");
    smokerListBox.addItem("Nein", "NSmoker");
    

    profilGrid.setWidget(5, 3, smokerLabel);
    profilGrid.setWidget(5, 4, smokerListBox);
    /*
     * https://stackoverflow.com/questions/3793650/convert-boolean-to-int-in-java
     * Konvertiert bei true zu 1 und bei false zu 0
     */
    //ToDo: Methode Funktioniert nicht, noch ausbessern
//    int smokerToInt = (getProfilFromServer.isRaucher()) ? 1 : 0;
//    switch (smokerToInt) {
//      case 1:
//        smokerListBox.getValue(0);
//        break;
//      case 2:
//        smokerListBox.getValue(1);
//        break;
//    }
    
    //smokerListBox.setValue(1, getProfilFromServer.isRaucher());


    // Spalte 6
    profilGrid.setWidget(6, 1, religionLabel);
    profilGrid.setWidget(6, 2, religionTextBox);
    religionTextBox.setValue(getProfilFromServer.getReligion());

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
        // ToDo Profil darf nicht Übergabeparameter sein, um sein Profil zu bekommen. Muss später
        // Abgeändert werden
        Profil setProfil = getProfileValuesFromFormular();

        partnerAdmin.updateProfil(setProfil, new AsyncCallback<Void>() {

          @Override
          public void onFailure(Throwable caught) {
            Window.alert("Profil wurde nicht gespeichert.");

          }

          @Override
          public void onSuccess(Void result) {
            Window.alert(vnameTextBox.getValue() + " Profil wurde gespeichert.");

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
        
        Profil profileToDelete = getProfileValuesFromFormular();
        
        partnerAdmin.deleteProfil(profileToDelete, new AsyncCallback<Void>() {

          @Override
          public void onFailure(Throwable caught) {
            Window.alert("Profil konnte nicht gelöscht werden");
            
          }

          @Override
          public void onSuccess(Void result) {
            //ToDo: Bei erfolgreicher Löschung wird dem Nutzer was angezeigt?
            //Sobald das geklart ist mit der Gruppe, muss dies implementiert werden
            //ToDo: alle Abhängigkeiten in der DB müssen auch gelöscht werden
            Window.alert("Profil wurde gelöscht");
            
          }});
        
      }
    });
  }

  /*
   * Werte aus den geänderten Formularen wird ausgelesen und in ein Profil gespeichert
   * und zurück gegeben
   */
  private Profil getProfileValuesFromFormular() {

    Profil setProfil = new Profil();

    /*
     * DateTimerFromat wandelt den Wert von bdayTextBox in Date um
     */
    Date bDayConvert = DateTimeFormat.getFormat("yyyy-MM-dd").parse(bdayTextBox.getValue());
    /*
     * Integer.parseInt wandelt String in int um
     */
    int heightConvert = Integer.parseInt(heightTextBox.getValue());

    //ToDo: setId muss raus, sobald Google Login Implementiert ist und Übergabeparameter stimmt.
    setProfil.setId(1);
    setProfil.setVorname(vnameTextBox.getValue());
    setProfil.setNachname(lnameTextBox.getValue());
    setProfil.setGeburtsdatum(bDayConvert);
    setProfil.setEmail(mailTextBox.getValue());
    setProfil.setPasswort(pwTextBox.getValue());
    setProfil.setKoerpergroesse(heightConvert);
    setProfil.setReligion(religionTextBox.getValue());
    setProfil.setHaarfarbe(hcolorTextBox.getValue());
    
    /*
     * String-Wert von Raucher wird ausgelesen und durch eine Switch-Anweisung
     * wird der Wert zu einem Boolean konvertiert.
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
