import java.util.ArrayList;
import java.util.Scanner;

public class CineApp {
    static ArrayList<Pelicula> peliculas = new ArrayList<>();
    static ArrayList<Sala> salas = new ArrayList<>();
    static ArrayList<Funcion> funciones = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Inicialización de salas
        salas.add(new Sala(1, "35mm"));
        salas.add(new Sala(2, "35mm"));
        salas.add(new Sala(3, "3D"));
        
        while (true) {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Crear película");
            System.out.println("2. Asignar función");
            System.out.println("3. Vender entradas");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    crearPelicula();
                    break;
                case 2:
                    asignarFuncion();
                    break;
                case 3:
                    venderEntradas();
                    break;
                case 4:
                    System.out.println("Saliendo...");
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    static void crearPelicula() {
        System.out.print("Nombre de la película: ");
        String nombre = scanner.nextLine();
        System.out.print("Idioma: ");
        String idioma = scanner.nextLine();
        System.out.print("Tipo (35mm o 3D): ");
        String tipo = scanner.nextLine();
        System.out.print("Duración (minutos): ");
        int duracion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        Pelicula pelicula = new Pelicula(nombre, idioma, tipo, duracion);
        peliculas.add(pelicula);
        System.out.println("Película creada: " + pelicula);
    }

    public static void asignarFuncion() {
        System.out.println("\n--- Asignar Función ---");
        
        // Verificar si hay películas disponibles
        if (peliculas.size() == 0) {
            System.out.println("No hay películas disponibles. Primero debe crear una película.");
            return;
        }
        
        System.out.println("Películas disponibles:");
        for (int i = 0; i < peliculas.size(); i++) {
            System.out.println((i + 1) + ". " + peliculas.get(i));
        }
        System.out.print("Seleccione una película: ");
        int peliculaIndex = scanner.nextInt() - 1;
        scanner.nextLine(); 
        
        if (peliculaIndex < 0 || peliculaIndex >= peliculas.size()) {
            System.out.println("Selección no válida.");
            return;
        }

        System.out.println("Salas disponibles:");
        for (Sala sala : salas) {
            System.out.println("Sala " + sala.numero + " (" + sala.tipo + ")");
        }
        System.out.print("Seleccione una sala (número): ");
        int salaNumero = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer
        
        // Verificar que la sala seleccionada exista
        if (salaNumero < 1 || salaNumero > salas.size()) {
            System.out.println("Número de sala no válido.");
            return;
        }

        Sala sala = salas.get(salaNumero - 1);
        if (sala.tipo.equals("3D") && !peliculas.get(peliculaIndex).tipo.equals("3D")) {
            System.out.println("Error: La sala 3D solo puede proyectar películas en 3D.");
            return;
        }

        System.out.print("Horario (14:00, 16:30, 19:00): ");
        String horario = scanner.nextLine();

        Funcion funcion = new Funcion(sala, horario, peliculas.get(peliculaIndex));
        funciones.add(funcion);
        System.out.println("Función asignada: " + funcion);
    }

    public static void venderEntradas() {
        System.out.println("\n--- Vender Entradas ---");
        
        // Verificar si hay funciones disponibles
        if (funciones.size() == 0) {
            System.out.println("No hay funciones disponibles. Primero debe asignar una función.");
            return;
        }
        
        System.out.println("Funciones disponibles:");
        for (int i = 0; i < funciones.size(); i++) {
            System.out.println((i + 1) + ". " + funciones.get(i));
        }
        System.out.print("Seleccione una función: ");
        int funcionIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // Limpiar el buffer
        
        // Verificar que la función seleccionada exista
        if (funcionIndex < 0 || funcionIndex >= funciones.size()) {
            System.out.println("Selección no válida.");
            return;
        }

        Funcion funcion = funciones.get(funcionIndex);
        funcion.sala.mostrarAsientos();

        System.out.print("Seleccione asientos (ej. A1, B2): ");
        String[] asientos = scanner.nextLine().split(", ");
        int total = 0;

        for (String asiento : asientos) {
            if (funcion.sala.comprarAsiento(asiento)) {
                char fila = asiento.charAt(0);
                int precio = 0;
                
                if (fila >= 'A' && fila <= 'F') {
                    precio = 8000; // General
                } else if (fila >= 'G' && fila <= 'H') {
                    precio = 12000; // Preferencial
                }
                
                if (funcion.sala.tipo.equals("3D")) {
                    precio += 2000; // Sobrecargo para 3D
                }
                
                total += precio;
                System.out.println("Asiento " + asiento + " comprado. Precio: $" + precio);
            } else {
                System.out.println("Asiento " + asiento + " no disponible o inválido.");
            }
        }

        System.out.println("Total a pagar: $" + total);
    }
}