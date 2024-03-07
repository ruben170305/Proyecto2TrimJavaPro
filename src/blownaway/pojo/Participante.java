package blownaway.pojo;

import blownaway.exception.PiezaSeRompeException;
import blownaway.main.Concurso;

import java.util.ArrayList;
import java.util.Random;

public class Participante {
    //Datos del participante
    Random rd = new Random();
    private String nombre;
    private int edad;
    private int experiencia;
    private double estres;
    public ArrayList<String> habilidades = new ArrayList<String>();

    //ESTRES
    private final int NIVEL_ESTRES_BASE = 2;
    private final int NIVEL_ESTRES_PELIGRO = 8;

    /**
     * Constructor de la clase Participante
     * @param nombre Nombre del participante
     * @param edad Edad del participante
     * @param experiencia Experiencia del participante
     */
    public Participante (String nombre, int edad, int experiencia){
        this.nombre=nombre;
        this.edad=edad;
        this.experiencia=experiencia;
        this.estres=NIVEL_ESTRES_BASE;
        generarHablidades();
    }

    /**
     * Método que añade habilidades al participante
     */
    public void generarHablidades() {
        int a = Concurso.HABILIDADES_SOPLADO_VIDRIO.length;
        int exp = this.experiencia;
        if (exp>=3){
            if (this.experiencia==3){
                habilidades.add(Concurso.HABILIDADES_SOPLADO_VIDRIO[rd.nextInt(a)]);

            } else {
                while (exp>=3){
                    habilidades.add(Concurso.HABILIDADES_SOPLADO_VIDRIO[rd.nextInt(a)]);
                    exp=exp-3;
                }
            }
        }
    }

    /**
     * Método que gestiona el estres del participante
     * @throws PiezaSeRompeException Excepción que se lanza si el estres es muy alto y se rompe la pieza
     */
    public void pesoDelConcurso() throws PiezaSeRompeException {
        this.estres=this.estres + (rd.nextDouble(2)+1);
        if (this.estres>NIVEL_ESTRES_PELIGRO){
        	int aleatorio = rd.nextInt(21);
            if (aleatorio>12) {
                throw new PiezaSeRompeException("La pieza de "+
                		nombre+" se ha roto porque el concursante estaba muy estresado.");
            } 
        }
    }

    /**
     * Método que reduce el estres si el participante gana una prueba
     */
    public void ganaPrueba(){
        this.estres=NIVEL_ESTRES_BASE;
    }

    /**
     * Método que reduce el estres del participante cuando descansa entre pruebas
     */
    public void descansosEntrePruebas(){
        this.estres=NIVEL_ESTRES_BASE*2;
    }

    /**
     * Método que muestra la información del participante
     */
    public void mostrarInfo() {
        System.out.println(
                "Nombre: "+this.nombre+
                "\n------------------------"+
                "\nEdad: "+this.edad+
                "\nExperiencia: "+this.experiencia);
        mostrarHabilidades();
    }

    /**
     * Método para mostrar las habilidades
     */
    public void mostrarHabilidades() {
        System.out.println("Habilidades: ");
        for (String h : habilidades) {
            System.out.println("-"+h);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<String> getHabilidades() {
        return habilidades;
    }
    
    public String toString() {
    	return nombre;
    }

	public Random getRd() {
		return rd;
	}

	public int getEdad() {
		return edad;
	}

	public int getExperiencia() {
		return experiencia;
	}

	public double getEstres() {
		return estres;
	}

	public int getNIVEL_ESTRES_BASE() {
		return NIVEL_ESTRES_BASE;
	}

	public int getNIVEL_ESTRES_PELIGRO() {
		return NIVEL_ESTRES_PELIGRO;
	}

	public void setEstres(double estres) {
		this.estres = estres;
	}
    
    
}
