package com.ar.apimovies;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/peliculas")/* http://elServidor/peliculas http://localhost:8080/apimovies/peliculas */

/*
Una clase Servlet en Java es una clase que se utiliza para manejar solicitudes y respuestas en una aplicación web. 
Los Servlets son componentes del lado del servidor que se ejecutan en un contenedor de servlets (como Apache Tomcat) 
*/

public class PeliculaServlet extends HttpServlet {
/*La mayoria de los Servlets extienden la clase HttpServlet, lo que permite manejar solicitudes HTTP (como GET, POST, PUT, DELETE, etc). */
   private PeliculaDAO peliculaDAO = new PeliculaDAO();

   private ObjectMapper objectMapper = new ObjectMapper();

   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

      /*Definicion de Cors */
      resp.setHeader("Access-Control-Allow-Origin", "*");  //segundo parametro: indica que origenes pueden acceder a este recurso. Por lo general es una url.
      resp.setHeader("Access-Control-Allow-Methods", "*"); //segundo parametro: define los metodos permitidos, pueden ser GET,POST,UPDATE, etc. * es todos.
      resp.setHeader("Access-Control-Allow-Headers", "Content-Type"); //se utiliza para indicar el tipo de contenido que se está enviando en la solicitud. Por ejemplo, si envías datos en formato JSON, el encabezado sería Content-Type: application/json

      req.setCharacterEncoding("UTF-8");      
      resp.setCharacterEncoding("UTF-8");
      /*------------------- */


      Pelicula pelicula = objectMapper.readValue(req.getInputStream(), Pelicula.class); //convierte un json en un objeto de la clase Pelicula.

      Long id = peliculaDAO.agregarPelicula(pelicula); //el metodo agregar pelicula nos devolvia el id del registro cargado en la bd.

      String jsonResponse = objectMapper.writeValueAsString(id); //convierte el id en una cadena (string) json

      resp.setContentType("application/json");
      resp.getWriter().write(jsonResponse);
      resp.setStatus(HttpServletResponse.SC_CREATED); /* 201 creado */

      //super.doPost(req, resp);
   }

   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      
      /*Definicion de Cors */
      resp.setHeader("Access-Control-Allow-Origin", "*");
      resp.setHeader("Access-Control-Allow-Methods", "*");
      resp.setHeader("Access-Control-Allow-Headers", "Content-Type");

      req.setCharacterEncoding("UTF-8");      
      resp.setCharacterEncoding("UTF-8");
      /*------------------- */

      try {
         
         List<Pelicula> peliculas = peliculaDAO.mostrarPeliculas(); //el metodo mostrarPeliculas() nos devolvia una lista de peliculas
         
         String jsonResp = objectMapper.writeValueAsString(peliculas); //convierte la lista de peliculas en una cadena (string) json
         resp.setContentType("application/json");
         resp.getWriter().write(jsonResp);

      } catch (NumberFormatException e) {
         resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"ID invalido"); /* 400 bad request */
         e.printStackTrace();
      }
   }
   
}












