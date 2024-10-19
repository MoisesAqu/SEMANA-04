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

        // Asignar los listeners a los botones
        this.vista.getBtnGuardar().addActionListener(e -> guardarAlumno());
        this.vista.getBtnCargar().addActionListener(e -> cargarDatosDesdeArchivo());

        // Validación en tiempo real para Código (exactamente 7 caracteres alfanuméricos)
        this.vista.getTxtCodigo().addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                validarCodigo(evt, vista.getTxtCodigo().getText());
            }
        });

        // Validación en tiempo real para Nombres (solo letras)
        this.vista.getTxtNombres().addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                validarSoloLetras(evt);
            }
        });

        // Validación en tiempo real para Teléfono (solo 9 números)
        this.vista.getTxtTelefono().addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                validarTelefono(evt, vista.getTxtTelefono().getText());
            }
        });
    }


    public void guardarAlumno() {
    // Validar que los campos no estén vacíos
    if (vista.getTxtCodigo().getText().isEmpty() || vista.getTxtNombres().getText().isEmpty()
            || vista.getTxtDireccion().getText().isEmpty() || vista.getTxtTelefono().getText().isEmpty()
            || vista.getJCalendar().getDate() == null) {
        JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos.");
        return;
    }

    // Validar que el código tenga exactamente 7 caracteres alfanuméricos
    if (!vista.getTxtCodigo().getText().matches("[a-zA-Z0-9]{7}")) {
        JOptionPane.showMessageDialog(vista, "El código debe tener exactamente 7 caracteres alfanuméricos.");
        return;
    }

    // Validar que los nombres solo contengan letras
    if (!vista.getTxtNombres().getText().matches("[a-zA-Z ]+")) {
        JOptionPane.showMessageDialog(vista, "El nombre solo debe contener letras.");
        return;
    }

    // Validar que el teléfono tenga exactamente 9 dígitos
    if (!vista.getTxtTelefono().getText().matches("\\d{9}")) {
        JOptionPane.showMessageDialog(vista, "El teléfono debe contener exactamente 9 números.");
        return;
    }

    // Aquí continúa el proceso de guardado si todo es válido
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

    
        // Validar que el código sea alfanumérico y tenga exactamente 7 caracteres
    private void validarCodigo(java.awt.event.KeyEvent evt, String textoActual) {
        char c = evt.getKeyChar();
        if ((!Character.isLetterOrDigit(c)) || textoActual.length() >= 7) {
            evt.consume(); // No permitir más de 7 caracteres o caracteres no alfanuméricos
        }
    }

    // Validar que solo se ingresen letras en el campo de Nombres y Apellidos
    private void validarSoloLetras(java.awt.event.KeyEvent evt) {
        char c = evt.getKeyChar();
        if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
            evt.consume(); // No permitir caracteres que no sean letras ni espacios
        }
    }

    // Validar que el teléfono solo contenga números y tenga un máximo de 9 caracteres
    private void validarTelefono(java.awt.event.KeyEvent evt, String textoActual) {
        char c = evt.getKeyChar();
        if (!Character.isDigit(c) || textoActual.length() >= 9) {
            evt.consume(); // No permitir más de 9 números
        }
    }

}
