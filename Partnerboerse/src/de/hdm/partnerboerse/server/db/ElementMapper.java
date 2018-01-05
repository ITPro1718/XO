package de.hdm.partnerboerse.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.partnerboerse.shared.bo.Auswahl;
import de.hdm.partnerboerse.shared.bo.Element;

/**
 * Diese Mapper Klasse dient zur Abbildung von {@link Element} Objekten auf eine
 * relationale Datenbank. Das Mapping ist bidirektional, Objekte werden auf
 * DB-Strukturen abgebildet und DB-Strukturen auf Java-Objekte.
 * 
 * @author Mikulic
 *
 */
public class ElementMapper {

	/**
	 * Stellt sicher, dass die Klasse nur ein mal instanziiert wird *
	 */
	private static ElementMapper elementMapper = null;

	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit
	 * <code>new</code> neue Instanzen dieser Klasse zu erzeugen.
	 */
	protected ElementMapper() {
	}

	/**
	 * Stellt sicher, dass es nur eine Instanz der Klasse gibt. Die Klasse kann
	 * nicht mit <code>new</code> instanziiert werden
	 * 
	 * @return <code>ElementMapper</code>-Objekt.
	 */
	public static ElementMapper elementMapper() {
		if (elementMapper == null) {
			elementMapper = new ElementMapper();
		}

		return elementMapper;
	}

	/**
	 * Gibt ein Element-Eintrag aus der Datenbank zurueck
	 * 
	 * @return
	 */
	public Element findByKey(int id) {
		Connection con = DBConnection.getConnection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM element  WHERE id=" + id);

			if (rs.next()) {
				Element element = new Element();
				element.setId(rs.getInt("id"));
				element.setBezeichnung(rs.getString("bezeichnung"));
				element.setAuswahlID(rs.getInt("auswahlID"));
				return element;
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}

		return null;
	}

	/**
	 * Gibt alle Element-Eintraege zurueck
	 * 
	 * @return
	 */
	public ArrayList<Element> findAll() {
		Connection con = DBConnection.getConnection();

		ArrayList<Element> result = new ArrayList<>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM element ORDER BY id");

			while (rs.next()) {
				Element element = new Element();
				element.setId(rs.getInt("id"));
				element.setBezeichnung(rs.getString("bezeichnung"));
				element.setAuswahlID(rs.getInt("auswahlID"));

				result.add(element);
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return result;
	}

	public void deleteElement(Auswahl auswahl) {
		Connection con = DBConnection.getConnection();
		
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM element WHERE auswahlID = " + auswahl.getId());
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		
	}
	
	public void insertElementAuswahl(Element e){
		Connection con = DBConnection.getConnection();
		
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM element");
			
			if (rs.next()){
				e.setId(rs.getInt("maxid") + 1);
				
				stmt = con.createStatement();
				stmt.executeUpdate("INSERT INTO element (id, bezeichnung, auswahlID) VALUES (" + e.getId() + ", " + e.getBezeichnung()
				+ ", " + e.getAuswahlID() + ")");
				
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}