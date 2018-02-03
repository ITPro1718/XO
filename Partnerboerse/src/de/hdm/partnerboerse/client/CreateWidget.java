package de.hdm.partnerboerse.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * In der CreateWidget Klasse werden alle Attribute die mehrmals genutzt werden,
 * definiert. Damit verhindern wir die redundante Erstellung von Attributen.
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
	 * Getter für das Vornamen-Label
	 */
	public Label getVnameLabel() {
		return vnameLabel;
	}

	/**
	 * Setter für das Vornamen-Label
	 */
	public void setVnameLabel(Label vnameLabel) {
		this.vnameLabel = vnameLabel;
	}

	/**
	 * Getter für das Nachnamen-Label
	 */
	public Label getLnameLabel() {
		return lnameLabel;
	}

	/**
	 * Setter für das Nachnamen-Label
	 */
	public void setLnameLabel(Label lnameLabel) {
		this.lnameLabel = lnameLabel;
	}
	/**
	 * Getter für das Geburtstags-Label
	 */
	public Label getBdayLabel() {
		return bdayLabel;
	}

	/**
	 * Setter für das Geburtstags-Label
	 */
	public void setBdayLabel(Label bdayLabel) {
		this.bdayLabel = bdayLabel;
	}
	
	/**
	 * Getter für die Haarfarbe
	 */
	public Label getHcolorLabel() {
		return hcolorLabel;
	}
	
	/**
	 * Setter für das Haarfarben-Label
	 */
	public void setHcolorLabel(Label hcolorLabel) {
		this.hcolorLabel = hcolorLabel;
	}

	/**
	 * Getter für die Größe
	 */
	public Label getPHeightLabel() {
		return pheightLabel;
	}

	/**
	 * Setter für das Größen-Label
	 */
	public void setPHeightLabel(Label heightLabel) {
		this.pheightLabel = heightLabel;
	}

	/**
	 * Getter für das Raucherstatus-Label
	 */
	public Label getSmokerLabel() {
		return smokerLabel;
	}

	/**
	 * Setter für das Raucherstatus-Label
	 */
	public void setSmokerLabel(Label smokerLabel) {
		this.smokerLabel = smokerLabel;
	}

	/**
	 * Getter für das Religions-Label
	 */
	public Label getReligionLabel() {
		return religionLabel;
	}

	/**
	 * Setter für das Religions-Label
	 */
	public void setReligionLabel(Label religionLabel) {
		this.religionLabel = religionLabel;
	}

	/**
	 * Getter für die Vornamen-Textbox
	 */
	public TextBox getVnameTextBox() {
		return vnameTextBox;
	}

	/**
	 * Setter für die Vornamen-Textbox
	 */
	public void setVnameTextBox(TextBox vnameTextBox) {
		this.vnameTextBox = vnameTextBox;
	}

	/**
	 * Getter für die Nachnamen-Textbox
	 */
	public TextBox getLnameTextBox() {
		return lnameTextBox;
	}

	/**
	 * Setter für die Nachnamen-Textbox
	 */
	public void setLnameTextBox(TextBox lnameTextBox) {
		this.lnameTextBox = lnameTextBox;
	}

	/**
	 * Getter für die Geburtstags-Textbox
	 */
	public TextBox getBdayTextBox() {
		return bdayTextBox;
	}

	/**
	 * Setter für die Geburtstags-Textbox
	 */
	public void setBdayTextBox(TextBox bdayTextBox) {
		this.bdayTextBox = bdayTextBox;
	}

	/**
	 * Getter für die Haarfarbe-Textbox
	 */
	public ListBox getHcolorListBox() {
		return hcolorListBox;
	}

	/**
	 * Setter Liste für die Haarfabren Auswahl
	 */
	public ListBox setHcolorListBox() {
		hcolorListBox.addItem("schwarz", "schwarz");
		hcolorListBox.addItem("braun", "braun");
		hcolorListBox.addItem("blond", "blond");
		hcolorListBox.addItem("grau", "grau");
		hcolorListBox.addItem("glatze", "glatze");
		hcolorListBox.addItem("sonstige", "sonstige");
		return hcolorListBox;
	}

	/**
	 * Getter für die Körpergrößen-Textbox
	 */
	public TextBox getHeightTextBox() {
		return pheightTextBox;
	}

	/**
	 * Setter für die Körpgergrößen-Textbox
	 */
	public void setHeightTextBox(TextBox heightTextBox) {
		this.pheightTextBox = heightTextBox;
	}

	/**
	 * Getter für die Religions-Textbox
	 */
	public ListBox getReligionListBox() {
		return religionListBox;
	}

	/**
	 * Setter Liste für die Religionsauswahl
	 */
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

	/**
	 * Getter für die Raucherstatus-Textbox
	 */
	public ListBox getSmokerListBox() {
		return smokerListBox;

	}

	/**
	 * Setter für die Raucherstatus-Textbox
	 */
	public ListBox setSmokerListBox() {

		smokerListBox.addItem("Ja", "Ja");
		smokerListBox.addItem("Nein", "Nein");
		return smokerListBox;
	}

	/**
	 * Getter für das Titel-Label
	 */
	public Label getTitleLabel() {
		return titleLabel;
	}
	
	/**
	 * Setter für das Titel-Label
	 */
	public void setTitleLabel(Label titleLabel) {
		this.titleLabel = titleLabel;
	}

	/**
	 * Getter für die Titel-Textbox
	 */
	public TextBox getTitleTextBox() {
		return titleTextBox;
	}

	/**
	 * Setter für die Titel-Textbox
	 */
	public void setTitleTextBox(TextBox titleTextBox) {
		this.titleTextBox = titleTextBox;
	}

	/**
	 * Getter für das Alter-Label
	 */
	public Label getAlterLabel() {
		return alterLabel;
	}

	/**
	 * Setter für das Alter-Label
	 */
	public void setAlterLabel(Label alterLabel) {
		this.alterLabel = alterLabel;
	}

	/**
	 * Getter für die Alter-Textbox
	 */
	public TextBox getAlterTextBox() {
		return alterTextBox;
	}

	/**
	 * Setter für die Alter-Textbox
	 */
	public void setAlterTextBox(TextBox alterTextBox) {
		this.alterTextBox = alterTextBox;
	}

	/**
	 * Getter für das Email-Label
	 */
	public Label getEmailLabel() {
		return emailLabel;
	}

	/**
	 * Setter für das Email-Label
	 */
	public void setEmailLabel(Label emailLabel) {
		this.emailLabel = emailLabel;
	}

	/**
	 * Getter für die Alter-Listbox
	 */
	public ListBox getAlterListBox() {
		return alterListBox;
	}

	/**
	 * Setter Liste für das Alter
	 */
	public ListBox setAlterListBox() {

		alterListBox.addItem("20", "20");
		alterListBox.addItem("30", "30");
		alterListBox.addItem("40", "40");
		alterListBox.addItem("50", "50");
		return alterListBox;
	}

	/**
	 * Getter für die Körpergrößen-Listbox
	 */
	public ListBox getHeightListBox() {
		return heightListBox;
	}

	/**
	 * Setter für die Körpergrößen-Listbox
	 */
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
	 * Getter für das Suchprofil Körpergrößen-Label
	 */
	public Label getSpheightLabel() {
		return spheightLabel;
	}

	/**
	 * Setter für das Suchprofil Körpergrößen-Label
	 */
	public void setSpheightLabel(Label spheightLabel) {
		this.spheightLabel = spheightLabel;
	}

	/**
	 * Getter für das Geschlecht-Label
	 */
	public Label getSexLabel() {
		return sexLabel;
	}

	/**
	 * Setter für das Geschlecht-Label
	 */
	public void setSexLabel(Label sexLabel) {
		this.sexLabel = sexLabel;
	}

	/**
	 * Getter für das Suche-Nach-Label
	 */
	public Label getSearchForLabel() {
		return searchForLabel;
	}

	/**
	 * Setter für das Suche-Nach-Label
	 */
	public void setSearchForLabel(Label searchForLabel) {
		this.searchForLabel = searchForLabel;
	}

	/**
	 * Getter für die Geschlecht-Listbox
	 */
	public ListBox getSexListBox() {

		return sexListBox;
	}

	/**
	 * Setter für die Geschlecht-Listbox
	 */
	public ListBox setSexListBox() {
		sexListBox.addItem("Mann");
		sexListBox.addItem("Frau");
		return sexListBox;
	}

	/**
	 * Getter für die Suche-Nach-Listbox
	 */
	public ListBox getSearchForListBox() {
		return searchForListBox;
	}

	/**
	 * Setter für die Suche-Nach-Listbox
	 */
	public ListBox setSearchForListBox() {
		searchForListBox.addItem("Männer");
		searchForListBox.addItem("Frauen");
		return searchForListBox;
	}

}
