import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CineSimplificado {
    // Clase para representar una película
    static class Pelicula {
        private String nombre;
        private String idioma;
        private String tipo; // 35mm o 3D
        private int duracion;
        
        public Pelicula(String nombre, String idioma, String tipo, int duracion) {
            this.nombre = nombre;
            this.idioma = idioma;
            this.tipo = tipo;
            this.duracion = duracion;
        }
        
        @Override
        public String toString() {
            return nombre + " - " + idioma + " - " + tipo + " - " + duracion + " min";
        }
    }
    
    // Clase para representar una sala
    static class Sala {
        private int numero;
        private int filas;
        private int asientosPorFila;
        private boolean es3D;
        private Map<String, Pelicula> funciones;
        private Map<String, List<String>> asientosOcupados;
        
        public Sala(int numero, int filas, int asientosPorFila, boolean es3D) {
            this.numero = numero;
            this.filas = filas;
            this.asientosPorFila = asientosPorFila;
            this.es3D = es3D;
            this.funciones = new HashMap<>();
            this.asientosOcupados = new HashMap<>();
        }
        
        public void agregarFuncion(String horario, Pelicula pelicula) {
            if (es3D && !pelicula.tipo.equals("3D")) {
                System.out.println("Error: Esta sala solo acepta películas 3D");
                return;
            }
            if (!es3D && pelicula.tipo.equals("3D")) {
                System.out.println("Error: Esta sala no puede proyectar películas 3D");
                return;
            }
            
            funciones.put(horario, pelicula);
            asientosOcupados.put(horario, new ArrayList<>());
            System.out.println("Función agregada con éxito");
        }
        
        public boolean esAsientoDisponible(String horario, String asiento) {
            if (!funciones.containsKey(horario)) return false;
            if (!asientosOcupados.containsKey(horario)) return false;
            
            return !asientosOcupados.get(horario).contains(asiento);
        }
        
        public void mostrarDisponibilidad(String horario) {
            if (!funciones.containsKey(horario)) {
                System.out.println("No hay función en este horario");
                return;
            }
            
            List<String> ocupados = asientosOcupados.get(horario);
            System.out.println("Sala " + numero + " - " + funciones.get(horario).nombre);
            System.out.println("Horario: " + horario);
            
            // Mostrar encabezado con números de asientos
            System.out.print("   ");
            for (int j = 1; j <= asientosPorFila; j++) {
                System.out.printf("%2d ", j);
            }
            System.out.println();
            
            // Mostrar asientos
            for (int i = 0; i < filas; i++) {
                char fila = (char) ('A' + i);
                System.out.print(fila + "  ");
                
                for (int j = 1; j <= asientosPorFila; j++) {
                    String asiento = "" + fila + j;
                    if (ocupados.contains(asiento)) {
                        System.out.print("[X] ");
                    } else {
                        System.out.print("[ ] ");
                    }
                }
                System.out.println();
            }
            
            System.out.println("\n[ ] = Disponible, [X] = Ocupado");
        }
        
        public void comprarAsiento(String horario, String asiento) {
            if (!funciones.containsKey(horario)) {
                System.out.println("No hay función en este horario");
                return;
            }
            
            if (!esAsientoDisponible(horario, asiento)) {
                System.out.println("El asiento no está disponible");
                return;
            }
            
            asientosOcupados.get(horario).add(asiento);
            System.out.println("Asiento " + asiento + " comprado con éxito");
        }
        
        public double calcularPrecio(String asiento) {
            char fila = asiento.charAt(0);
            int precio = es3D ? 10000 : 8000;
            
            // Si es una fila del final (preferencial)
            if (fila >= 'E' && !es3D) {
                precio = 12000;
            }
            
            return precio;
        }
    }
    
    // Variables principales
    private static List<Pelicula> peliculas = new ArrayList<>();
    private static List<Sala> salas = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        inicializarSalas();
        boolean salir = false;
        
        while (!salir) {
            System.out.println("\n===== SISTEMA DE CINE =====");
            System.out.println("1. Gestionar Películas");
            System.out.println("2. Gestionar Funciones");
            System.out.println("3. Vender Entradas");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir salto de línea
            
            switch (opcion) {
                case 1:
                    gestionarPeliculas();
                    break;
                case 2:
                    gestionarFunciones();
                    break;
                case 3:
                    venderEntradas();
                    break;
                case 4:
                    salir = true;
                    System.out.println("¡Gracias por usar el sistema!");
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }
    
    // Inicializar las salas del cine
    private static void inicializarSalas() {
        salas.add(new Sala(1, 8, 12, false)); // Sala normal
        salas.add(new Sala(2, 8, 12, false)); // Sala normal
        salas.add(new Sala(3, 6, 10, true));  // Sala 3D
    }
    
    // Gestión de películas
    private static void gestionarPeliculas() {
        boolean volver = false;
        
        while (!volver) {
            System.out.println("\n===== GESTIÓN DE PELÍCULAS =====");
            System.out.println("1. Ver películas");
            System.out.println("2. Agregar película");
            System.out.println("3. Volver");
            System.out.print("Seleccione una opción: ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir salto de línea
            
            switch (opcion) {
                case 1:
                    mostrarPeliculas();
                    break;
                case 2:
                    agregarPelicula();
                    break;
                case 3:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }
    
    // Mostrar películas registradas
    private static void mostrarPeliculas() {
        if (peliculas.isEmpty()) {
            System.out.println("No hay películas registradas");
            return;
        }
        
        System.out.println("\n===== PELÍCULAS REGISTRADAS =====");
        for (int i = 0; i < peliculas.size(); i++) {
            System.out.println((i + 1) + ". " + peliculas.get(i));
        }
    }
    
    // Agregar una nueva película
    private static void agregarPelicula() {
        System.out.println("\n===== AGREGAR PELÍCULA =====");
        
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Idioma: ");
        String idioma = scanner.nextLine();
        
        System.out.print("Tipo (1: 35mm, 2: 3D): ");
        int tipo = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea
        
        System.out.print("Duración (minutos): ");
        int duracion = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea
        
        String tipoStr = (tipo == 1) ? "35mm" : "3D";
        peliculas.add(new Pelicula(nombre, idioma, tipoStr, duracion));
        
        System.out.println("Película agregada con éxito");
    }
    
    // Gestión de funciones
    private static void gestionarFunciones() {
        if (peliculas.isEmpty()) {
            System.out.println("No hay películas registradas. Agregue películas primero.");
            return;
        }
        
        boolean volver = false;
        
        while (!volver) {
            System.out.println("\n===== GESTIÓN DE FUNCIONES =====");
            System.out.println("1. Ver funciones");
            System.out.println("2. Agregar función");
            System.out.println("3. Volver");
            System.out.print("Seleccione una opción: ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir salto de línea
            
            switch (opcion) {
                case 1:
                    mostrarFunciones();
                    break;
                case 2:
                    agregarFuncion();
                    break;
                case 3:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }
    
    // Mostrar funciones programadas
    private static void mostrarFunciones() {
        System.out.println("\n===== FUNCIONES PROGRAMADAS =====");
        
        for (Sala sala : salas) {
            System.out.println("\nSala " + sala.numero + (sala.es3D ? " (3D)" : ""));
            
            if (sala.funciones.isEmpty()) {
                System.out.println("No hay funciones programadas");
                continue;
            }
            
            for (Map.Entry<String, Pelicula> funcion : sala.funciones.entrySet()) {
                System.out.println("- " + funcion.getKey() + ": " + funcion.getValue().nombre);
            }
        }
    }
    
    // Agregar una nueva función
    private static void agregarFuncion() {
        System.out.println("\n===== AGREGAR FUNCIÓN =====");
        
        // Seleccionar sala
        System.out.println("Salas disponibles:");
        for (Sala sala : salas) {
            System.out.println(sala.numero + ". Sala " + sala.numero + (sala.es3D ? " (3D)" : ""));
        }
        
        System.out.print("Seleccione una sala: ");
        int numSala = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea
        
        Sala sala = null;
        for (Sala s : salas) {
            if (s.numero == numSala) {
                sala = s;
                break;
            }
        }
        
        if (sala == null) {
            System.out.println("Sala no válida");
            return;
        }
        
        // Seleccionar horario
        System.out.println("\nHorarios disponibles:");
        System.out.println("1. 14:00-16:30");
        System.out.println("2. 16:30-19:00");
        System.out.println("3. 19:00-21:30");
        
        System.out.print("Seleccione un horario: ");
        int opcionHorario = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea
        
        String horario;
        switch (opcionHorario) {
            case 1: horario = "14:00-16:30"; break;
            case 2: horario = "16:30-19:00"; break;
            case 3: horario = "19:00-21:30"; break;
            default:
                System.out.println("Horario no válido");
                return;
        }
        
        if (sala.funciones.containsKey(horario)) {
            System.out.println("Ya existe una función en este horario");
            return;
        }
        
        // Seleccionar película
        System.out.println("\nPelículas disponibles:");
        List<Pelicula> peliculasCompatibles = new ArrayList<>();
        
        for (Pelicula p : peliculas) {
            if ((sala.es3D && p.tipo.equals("3D")) || (!sala.es3D && p.tipo.equals("35mm"))) {
                peliculasCompatibles.add(p);
            }
        }
        
        if (peliculasCompatibles.isEmpty()) {
            System.out.println("No hay películas compatibles con esta sala");
            return;
        }
        
        for (int i = 0; i < peliculasCompatibles.size(); i++) {
            System.out.println((i + 1) + ". " + peliculasCompatibles.get(i));
        }
        
        System.out.print("Seleccione una película: ");
        int numPelicula = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea
        
        if (numPelicula < 1 || numPelicula > peliculasCompatibles.size()) {
            System.out.println("Película no válida");
            return;
        }
        
        Pelicula pelicula = peliculasCompatibles.get(numPelicula - 1);
        sala.agregarFuncion(horario, pelicula);
    }
    
    // Venta de entradas
    private static void venderEntradas() {
        System.out.println("\n===== VENTA DE ENTRADAS =====");
        
        // Verificar si hay funciones
        boolean hayFunciones = false;
        for (Sala sala : salas) {
            if (!sala.funciones.isEmpty()) {
                hayFunciones = true;
                break;
            }
        }
        
        if (!hayFunciones) {
            System.out.println("No hay funciones programadas");
            return;
        }
        
        // Seleccionar sala
        System.out.println("Salas con funciones:");
        for (Sala sala : salas) {
            if (!sala.funciones.isEmpty()) {
                System.out.println(sala.numero + ". Sala " + sala.numero + (sala.es3D ? " (3D)" : ""));
            }
        }
        
        System.out.print("Seleccione una sala: ");
        int numSala = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea
        
        Sala sala = null;
        for (Sala s : salas) {
            if (s.numero == numSala) {
                sala = s;
                break;
            }
        }
        
        if (sala == null || sala.funciones.isEmpty()) {
            System.out.println("Sala no válida o sin funciones");
            return;
        }
        
        // Seleccionar función
        System.out.println("\nFunciones disponibles:");
        List<String> horarios = new ArrayList<>(sala.funciones.keySet());
        
        for (int i = 0; i < horarios.size(); i++) {
            String horario = horarios.get(i);
            System.out.println((i + 1) + ". " + horario + ": " + sala.funciones.get(horario).nombre);
        }
        
        System.out.print("Seleccione una función: ");
        int numFuncion = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea
        
        if (numFuncion < 1 || numFuncion > horarios.size()) {
            System.out.println("Función no válida");
            return;
        }
        
        String horario = horarios.get(numFuncion - 1);
        
        // Mostrar disponibilidad y comprar asientos
        sala.mostrarDisponibilidad(horario);
        
        List<String> asientosSeleccionados = new ArrayList<>();
        double total = 0;
        boolean seguirComprando = true;
        
        while (seguirComprando) {
            System.out.print("\nIngrese asiento a comprar (ej: A5): ");
            String asiento = scanner.nextLine().toUpperCase();
            
            if (sala.esAsientoDisponible(horario, asiento)) {
                asientosSeleccionados.add(asiento);
                double precio = sala.calcularPrecio(asiento);
                total += precio;
                System.out.println("Asiento " + asiento + " seleccionado - Precio: $" + precio);
            } else {
                System.out.println("Asiento no disponible o no válido");
            }
            
            System.out.print("¿Comprar otro asiento? (s/n): ");
            String respuesta = scanner.nextLine();
            seguirComprando = respuesta.equalsIgnoreCase("s");
        }
        
        // Finalizar compra
        if (!asientosSeleccionados.isEmpty()) {
            System.out.println("\n===== RESUMEN DE COMPRA =====");
            System.out.println("Sala: " + sala.numero + (sala.es3D ? " (3D)" : ""));
            System.out.println("Película: " + sala.funciones.get(horario).nombre);
            System.out.println("Horario: " + horario);
            System.out.println("Asientos: " + String.join(", ", asientosSeleccionados));
            System.out.println("Total: $" + total);
            
            System.out.print("\n¿Confirmar compra? (s/n): ");
            String confirmar = scanner.nextLine();
            
            if (confirmar.equalsIgnoreCase("s")) {
                for (String asiento : asientosSeleccionados) {
                    sala.comprarAsiento(horario, asiento);
                }
                System.out.println("¡Compra finalizada! Disfrute su película.");
            } else {
                System.out.println("Compra cancelada");
            }
        } else {
            System.out.println("No se seleccionaron asientos. Compra cancelada.");
        }
    }
} 
