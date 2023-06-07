
package Models;

import Util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class AlumnoDAO  {
    Connection con;
    Conexion cn = Conexion.getInstance();
    PreparedStatement ps;
    ResultSet rs;
    
    // Método para registrar alumnos
    public boolean registrar(Alumno est) {
        String sql = "INSERT INTO alumnos (nombre, correo, numeroControl, telefono, carrera) VALUES (?,?,?,?,?)";
        con = cn.conectar();
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, est.getNombre());
            ps.setString(2, est.getCorreo());
            ps.setString(3, est.getNumeroControl());
            ps.setString(4, est.getTelefono());
            ps.setString(5, est.getCarrera());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }

    // Método para actualizar los alumnos de la clase Alumno
  public boolean modificar(Alumno est) {
    boolean res;
    String sql = "UPDATE alumnos SET nombre=?, correo=?, numeroControl=?, telefono=?, carrera=? WHERE numeroControl=?";
    con = cn.conectar();
    try {
        ps = con.prepareStatement(sql);
        ps.setString(1, est.getNombre());
        ps.setString(2, est.getCorreo());
        ps.setString(3, est.getNumeroControl());
        ps.setString(4, est.getTelefono());
        ps.setString(5, est.getCarrera());
        ps.setString(6, est.getNumeroControl()); // Utilizamos el número de control para la cláusula WHERE
        ps.executeUpdate();
        res = true;
    } catch (SQLException ex) {
        System.out.println(ex.toString());
        res = false;
    } finally {
        try {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
    return res;
}

    // Método para eliminar alumnos de la base de datos
   public boolean eliminar(String numeroControl) {
    String sql = "DELETE FROM alumnos WHERE numeroControl=?";
    try {
        con = cn.conectar();
        ps = con.prepareStatement(sql);
        ps.setString(1, numeroControl);
        ps.executeUpdate();
        return true;
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.toString());
        return false;
    } finally {
        try {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
}

    
   //Metodo para consultar la base de datos
  public DefaultTableModel consultar() {
    DefaultTableModel modelo = new DefaultTableModel();
    modelo.addColumn("ID");
    modelo.addColumn("Nombre");
    modelo.addColumn("Correo");
    modelo.addColumn("Número de Control");
    modelo.addColumn("Teléfono");
    modelo.addColumn("Carrera");
    
    try {
        con = cn.conectar();
        PreparedStatement seleccionar = con.prepareStatement("SELECT * FROM alumnos");
        ResultSet consulta = seleccionar.executeQuery();

        while (consulta.next()) {
            String id = consulta.getString("id");
            String nombre = consulta.getString("nombre");
            String correo = consulta.getString("correo");
            String numeroControl = consulta.getString("numeroControl");
            String telefono = consulta.getString("telefono");
            String carrera = consulta.getString("carrera");
            
            modelo.addRow(new Object[] { id, nombre, correo, numeroControl, telefono, carrera });
        }

        cn.cerrarConexion();

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error: " + e);
    } finally {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
    
    return modelo;
}

  //Metodo para buscar un alumno por su numero de control
  
  public Alumno buscar(String numeroControl) {
    Alumno alumno = null;
    String sql = "SELECT * FROM alumnos WHERE numeroControl = ?";
    con = cn.conectar();
    try {
        ps = con.prepareStatement(sql);
        ps.setString(1, numeroControl);
        rs = ps.executeQuery();

        if (rs.next()) {
            alumno = new Alumno();
            alumno.setId(rs.getInt("id"));
            alumno.setNombre(rs.getString("nombre"));
            alumno.setCorreo(rs.getString("correo"));
            alumno.setNumeroControl(rs.getString("numeroControl"));
            alumno.setTelefono(rs.getString("telefono"));
            alumno.setCarrera(rs.getString("carrera"));
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error: " + e);
    } finally {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
    return alumno;
}

    
    
}

  


