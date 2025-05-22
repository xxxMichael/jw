/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package home.practcica;

import com.google.gson.Gson;import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

class Estudiante {
    public String cedula;
    public String nombre;
    public String apellido;
    public String direccion;
    public String telefono;

    public Estudiante(String cedula, String nombre, String apellido, String direccion, String telefono) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return cedula + " " + nombre + " " + apellido;
    }
}

public class Practcica {

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        String url = "http://localhost/SOA11/SOA/controllers/apirest.php";
        HttpClient cliente = HttpClient.newHttpClient();
        Gson gson = new Gson();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Ver todos");
            System.out.println("2. Buscar por cédula");
            System.out.println("3. Ingresar estudiante");
            System.out.println("4. Salir");
            System.out.print("Elija una opción: ");
            String opcion = sc.nextLine();
            System.out.println(opcion);
            switch (opcion) {
                case "1":
                    verTodos(url, cliente, gson);
                    break;
                case "2":
                    System.out.print("Ingrese cédula a buscar: ");
                    String cedulaBuscar = sc.nextLine();
                    buscarPorCedula(url, cliente, gson, cedulaBuscar);
                    break;
                case "3":
                    System.out.print("Ingrese cédula: ");
                    String cedula = sc.nextLine();
                    System.out.print("Ingrese nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Ingrese apellido: ");
                    String apellido = sc.nextLine();
                    System.out.print("Ingrese dirección: ");
                    String direccion = sc.nextLine();
                    System.out.print("Ingrese teléfono: ");
                    String telefono = sc.nextLine();

                    Estudiante nuevo = new Estudiante(cedula, nombre, apellido, direccion, telefono);
                    guardar(nuevo, gson, url, cliente);
                    break;
                case "4":
                    System.out.println("Saliendo...");
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    public static void verTodos(String url, HttpClient cliente, Gson gson) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest getR = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Content-type", "application/json")
                .GET()
                .build();

        HttpResponse<String> getResponse = cliente.send(getR, HttpResponse.BodyHandlers.ofString());
        String json = getResponse.body();

        Type listType = new TypeToken<ArrayList<Estudiante>>() {}.getType();
        ArrayList<Estudiante> estudiantes = gson.fromJson(json, listType);

        System.out.println("Lista de estudiantes:");
        for (Estudiante e : estudiantes) {
            System.out.println(e);
        }
    }

    public static void buscarPorCedula(String url, HttpClient cliente, Gson gson, String cedula) throws URISyntaxException, IOException, InterruptedException {
        String fullUrl = url + "?cedula=" + cedula;

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI(fullUrl))
                .header("Content-type", "application/json")
                .GET()
                .build();

        HttpResponse<String> getResponse = cliente.send(getRequest, HttpResponse.BodyHandlers.ofString());
        String json = getResponse.body();

        Estudiante est = gson.fromJson(json, Estudiante.class);

        if (est != null && est.cedula != null) {
            System.out.println("Estudiante encontrado: " + est);
        } else {
            System.out.println("No se encontró estudiante con esa cédula.");
        }
    }

    public static void guardar(Estudiante est, Gson gson, String url, HttpClient cliente) throws URISyntaxException, IOException, InterruptedException {
        String pBody = gson.toJson(est);

        HttpRequest post = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(pBody))
                .build();

        HttpResponse<String> postResponse = cliente.send(post, HttpResponse.BodyHandlers.ofString());

        System.out.println("Respuesta POST: " + postResponse.body());
    }
}
