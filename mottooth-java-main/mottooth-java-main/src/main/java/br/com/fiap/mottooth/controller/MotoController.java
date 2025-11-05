package br.com.fiap.mottooth.controller;

import br.com.fiap.mottooth.dto.MotoDTO;
import br.com.fiap.mottooth.service.MotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/motos")
@Tag(name = "Motos", description = "API para gerenciamento de motos")
public class MotoController {

    private final MotoService motoService;

    public MotoController(MotoService motoService) {
        this.motoService = motoService;
    }

    @GetMapping
    @Operation(summary = "Listar todas as motos", description = "Retorna uma lista paginada de motos")
    public ResponseEntity<Page<MotoDTO>> findAll(
            @RequestParam(required = false) String placa,
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) Long modeloId,
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {

        if (placa != null || clienteId != null || modeloId != null) {
            return ResponseEntity.ok(motoService.findByFilters(placa, clienteId, modeloId, pageable));
        }

        return ResponseEntity.ok(motoService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar moto por ID", description = "Retorna uma moto pelo seu ID")
    public ResponseEntity<MotoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(motoService.findById(id));
    }

    @GetMapping("/placa/{placa}")
    @Operation(summary = "Buscar moto por placa", description = "Retorna uma moto pela sua placa")
    public ResponseEntity<MotoDTO> findByPlaca(@PathVariable String placa) {
        return ResponseEntity.ok(motoService.findByPlaca(placa));
    }

    @PostMapping
    @Operation(summary = "Cadastrar moto", description = "Cadastra uma nova moto")
    public ResponseEntity<MotoDTO> save(@Valid @RequestBody MotoDTO motoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(motoService.save(motoDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar moto", description = "Atualiza uma moto existente")
    public ResponseEntity<MotoDTO> update(@PathVariable Long id, @Valid @RequestBody MotoDTO motoDTO) {
        return ResponseEntity.ok(motoService.update(id, motoDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir moto", description = "Exclui uma moto pelo seu ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        motoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
