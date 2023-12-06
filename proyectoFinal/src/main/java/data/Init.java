
package data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Auto;

public class Init {
    public static void main(String[] args) {
        Connection conn = null;
        List<Auto> autos = new ArrayList();
        try {
            conn = Conexion.getConexion();
            System.out.println("Exito");
            autos = AutoDAO.seleccionar();
             
            System.out.println(autos);
            
        } catch (SQLException ex) {
            Logger.getLogger(Init.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Conexion.close(conn);
            System.out.println("Conexion cerrada");
        } catch (SQLException ex) {
            Logger.getLogger(Init.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
