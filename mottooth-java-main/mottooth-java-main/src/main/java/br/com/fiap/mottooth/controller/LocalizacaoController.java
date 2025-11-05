package br.com.fiap.mottooth.controller;

import br.com.fiap.mottooth.dto.LocalizacaoDTO;
import br.com.fiap.mottooth.service.LocalizacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/localizacoes")
@Tag(name = "Localizações", description = "API para gerenciamento de localizações")
public class LocalizacaoController {

    private final LocalizacaoService localizacaoService;

    public LocalizacaoController(LocalizacaoService localizacaoService) {
        this.localizacaoService = localizacaoService;
    }

    @GetMapping
    @Operation(summary = "Listar todas as localizações", description = "Retorna uma lista paginada de localizações")
    public ResponseEntity<Page<LocalizacaoDTO>> findAll(
            @RequestParam(required = false) Long motoId,
            @RequestParam(required = false) Long patioId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim,
            @PageableDefault(size = 10, sort = "dataHora") Pageable pageable) {

        if (motoId != null || patioId != null || dataInicio != null || dataFim != null) {
            return ResponseEntity.ok(localizacaoService.findByFilters(motoId, patioId, dataInicio, dataFim, pageable));
        }

        return ResponseEntity.ok(localizacaoService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar localização por ID", description = "Retorna uma localização pelo seu ID")
    public ResponseEntity<LocalizacaoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(localizacaoService.findById(id));
    }

    @GetMapping("/moto/{motoId}/ultima")
    @Operation(summary = "Buscar última localização de uma moto", description = "Retorna a última localização registrada para uma moto")
    public ResponseEntity<List<LocalizacaoDTO>> findLastLocationByMotoId(@PathVariable Long motoId) {
        List<LocalizacaoDTO> localizacoes = localizacaoService.findLastLocationByMotoId(motoId);
        if (localizacoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(localizacoes);
    }

    @PostMapping
    @Operation(summary = "Cadastrar localização", description = "Cadastra uma nova localização")
    public ResponseEntity<LocalizacaoDTO> save(@Valid @RequestBody LocalizacaoDTO localizacaoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(localizacaoService.save(localizacaoDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar localização", description = "Atualiza uma localização existente")
    public ResponseEntity<LocalizacaoDTO> update(@PathVariable Long id, @Valid @RequestBody LocalizacaoDTO localizacaoDTO) {
        return ResponseEntity.ok(localizacaoService.update(id, localizacaoDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir localização", description = "Exclui uma localização pelo seu ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        localizacaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
