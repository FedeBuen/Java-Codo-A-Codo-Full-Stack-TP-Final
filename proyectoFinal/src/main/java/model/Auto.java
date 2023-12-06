
package model;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Auto {
    private int idAuto;
    private String marca;
    private String modelo;
    private String nacionalidad;
    private String periodo;
    private String potencia;
    private String aceleracion;
    private String velocidad;
    private byte[] imagen;
    private String imagenBase64;
    private double precio;
    
    
    public Auto(int idAuto, String marca, String modelo, String nacionalidad, String periodo, String potencia, String aceleracion, String velocidad, byte[] imagen, double precio) {
        this.idAuto = idAuto;
        this.marca = marca;
        this.modelo = modelo;
        this.nacionalidad = nacionalidad;
        this.periodo = periodo;
        this.potencia = potencia;
        this.aceleracion = aceleracion;
        this.velocidad = velocidad;
        this.imagen = imagen;
        this.precio = precio;
    }

    public Auto(String marca, String modelo, String nacionalidad, String periodo, String potencia, String aceleracion, String velocidad, byte[] imagen, double precio) {
        this.marca = marca;
        this.modelo = modelo;
        this.nacionalidad = nacionalidad;
        this.periodo = periodo;
        this.potencia = potencia;
        this.aceleracion = aceleracion;
        this.velocidad = velocidad;
        this.imagen = imagen;
        this.precio = precio;
    }
       
    
}
