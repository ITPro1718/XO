package de.hdm.partnerboerse.client.editor.forms;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * <code> CreateWidget </code> erstellt die Attribute der Profile und der
 * Suchprofile, sowie dessegn getter und setter Methoden;
 *
 */
public class CreateWidget extends VerticalPanel {
	/**
	 * Labels für die Attribute
	 */
	private Label vnameLabel = new Label("Vorname: ");
	private Label lnameLabel = new Label("Nachname: ");
	private Label bdayLabel = new Label("Geburtstag: ");
	private Label alterLabel = new Label("Alter ab: ");
	private Label hcolorLabel = new Label("Haarfarbe: ");
	private Label pheightLabel = new Label("Größe (in cm): ");
	private Label spheightLabel = new Label("Größe ab (in cm): ");
	private Label smokerLabel = new Label("Raucher: ");
	private Label religionLabel = new Label("Religion: ");
	private Label titleLabel = new Label("Name des Suchprofils:");
	private Label emailLabel = new Label("E-Mail: ");
	private Label sexLabel = new Label("Geschlecht: ");
	private Label searchForLabel = new Label("Ich suche nach: ");

	/**
	 * TextBoxes für die Attribute
	 */

	private TextBox vnameTextBox = new TextBox();
	private TextBox lnameTextBox = new TextBox();
	private TextBox bdayTextBox = new TextBox();
	private TextBox alterTextBox = new TextBox();
	private TextBox pheightTextBox = new TextBox();
	private TextBox titleTextBox = new TextBox();

	/**
	 * ListBoxes für die Attribute
	 */
	private ListBox hcolorListBox = new ListBox();
	private ListBox religionListBox = new ListBox();
	private ListBox smokerListBox = new ListBox();
	private ListBox alterListBox = new ListBox();
	private ListBox heightListBox = new ListBox();
	private ListBox sexListBox = new ListBox();
	private ListBox searchForListBox = new ListBox();

	/**
	 * Label getter und setter
	 * 
	 */
	// Attribut Vorname
	public Label getVnameLabel() {
		return vnameLabel;
	}

	public void setVnameLabel(Label vnameLabel) {
		this.vnameLabel = vnameLabel;
	}

	// Attribut Nachname

	public Label getLnameLabel() {
		return lnameLabel;
	}

	public void setLnameLabel(Label lnameLabel) {
		this.lnameLabel = lnameLabel;
	}

	// Attribut Geburtstag

	public Label getBdayLabel() {
		return bdayLabel;
	}

	public void setBdayLabel(Label bdayLabel) {
		this.bdayLabel = bdayLabel;
	}

	// Attribut Haarfarbe

	public Label getHcolorLabel() {
		return hcolorLabel;
	}

	public void setHcolorLabel(Label hcolorLabel) {
		this.hcolorLabel = hcolorLabel;
	}

	// Attribut Körpergröße

	public Label getPHeightLabel() {
		return pheightLabel;
	}

	public void setPHeightLabel(Label heightLabel) {
		this.pheightLabel = heightLabel;
	}

	// Attribut Raucher

	public Label getSmokerLabel() {
		return smokerLabel;
	}

	public void setSmokerLabel(Label smokerLabel) {
		this.smokerLabel = smokerLabel;
	}

	// Attribut Raucher

	public Label getReligionLabel() {
		return religionLabel;
	}

	public void setReligionLabel(Label religionLabel) {
		this.religionLabel = religionLabel;
	}

	// Attribut Titel
	public Label getTitleLabel() {
		return titleLabel;
	}

	public void setTitleLabel(Label titleLabel) {
		this.titleLabel = titleLabel;
	}

	// Attribut Alter

	public Label getAlterLabel() {
		return alterLabel;
	}

	public void setAlterLabel(Label alterLabel) {
		this.alterLabel = alterLabel;
	}

	// Attribut EMail

	public Label getEmailLabel() {
		return emailLabel;
	}

	public void setEmailLabel(Label emailLabel) {
		this.emailLabel = emailLabel;
	}

	// Attribut Suchprofil Körpergröße

	public Label getSpheightLabel() {
		return spheightLabel;
	}

	public void setSpheightLabel(Label spheightLabel) {
		this.spheightLabel = spheightLabel;
	}

	// Attribut Geschlecht

	public Label getSexLabel() {
		return sexLabel;
	}

	public void setSexLabel(Label sexLabel) {
		this.sexLabel = sexLabel;
	}

	// Attribut Such Nach

	public Label getSearchForLabel() {
		return searchForLabel;
	}

	public void setSearchForLabel(Label searchForLabel) {
		this.searchForLabel = searchForLabel;
	}

	/**
	 * Textbox getter und setter
	 * 
	 */

	// Attribut Vorname

	public TextBox getVnameTextBox() {
		return vnameTextBox;
	}

	public void setVnameTextBox(TextBox vnameTextBox) {
		this.vnameTextBox = vnameTextBox;
	}

	// Attribut Nachname

	public TextBox getLnameTextBox() {
		return lnameTextBox;
	}

	public void setLnameTextBox(TextBox lnameTextBox) {
		this.lnameTextBox = lnameTextBox;
	}

	// Attribut Geburtstag

	public TextBox getBdayTextBox() {
		return bdayTextBox;
	}

	public void setBdayTextBox(TextBox bdayTextBox) {
		this.bdayTextBox = bdayTextBox;
	}

	// Attribut Körpergröße

	public TextBox getHeightTextBox() {
		return pheightTextBox;
	}

	public void setHeightTextBox(TextBox heightTextBox) {
		this.pheightTextBox = heightTextBox;
	}

	// Attribut Titel

	public TextBox getTitleTextBox() {
		return titleTextBox;
	}

	public void setTitleTextBox(TextBox titleTextBox) {
		this.titleTextBox = titleTextBox;
	}

	// Attribut Alter

	public TextBox getAlterTextBox() {
		return alterTextBox;
	}

	public void setAlterTextBox(TextBox alterTextBox) {
		this.alterTextBox = alterTextBox;
	}

	/**
	 * ListBoxes der Attribut
	 * 
	 */

	// Attribut Haarfarbe

	public ListBox getHcolorListBox() {
		return hcolorListBox;
	}

	public ListBox setHcolorListBox() {
		hcolorListBox.addItem("schwarz", "schwarz");
		hcolorListBox.addItem("braun", "braun");
		hcolorListBox.addItem("blond", "blond");
		hcolorListBox.addItem("grau", "grau");
		hcolorListBox.addItem("glatze", "glatze");
		hcolorListBox.addItem("bunt", "bunt");
		return hcolorListBox;
	}

	// Attribut Religion

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
		religionListBox.addItem("andere", "andere");
		return religionListBox;
	}

	// Attribut Raucher

	public ListBox getSmokerListBox() {
		return smokerListBox;

	}

	public ListBox setSmokerListBox() {

		smokerListBox.addItem("Ja", "Ja");
		smokerListBox.addItem("Nein", "Nein");
		return smokerListBox;
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

	public ListBox getSexListBox() {

		return sexListBox;
	}

	public ListBox setSexListBox() {
		sexListBox.addItem("Mann");
		sexListBox.addItem("Frau");
		return sexListBox;
	}

	public ListBox getSearchForListBox() {
		return searchForListBox;
	}

	public ListBox setSearchForListBox() {
		searchForListBox.addItem("Männer");
		searchForListBox.addItem("Frauen");
		return searchForListBox;
	}

}
