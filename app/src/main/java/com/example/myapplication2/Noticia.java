package com.example.myapplication2;


public class Noticia {
    private String nombre;
    private String href;

    public Noticia(String nombre, String href) {
        this.nombre = nombre;
        this.href = href;
    }

    public String getNombre() {
        return nombre;
    }

    public String getHref() {
        return href;
    }

}
