package de.hdm.partnerboerse.client;

import com.google.gwt.user.client.ui.ListBox;

/**
 * Diese Klasse repräsentiert eine Listbox zur Auswahl der Religion. Erweitert
 * die Klasse {@link ListBox} und befüllt die zur Auswahl stehenden Werte.
 * 
 * @author sanjamikulic
 *
 */
public class ReligionListBox extends ListBox {

	/**
	 * Konstruktor - Setzt die Auswahlwerte.
	 */
	public ReligionListBox() {
		this.addItem("katholisch", "katholisch");
		this.addItem("evangelisch", "evangelisch");
		this.addItem("moslem", "moslem");
		this.addItem("buddhist", "buddhist");
		this.addItem("hindu", "hindu");
		this.addItem("atheist", "atheist");
		this.addItem("andere", "andereRel+");
	}
}
