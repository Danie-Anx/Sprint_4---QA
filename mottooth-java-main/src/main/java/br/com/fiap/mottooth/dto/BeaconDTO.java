package br.com.fiap.mottooth.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeaconDTO {
    private Long id;
    
    @NotBlank(message = "O UUID do beacon é obrigatório")
    @Size(max = 100, message = "O UUID deve ter no máximo 100 caracteres")
    private String uuid;
    
    @Min(value = 0, message = "O nível de bateria deve ser no mínimo 0")
    @Max(value = 100, message = "O nível de bateria deve ser no máximo 100")
    private Integer bateria;
    
    private Long motoId;
    private Long modeloBeaconId;
    private String placaMoto;
    private String modeloNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "O UUID do beacon é obrigatório") @Size(max = 100, message = "O UUID deve ter no máximo 100 caracteres") String getUuid() {
        return uuid;
    }

    public void setUuid(@NotBlank(message = "O UUID do beacon é obrigatório") @Size(max = 100, message = "O UUID deve ter no máximo 100 caracteres") String uuid) {
        this.uuid = uuid;
    }

    public @Min(value = 0, message = "O nível de bateria deve ser no mínimo 0") @Max(value = 100, message = "O nível de bateria deve ser no máximo 100") Integer getBateria() {
        return bateria;
    }

    public void setBateria(@Min(value = 0, message = "O nível de bateria deve ser no mínimo 0") @Max(value = 100, message = "O nível de bateria deve ser no máximo 100") Integer bateria) {
        this.bateria = bateria;
    }

    public Long getMotoId() {
        return motoId;
    }

    public void setMotoId(Long motoId) {
        this.motoId = motoId;
    }

    public Long getModeloBeaconId() {
        return modeloBeaconId;
    }

    public void setModeloBeaconId(Long modeloBeaconId) {
        this.modeloBeaconId = modeloBeaconId;
    }

    public String getPlacaMoto() {
        return placaMoto;
    }

    public void setPlacaMoto(String placaMoto) {
        this.placaMoto = placaMoto;
    }

    public String getModeloNome() {
        return modeloNome;
    }

    public void setModeloNome(String modeloNome) {
        this.modeloNome = modeloNome;
    }
}
