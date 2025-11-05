package br.com.fiap.mottooth.controller;

import br.com.fiap.mottooth.dto.BeaconDTO;
import br.com.fiap.mottooth.service.BeaconService;
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
@RequestMapping("/api/beacons")
@Tag(name = "Beacons", description = "API para gerenciamento de beacons")
public class BeaconController {

    private final BeaconService beaconService;

    public BeaconController(BeaconService beaconService) {
        this.beaconService = beaconService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os beacons", description = "Retorna uma lista paginada de beacons")
    public ResponseEntity<Page<BeaconDTO>> findAll(
            @RequestParam(required = false) String uuid,
            @RequestParam(required = false) Long motoId,
            @RequestParam(required = false) Long modeloId,
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {

        if (uuid != null || motoId != null || modeloId != null) {
            return ResponseEntity.ok(beaconService.findByFilters(uuid, motoId, modeloId, pageable));
        }

        return ResponseEntity.ok(beaconService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar beacon por ID", description = "Retorna um beacon pelo seu ID")
    public ResponseEntity<BeaconDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(beaconService.findById(id));
    }

    @GetMapping("/uuid/{uuid}")
    @Operation(summary = "Buscar beacon por UUID", description = "Retorna um beacon pelo seu UUID")
    public ResponseEntity<BeaconDTO> findByUuid(@PathVariable String uuid) {
        return ResponseEntity.ok(beaconService.findByUuid(uuid));
    }

    @PostMapping
    @Operation(summary = "Cadastrar beacon", description = "Cadastra um novo beacon")
    public ResponseEntity<BeaconDTO> save(@Valid @RequestBody BeaconDTO beaconDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(beaconService.save(beaconDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar beacon", description = "Atualiza um beacon existente")
    public ResponseEntity<BeaconDTO> update(@PathVariable Long id, @Valid @RequestBody BeaconDTO beaconDTO) {
        return ResponseEntity.ok(beaconService.update(id, beaconDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir beacon", description = "Exclui um beacon pelo seu ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        beaconService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
