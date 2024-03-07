/**
 * 
 */
package blownaway.main;

import blownaway.exception.PiezaSeRompeException;
import blownaway.pojo.Participante;
import blownaway.pojo.Prueba;
import blownaway.pojo.PruebaFinal;
import blownaway.pojo.PruebaIndividual;
import blownaway.pojo.PruebaParejas;

import java.util.ArrayList;
import java.util.Scanner;

public class Concurso {
	// Constante de las habilidades
	public static final String[] HABILIDADES_SOPLADO_VIDRIO = { "Técnica", "Escultura", "Realismo", "Creatividad",
			"Uso del color", "Texturas" };
	public static ArrayList<Participante> concursantes = new ArrayList<Participante>();
	public static ArrayList<Participante> concursoCopy = new ArrayList<Participante>();

	public static ArrayList<String> ganadoresConcurso = new ArrayList<String>();

	public static void main(String[] args) throws PiezaSeRompeException {
		// TODO Auto-generated method stub
		int opcion = 0;
		// Bucle para que se repita hasta pulsar 5
		do {
			// Menú principal
			System.out.println("\n** MENU PRINCIPAL **");
			System.out.println("1. Mostrar los participantes");
			System.out.println("2. Añadir participante nuevo");
			System.out.println("3. Empezar el concurso");
			System.out.println("4. Mostrar los resultados del concurso");
			System.out.println("5. Salir del programa");

			System.out.print("Introduce la opción que quieres hacer: ");
			opcion = scannerInt();
			// Switch para elegir la opción
			switch (opcion) {
			case 1:
				if (concursantes.isEmpty()) {
					System.out.println("Aún no hay sopladores en el concurso.");
				} else {
					mostrarParticipantes();
				}
				break;
			case 2:
				nuevoParticipante();
				break;
			case 3:
				empezarConcurso();
				break;
			case 4:
				resultados();
				break;
			case 5:
				System.out.println("¡Gracias por jugar!");
				break;
			default:
				System.out.println("Opción no disponible");
				break;
			}
		} while (opcion != 5);
	}

	/**
	 * Método que escanea un String
	 * 
	 * @return Devuelve un String
	 */
	public static String scannerString() {
		Scanner st = new Scanner(System.in);
		String s = st.nextLine();
		return s;
	}

	/**
	 * Método para convertir de String a int controlando las excepciones
	 * 
	 * @return Entero transformado
	 * @throws NumberFormatException Excepcion que lanza si el String no es un
	 *                               entero
	 */
	public static int scannerInt() throws NumberFormatException {
		Scanner sint = new Scanner(System.in);
		int b = 0;
		boolean esNumero = false;
		do {
			String a = sint.nextLine();
			try {
				b = Integer.parseInt(a);
				esNumero = true;
			} catch (NumberFormatException nfe) {
				System.out.println("Este campo solo puede ser un número.");
				esNumero = false;
			}
		} while (!esNumero);
		return b;
	}

	/**
	 * Método para añadir un nuevo participante al concurso. Lee el nombre, la edad
	 * y la experiencia del participante.
	 */
	public static void nuevoParticipante() {
		System.out.println("\n** PARTICIPANTE NUEVO **");
		String name = "";
		boolean isCorrect = false;
		do {
			System.out.print("Introduce el nombre del participante: ");
			name = scannerString();
			try {
				Integer.parseInt(name);
			} catch (NumberFormatException nfe) {
				// TODO: handle exception
				isCorrect = true;
			}
			if (!isCorrect) {
				System.err.println("El nombre no es válido");
			}
		} while (!isCorrect);
		int edad = 0;
		do {
			System.out.print("Introduce la edad del participante: ");
			edad = scannerInt();
			if (edad < 18) {
				System.err.println("Tienes que ser mayor de edad");
				edad = 0;
			}
			if (edad>110) {
				System.err.println("Edad no válida");
				edad=0;
			}
		} while (edad == 0);
		boolean comprobacion = false;
		int exp = 0;
		do {
			System.out.print("Introduce cuantos años de experiencia tienes: ");
			exp = scannerInt();
			if (edad - exp >= 10) {
				comprobacion = true;
			} else {
				System.err.println("Tu experiencia no es válida.");
				comprobacion = false;
			}
		} while (!comprobacion);
		concursantes.add(new Participante(name, edad, exp));
		System.out.println();
	}

	/**
	 * Método para mostrar los participantes del concurso.
	 */
	public static void mostrarParticipantes() {
		System.out.println("Participante: ");
		for (Participante c : concursantes) {
			c.mostrarInfo();
			System.out.println();
		}
	}

	/**
	 * Método para empezar el concurso
	 * 
	 * @throws PiezaSeRompeException Excepcion si se rompe la pieza
	 */
	public static void empezarConcurso() throws PiezaSeRompeException {
		// TODO Auto-generated method stub
		int cantidad = concursoCopy.size();
		String nombrePrueba;
		int duracion;
		boolean ispruebaParejas = false;
		int i = 1;
		if (concursantes.size() == 0 || concursantes.size() == 1) {
			System.out.println("No tienes los participantes suficientes para empezar");
		} else {
			concursoCopy.clear();
			ganadoresConcurso.clear();
			concursoCopy.addAll(concursantes);
			do {
				if (concursoCopy.size() > 2) {
					if (concursoCopy.size() == 4 && !ispruebaParejas) {
						nombrePrueba = "Prueba en parejas";
						new PruebaParejas(nombrePrueba, HABILIDADES_SOPLADO_VIDRIO);
						ispruebaParejas = true;
					} else {
						nombrePrueba = "Prueba " + i;
						new PruebaIndividual(nombrePrueba, HABILIDADES_SOPLADO_VIDRIO);
						cantidad = concursoCopy.size();
						i++;
					}
					cantidad = concursoCopy.size();
				} else if (concursoCopy.size() == 2) {
					nombrePrueba = "Final";
					duracion = Prueba.HORAS_FINAL;
					new PruebaFinal(nombrePrueba, HABILIDADES_SOPLADO_VIDRIO);
				}
			} while (concursoCopy.size() > 1);
		}

	}

	/**
	 * Método de mostrar el array de ganadores
	 */
	public static void resultados() throws IndexOutOfBoundsException{
		System.out.println();
		try {
			System.out.println("** GANADORES **");
			if (ganadoresConcurso.size() > 1) {
				int c = 1;
				for (int i = 0; i < ganadoresConcurso.size() - 1; i++) {
					System.out.println("Prueba " + c + ": " + ganadoresConcurso.get(i));
					c++;
				}
				System.out.println("Final: " + ganadoresConcurso.get(c - 1));
			} else {
				System.out.println("Final: " + ganadoresConcurso.get(0));
			}
		}catch (IndexOutOfBoundsException iobe) {
			// TODO: handle exception
			System.err.println("No se ha iniciado el concurso");
		}
		
	}

	/**
	 * Metodo que recorre el array HABILIDADES SOPLADO VIDRIO
	 * 
	 * @return Lo devuelve en forma de String
	 */
	public static String recorrerArray() {
		String h = "";
		for (int i = 0; i < HABILIDADES_SOPLADO_VIDRIO.length; i++) {
			h = h + " " + HABILIDADES_SOPLADO_VIDRIO;
		}
		return h;
	}
}
