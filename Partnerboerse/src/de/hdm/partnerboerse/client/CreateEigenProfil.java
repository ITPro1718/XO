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
  
  Label sdescriptLab = new Label("Beschreibe dich kurz: ");
  Label hobbyLab = new Label("Deine Hobbies: ");
  Label jobLab = new Label("Dein Beruf: ");
  Label nationLab = new Label("Deine Nationalität: ");
  Label eduLab = new Label("Dein Bildungsniveau: ");
  Label musicLab = new Label("Deine lieblings Musik: ");

  Label sexPrefLab = new Label("Deine sexuellen Vorlieben: ");
  Label searchForLab = new Label("Du Bist auf der Suche Nach? ");
  Label sexOrientLab = new Label("Deine sexuelle Ausrichtung: ");

  TextBox hobby = new TextBox();
  TextBox job = new TextBox();
  TextBox nation = new TextBox();
  TextBox music = new TextBox();
  TextArea sdescript = new TextArea();
  TextBox sexPref = new TextBox();

  ListBox edu = new ListBox();
  ListBox sexOrient = new ListBox();
  ListBox searchFor = new ListBox();



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
    
    FlexTable descripton = new FlexTable();
    descripton.setStyleName("desctable");
    this.add(descripton);

    descripton.setWidget(0, 0, sdescriptLab);
    descripton.setWidget(0, 1, sdescript);
    // sdescript.setValue(getProfilFromServer.getVorname());

    Grid infoGrid = new Grid(4, 6);
    infoGrid.setStyleName("etable");
    this.add(infoGrid);

    infoGrid.setWidget(0, 1, hobbyLab);
    infoGrid.setWidget(0, 2, hobby);
    // hobby.setValue(getProfilFromServer.getNachname());

    // Spalte 2
    infoGrid.setWidget(0, 3, jobLab);
    infoGrid.setWidget(0, 4, job);
    // job.setValue(getProfilFromServer.getGeburtsdatum().toString());

    infoGrid.setWidget(1, 1, nationLab);
    infoGrid.setWidget(1, 2, nation);
    // nation.setValue(getProfilFromServer.getEmail());

    // Spalte 3
    edu.addItem("Universität", "uni");
    edu.addItem("Abitur", "abi");
    edu.addItem("Fachhochschulreife", "fh");
    edu.addItem("Realschulabschluss", "real");
    edu.addItem("Hauptschulabschluss", "haupt");
    edu.addItem("Andere", "andere");

    infoGrid.setWidget(1, 3, eduLab);
    infoGrid.setWidget(1, 4, edu);

    // Spalte 4
    infoGrid.setWidget(2, 1, musicLab);
    infoGrid.setWidget(2, 2, music);
    // music.setValue(getProfilFromServer.getHaarfarbe());

    infoGrid.setWidget(2, 3, sexPrefLab);
    infoGrid.setWidget(2, 4, sexPref);
    // heightTextBox.setValue(String.valueOf(getProfilFromServer.getKoerpergroesse()));

    // Spalte 5
    getItemsOfAuswahl();
 

    infoGrid.setWidget(3, 1, sexOrientLab);
    infoGrid.setWidget(3, 2, sexOrient);

    infoGrid.setWidget(3, 3, searchForLab);
    infoGrid.setWidget(3, 4, searchFor);

    /**
     * Button zum Speichern des eigenen geänderten Profils
     */
    createButton.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {

        createProfileOnServer();
        generateInfosOfUser();
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
      
      private void generateInfosOfUser(){
						
			// TODO: aktuelles Profil verwenden und kein Mockup
			Profil prof = new Profil();
			prof.setId(1);
			
			
			Freitext f = new Freitext();
			f.setBeschreibung(sdescript.getValue());			
			Info i = new Info();
			i.setText(sdescriptLab.getText());			
			partnerAdmin.createEigenschaftForFreitext(prof, i, f, new AsyncCallback<Void>(){

				@Override
				public void onFailure(Throwable caught) {
				}

				@Override
				public void onSuccess(Void result){
					nextFreitextCallback();
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
  
  private void getItemsOfAuswahl(){
	  
	  	  
	  partnerAdmin.getAuswahl(new AsyncCallback<ArrayList<Auswahl>>(){

		@Override
		public void onFailure(Throwable caught) {			
		}

		@Override
		public void onSuccess(ArrayList<Auswahl> result) {

			for (Auswahl a : result){
				if ( a.getAuswahlFor() == "Sexualität"){
					sexOrient.addItem(a.getTitel(), a.getTitel());					
				}
				if (a.getAuswahlFor() == "searchingFor"){
					searchFor.addItem(a.getTitel(), a.getTitel());
				}
			}				
		}
				  
	  });
	  	  
  }

  public LoginInfo getLoginInfo() {
    return loginInfo;
  }

  public void setLoginInfo(LoginInfo loginInfo) {
    this.loginInfo = loginInfo;
  }
  
  private void nextFreitextCallback(){
	  	
	  	Profil prof = new Profil();
		prof.setId(1);
	  
		Freitext f1 = new Freitext();
		f1.setBeschreibung(hobby.getValue());
		Info i1 = new Info();
		i1.setText(hobbyLab.getText());
		partnerAdmin.createEigenschaftForFreitext(prof, i1, f1, new AsyncCallback<Void>(){

			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(Void result) {
				auswahlCallback();
			}
			
		});
	  
  }
  
  private void auswahlCallback(){
	  
	  	Profil prof = new Profil();
		prof.setId(1);
	  
	  	Auswahl a = new Auswahl();
		a.setTitel(searchFor.getSelectedValue());
		Info i3 = new Info();
		i3.setText(searchForLab.getText());
		partnerAdmin.createEigenschaftForAuswahl(prof, i3, a, new AsyncCallback<Void>(){

			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(Void result) {
			}
		});
  }
}
