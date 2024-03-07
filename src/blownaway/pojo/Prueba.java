package blownaway.pojo;

import blownaway.main.Concurso;
import blownaway.exception.PiezaSeRompeException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public abstract class Prueba {
    public static final int HORAS_INDIV=4;
    public static final int HORAS_FINAL=8;
    public static final int HORAS_PAR=5;

    protected String nombre;
    protected String[] habilidades = new String[3];
    protected ArrayList<Participante> participantes = new ArrayList<Participante>();
    protected HashMap<Participante, Integer> puntuaciones = new HashMap<Participante, Integer>();
    protected int duracion;

    public Prueba(String nombre, String[] habilidades, int duracion) {
        this.nombre = nombre;
        this.duracion = duracion;
        participantes.addAll(Concurso.concursoCopy);
        System.out.println();
        asignarHabilidadesPrueba();
        mostrarInfo();
    }

    /**
     * Metodo que añade las habilidades a la prueba
     */
    public void asignarHabilidadesPrueba() {
    	Random rd = new Random();
    	ArrayList<String> listaHabilidades = new ArrayList<String>();
    	for (int i = 0; i < habilidades.length; i++) {
			listaHabilidades.add(Concurso.HABILIDADES_SOPLADO_VIDRIO[i]);
		}
    	for (int i = 0; i < habilidades.length; i++) {
    		int random = rd.nextInt(listaHabilidades.size());
			habilidades[i] = listaHabilidades.get(random);
			listaHabilidades.remove(random);
		}
    }
    
    /**
     * Método preparacion del concurso
     * @throws PiezaSeRompeException Excepcion si se rompe la pieza
     */
    public void preparacion() throws PiezaSeRompeException{
    	System.out.println();
    	System.out.println("Puntuaciones iniciales:");
    	System.out.println("------------------------");
        for (Participante p : participantes) {
        	int puntos = puntuacionesHab(p);
            puntuaciones.put(p, puntos);
            System.out.println(p.getNombre()+": "+puntos);
            p.setEstres(p.getNIVEL_ESTRES_BASE());
        }
        this.planificacion();
        
    }

    /**
     * Metodo que suma 1 punto si coinciden las habilidades del jugador con las de la prueba
     * @param p Participante
     * @return Devuelve los puntos que se le deben sumar
     */
    public int puntuacionesHab(Participante p) {
    	int punt = 0;
    	for (int i = 0; i < habilidades.length; i++) {
			for (int j = 0; j < p.habilidades.size(); j++) {
				if(habilidades[i] == p.habilidades.get(j)) {
					punt++;
					break;
				}
			}
		}
    	return punt;
    }
    /**
     * Metodo que planifica la prueba y da 10 puntos a cada participante por el diseño de la pieza
     */
    public void planificacion() throws PiezaSeRompeException {
        for (Participante p : participantes) {
            sumarPuntuacion(puntuaciones, p, 10);
        }
        this.ejecucion();
    }

    /**
     * Metodo que ejecuta la prueba y va actualizando la puntuacion de los participantes y el estres
     * @throws PiezaSeRompeException Excepcion que se lanza en caso en el que la pieza se rompa
     */
    public void ejecucion() throws PiezaSeRompeException {
        int dur = duracion;
        Random puntosmas = new Random();
        while (dur>0) {
        	if (dur>1) {
        		System.out.println("\nFaltan "+dur+" horas.");
			} else if (dur==1) {
				System.out.println("\n¡¡Falta sólo una hora para terminar!!");
			}
            for (Participante p : participantes) {
                sumarPuntuacion(puntuaciones, p, puntosmas.nextInt(11));
                try {
                	p.pesoDelConcurso();
				} catch (PiezaSeRompeException psre) {
					// TODO: handle exception
					int puntosAQuitar = puntosmas.nextInt(puntuaciones.get(p));
					if (puntosAQuitar > (puntuaciones.get(p) /2 ) ) {
						System.out.println("La pieza de "+p.getNombre()+" se ha roto bastante peligra tu continuidad");
					} else {
						System.out.println("La pieza de "+p.getNombre()+" se ha roto poco, puedes recuperarte");
					}
					restarPuntuacion(puntuaciones, p, puntosAQuitar);
				}
                System.out.print(p.getNombre()+ ": ");
                pintarAvance(puntuaciones.get(p));
                System.out.println();
            }
            dur--;
        }
        evaluacion();
    }
    
    /**
     * Metodo que imprime "-" por cada punto que tenga el participante
     * @param puntos Puntos actuales del participante
     */
    public void pintarAvance(int puntos) {
    	for (int i = 0; i < puntos; i++) {
    		System.out.print("-");
		}
    }

    /**
     * Metodo abstracto que se implementa en las hijas
     */
    public void evaluacion() {}

    /**
     * Metodo que muestra la informacion de la prueba y las habilidades requeridas
     */
    public void mostrarInfo() {
        System.out.println("Prueba: " + nombre);
        System.out.println("-----------------");
        System.out.println("Habilidades: ");
        for (String h : habilidades) {
            System.out.println("-"+h);
        }
    }
    /**
     * Metodo que suma la puntuacion de un participante y lo actualiza en el hashmap
     * @param puntuaciones Recibe el hashmap a actualizar
     * @param participante Recibe el participante al que se le suma la puntuacion
     * @param valorASumar Recibe el valor que se le suma a la puntuacion
     */
    public void sumarPuntuacion(HashMap<Participante, Integer> puntuaciones, Participante participante, int valorASumar) {
        // Obtener la puntuación actual del participante
        Integer puntuacionActual = puntuaciones.get(participante);

        // Verificar si el participante existe en el HashMap y la puntuación actual no es null
        if (puntuacionActual != null) {
            // Sumar el valor al puntuación actual
            puntuaciones.put(participante, puntuacionActual + valorASumar);
        }
    }
    /**
     * Metodo que resta la puntuacion de un participante y lo actualiza en el hashmap
     * @param puntuaciones Puntuacion actual
     * @param participante Participante
     * @param valorARestar Cuanto quiero restar
     */
    public void restarPuntuacion(HashMap<Participante, Integer> puntuaciones, Participante participante, int valorARestar) {
        // Obtener la puntuación actual del participante
        Integer puntuacionActual = puntuaciones.get(participante);

        // Verificar si el participante existe en el HashMap y la puntuación actual no es null
        if (puntuacionActual != null) {
            // Sumar el valor al puntuación actual
            puntuaciones.put(participante, puntuacionActual - valorARestar);
        }
    }

	public ArrayList<Participante> getParticipantes() {
		return participantes;
	}
    
}
