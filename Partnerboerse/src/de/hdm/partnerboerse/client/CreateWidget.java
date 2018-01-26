package de.hdm.partnerboerse.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Diese Klasse repräsentiert eine Listbox zur Auswahl der Religion. Erweitert
 * die Klasse {@link ListBox} und befüllt die zur Auswahl stehenden Werte.
 * 
 * @author sanjamikulic
 *
 */
public class CreateWidget extends VerticalPanel {
 /**
  * Labels für die Attribute
  */
	private Label vnameLabel = new Label("Vorname: ");
	private	Label lnameLabel = new Label("Nachname: ");
	private	Label bdayLabel = new Label("Geburtstag: ");
	private Label alterLabel = new Label("Alter ab: ");
	private	Label hcolorLabel = new Label("Haarfarbe: ");
	private	Label heightLabel = new Label("Größe (in cm) ab: ");
	private	Label smokerLabel = new Label("Raucher: ");
	private	Label religionLabel = new Label("Religion: ");
	private Label titleLabel = new Label("Name des Suchprofils:");
	private Label emailLabel = new Label("E-Mail: ");
	
	/**
	 * TextBoxes für die Attribute
	 */

	private	TextBox vnameTextBox = new TextBox();
	private	TextBox lnameTextBox = new TextBox();
	private	TextBox bdayTextBox = new TextBox();
	private TextBox alterTextBox = new TextBox();
	private	TextBox heightTextBox = new TextBox();
	private TextBox titleTextBox = new TextBox();

	
	/**
	 * ListBoxes für die Attribute
	 */
	private	ListBox hcolorListBox = new ListBox();
	private	ListBox religionListBox = new ListBox();
	private	ListBox smokerListBox = new ListBox();
	private ListBox alterListBox = new ListBox();
	private ListBox heightListBox = new ListBox();
	
	

	public Label getVnameLabel() {
		return vnameLabel;
	}


	public void setVnameLabel(Label vnameLabel) {
		this.vnameLabel = vnameLabel;
	}


	public Label getLnameLabel() {
		return lnameLabel;
	}


	public void setLnameLabel(Label lnameLabel) {
		this.lnameLabel = lnameLabel;
	}


	public Label getBdayLabel() {
		return bdayLabel;
	}


	public void setBdayLabel(Label bdayLabel) {
		this.bdayLabel = bdayLabel;
	}


	public Label getHcolorLabel() {
		return hcolorLabel;
	}


	public void setHcolorLabel(Label hcolorLabel) {
		this.hcolorLabel = hcolorLabel;
	}


	public Label getHeightLabel() {
		return heightLabel;
	}


	public void setHeightLabel(Label heightLabel) {
		this.heightLabel = heightLabel;
	}


	public Label getSmokerLabel() {
		return smokerLabel;
	}


	public void setSmokerLabel(Label smokerLabel) {
		this.smokerLabel = smokerLabel;
	}


	public Label getReligionLabel() {
		return religionLabel;
	}


	public void setReligionLabel(Label religionLabel) {
		this.religionLabel = religionLabel;
	}


	public TextBox getVnameTextBox() {
		return vnameTextBox;
	}


	public void setVnameTextBox(TextBox vnameTextBox) {
		this.vnameTextBox = vnameTextBox;
	}


	public TextBox getLnameTextBox() {
		return lnameTextBox;
	}


	public void setLnameTextBox(TextBox lnameTextBox) {
		this.lnameTextBox = lnameTextBox;
	}


	public TextBox getBdayTextBox() {
		return bdayTextBox;
	}


	public void setBdayTextBox(TextBox bdayTextBox) {
		this.bdayTextBox = bdayTextBox;
	}


	public ListBox getHcolorListBox() {
		return hcolorListBox;
	}


	public ListBox setHcolorListBox() {
		hcolorListBox.addItem("schwarz", "schwarz");
		hcolorListBox.addItem("braun", "braun");
		hcolorListBox.addItem("blond", "blond");
		hcolorListBox.addItem("grau", "grau");
		hcolorListBox.addItem("sonstige", "sonstige");
		return hcolorListBox;
	}


	public TextBox getHeightTextBox() {
		return heightTextBox;
	}


	public void setHeightTextBox(TextBox heightTextBox) {
		this.heightTextBox = heightTextBox;
	}


	public ListBox getReligionListBox() {
		return religionListBox;
	}


	public ListBox setReligionListBox() {

		religionListBox.addItem("katholisch", "katholisch");
		religionListBox.addItem("evangelisch", "evangelisch");
		religionListBox.addItem("moslem", "moslem");
		religionListBox.addItem("buddhist", "buddhist");
		religionListBox.addItem("hindu", "hindu");
		religionListBox.addItem("atheist", "atheist");
		religionListBox.addItem("andere", "andereRel+");
		return religionListBox;
	}


	public ListBox getSmokerListBox() {
		return smokerListBox;

	}


	public ListBox setSmokerListBox() {

		smokerListBox.addItem("Ja", "YSmoker");
		smokerListBox.addItem("Nein", "NSmoker");
		return smokerListBox;
	}


	public Label getTitleLabel() {
		return titleLabel;
	}


	public void setTitleLabel(Label titleLabel) {
		this.titleLabel = titleLabel;
	}


	public TextBox getTitleTextBox() {
		return titleTextBox;
	}


	public void setTitleTextBox(TextBox titleTextBox) {
		this.titleTextBox = titleTextBox;
	}


	public Label getAlterLabel() {
		return alterLabel;
	}


	public void setAlterLabel(Label alterLabel) {
		this.alterLabel = alterLabel;
	}


	public TextBox getAlterTextBox() {
		return alterTextBox;
	}


	public void setAlterTextBox(TextBox alterTextBox) {
		this.alterTextBox = alterTextBox;
	}


	public Label getEmailLabel() {
		return emailLabel;
	}


	public void setEmailLabel(Label emailLabel) {
		this.emailLabel = emailLabel;
	}


	public ListBox getAlterListBox() {
		return alterListBox;
	}


	public ListBox setAlterListBox() {

		alterListBox.addItem("20", "20");
		alterListBox.addItem("30", "30");
		alterListBox.addItem("40", "40");
		alterListBox.addItem("50", "50");
		return alterListBox;
	}


	public ListBox getHeightListBox() {
		return heightListBox;
	}


	public ListBox setHeightListBox() {

		heightListBox.addItem("150", "150");
		heightListBox.addItem("160", "160");
		heightListBox.addItem("170", "170");
		heightListBox.addItem("180", "180");
		heightListBox.addItem("190", "190");
		heightListBox.addItem("200", "200");

		return heightListBox;
	}
	

	

	
	
	
	/**
	 * Konstruktor - Setzt die Auswahlwerte.
	 */
/*	public CreateWidget() {
		this.addItem("katholisch", "katholisch");
		this.addItem("evangelisch", "evangelisch");
		this.addItem("moslem", "moslem");
		this.addItem("buddhist", "buddhist");
		this.addItem("hindu", "hindu");
		this.addItem("atheist", "atheist");
		this.addItem("andere", "andereRel+");
	}*/
}
