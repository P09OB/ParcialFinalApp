package com.example.parcial;

public class Question {

    String id;
    String pregunta;
    String puntaje;
    String sumatoria;
    String votantes;

    public Question(String id, String pregunta, String puntaje, String sumatoria, String votantes) {
        this.id = id;
        this.pregunta = pregunta;
        this.puntaje = puntaje;
        this.sumatoria = sumatoria;
        this.votantes = votantes;
    }

    public Question() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(String puntaje) {
        this.puntaje = puntaje;
    }

    public String getSumatoria() {
        return sumatoria;
    }

    public void setSumatoria(String sumatoria) {
        this.sumatoria = sumatoria;
    }

    public String getVotantes() {
        return votantes;
    }

    public void setVotantes(String votantes) {
        this.votantes = votantes;
    }
}
