package ipm.estudiantes;

import ipm.estudiantes.modelo.Estudiante;
import ipm.estudiantes.servicio.EstudianteServicio;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;


@SpringBootApplication
public class EstudiantesApplication implements CommandLineRunner {

    @Autowired
    private EstudianteServicio estudianteServicio;

    private static final Logger logger = LoggerFactory.getLogger(EstudiantesApplication.class);

    String nl = System.lineSeparator();

    public static void main(String[] args) {
        logger.info("Iniciando la aplicación...");
        SpringApplication.run(EstudiantesApplication.class, args);
        logger.info("Aplicación finalizada.");
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info(nl + "Ejecutando método run de Spring..." + nl);
        var salir = false;
        var consola = new Scanner(System.in);
        while (!salir) {
            mostrarMenu();
            salir = ejecutarOpciones(consola);
            logger.info("");
        }
    }

    private void mostrarMenu() {
        logger.info("""
                *** Sistema de estudiantes ***
                1. Listar estudiantes
                2. Buscar estudiante
                3. Agregar estudiante
                4. Modificar estudiante
                5. Eliminar estudiante
                6. Salir
                Elige una opcion:""");
    }

    private boolean ejecutarOpciones(Scanner consola) {
        var opcion = Integer.parseInt(consola.nextLine());
        var salir = false;
        switch (opcion) {
            case 1 -> {
                logger.info(nl + "Listado de estudiantes: " + nl);
                List<Estudiante> estudiantes = estudianteServicio.listarEstudiantes();
                estudiantes.forEach((estudiante -> logger.info(estudiante.toString() + nl)));
            }
            case 2 -> {
                logger.info("Introduce el id estudiante a buscar: ");
                var idEstudiante = Integer.parseInt(consola.nextLine());
                Estudiante estudiante = estudianteServicio.buscarEstudiantePorId(idEstudiante);
                if (estudiante != null)
                    logger.info("Estudainte encontrado: " + estudiante + nl);
                else
                    logger.info("Estudiante no encontrado con id: " + idEstudiante + nl);
            }
            case 3 -> {
                logger.info("Agregar estudiante: " + nl);
                logger.info("Nombre: ");
                var nombre = consola.nextLine();
                logger.info("Apellido: ");
                var apellido = consola.nextLine();
                logger.info("Telefono: ");
                var telefono = consola.nextLine();
                logger.info("Email: ");
                var email = consola.nextLine();
                // Crear el objeto estudiante sin el id
                var estudiante = new Estudiante();
                estudiante.setNombre(nombre);
                estudiante.setApellido(apellido);
                estudiante.setTelefono(telefono);
                estudiante.setEmail(email);
                estudianteServicio.guardarEstudiante(estudiante);
                logger.info("Estudiante guardado: " + estudiante + nl);
            }
            case 4 -> {
                logger.info("Modificar estudiante: " + nl);
                logger.info("Id estudiante: ");
                var idEstudiante = Integer.parseInt(consola.nextLine());
                Estudiante estudiante = estudianteServicio.buscarEstudiantePorId(idEstudiante);
                if (estudiante != null) {
                    logger.info("Nombre: ");
                    var nombre = consola.nextLine();
                    logger.info("Apellido: ");
                    var apellido = consola.nextLine();
                    logger.info("Telefono: ");
                    var telefono = consola.nextLine();
                    logger.info("Email: ");
                    var email = consola.nextLine();

                    estudiante.setNombre(nombre);
                    estudiante.setApellido(apellido);
                    estudiante.setTelefono(telefono);
                    estudiante.setEmail(email);
                    estudianteServicio.guardarEstudiante(estudiante);
                    logger.info("Estudiante modificaado: " + estudiante + nl);
                } else
                    logger.info("Estudiante no encontrado con id: " + idEstudiante + nl);
            }
            case 5 -> {
                logger.info("Eliminar estudiante: " + nl);
                logger.info("Id estudiante: ");
                var idEstudiante = Integer.parseInt(consola.nextLine());
                var estudiante = estudianteServicio.buscarEstudiantePorId(idEstudiante);
                if (estudiante != null) {
                    estudianteServicio.eliminarEstudiante(estudiante);
                    logger.info("Estudiante eliminado: " + estudiante + nl);
                } else
                    logger.info("Estudiante no encontrado con id: " + idEstudiante);
            }
            case 6 -> {
                logger.info("Hasta pronto..." + nl + nl);
                salir = true;
            }
            default -> logger.info("Opción no reconocida: " + opcion + nl);
        }
        return salir;
    }
}
