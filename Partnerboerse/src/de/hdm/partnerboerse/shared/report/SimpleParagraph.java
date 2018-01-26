package de.hdm.partnerboerse.shared.report;

/**
 * Diese Klasse stellt einzelne AbsÃ¤tze dar. Der Absatzinhalt wird als String
 * gespeichert. Der Anwender sollte in diesem Strinig keinerlei
 * Formatierungssymbole einfügen, da diese in der Regel zielformatspezifisch
 * sind.
 * 
 * @author Thies
 */
public class SimpleParagraph extends Paragraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	  /**
	   * Inhalt des Absatzes.
	   */
	  private String text = "";

	  /**
	   * no argument constructor
	   */
	  public SimpleParagraph() {
	  }

	  /**
	   * Dieser Konstruktor ermöglicht es, bereits bei Instantiierung von
	   * <code>SimpleParagraph</code>-Objekten deren Inhalt angeben zu können.
	   * 
	   * @param value der Inhalt des Absatzes
	   * @see #SimpleParagraph()
	   */
	  public SimpleParagraph(String value) {
	    this.text = value;
	  }

	  /**
	   * Auslesen des Inhalts.
	   * 
	   * @return Inhalt als String
	   */
	  public String getText() {
	    return this.text;
	  }

	  /**
	   * Ãœberschreiben des Inhalts.
	   * 
	   * @param text der neue Inhalt des Absatzes.
	   */
	  public void setText(String text) {
	    this.text = text;
	  }

	  /**
	   * Umwandeln des <code>SimpleParagraph</code>-Objekts in einen String.
	   */
	  public String toString() {
	    return this.text;
	  }

}
