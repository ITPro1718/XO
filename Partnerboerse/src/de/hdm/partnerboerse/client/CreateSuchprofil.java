package de.hdm.partnerboerse.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Profil;
import de.hdm.partnerboerse.shared.bo.Suchprofil;

public class CreateSuchprofil extends VerticalPanel {

  private final PartnerboerseAdministrationAsync partnerAdmin =
      GWT.create(PartnerboerseAdministration.class);

  	Button safeButton = new Button("speichern");
  
	// Attribute für SuchprofilGrid

	Label bdayLabel = new Label("Alter: ");
	Label hcolorLabel = new Label("Haarfarbe: ");
	Label heightLabel = new Label("Größe (im cm)");
	Label smokerLabel = new Label("Raucher: ");
	Label religionLabel = new Label("Religion: ");
	Label titleLabel = new Label("Name des Suchprofils:");

	
	TextBox bdayTextBox = new TextBox();
	ListBox hcolorListBox = new ListBox();
	TextBox heightTextBox = new TextBox();
	ListBox smokerListBox = new ListBox();
	ListBox religionListBox = new ListBox();
	TextBox titleTextBox = new TextBox();


	/**
	 * TODO wenn EigenschaftsModell steht
	 * // Eigenschaften für InfoGrid 
	 * Label sdescriptLab = new Label("Beschreibe dich kurz: "); 
	 * Label hobbyLab = new Label("Deine Hobbies: "); 
	 * Label musicLab = new Label("Deine lieblings Musik: "); 
	 * Label searchForLab = newLabel("Du Bist auf der Suche Nach? "); 
	 * Label sexOrientLab = new Label("Deine sexuelle Ausrichtung: ");
	 * 
	 * TextBox hobby = new TextBox(); 
	 * TextBox music = new TextBox(); 
	 * TextArea sdescript = new TextArea(); 
	 * ListBox sexOrient = new ListBox(); ListBox
	 * searchFor = new ListBox();
	 **/

  @Override
  public void onLoad() {

    /**
     * DropDown in Profil genauso wie bei Suchprofil
     */

    hcolorListBox.addItem("braun", "braun");
    hcolorListBox.addItem("blond", "blond");
    hcolorListBox.addItem("schwarz", "schwarz");
    hcolorListBox.addItem("rot", "rot");
    hcolorListBox.addItem("andere", "andere");

    religionListBox.addItem("katholisch", "katholisch");
    religionListBox.addItem("evangelisch", "evangelisch");
    religionListBox.addItem("moslem", "moslem");
    religionListBox.addItem("buddhist", "buddhist");
    religionListBox.addItem("hindu", "hindu");
    religionListBox.addItem("atheist", "atheist");
    religionListBox.addItem("andere", "andereRel+");

    smokerListBox.addItem("Ja", "YSmoker");
    smokerListBox.addItem("Nein", "NSmoker");

    // Grid erstellen zur besseren Darstellung

    FlexTable SprofilGrid = new FlexTable();
    SprofilGrid.setStyleName("etable");
    this.add(SprofilGrid);

    // Spalte 2
    SprofilGrid.setWidget(0, 0, bdayLabel);
    SprofilGrid.setWidget(0, 1, bdayTextBox);

    // Spalte 4
    SprofilGrid.setWidget(1, 0, hcolorLabel);
    SprofilGrid.setWidget(1, 1, hcolorListBox);

    SprofilGrid.setWidget(2, 0, heightLabel);
    SprofilGrid.setWidget(2, 1, heightTextBox);

    // Spalte 5
    SprofilGrid.setWidget(0, 2, smokerLabel);
    SprofilGrid.setWidget(0, 3, smokerListBox);

    // Spalte 6
    SprofilGrid.setWidget(1, 2, religionLabel);
    SprofilGrid.setWidget(1, 3, religionListBox);

    // Spalte 7
    SprofilGrid.setWidget(2, 2, titleLabel);
    SprofilGrid.setWidget(2, 3, titleTextBox);

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
            (float) search.getKoerpergroesse(), search.isRaucher(), search.getReligion(),
            search.getAlter(), new AsyncCallback<Void>() {

              @Override
              public void onFailure(Throwable caught) {
            	  Window.alert("Suchprofil wurde nicht gespeichert.");

            }

              @Override
              public void onSuccess(Void result) {
                ListViewSuchProfil lvsp = new ListViewSuchProfil();

                HTMLPanel splistViewPanel =
                    new HTMLPanel("<h3>" + "Hier können sie ein Suchprofil erstellen!" + "</h3>");
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
    int alter = Integer.parseInt(bdayTextBox.getValue());
    s.setAlter(alter);
    s.setHaarFarbe(hcolorListBox.getSelectedValue());
    float kgr = Float.parseFloat(heightTextBox.getValue());
    s.setKoerpergroesse(kgr);

    String raucherSelectedValue = smokerListBox.getSelectedValue();
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

    s.setReligion(religionListBox.getSelectedValue());
    s.setTitle(titleTextBox.getValue());

    return s;
  }
}
