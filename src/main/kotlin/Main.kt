import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.File
import java.io.PrintWriter
import javax.xml.parsers.DocumentBuilderFactory

fun main() {
    //Variable para salir de la aplicación
    var exit = false
    //Se crea un File buscando el fichero XML en la ruta especificada
    val ficheroXML = File("resources${System.getProperty("file.separator")}personajesLol.xml")
    //Si el fichero se encuentra
    if(ficheroXML.exists()){
        //Se crea un Document con la estructura del fichero XML
        var nodoPadre: Document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(ficheroXML)
        //Y se almacena una lista de los nombres de los personajes, para agilizar la búsqueda del personaje
        var nombres = nodoPadre.getElementsByTagName("name")
        //Se entra en la aplicación
        while(exit == false){
            println("¿Qué deseas hacer? -1. Buscar personaje -2. Salir")
            var option = readln()
            //Con la opción 1 se busca personaje
            if(option=="1"){
                //Variable para comprobar si el nombre está en la lista de nombres o no
                var encontrado = false
                //Sino se encuentra, vuelve a pedir el nombre del personaje
                while(encontrado==false){
                println("Introduce el nombre del personaje: ")
                    var nombre = readln()
                    //Se cambia el formato del nombre introducido para que encaje con el formato utilizado en el XML
                    var letra = nombre.toCharArray()[0].toUpperCase()
                    nombre = nombre.toLowerCase().replaceFirstChar { letra }
                    //Contador para recorrer la lista
                var i = 0
                    //Recorre la lista de nombres y sale en el momento en que, o bien se encuentre el nombre o bien se haya recorrido la lista entera
                    //Así, si acaba la lista sin encontrar el nombre, sale de este bucle, pero vuelve a entrar en el anterior al ser aún encontrado = false
                while(i<nombres.length&&encontrado == false){
                    //Cuando encuentra el nombre
                    if(nombres.item(i).textContent==nombre){
                        println("Personaje encontrado. Generando informe...")
                        //Crea el fichero en el que se van a escribir los datos
                        val ficheroEscritura = File("personajes${System.getProperty("file.separator")}${nombre}.txt")
                        //Crea el printwriter
                        val pw = PrintWriter(ficheroEscritura, Charsets.UTF_8)
                        //Crea la variable texto para incluir el texto a escribir
                        var texto = ""
                        //Se busca el nodo del personaje del que queremos obtener la información
                        val personajes = nodoPadre.getElementsByTagName(nombre)
                        //Se recorre la lista de nodos del personaje
                        for(j in 0..personajes.length-1){
                            val nodePersonaje = personajes.item(j)
                            if(nodePersonaje.nodeType == Node.ELEMENT_NODE){
                                val personaje: Element = nodePersonaje as Element
                                //Con los nodos "tags", cogemos sólo el primero
                                val tag = personaje.getElementsByTagName("tags").item(0).textContent
                                //Metemos los nodos que estén dentro del personaje en campos
                                val campos = personaje.childNodes
                                //Recorremos los campos, y vamos comprobando uno a uno para quedarnos sólo con lo que nos interesan
                                for(x in 0..campos.length-1) {
                                    val campo = campos.item(x)
                                    //Cuando el nombre del nodo es alguno de los que queremos guardar la información, guardamos su texto
                                    when(campo.nodeName){
                                        "name" -> texto+="name: ${campo.textContent}\n"
                                        "title" -> texto+="title: ${campo.textContent}\n"
                                        "blurb" -> texto+="blurb: ${campo.textContent}\n"
                                    }
                                }
                                //Luego se le añade el tag que guardamos antes
                                texto+="tags: ${tag}"
                            }

                        }
                        //Se escribe la información con el printwriter (no sé por qué no lo escribe hasta que se cierra el programa)
                        pw.write(texto)
                        //Se cierra el printwriter
                        pw.close()
                        //println(texto)
                        //Y se pasa encontrado a true para cerrar el bucle
                        encontrado = true
                    }
                    //Subir el contador para recorrer la lista
                    i+=1
                }
                    //Si se ha recorrido la lista entera de nombres y no se ha encontrado, muestra el error y vuelve a empezar
                    if(encontrado==false){
                        println("Personaje no encontrado. Vuelve a intentarlo...")}
                }
            }
            //Con la opción 2 se sale de la aplicación, cambiando exit a true
            else if(option=="2"){
                println("Hasta pronto!")
               exit = true
            }
            //Sino se introduce ningún comando válido, se muestra error y se vuelve a pedir
            else{
                println("Comando inválido")
            }
        }}
    //Si el fichero no se encuentra, se muestra el mensaje de error y no se entra en la aplicación
    else{
        println("No se encuentra el fichero XML.")
    }
}