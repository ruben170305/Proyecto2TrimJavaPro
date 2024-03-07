package blownaway.pojo;

import java.util.ArrayList;

import blownaway.exception.PiezaSeRompeException;
import blownaway.main.Concurso;

public class PruebaParejas extends Prueba {
	public Participante ganador;
	
	public Participante grupo1[] = new Participante[2];
	public Participante grupo2[] = new Participante[2];
	
	public PruebaParejas(String nombre, String[] habilidades) throws PiezaSeRompeException{
		super(nombre, habilidades, Prueba.HORAS_PAR);
        super.preparacion();
	}
	
	public void asignarGrupos() {
		grupo1[0]=Concurso.concursoCopy.get(0);
		grupo1[1]=Concurso.concursoCopy.get(1);
		grupo2[0]=Concurso.concursoCopy.get(2);
		grupo2[1]=Concurso.concursoCopy.get(3);
		
		System.out.println();
		System.out.println("*** Grupo 1 ***");
		System.out.println(grupo1[0]+", "+grupo1[1]);
		System.out.println("*** Grupo 2 ***");
		System.out.println(grupo2[0]+", "+grupo2[1]);
	}
	
	/**
     * Metodo que evalua a los participantes al final de la prueba y decide el ganador y el perdedor que lo elimina del array
     */
    public void evaluacion() {
    	
    	asignarGrupos();

        int max_puntuacion = 0, min_puntuacion = 0, suma1=0, suma2=0;
        
        for (Participante p : grupo1) {
			suma1 = suma1 + puntuaciones.get(p);
		}
        System.out.println("Puntos del grupo 1: "+suma1);
        
        for (Participante p : grupo2) {
        	suma2 = suma2 + puntuaciones.get(p);
		}
        System.out.println("Puntos del grupo 2: "+suma2);
        
        if (suma1>suma2) {
        	for (Participante p : grupo1) {
				p.ganaPrueba();
			}
        	for (Participante p : grupo2) {
				p.descansosEntrePruebas();
			}
        	System.out.println("Ganadores: "+ponerGanadorString(grupo1));
			Concurso.ganadoresConcurso.add(ponerGanadorString(grupo1));
        } else if (suma1<suma2) {
        	for (Participante p : grupo2) {
				p.ganaPrueba();
			}
        	for (Participante p : grupo1) {
				p.descansosEntrePruebas();
			}
        	System.out.println("Ganadores: "+ponerGanadorString(grupo2));
        	Concurso.ganadoresConcurso.add(ponerGanadorString(grupo2));
        } else {
        	Concurso.ganadoresConcurso.add("EMPATE");
        }
        
	}
	
	public String ponerGanadorString(Participante[] array) {
		String unir = "";
		for (Participante p : array) {
			unir = unir+ " y " + p.getNombre();
		}
		return unir;
	}
}
