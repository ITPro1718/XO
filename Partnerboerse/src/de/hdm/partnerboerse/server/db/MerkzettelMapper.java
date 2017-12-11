package de.hdm.partnerboerse.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.partnerboerse.shared.bo.Merkzettel;
import de.hdm.partnerboerse.shared.bo.Suchprofil;

public class MerkzettelMapper {

	/**
	   * Stellt sicher, dass die Klasse nur ein mal instanziiert wird   * 
	   */
	  private static MerkzettelMapper merkzettelMapper = null;

	  /**
	   * Geschützter Konstruktor - verhindert die Möglichkeit, mit <code>new</code>
	   * neue Instanzen dieser Klasse zu erzeugen.
	   */
	  protected MerkzettelMapper() {
	  }

	  /**
	   * Stellt sicher, dass es nur eine Instanz der Klasse gibt. Die Klasse kann nicht mit <code>new</code> instanziiert werden
	   * 
	   * @return <code>MerkzettelMapper</code>-Objekt.
	   */
	  public static MerkzettelMapper merkzettelMapper() {
	    if (merkzettelMapper == null) {
	    	merkzettelMapper = new MerkzettelMapper();
	    }

	    return merkzettelMapper;
	  }
	  
	  
	  /**
	   * Speichert einen MerkzettelEintrag in der DB
	   * @param merkzettel
	   */
	  public void insertMerkzettelEintrag(Merkzettel merkzettel){
		  Connection con = DBConnection.getConnection();

		try {
			Statement stmt = con.createStatement();

			
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM merkzettel");

			if (rs.next()) {
				
				merkzettel.setId(rs.getInt("maxid") + 1);
				stmt = con.createStatement();

				
				stmt.executeUpdate("INSERT INTO merkzettel(id, epID, fpID) " + "VALUES (" + merkzettel.getId() + ","
						+ merkzettel.getEigenprofilID() +  "," + merkzettel.getFremdprofilID() +  ")");
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
		  
	  
	  /**
	   * L�scht einen MerkzettelEintrag in der DB
	   * @param merkzettel
	 * @return 
	   */
	  public void deleteMerkzettelEintrag(Merkzettel merkzettel){
		  Connection con = DBConnection.getConnection();

			try {
				Statement stmt = con.createStatement();

				stmt.executeUpdate("DELETE FROM merkzettel " + "WHERE id=" + merkzettel.getId());
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
	  }
	  
	  /**
	   * Gibt eine Merkzettel zur�ck per ID (Prim�rschl�ssel)
	   * @param id
	   * @return
	   */
	  public Merkzettel findByKey(int id){
			Connection con = DBConnection.getConnection();

			try {
				Statement stmt = con.createStatement();

				ResultSet rs = stmt.executeQuery("SELECT * FROM merkzettel" + "WHERE id=" + id);
				if (rs.next()) {
					Merkzettel m = new Merkzettel();
					m.setId(id);
					m.setEigenprofilID(rs.getInt("epID"));
					m.setFremdprofilID(rs.getInt("fpID"));
					
					return m;
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
				return null;
			}
			return null;
	  }
	  
	  /**
	   * Gibt alle Merkzettel-Eintr�ge zur�ck
	   * @return
	   */
	  public ArrayList<Merkzettel> findAll(){
		  ArrayList<Merkzettel> result = new ArrayList<>();

			Connection con = DBConnection.getConnection();
			try {
				
				Statement stmt = con.createStatement();

				ResultSet rs = stmt.executeQuery("SELECT * FROM merkzettel"+ "ORDER BY id");

				
				while (rs.next()) {
					Merkzettel m = new Merkzettel();
					m.setId(rs.getInt("id"));
					m.setEigenprofilID(rs.getInt("epID"));
					m.setFremdprofilID(rs.getInt("fpID"));
					

					
					result.add(m);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			
			return result;
		 
	  }
}
