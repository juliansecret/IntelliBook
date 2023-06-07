
package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Util.Conexion;
import javax.swing.table.DefaultTableModel;


public abstract class DAOBase {
    protected Connection con;
    protected PreparedStatement ps;
    protected ResultSet rs;
    protected Conexion cn = Conexion.getInstance();

    public abstract boolean registrar(Object obj);
    public abstract boolean modificar(Object obj);
    public abstract boolean eliminar(Object obj);
    public abstract DefaultTableModel consultar() ;
    public abstract Object buscar(String valor);
}
