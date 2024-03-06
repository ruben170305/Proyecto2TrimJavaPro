package blownaway.pojo;

import blownaway.exception.PiezaSeRompeException;
import blownaway.main.Concurso;

public class PruebaFinal extends Prueba{
	public Participante ganador;
	public Participante perdedor;
	
	public PruebaFinal(String nombre, String[] habilidades) throws PiezaSeRompeException{
		super(nombre, habilidades, Prueba.HORAS_FINAL);
        super.preparacion();
	}
	
	/**
     * Metodo que evalua a los participantes al final de la prueba y decide el ganador y el perdedor que lo elimina del array
     */
    public void evaluacion() {

        int max_puntuacion = 0, min_puntuacion = 0;
        for( Participante part : super.participantes ) {
        	if (ganador == null) {
				ganador = part;
			}
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
        Concurso.concursoCopy.remove(perdedor);
        Concurso.ganadoresConcurso.add(ganador.getNombre());
        System.out.println( "HA GANADO EL CONCURSO " + ganador.getNombre()+"!" );
        System.out.println("¡FIN DEL CONCURSO!");
    }
}
