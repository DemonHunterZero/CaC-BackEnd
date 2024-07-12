package com.ar.apimovies;

import java.sql.Connection; //cn
import java.sql.ResultSet; //rs
//import java.sql.Statement;  //stm
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement; //pstm

/* DAO Data Access Objet */
/* Esta clase gestiona el acceso a los datos. En este caso para la entidad Pelicula */
public class PeliculaDAO {

   /*Metodo para agregar una pelicula a la base de datos */
   public Long agregarPelicula(Pelicula pelicula) {
      DatabaseConnection conexion = new DatabaseConnection();     /* Utiliza la clase DatabaseConnection para conectarse a la base de datos. Crea una nueva coneccion */
		
      /*Permite ejecutar consultas SQL simples sin parámetros. Generalmente se usa para ejecutar consultas de tipo SELECT, INSERT, UPDATE, etc. */
      /*Hay un ejemplo mas abajo comentado */
      //Statement stm = null;

      /*Permite ejecutar consultas con parametros precomiladas */
      PreparedStatement pstm = null;
      
      /*Nos permite acceder a los datos resultantes de la consula a la bd */
      ResultSet rs = null;

      String insertarPeliculaSql = "INSERT INTO peliculas(titulo,genero,duracion,imagen) VALUES(?,?,?,?)";  //aloja la consulta en una variable string
      
      /*Es una instancia de la clase coneccion que almacenara el resultado del metodo conectar de la instancia conexion de DatabaseConecction */
      Connection cn = conexion.conectar();

      try {
         pstm = cn.prepareStatement(insertarPeliculaSql);   //prepara la consulta para ser ejecutada. Es un metodo de la clase Conecction
         
         /*Configura los valores de la consulta segun el index y esos valores seran los que los metodos get de la instancia pelicula nos devuelva */
         pstm.setString(1, pelicula.getTitulo());
         pstm.setString(2, pelicula.getGenero());
         pstm.setString(3, pelicula.getDuracion());
         pstm.setString(4, pelicula.getImagen());

         /* Ejecuta la consulta pstm a traves de su metodo y eso nos devolvera un numero que sera guardado en la variable result */
         int result = pstm.executeUpdate(); //pstm.executeUpdate(); nos devuelve un numero que es el numero de filas(registros) afectadas

         if(result > 0) {

            rs = pstm.getGeneratedKeys(); //Si una fila fue insertada nos devuelve el numero de id de esa fila

            /* rs.next nos devuelve true si hay una nueva fila y false sino */
            if(rs.next()) {
               Long id = rs.getLong(1); //el index 1 pertenece al id en sql
               System.out.println("Se cargo exitosamente un nueva pelicula");
               return id;
            }
            else {
               System.out.println("Error al obtener ID de la pelicula insertada");
               return null;
            }
         }
         else {
            System.out.println("Error al insertar la pelicula");
            return null;
         }

      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }
   }

   public List<Pelicula> mostrarPeliculas() {

      DatabaseConnection conexion = new DatabaseConnection();
      Connection cn = conexion.conectar();
		
      List<Pelicula> listaPeliculas = new ArrayList<>();

      //Statement stm = null;
      PreparedStatement pstm = null;
      ResultSet rs = null;

      String SelectPeliculaSql = "SELECT * FROM peliculas";
      
      try {
			/*Hay dos formas */

			/*Forma 1 */
			/* 
				stm = cn.createStatement();
				stm.executeQuery(selectPeliculaSql);
			*/

			/*Forma 2 */
         pstm = cn.prepareStatement(SelectPeliculaSql);
         rs = pstm.executeQuery();

         while (rs.next()) {
            /*Ambas formas estan bien */
				/*
               rs.getLong(0);
               rs.getLong(1);
               rs.getLong(2);
               rs.getLong(3);
               rs.getLong(4);
				 */
            Long idP = rs.getLong("id");
            String tit = rs.getString("titulo");
            String gen = rs.getString("genero");
            String durac = rs.getString("duracion");
            String img = rs.getString("imagen");

            Pelicula pelicula = new Pelicula(idP, tit, gen, durac, img);

            listaPeliculas.add(pelicula);
         }

      } catch (Exception e) {
         e.printStackTrace();
      }
      return listaPeliculas;
   }

}










