package de.hdm.partnerboerse.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Profil;

public class Searchprofile extends VerticalPanel {

	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);

	Button deleteButton = new Button("Suchprofil löschen");
	Button safeButton = new Button("Suchprofil speichern");



	public void onLoad() {
		// Create a FormPanel and point it at a service.
		final FormPanel form = new FormPanel();
		this.add(form);
		form.setAction("/myFormHandler");

		// Because we're going to add a FileUpload widget,
		// we'll need to set the form to use the POST method,
		// and multipart MIME encoding.
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);

		// Create a panel to hold all of the form widgets.
		VerticalPanel panel = new VerticalPanel();
		panel.setSpacing(10);
		form.setWidget(panel);

		Label bdayLabel = new Label("Alter: ");
		Label hcolorLabel = new Label("Haarfarbe: ");
		Label heightLabel = new Label("Größe: ");
		Label smokerLabel = new Label("Raucher: ");
		Label religionLabel = new Label("Religion: ");
		Label nationLabel = new Label("Nationalität: ");


		ListBox bdayListBox = new ListBox();
		bdayListBox.addItem("20", "20");
		bdayListBox.addItem("30", "30");
		bdayListBox.addItem("40", "40");
		bdayListBox.addItem("50", "50");
		bdayListBox.addItem("60", "60");
		
		ListBox hcolorListBox = new ListBox();
		hcolorListBox.addItem("braun", "braun");
		hcolorListBox.addItem("blond", "blond");
		hcolorListBox.addItem("schwarz", "schwarz");
		hcolorListBox.addItem("rot", "rot");
		hcolorListBox.addItem("andere+", "andere");
		
		ListBox heightListBox = new ListBox();
		heightListBox.addItem("150", "150");
		heightListBox.addItem("160", "160");
		heightListBox.addItem("170", "170");
		heightListBox.addItem("180", "180");
		heightListBox.addItem("190", "190");
		heightListBox.addItem("200", "200");
		
		ListBox religionListBox = new ListBox();
		religionListBox.addItem("katholisch", "katholisch");
		religionListBox.addItem("evangelisch", "evangelisch");
		religionListBox.addItem("moslem", "moslem");
		religionListBox.addItem("buddhist", "buddhist");
		religionListBox.addItem("hindu", "hindu");
		religionListBox.addItem("atheist", "atheist");
		religionListBox.addItem("andere", "andereRel+");
		
		ListBox smokerListBox = new ListBox();
		smokerListBox.addItem("Ja", "YSmoker");
		smokerListBox.addItem("Nein", "NSmoker");
		smokerListBox.addItem("Gelegentlich", "SSmoker");
		
		TextBox nationTB = new TextBox();
		
		// Grid erstellen zur besseren Darstellung


		Grid SprofilGrid = new Grid(3, 4);
		SprofilGrid.setStyleName("etable");
		panel.add(SprofilGrid);


		// Spalte 2
		SprofilGrid.setWidget(0, 0, bdayLabel);
		SprofilGrid.setWidget(0, 1, bdayListBox);

		// Spalte 4
		SprofilGrid.setWidget(1, 0, hcolorLabel);
		SprofilGrid.setWidget(1, 1, hcolorListBox);

		SprofilGrid.setWidget(2, 0, heightLabel);
		SprofilGrid.setWidget(2, 1, heightListBox);

		// Spalte 5
		SprofilGrid.setWidget(0, 2, smokerLabel);
		SprofilGrid.setWidget(0, 3, smokerListBox);

		// Spalte 6
		SprofilGrid.setWidget(1, 2, religionLabel);
		SprofilGrid.setWidget(1, 3, religionListBox);
		
		// Spalte 3
		SprofilGrid.setWidget(2, 2, nationLabel);
		SprofilGrid.setWidget(2, 3, nationTB);
		
		final String haircolor = hcolorListBox.getSelectedItemText();
		final int height = Integer.parseInt(heightListBox.getSelectedItemText());
		final boolean raucher = true;
		final int alter = Integer.parseInt(bdayListBox.getSelectedItemText());
		final String religion = religionListBox.getSelectedItemText();
		

		// Add a 'submit' button.
		panel.add(new Button("Submit", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				form.submit();
				
				createSuchprofilCallback();
			}
			
			private void createSuchprofilCallback(){				
				
				
				Profil source = new Profil();
				source.setId(5);
				
				partnerAdmin.createSuchprofil(source, "Suchprofil Test", haircolor, height, raucher, 
						religion, alter, new AsyncCallback<Void>(){

							@Override
							public void onFailure(Throwable caught) {
							
								
							}

							@Override
							public void onSuccess(Void result) {
								System.out.println(haircolor);
								System.out.println(alter);
								System.out.println(religion);
								
							}
					
				});
				
			}
		}));
			
	}

}