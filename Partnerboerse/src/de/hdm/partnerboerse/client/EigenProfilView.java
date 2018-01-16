package de.hdm.partnerboerse.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Profil;

public class EigenProfilView extends VerticalPanel {

  private final PartnerboerseAdministrationAsync partnerAdmin =
      GWT.create(PartnerboerseAdministration.class);

  LoginInfo loginInfo = null;

  /*
   * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
   */

  Button editButton = new Button("Profil bearbeiten");

  String vnameLabel = new String("Vorname: ");
  String lnameLabel = new String("Nachname: ");
  String bdayLabel = new String("Geburtstag: ");
  String hcolorLabel = new String("Haarfarbe: ");
  String heightLabel = new String("Größe (in cm): ");
  String smokerLabel = new String("Raucher: ");
  String religionLabel = new String("Religion: ");

  public void onLoad() {

    loadProfileFromServer();

    editButton.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {

        partnerAdmin.getProfilByEmail(loginInfo.getEmailAddress(), new AsyncCallback<Profil>() {

          @Override
          public void onFailure(Throwable caught) {
            // TODO Auto-generated method stub

          }

          @Override
          public void onSuccess(Profil result) {
            loadEditProfilView(result);

          }
        });

      }
    });
  }

  private void loadProfileFromServer() {

    partnerAdmin.getProfilByEmail(loginInfo.getEmailAddress(), new AsyncCallback<Profil>() {

      @Override
      public void onFailure(Throwable caught) {
        // TODO Auto-generated method stub

      }

      @Override
      public void onSuccess(Profil result) {
        updateProfilTable(result);

      }
    });
  }

  private void updateProfilTable(Profil result) {
    Profil meinProfil = result;

    Grid profilIntGrid = new Grid(2, 3);
    profilIntGrid.setStyleName("itable");
    this.add(profilIntGrid);

    profilIntGrid.setWidget(1, 0, editButton);

    Grid profilGrid = new Grid(7, 6);
    profilGrid.setStyleName("etable");
    this.add(profilGrid);

    // Spalte 1
    profilGrid.setText(1, 1, vnameLabel);
    profilGrid.setText(1, 2, meinProfil.getVorname());

    profilGrid.setText(1, 3, lnameLabel);
    profilGrid.setText(1, 4, meinProfil.getNachname());

    // Spalte 2
    profilGrid.setText(2, 1, bdayLabel);
    profilGrid.setText(2, 2, meinProfil.getGeburtsdatum().toString());

    profilGrid.setText(2, 3, hcolorLabel);
    profilGrid.setText(2, 4, meinProfil.getHaarfarbe());

    // Spalte 3
    profilGrid.setText(3, 1, heightLabel);
    profilGrid.setText(3, 2, String.valueOf(meinProfil.getKoerpergroesse()));

    profilGrid.setText(3, 3, smokerLabel);
    profilGrid.setText(3, 4, String.valueOf(meinProfil.isRaucher()));

    // Spalte 4
    profilGrid.setText(4, 1, religionLabel);
    profilGrid.setText(4, 2, meinProfil.getReligion());

  }

  // ToDo: Überlegen wie man den Parameter für die neue View übertragen kann
  private void loadEditProfilView(Profil result) {

    EditProfile ep = new EditProfile();

    // ToDo: Sollte man umändern, wirkt ziemlich unsicher
    ep.getProfilFromServer = result;

    // Profile Edit - Panel wird erzeugt und eingefügt.
    HTMLPanel editProfilePanel =
        new HTMLPanel("<h3>" + "Hier können Sie ihre Profilinformationen bearbeiten." + "</h3>");

    editProfilePanel.add(ep);

    RootPanel.get("contwrap").clear();
    RootPanel.get("contwrap").add(editProfilePanel);

  }

  public LoginInfo getLoginInfo() {
    return loginInfo;
  }

  public void setLoginInfo(LoginInfo loginInfo) {
    this.loginInfo = loginInfo;
  }

}
