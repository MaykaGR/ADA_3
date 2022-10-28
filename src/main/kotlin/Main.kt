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
        var personaje = ""
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
                        if(nombres.item(i).textContent==nombre) {
                            println("Personaje encontrado.")
                            personaje = nombre
                        }}}
                var character = nodoPadre.getElementsByTagName(personaje).item(0) as Element
                var caracteristicas = character.childNodes
                var end = false
                var lista = mutableListOf<String>()
                while(end == false){
                    if(readln()=="exit"){
                        end = true
                    }
                    else {
                        println("¿Qué característica desea ver? -Introducir exit para acabar de introducir características")
                        if(caracteristicas.getElementsByTagName(readln())!=null){
                        lista.add(readln())}
                    }
                }
                for(i in 0..lista.size-1){
                    var caracteristica = caracteristicas.getElementsByTagName(lista[i]).item(0) as Element
                    print(caracteristica.nodeName)
                    print(": ")
                    println(caracteristica.nodeValue)
                }
            }}
            else if(option=="2"){
                println("Hasta pronto!")
                exit = true
            }}
            else{
                println("Comando inválido")
            }}

    //Si el fichero no se encuentra, se muestra el mensaje de error y no se entra en la aplicación
    else{
        println("No se encuentra el fichero XML.")
    }
}