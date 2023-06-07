
package Util;



import java.sql.*;
import javax.swing.JOptionPane;

public class Conexion {
    
    //Constructor 
    private Conexion(){
        
    }
    
    
    //Creamos una variable para guardar el estatus de conexion a la BD
    
    private static Connection conexion;
    
    
    //Creamos una variable para crear una sola intancia
    
    private static Conexion instancia;
    
    //Creamos las variables para conectarnos a la BD
    public static final String URL = "jdbc:mysql://localhost/intellibook";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "snowy123";

    
    //Creamos el metodo para conectarnos a la BD
    
    public  Connection conectar(){
        
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            
            return conexion;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error : " + e);
        }
            return conexion;

    }
    
//Creamos el metodo para cerrar la conexion
    
    public void cerrarConexion() throws SQLException{
        try {
            
            conexion.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
            conexion.close();
        }finally{
            conexion.close();
        }
    }
    
    // Creacion del patron singleton
    
    public static Conexion getInstance(){
        
        if(instancia == null){
            instancia = new Conexion();
        }
        return instancia;
    }
    
}

