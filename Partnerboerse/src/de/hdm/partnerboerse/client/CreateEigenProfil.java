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
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Profil;

public class CreateEigenProfil extends VerticalPanel {

  private final PartnerboerseAdministrationAsync partnerAdmin =
      GWT.create(PartnerboerseAdministration.class);

  // Loginattribute
  private LoginInfo loginInfo = null;

  /*
   * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
   */

  Button createButton = new Button("Profil erstellen");

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

    profilIntGrid.setWidget(1, 0, createButton);

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


    profilGrid.setWidget(2, 3, hcolorLabel);
    profilGrid.setWidget(2, 4, hcolorTextBox);

    // Spalte 3
    profilGrid.setWidget(3, 1, heightLabel);
    profilGrid.setWidget(3, 2, heightTextBox);


    smokerListBox.addItem("Ja", "YSmoker");
    smokerListBox.addItem("Nein", "NSmoker");


    profilGrid.setWidget(3, 3, smokerLabel);
    profilGrid.setWidget(3, 4, smokerListBox);

    // Spalte 4
    profilGrid.setWidget(4, 1, religionLabel);
    profilGrid.setWidget(4, 2, religionTextBox);

    /**
     * Button zum Speichern des eigenen geänderten Profils
     */
    createButton.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {

        createProfileOnServer();
      }

      private void createProfileOnServer() {

        Profil setProfil = getProfileValuesFromFormular();
        System.out.println("tes01" + setProfil.toString());


        partnerAdmin.createProfil(setProfil, new AsyncCallback<Void>() {

          @Override
          public void onFailure(Throwable caught) {
            Window.alert("Fehler: Profil konnte nicht erstellt werden!");

          }

          @Override
          public void onSuccess(Void result) {

            Editor ed = new Editor();
            RootPanel.get("contwrap").clear();
            ed.onModuleLoad();

          }
        });

      }
    });

  }

  /*
   * Werte aus den geÃ¤nderten Formularen wird ausgelesen und in ein Profil gespeichert und zurÃ¼ck
   * gegeben
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

    setProfil.setVorname(vnameTextBox.getValue());
    setProfil.setNachname(lnameTextBox.getValue());
    setProfil.setGeburtsdatum(bDayConvert);
    setProfil.setKoerpergroesse(heightConvert);
    setProfil.setReligion(religionTextBox.getValue());
    setProfil.setHaarfarbe(hcolorTextBox.getValue());
    setProfil.setEmail(loginInfo.getEmailAddress());
    // TODO: Wichtig Passwort muss raus, sobald das Datenmodell geändert wurde
    setProfil.setPasswort("sdfghfdghhjfhgdhggm");
    String raucherSelectedValue = smokerListBox.getSelectedValue();

    /*
     * String-Wert von Raucher wird ausgelesen und durch eine Switch-Anweisung wird der Wert zu
     * einem Boolean konvertiert.
     */
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

  public LoginInfo getLoginInfo() {
    return loginInfo;
  }

  public void setLoginInfo(LoginInfo loginInfo) {
    this.loginInfo = loginInfo;
  }



}
