package de.hdm.partnerboerse.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * 
 * 
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

	public Label getPHeightLabel() {
		return pheightLabel;
	}

	public void setPHeightLabel(Label heightLabel) {
		this.pheightLabel = heightLabel;
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
		hcolorListBox.addItem("glatze", "glatze");
		hcolorListBox.addItem("sonstige", "sonstige");
		return hcolorListBox;
	}

	public TextBox getHeightTextBox() {
		return pheightTextBox;
	}

	public void setHeightTextBox(TextBox heightTextBox) {
		this.pheightTextBox = heightTextBox;
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

		smokerListBox.addItem("Ja", "Ja");
		smokerListBox.addItem("Nein", "Nein");
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

	public Label getSpheightLabel() {
		return spheightLabel;
	}

	public void setSpheightLabel(Label spheightLabel) {
		this.spheightLabel = spheightLabel;
	}

	public Label getSexLabel() {
		return sexLabel;
	}

	public void setSexLabel(Label sexLabel) {
		this.sexLabel = sexLabel;
	}

	public Label getSearchForLabel() {
		return searchForLabel;
	}

	public void setSearchForLabel(Label searchForLabel) {
		this.searchForLabel = searchForLabel;
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
