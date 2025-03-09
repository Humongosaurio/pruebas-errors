public class Funcion {
    Sala sala;
    String horario;
    Pelicula pelicula;

    public Funcion(Sala sala, String horario, Pelicula pelicula) {
        this.sala = sala;
        this.horario = horario;
        this.pelicula = pelicula;
    }

    @Override
    public String toString() {
        return "Sala " + sala.numero + " - " + horario + " - " + pelicula.nombre;
    }
    
}
