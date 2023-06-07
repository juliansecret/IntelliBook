package Models;

import Util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class LibroDAO {
    Connection con;
    Conexion cn = Conexion.getInstance();
    PreparedStatement ps;
    ResultSet rs;

    public boolean registrar(Libro libro) {
        String sql = "INSERT INTO libros (titulo, unidades, id_libro) VALUES (?,?,?)";
        con = cn.conectar();
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, libro.getTitulo());
            ps.setInt(2, libro.getUnidades());
            ps.setString(3, libro.getId_libro());
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

    public boolean modificar(Libro libro) {
        boolean res;
        String sql = "UPDATE libros SET titulo=?, unidades=? WHERE id_libro=?";
        con = cn.conectar();
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, libro.getTitulo());
            ps.setInt(2, libro.getUnidades());
            ps.setString(3, libro.getId_libro());
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

    public boolean eliminar(String id_libro) {
        String sql = "DELETE FROM libros WHERE id_libro=?";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, id_libro);
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

    public DefaultTableModel consultar() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Título");
        modelo.addColumn("Unidades");
        modelo.addColumn("ID Libro");

        try {
            con = cn.conectar();
            PreparedStatement seleccionar = con.prepareStatement("SELECT * FROM libros");
            ResultSet consulta = seleccionar.executeQuery();

            while (consulta.next()) {
                String id = consulta.getString("id");
                String titulo = consulta.getString("titulo");
                int unidades = consulta.getInt("unidades");
                String id_libro = consulta.getString("id_libro");

                modelo.addRow(new Object[]{id, titulo, unidades, id_libro});
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

    public Libro buscar(String id_libro) {
        Libro libro = null;
        String sql = "SELECT * FROM libros WHERE id_libro = ?";
        con = cn.conectar();
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id_libro);
            rs = ps.executeQuery();

            if (rs.next()) {
                libro = new Libro();
                libro.setId(rs.getInt("id"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setUnidades(rs.getInt("unidades"));
                libro.setId_libro(rs.getString("id_libro"));
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
        return libro;
    }
}
