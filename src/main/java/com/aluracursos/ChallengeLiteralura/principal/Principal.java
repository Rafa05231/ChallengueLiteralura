package com.aluracursos.ChallengeLiteralura.principal;

import com.aluracursos.ChallengeLiteralura.model.*;

import com.aluracursos.ChallengeLiteralura.repository.AutorRepository;
import com.aluracursos.ChallengeLiteralura.repository.LibroRepository;
import com.aluracursos.ChallengeLiteralura.service.ConsumoAPI;
import com.aluracursos.ChallengeLiteralura.service.ConvertirDatos;


import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private Scanner leer = new Scanner(System.in);
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvertirDatos convertirDatos = new ConvertirDatos();
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    private List<Libro> libros;
    private List<Autor> autores;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void mostrarMenu(){
        int opcion = 0;

        while(opcion != 6){
            var menu = """
                -----------------------------------------------
                           Bienvenido a Literalura
                      
                Selecciona la opcion que deseas:
                
                1) Buscar libro por titulo
                2) Listar libros registrados
                3) Listar autores registrados
                4) Listar autores vivos en un determinado año
                5) Listar libros por idioma      
                6) Salir           
                           
                -----------------------------------------------
                """;
            System.out.println(menu);
            opcion = leer.nextInt();
            leer.nextLine();

            switch(opcion){
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    listarLibroRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listaAutoresPorAño();
                    break;
                case 5:
                    listaLibrosPorIdioma();
                    break;
                case 6:
                    System.out.println("Gracias por usar Literalura. Hasta pronto!");
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }
        }
    }

    private Datos obtenerDatos(){
        System.out.println("Ingresa el nombre del libro que deseas: ");
        var nombreLibro = leer.nextLine();
        var json = consumoAPI.obtenerDatosApi(URL_BASE+nombreLibro.replace(" ", "+"));
        var datos = convertirDatos.convertirDatos(json, Datos.class);
        return datos;

    }

    private void buscarLibro(){
        Datos nuevoLibro = obtenerDatos();
        if(!nuevoLibro.libros().isEmpty()){
            DatosLibro datosLibro = nuevoLibro.libros().get(0);
            DatosAutor datosAutor = datosLibro.autor().get(0);

            var libroRegistrado = libroRepository.findLibroByTitulo(datosLibro.titulo());
            if(libroRegistrado==null){
                var autorRegistrado = autorRepository.findAutorByNombreIgnoreCase(datosAutor.nombre());
                Libro registroLibro;
                if(autorRegistrado==null){
                    Autor nuevoAutor = new Autor(datosAutor);
                    autorRepository.save(nuevoAutor);
                    registroLibro = new Libro(datosLibro, nuevoAutor);
                }else{
                    registroLibro = new Libro(datosLibro, autorRegistrado);
                }

                libroRepository.save(registroLibro);
                System.out.println("¡Libro registrado con exito!");
                System.out.println(registroLibro);


            }else{
                System.out.println("Este libro " + nuevoLibro.libros().get(0).titulo() +" ya se encuentra registrado");

            }
        }else{
            System.out.println("Este libro no existe.");
        }
    }

    private void listarLibroRegistrados(){
        libros = libroRepository.findAll();

        System.out.println("--------------------------------------------------------------");
        System.out.println("Libros registrados: ");
        libros.stream()
                .sorted(Comparator.comparing(Libro::getIdioma))
                .forEach(System.out::println);
        System.out.println("--------------------------------------------------------------");

    }

    private void listarAutoresRegistrados(){
        autores = autorRepository.findAll();

        System.out.println("--------------------------------------------------------------");
        System.out.println("Autores registrados: ");
        autores.stream()
                .forEach(System.out::println);
        System.out.println("--------------------------------------------------------------");
    }

    private void listaAutoresPorAño(){
        System.out.println("Ingresa el año por el quieres buscar: ");
        var año = leer.nextInt();
        autores = autorRepository.findAutorByFechaNacimientoGreaterThan(año);
        autores.stream()
                .forEach(System.out::println);
    }

    private void listaLibrosPorIdioma(){
        System.out.println("Ingresa el idioma por el quieres buscar: ");
        System.out.println("es - español");
        System.out.println("en - ingles");
        System.out.println("fr - frances");
        System.out.println("pt - portugues");
        var idioma = leer.nextLine();
        var idiomaLibro = Idioma.getIdioma(idioma);
        List<Libro> libroPorIdioma = libroRepository.findLibroByIdioma(idiomaLibro);
        libroPorIdioma.stream()
                .forEach(System.out::println);
    }

}
