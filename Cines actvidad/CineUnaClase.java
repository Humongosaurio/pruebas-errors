// Clase principal para ejecutar la aplicación
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CineUnaClase {
    private static List<Pelicula> peliculas = new ArrayList<>();
    private static List<Sala> salas = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        inicializarSalas();
        menuPrincipal();
    }

    private static void inicializarSalas() {
        // Inicializar salas según especificaciones
        salas.add(new SalaNormal(1, 6, 12, 2, 9)); // Sala 1: 6 filas normales, 12 sillas por fila, 2 filas preferenciales, 9 sillas por fila
        salas.add(new SalaNormal(2, 6, 12, 2, 9)); // Sala 2: igual que la Sala 1
        salas.add(new Sala3D(3, 6, 12));          // Sala 3: solo 3D, 6 filas normales, 12 sillas por fila
    }

    private static void menuPrincipal() {
        int opcion;
        do {
            System.out.println("\n===== SISTEMA DE CINE CINEMASTAR =====");
            System.out.println("1. Creación de Películas");
            System.out.println("2. Asignación de Funciones");
            System.out.println("3. Ventas");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea
            
            switch (opcion) {
                case 1:
                    menuPeliculas();
                    break;
                case 2:
                    menuFunciones();
                    break;
                case 3:
                    menuVentas();
                    break;
                case 4:
                    System.out.println("Gracias por usar el sistema CinemaStar");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 4);
    }

    private static void menuPeliculas() {
        int opcion;
        do {
            System.out.println("\n===== GESTIÓN DE PELÍCULAS =====");
            System.out.println("1. Ver películas registradas");
            System.out.println("2. Añadir nueva película");
            System.out.println("3. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea
            
            switch (opcion) {
                case 1:
                    mostrarPeliculas();
                    break;
                case 2:
                    agregarPelicula();
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 3);
    }

    private static void mostrarPeliculas() {
        if (peliculas.isEmpty()) {
            System.out.println("No hay películas registradas.");
            return;
        }
        
        System.out.println("\n===== PELÍCULAS REGISTRADAS =====");
        for (int i = 0; i < peliculas.size(); i++) {
            Pelicula pelicula = peliculas.get(i);
            System.out.println((i + 1) + ". " + pelicula.getNombre() + 
                    " - Idioma: " + pelicula.getIdioma() + 
                    " - Tipo: " + pelicula.getTipo() + 
                    " - Duración: " + pelicula.getDuracion() + " minutos");
        }
    }

    private static void agregarPelicula() {
        System.out.println("\n===== AÑADIR NUEVA PELÍCULA =====");
        
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Idioma: ");
        String idioma = scanner.nextLine();
        
        System.out.println("Tipo (1: 35mm, 2: 3D): ");
        int tipoOpcion = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        
        String tipo = (tipoOpcion == 1) ? "35mm" : "3D";
        
        System.out.print("Duración (minutos): ");
        int duracion = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        
        peliculas.add(new Pelicula(nombre, idioma, tipo, duracion));
        System.out.println("Película añadida correctamente.");
    }

    private static void menuFunciones() {
        if (peliculas.isEmpty()) {
            System.out.println("No hay películas registradas. Debe agregar películas primero.");
            return;
        }
        
        int opcion;
        do {
            System.out.println("\n===== ASIGNACIÓN DE FUNCIONES =====");
            System.out.println("1. Ver funciones asignadas");
            System.out.println("2. Asignar nueva función");
            System.out.println("3. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea
            
            switch (opcion) {
                case 1:
                    mostrarFunciones();
                    break;
                case 2:
                    asignarFuncion();
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 3);
    }

    private static void mostrarFunciones() {
        System.out.println("\n===== FUNCIONES ASIGNADAS =====");
        
        for (Sala sala : salas) {
            System.out.println("\nSala " + sala.getNumero() + ":");
            Map<String, Pelicula> funciones = sala.getFunciones();
            
            if (funciones.isEmpty()) {
                System.out.println("No hay funciones asignadas.");
            } else {
                for (Map.Entry<String, Pelicula> entry : funciones.entrySet()) {
                    String franja = entry.getKey();
                    Pelicula pelicula = entry.getValue();
                    System.out.println("- Franja " + franja + ": " + pelicula.getNombre() + 
                            " (" + pelicula.getTipo() + ")");
                }
            }
        }
    }

    private static void asignarFuncion() {
        System.out.println("\n===== ASIGNAR NUEVA FUNCIÓN =====");
        
        // Mostrar salas disponibles
        System.out.println("Salas disponibles:");
        for (Sala sala : salas) {
            System.out.println(sala.getNumero() + ". Sala " + sala.getNumero() + 
                    (sala instanceof Sala3D ? " (Solo películas 3D)" : ""));
        }
        
        System.out.print("Seleccione una sala: ");
        int numSala = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        
        // Validar sala
        Sala sala = null;
        for (Sala s : salas) {
            if (s.getNumero() == numSala) {
                sala = s;
                break;
            }
        }
        
        if (sala == null) {
            System.out.println("Sala inválida.");
            return;
        }
        
        // Mostrar franjas horarias disponibles
        System.out.println("\nFranjas horarias:");
        System.out.println("1. 14:00 - 16:30");
        System.out.println("2. 16:30 - 19:00");
        System.out.println("3. 19:00 - 21:00");
        
        System.out.print("Seleccione una franja: ");
        int franjaOpcion = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        
        String franja;
        switch (franjaOpcion) {
            case 1:
                franja = "14:00-16:30";
                break;
            case 2:
                franja = "16:30-19:00";
                break;
            case 3:
                franja = "19:00-21:00";
                break;
            default:
                System.out.println("Franja inválida.");
                return;
        }
        
        // Verificar si la franja ya está ocupada
        if (sala.getFunciones().containsKey(franja)) {
            System.out.println("Esta franja horaria ya está ocupada en la sala seleccionada.");
            return;
        }
        
        // Mostrar películas disponibles según el tipo de sala
        System.out.println("\nPelículas disponibles:");
        List<Pelicula> peliculasDisponibles = new ArrayList<>();
        
        for (int i = 0; i < peliculas.size(); i++) {
            Pelicula pelicula = peliculas.get(i);
            boolean esCompatible = false;
            
            if (sala instanceof Sala3D) {
                esCompatible = pelicula.getTipo().equals("3D");
            } else {
                esCompatible = pelicula.getTipo().equals("35mm");
            }
            
            if (esCompatible) {
                peliculasDisponibles.add(pelicula);
                System.out.println((peliculasDisponibles.size()) + ". " + pelicula.getNombre() + 
                        " - Tipo: " + pelicula.getTipo() + 
                        " - Duración: " + pelicula.getDuracion() + " minutos");
            }
        }
        
        if (peliculasDisponibles.isEmpty()) {
            System.out.println("No hay películas compatibles con esta sala.");
            return;
        }
        
        System.out.print("Seleccione una película: ");
        int peliculaIndex = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        
        if (peliculaIndex < 1 || peliculaIndex > peliculasDisponibles.size()) {
            System.out.println("Película inválida.");
            return;
        }
        
        Pelicula peliculaSeleccionada = peliculasDisponibles.get(peliculaIndex - 1);
        sala.asignarFuncion(franja, peliculaSeleccionada);
        System.out.println("Función asignada correctamente.");
    }

    private static void menuVentas() {
        int opcion;
        do {
            System.out.println("\n===== MENÚ DE VENTAS =====");
            System.out.println("1. Ver disponibilidad de salas");
            System.out.println("2. Comprar entradas");
            System.out.println("3. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea
            
            switch (opcion) {
                case 1:
                    mostrarDisponibilidad();
                    break;
                case 2:
                    comprarEntradas();
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 3);
    }

    private static void mostrarDisponibilidad() {
        System.out.println("\n===== DISPONIBILIDAD DE SALAS =====");
        
        for (Sala sala : salas) {
            System.out.println("\nSala " + sala.getNumero() + ":");
            Map<String, Pelicula> funciones = sala.getFunciones();
            
            if (funciones.isEmpty()) {
                System.out.println("No hay funciones asignadas.");
            } else {
                for (Map.Entry<String, Pelicula> entry : funciones.entrySet()) {
                    String franja = entry.getKey();
                    Pelicula pelicula = entry.getValue();
                    int asientosDisponibles = sala.contarAsientosDisponibles(franja);
                    int asientosTotales = sala.getCapacidadTotal();
                    
                    System.out.println("- Franja " + franja + ": " + pelicula.getNombre() + 
                            " (" + pelicula.getTipo() + ") - " + 
                            asientosDisponibles + " de " + asientosTotales + " asientos disponibles");
                }
            }
        }
    }

    private static void comprarEntradas() {
        System.out.println("\n===== COMPRAR ENTRADAS =====");
        
        // Seleccionar sala
        System.out.println("Salas disponibles:");
        for (Sala sala : salas) {
            if (!sala.getFunciones().isEmpty()) {
                System.out.println(sala.getNumero() + ". Sala " + sala.getNumero());
            }
        }
        
        System.out.print("Seleccione una sala: ");
        int numSala = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        
        // Validar sala
        Sala sala = null;
        for (Sala s : salas) {
            if (s.getNumero() == numSala) {
                sala = s;
                break;
            }
        }
        
        if (sala == null || sala.getFunciones().isEmpty()) {
            System.out.println("Sala inválida o sin funciones asignadas.");
            return;
        }
        
        // Seleccionar función
        System.out.println("\nFunciones disponibles:");
        Map<String, Pelicula> funciones = sala.getFunciones();
        List<String> franjas = new ArrayList<>(funciones.keySet());
        
        for (int i = 0; i < franjas.size(); i++) {
            String franja = franjas.get(i);
            Pelicula pelicula = funciones.get(franja);
            System.out.println((i + 1) + ". Franja " + franja + ": " + pelicula.getNombre());
        }
        
        System.out.print("Seleccione una función: ");
        int funcionIndex = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        
        if (funcionIndex < 1 || funcionIndex > franjas.size()) {
            System.out.println("Función inválida.");
            return;
        }
        
        String franjaSeleccionada = franjas.get(funcionIndex - 1);
        
        // Mostrar disponibilidad de asientos
        sala.mostrarDisponibilidad(franjaSeleccionada);
        
        // Seleccionar asientos
        List<String> asientosSeleccionados = new ArrayList<>();
        boolean seguirComprando = true;
        
        while (seguirComprando) {
            System.out.print("\nIngrese el asiento que desea comprar (ej: A3): ");
            String asiento = scanner.nextLine().toUpperCase();
            
            if (sala.esAsientoValido(asiento)) {
                if (sala.esAsientoDisponible(franjaSeleccionada, asiento)) {
                    asientosSeleccionados.add(asiento);
                    System.out.println("Asiento " + asiento + " añadido a la compra.");
                } else {
                    System.out.println("El asiento " + asiento + " no está disponible.");
                }
            } else {
                System.out.println("Asiento inválido. Use el formato correcto (ej: A3).");
            }
            
            System.out.print("¿Desea comprar otro asiento? (s/n): ");
            String respuesta = scanner.nextLine();
            seguirComprando = respuesta.equalsIgnoreCase("s");
        }
        
        // Calcular total a pagar
        if (!asientosSeleccionados.isEmpty()) {
            double total = sala.calcularTotal(asientosSeleccionados);
            
            System.out.println("\n===== RESUMEN DE COMPRA =====");
            System.out.println("Sala: " + sala.getNumero());
            System.out.println("Función: " + franjaSeleccionada + " - " + 
                    funciones.get(franjaSeleccionada).getNombre());
            System.out.println("Asientos: " + String.join(", ", asientosSeleccionados));
            System.out.println("Total a pagar: $" + total);
            
            System.out.print("\n¿Confirmar compra? (s/n): ");
            String confirmar = scanner.nextLine();
            
            if (confirmar.equalsIgnoreCase("s")) {
                // Marcar asientos como no disponibles
                for (String asiento : asientosSeleccionados) {
                    sala.comprarAsiento(franjaSeleccionada, asiento);
                }
                System.out.println("¡Compra realizada con éxito! Disfrute la función.");
            } else {
                System.out.println("Compra cancelada.");
            }
        } else {
            System.out.println("No se seleccionaron asientos. Compra cancelada.");
        }
    }
}

class Pelicula {
    private String nombre;
    private String idioma;
    private String tipo; // 35mm o 3D
    private int duracion; // en minutos
    
    public Pelicula(String nombre, String idioma, String tipo, int duracion) {
        this.nombre = nombre;
        this.idioma = idioma;
        this.tipo = tipo;
        this.duracion = duracion;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getIdioma() {
        return idioma;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public int getDuracion() {
        return duracion;
    }
}

abstract class Sala {
    protected int numero;
    protected int filasGenerales;
    protected int sillasGenerales;
    protected Map<String, Pelicula> funciones; // franja -> película
    protected Map<String, List<String>> asientosOcupados; // franja -> lista de asientos
    
    public Sala(int numero, int filasGenerales, int sillasGenerales) {
        this.numero = numero;
        this.filasGenerales = filasGenerales;
        this.sillasGenerales = sillasGenerales;
        this.funciones = new HashMap<>();
        this.asientosOcupados = new HashMap<>();
    }
    
    public int getNumero() {
        return numero;
    }
    
    public Map<String, Pelicula> getFunciones() {
        return funciones;
    }
    
    public void asignarFuncion(String franja, Pelicula pelicula) {
        funciones.put(franja, pelicula);
        asientosOcupados.put(franja, new ArrayList<>());
    }
    
    public abstract int getCapacidadTotal();
    
    public int contarAsientosDisponibles(String franja) {
        if (!asientosOcupados.containsKey(franja)) {
            return 0;
        }
        return getCapacidadTotal() - asientosOcupados.get(franja).size();
    }
    
    public boolean esAsientoDisponible(String franja, String asiento) {
        if (!asientosOcupados.containsKey(franja)) {
            return false;
        }
        return !asientosOcupados.get(franja).contains(asiento) && esAsientoValido(asiento);
    }
    
    public void comprarAsiento(String franja, String asiento) {
        if (asientosOcupados.containsKey(franja)) {
            asientosOcupados.get(franja).add(asiento);
        }
    }
    
    public abstract boolean esAsientoValido(String asiento);
    
    public abstract double calcularTotal(List<String> asientos);
    
    public abstract void mostrarDisponibilidad(String franja);
}

class SalaNormal extends Sala {
    private int filasPreferenciales;
    private int sillasPreferenciales;
    
    public SalaNormal(int numero, int filasGenerales, int sillasGenerales, 
            int filasPreferenciales, int sillasPreferenciales) {
        super(numero, filasGenerales, sillasGenerales);
        this.filasPreferenciales = filasPreferenciales;
        this.sillasPreferenciales = sillasPreferenciales;
    }
    
    @Override
    public int getCapacidadTotal() {
        return (filasGenerales * sillasGenerales) + (filasPreferenciales * sillasPreferenciales);
    }
    
    @Override
    public boolean esAsientoValido(String asiento) {
        if (asiento.length() < 2) {
            return false;
        }
        
        char fila = asiento.charAt(0);
        int silla;
        
        try {
            silla = Integer.parseInt(asiento.substring(1));
        } catch (NumberFormatException e) {
            return false;
        }
        
        if (fila >= 'A' && fila < 'A' + filasGenerales) {
            return silla >= 1 && silla <= sillasGenerales;
        } else if (fila >= 'G' && fila < 'G' + filasPreferenciales) {
            return silla >= 1 && silla <= sillasPreferenciales;
        }
        
        return false;
    }
    
    @Override
    public double calcularTotal(List<String> asientos) {
        double total = 0;
        
        for (String asiento : asientos) {
            char fila = asiento.charAt(0);
            
            if (fila >= 'A' && fila < 'A' + filasGenerales) {
                total += 8000; // Sección general
            } else if (fila >= 'G' && fila < 'G' + filasPreferenciales) {
                total += 12000; // Sección preferencial
            }
        }
        
        return total;
    }
    
    @Override
    public void mostrarDisponibilidad(String franja) {
        System.out.println("\n===== DISPONIBILIDAD DE ASIENTOS =====");
        System.out.println("Sala " + numero + " - Franja " + franja);
        System.out.println("(Sección General: $8,000 | Sección Preferencial: $12,000)");
        
        List<String> ocupados = asientosOcupados.getOrDefault(franja, new ArrayList<>());
        
        // Mostrar sección general
        System.out.println("\nSECCIÓN GENERAL:");
        
        // Imprimir encabezados de columnas
        System.out.print("   ");
        for (int j = 1; j <= sillasGenerales; j++) {
            System.out.printf("%2d ", j);
        }
        System.out.println();
        
        // Imprimir filas de la sección general
        for (int i = 0; i < filasGenerales; i++) {
            char fila = (char) ('A' + i);
            System.out.print(fila + "  ");
            
            for (int j = 1; j <= sillasGenerales; j++) {
                String asiento = fila + String.valueOf(j);
                if (ocupados.contains(asiento)) {
                    System.out.print("[X] ");
                } else {
                    System.out.print("[ ] ");
                }
            }
            System.out.println();
        }
        
        // Mostrar sección preferencial si existe
        if (filasPreferenciales > 0) {
            System.out.println("\nSECCIÓN PREFERENCIAL:");
            
            // Imprimir encabezados de columnas
            System.out.print("   ");
            for (int j = 1; j <= sillasPreferenciales; j++) {
                System.out.printf("%2d ", j);
            }
            System.out.println();
            
            // Imprimir filas de la sección preferencial
            for (int i = 0; i < filasPreferenciales; i++) {
                char fila = (char) ('G' + i);
                System.out.print(fila + "  ");
                
                for (int j = 1; j <= sillasPreferenciales; j++) {
                    String asiento = fila + String.valueOf(j);
                    if (ocupados.contains(asiento)) {
                        System.out.print("[X] ");
                    } else {
                        System.out.print("[ ] ");
                    }
                }
                System.out.println();
            }
        }
        
        System.out.println("\nLeyenda: [ ] Disponible | [X] Ocupado");
        int disponibles = contarAsientosDisponibles(franja);
        System.out.println("Asientos disponibles: " + disponibles + " de " + getCapacidadTotal());
    }
}

class Sala3D extends Sala {
    
    public Sala3D(int numero, int filasGenerales, int sillasGenerales) {
        super(numero, filasGenerales, sillasGenerales);
    }
    
    @Override
    public int getCapacidadTotal() {
        return filasGenerales * sillasGenerales;
    }
    
    @Override
    public boolean esAsientoValido(String asiento) {
        if (asiento.length() < 2) {
            return false;
        }
        
        char fila = asiento.charAt(0);
        int silla;
        
        try {
            silla = Integer.parseInt(asiento.substring(1));
        } catch (NumberFormatException e) {
            return false;
        }
        
        return fila >= 'A' && fila < 'A' + filasGenerales && silla >= 1 && silla <= sillasGenerales;
    }
    
    @Override
    public double calcularTotal(List<String> asientos) {
        // Todas las entradas para la sala 3D cuestan 10,000
        return asientos.size() * 10000;
    }
    
    @Override
    public void mostrarDisponibilidad(String franja) {
        System.out.println("\n===== DISPONIBILIDAD DE ASIENTOS =====");
        System.out.println("Sala " + numero + " (3D) - Franja " + franja);
        System.out.println("(Todas las entradas: $10,000)");
        
        List<String> ocupados = asientosOcupados.getOrDefault(franja, new ArrayList<>());
        
        // Imprimir encabezados de columnas
        System.out.print("   ");
        for (int j = 1; j <= sillasGenerales; j++) {
            System.out.printf("%2d ", j);
        }
        System.out.println();
        
        // Imprimir filas
        for (int i = 0; i < filasGenerales; i++) {
            char fila = (char) ('A' + i);
            System.out.print(fila + "  ");
            
            for (int j = 1; j <= sillasGenerales; j++) {
                String asiento = fila + String.valueOf(j);
                if (ocupados.contains(asiento)) {
                    System.out.print("[X] ");
                } else {
                    System.out.print("[ ] ");
                }
            }
            System.out.println();
        }
        
        System.out.println("\nLeyenda: [ ] Disponible | [X] Ocupado");
        int disponibles = contarAsientosDisponibles(franja);
        System.out.println("Asientos disponibles: " + disponibles + " de " + getCapacidadTotal());
    }
}