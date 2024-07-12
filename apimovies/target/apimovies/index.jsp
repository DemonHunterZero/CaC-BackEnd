<!DOCTYPE html>
<html lang="en">
<head>
   <meta charset="UTF-8">
   <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <title>Testeando</title>
   <script>
         function mostrarPeliculas() {
            fetch('http://localhost:8080/apimovies/peliculas')
               .then(response => response.json())
               .then(data => {
                     let listaPeliculas = document.getElementById('listaPeliculas');
                     listaPeliculas.innerHTML = '';
                     data.forEach(peli => {
                        let listItem = document.createElement('li');
                        listItem.textContent = `${peli.idPelicula} - ${peli.titulo} - ${peli.genero} - ${peli.duracion} - ${peli.imagen}`;
                        listaPeliculas.appendChild(listItem);
                  });
               })
               .catch(error => console.error('Error:', error));
         }

         function agregarPelicula(event) {
            event.preventDefault();

            let titulo = document.getElementById('titulo').value;
            let genero = document.getElementById('genero').value;
            let duracion = document.getElementById('duracion').value;
            let imagen = document.getElementById('imagen').value;

            let peli = {
               titulo: titulo,
               genero: genero,
               duracion: duracion,
               imagen: imagen
            };

            fetch('http://localhost:8080/apimovies/peliculas', {
               method: 'POST',
               headers: {
                  'Content-Type': 'application/json'
               },
               body: JSON.stringify(peli)
            })
            .then(response => response.json())
            .then(data => {
               console.log('Success:', data);
               mostrarPeliculas();
            })
            .catch(error => console.error('Error:', error));
         }
   </script>
</head>
<body>
   <h1>Probando Api Movies</h1>

   <h2>Obtener Peliculas</h2>
   <button onclick="mostrarPeliculas()">Mostrar Peliculas</button>
   <ul id="listaPeliculas"></ul>

   <h2>Agregar Pelicula</h2>
   <form onsubmit="agregarPelicula(event)">
      <label for="titulo">Titulo:</label><br>
      <input type="text" id="titulo" name="titulo" required><br>
      <label for="genero">Genero:</label><br>
      <input type="text" id="genero" name="genero" required><br>
      <label for="duracion">Duracion:</label><br>
      <input type="text" id="duracion" name="duracion" required><br>
      <label for="imagen">Imagen:</label><br>
      <input type="text" id="imagen" name="imagen"><br><br>
      <input type="submit" value="Submit">
   </form>
</body>
</html>