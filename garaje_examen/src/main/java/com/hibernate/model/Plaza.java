package com.hibernate.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

@Entity
@Table(name = "Plazas")
public class Plaza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
   @Column(name="id")
    private int id;
    @Column(name="NumeroPlanta")
    private int numeroPlanta;
    @Column(name="NumeroPlazasLibres")
    private int numeroPlazasLibres;

    public Plaza() {
       super();
    }

    public Plaza( int numeroPlanta, int numeroPlazasLibres) {
        
        this.numeroPlanta = numeroPlanta;
        this.numeroPlazasLibres = numeroPlazasLibres;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumeroPlanta() {
        return numeroPlanta;
    }

    public void setNumeroPlanta(int numeroPlanta) {
        this.numeroPlanta = numeroPlanta;
    }

    public int getNumeroPlazasLibres() {
        return numeroPlazasLibres;
    }

    public void setNumeroPlazasLibres(int numeroPlazasLibres) {
        this.numeroPlazasLibres = numeroPlazasLibres;
    }
}
