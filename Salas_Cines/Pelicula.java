public class Pelicula {
    String nombre;
    String idioma;
    String tipo; // 35mm o 3D
    int duracion;

    public Pelicula(String nombre, String idioma, String tipo, int duracion) {
        this.nombre = nombre;
        this.idioma = idioma;
        this.tipo = tipo;
        this.duracion = duracion;
    }

    @Override
    public String toString() {
        return nombre + " (" + idioma + ", " + tipo + ", " + duracion + " mins)";
    }
    
}