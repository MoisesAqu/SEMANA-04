/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;

import MODELO.Alumno;
import MODELO.AlumnoArray;
import VISTA.FrmAlumno;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author ADMIN
 */
public class Controlador {
    private AlumnoArray gestor;
    private FrmAlumno vista;

    public Controlador(FrmAlumno vista, AlumnoArray gestor) {
        this.vista = vista;
        this.gestor = gestor;

        this.vista.getBtnGuardar().addActionListener(e -> guardarAlumno());
        this.vista.getBtnCargar().addActionListener(e -> cargarDatosDesdeArchivo());
    }

    public void guardarAlumno() {
        if (vista.getTxtCodigo().getText().isEmpty() || vista.getTxtNombres().getText().isEmpty()
                || vista.getTxtDireccion().getText().isEmpty() || vista.getTxtTelefono().getText().isEmpty()
                || vista.getJCalendar().getDate() == null) {
            JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos.");
            return;
        }

        String codigo = vista.getTxtCodigo().getText();
        String nombre = vista.getTxtNombres().getText();
        String direccion = vista.getTxtDireccion().getText();
        String telefono = vista.getTxtTelefono().getText();
        Date fechaNacimiento = vista.getJCalendar().getDate();
        LocalDate fechaNacimientoLocal = fechaNacimiento.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

        Alumno alumno = new Alumno(codigo, nombre, fechaNacimientoLocal, direccion, telefono);
        gestor.agregarAlumno(alumno, fechaNacimiento);

        gestor.limpiar(vista.getTxtCodigo(), vista.getTxtNombres(), vista.getTxtDireccion(), vista.getTxtTelefono(), vista.getJCalendar());
        gestor.actualizarTabla();

        String rutaArchivo = "D:\\trabajo arquitectura software\\SEMANA 04\\Ejemplo\\estudiantes.txt";
        gestor.guardarDatosEnArchivo(rutaArchivo);
    }

    public void cargarDatosDesdeArchivo() {
    String rutaArchivo = "D:\\trabajo arquitectura software\\SEMANA 04\\Ejemplo\\estudiantes.txt";

    // Guardar los datos actuales de la tabla en un archivo
    gestor.guardarTablaEnArchivo(rutaArchivo);

    // Si también deseas cargar los datos desde el archivo, puedes mantener esta línea
    // gestor.cargarDatosDesdeArchivo(rutaArchivo);
}

}
