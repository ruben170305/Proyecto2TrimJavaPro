package blownaway.pojo;

import blownaway.exception.PiezaSeRompeException;
import blownaway.main.Concurso;

public class PruebaIndividual extends Prueba{
    private Participante ganador;
    private Participante perdedor;
    
    public  PruebaIndividual(String nombre, String[] habilidades) throws PiezaSeRompeException {
        super(nombre, habilidades, Prueba.HORAS_INDIV);
        super.preparacion();
    }

    /**
     * Metodo que evalua a los participantes al final de la prueba y decide el ganador y el perdedor que lo elimina del array
     */
    public void evaluacion() {

        int max_puntuacion = 0, min_puntuacion = 0;

        for( Participante part : super.participantes ) {
            if( puntuaciones.get(part) > max_puntuacion ) {
                max_puntuacion = puntuaciones.get( part );
                this.ganador = part;
            }

            if( puntuaciones.get( part ) < min_puntuacion || min_puntuacion == 0 ) {
                min_puntuacion = puntuaciones.get( part );
                perdedor = part;
            }
            part.descansosEntrePruebas();
        }

        ganador.ganaPrueba();
        
        //Muestro el ganador y el que queda eliminado
        System.out.println("Ha ganado: " + ganador.getNombre() );
        System.out.println("Queda eliminado: " + perdedor.getNombre());
        
        //Elimino de la lista concursoCopy el perdedor y de la de ganadores añado el ganador
        Concurso.concursoCopy.remove(perdedor);
        Concurso.ganadoresConcurso.add(ganador.getNombre());
    }

    }
