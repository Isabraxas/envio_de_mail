package com.viridian.kafkajson.model;

import lombok.*;

/**
 * Created by marcelo on 12-07-18
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Client {
    private String id;
    private String nombre;
    private int edad;
    private String extensionFile;
    private byte[] foto;

    public Client(String id, String nombre, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getExtensionFile() {
        return extensionFile;
    }

    public void setExtensionFile(String extensionFile) {
        this.extensionFile = extensionFile;
    }
}
