package de.hdm.partnerboerse.client;

/**
 * Hilfsmethoden für den Umgang mit der GUI
 * 
 * 
 */
public class GuiUtils {

	/**
	 * Konvertiert einen Boolean Wert in Ja bzw. Nein.
	 * 
	 * @param boolWert
	 *            - Boolscher Wert
	 * @return "Ja" wenn true, "Nein" wenn false.
	 */
	public static String getJaNein(boolean boolWert) {
		if (boolWert) {
			return "Ja";
		} else {
			return "Nein";
		}
	}

}
