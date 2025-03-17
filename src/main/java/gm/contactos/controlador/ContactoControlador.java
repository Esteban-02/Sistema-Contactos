package gm.contactos.controlador;

import gm.contactos.modelo.Contacto;
import gm.contactos.servicio.ContactoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ContactoControlador {
    private static final Logger logger =
            LoggerFactory.getLogger(ContactoControlador.class);

    /**
     * Se hace inyeccion de Contacto servicio para tener la conexion con los metodos de este servicio y la conexion a la base de datos
     */
    @Autowired
    ContactoServicio contactoServicio;

    @GetMapping("/")            //Se mapea el "/" para al momento que la URL pueda trabajar con este caracter
    public String iniciar(ModelMap modelo){
        List<Contacto>contactos = contactoServicio.listarContactos();                 //Se trae la lista de contactos del servicio
        contactos.forEach((contacto) -> logger.info(contacto.toString())); //Se lee la lista de contactos con un forEach para mostrar en la consola de la lista
        logger.info("Contactos Funcionales");
        modelo.put("contactos", contactos);     //Compartir la informacion del modelo a traves de la vista con un Hashtable con la llave "contactos" que desde la presentacion y proporcionar la lista recuperada de la base de datos
        return "index"; //index.html        Retorna la vista donde sera retornada
    }

    /**
     * Se crea el controlador para responder al URL de agregar y realizar las funciones de agregar nuevos contactos
     * @return
     */
    @GetMapping("/agregar")
    public String mostrarAgregar(){
        return "agregar";
    }

}
