package gm.contactos.controlador;

import gm.contactos.modelo.Contacto;
import gm.contactos.servicio.ContactoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/agregar")
    public String agregar(@ModelAttribute("contactoForma")Contacto contacto){
        logger.info("Contacto agregar: "+ contacto);
        contactoServicio.guardarContacto(contacto);
        return "redirect:/";    // Redirije al controlador PATH de inicio ya que este se cargara con el nuevo contacto, es decir actualiza la lista
    }

    /**
     * Metodo que toma los datos del contacto guardados para ser mostrados en el formuario de editar
     * y de esta manera poder cambiar los datos, no guarda solo muestra los valores
     * @param idContacto
     * @param modelo
     * @return
     */
    @GetMapping("/editar/{id}")
    public String mostrarEditar(@PathVariable(value = "id") int idContacto, ModelMap modelo){
        Contacto contacto = contactoServicio.buscarContactoPorId(idContacto);
        logger.info("Contacto a editar (Mostar): "+contacto);
        modelo.put("contacto",contacto);
        return "editar";        //Redirige a la pagina de editar.html
    }

    /**
     * Metodo que guarda los valores cambiados en el formulario de editar
     * @param contacto
     * @return
     */
    @PostMapping("/editar")
    public String editar(@ModelAttribute("contacto") Contacto contacto){
        logger.info("Contacto a guardar (editar): " + contacto);
        contactoServicio.guardarContacto(contacto);
        return "redirect:/";    //Se redirecciona al Path para actualizar la lista y el contacto editado
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") int idContacto){
        Contacto contacto = new Contacto();
        contacto.setIdContacto(idContacto);
        contactoServicio.elminarContacto(contacto);
        return "redirect:/";        //Se rederige al PATH principal para actualizar la lista con el contacto eliminado
    }

}
