package br.com.fiap.mottooth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocalizacaoDTO {
    private Long id;
    
    @NotNull(message = "A posição X é obrigatória")
    private BigDecimal posicaoX;
    
    @NotNull(message = "A posição Y é obrigatória")
    private BigDecimal posicaoY;
    
    private LocalDateTime dataHora;
    
    @NotNull(message = "O ID da moto é obrigatório")
    private Long motoId;
    
    @NotNull(message = "O ID do pátio é obrigatório")
    private Long patioId;
    
    private String placaMoto;
    private String nomePatio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "A posição X é obrigatória") BigDecimal getPosicaoX() {
        return posicaoX;
    }

    public void setPosicaoX(@NotNull(message = "A posição X é obrigatória") BigDecimal posicaoX) {
        this.posicaoX = posicaoX;
    }

    public @NotNull(message = "A posição Y é obrigatória") BigDecimal getPosicaoY() {
        return posicaoY;
    }

    public void setPosicaoY(@NotNull(message = "A posição Y é obrigatória") BigDecimal posicaoY) {
        this.posicaoY = posicaoY;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public @NotNull(message = "O ID da moto é obrigatório") Long getMotoId() {
        return motoId;
    }

    public void setMotoId(@NotNull(message = "O ID da moto é obrigatório") Long motoId) {
        this.motoId = motoId;
    }

    public @NotNull(message = "O ID do pátio é obrigatório") Long getPatioId() {
        return patioId;
    }

    public void setPatioId(@NotNull(message = "O ID do pátio é obrigatório") Long patioId) {
        this.patioId = patioId;
    }

    public String getPlacaMoto() {
        return placaMoto;
    }

    public void setPlacaMoto(String placaMoto) {
        this.placaMoto = placaMoto;
    }

    public String getNomePatio() {
        return nomePatio;
    }

    public void setNomePatio(String nomePatio) {
        this.nomePatio = nomePatio;
    }
}
