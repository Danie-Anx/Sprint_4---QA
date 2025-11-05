package br.com.fiap.mottooth.service;

import br.com.fiap.mottooth.dto.LocalizacaoDTO;
import br.com.fiap.mottooth.model.Localizacao;
import br.com.fiap.mottooth.model.Moto;
import br.com.fiap.mottooth.model.Patio;
import br.com.fiap.mottooth.repository.LocalizacaoRepository;
import br.com.fiap.mottooth.repository.MotoRepository;
import br.com.fiap.mottooth.repository.PatioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocalizacaoService {

    private final LocalizacaoRepository localizacaoRepository;
    private final MotoRepository motoRepository;
    private final PatioRepository patioRepository;

    public LocalizacaoService(LocalizacaoRepository localizacaoRepository, MotoRepository motoRepository, PatioRepository patioRepository) {
        this.localizacaoRepository = localizacaoRepository;
        this.motoRepository = motoRepository;
        this.patioRepository = patioRepository;
    }

    @Cacheable(value = "localizacoes", key = "#id")
    public LocalizacaoDTO findById(Long id) {
        Localizacao localizacao = localizacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Localização não encontrada com ID: " + id));
        return convertToDTO(localizacao);
    }

    @Cacheable(value = "localizacoes", key = "'page:' + #pageable.pageNumber + ':' + #pageable.pageSize")
    public Page<LocalizacaoDTO> findAll(Pageable pageable) {
        return localizacaoRepository.findAll(pageable).map(this::convertToDTO);
    }

    @Cacheable(value = "localizacoes", key = "'moto:' + #motoId")
    public List<LocalizacaoDTO> findLastLocationByMotoId(Long motoId) {
        return localizacaoRepository.findTop1ByMotoIdOrderByDataHoraDesc(motoId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "localizacoes", key = "'filter:' + #motoId + ':' + #patioId + ':' + #dataInicio + ':' + #dataFim + ':' + #pageable.pageNumber + ':' + #pageable.pageSize")
    public Page<LocalizacaoDTO> findByFilters(Long motoId, Long patioId, LocalDateTime dataInicio, LocalDateTime dataFim, Pageable pageable) {
        return localizacaoRepository.findByFilters(motoId, patioId, dataInicio, dataFim, pageable).map(this::convertToDTO);
    }

    @Transactional
    @CacheEvict(value = "localizacoes", allEntries = true)
    public LocalizacaoDTO save(LocalizacaoDTO localizacaoDTO) {
        Localizacao localizacao = convertToEntity(localizacaoDTO);
        localizacao = localizacaoRepository.save(localizacao);
        return convertToDTO(localizacao);
    }

    @Transactional
    @CacheEvict(value = "localizacoes", allEntries = true)
    public LocalizacaoDTO update(Long id, LocalizacaoDTO localizacaoDTO) {
        if (!localizacaoRepository.existsById(id)) {
            throw new EntityNotFoundException("Localização não encontrada com ID: " + id);
        }

        Localizacao localizacao = convertToEntity(localizacaoDTO);
        localizacao.setId(id);
        localizacao = localizacaoRepository.save(localizacao);
        return convertToDTO(localizacao);
    }

    @Transactional
    @CacheEvict(value = "localizacoes", allEntries = true)
    public void delete(Long id) {
        if (!localizacaoRepository.existsById(id)) {
            throw new EntityNotFoundException("Localização não encontrada com ID: " + id);
        }
        localizacaoRepository.deleteById(id);
    }

    private LocalizacaoDTO convertToDTO(Localizacao localizacao) {
        LocalizacaoDTO dto = new LocalizacaoDTO();
        dto.setId(localizacao.getId());
        dto.setPosicaoX(localizacao.getPosicaoX());
        dto.setPosicaoY(localizacao.getPosicaoY());
        dto.setDataHora(localizacao.getDataHora());

        if (localizacao.getMoto() != null) {
            dto.setMotoId(localizacao.getMoto().getId());
            dto.setPlacaMoto(localizacao.getMoto().getPlaca());
        }

        if (localizacao.getPatio() != null) {
            dto.setPatioId(localizacao.getPatio().getId());
            dto.setNomePatio(localizacao.getPatio().getNome());
        }

        return dto;
    }

    private Localizacao convertToEntity(LocalizacaoDTO dto) {
        Localizacao localizacao = new Localizacao();
        localizacao.setId(dto.getId());
        localizacao.setPosicaoX(dto.getPosicaoX());
        localizacao.setPosicaoY(dto.getPosicaoY());
        localizacao.setDataHora(dto.getDataHora() != null ? dto.getDataHora() : LocalDateTime.now());

        if (dto.getMotoId() != null) {
            Moto moto = motoRepository.findById(dto.getMotoId())
                    .orElseThrow(() -> new EntityNotFoundException("Moto não encontrada com ID: " + dto.getMotoId()));
            localizacao.setMoto(moto);
        }

        if (dto.getPatioId() != null) {
            Patio patio = patioRepository.findById(dto.getPatioId())
                    .orElseThrow(() -> new EntityNotFoundException("Pátio não encontrado com ID: " + dto.getPatioId()));
            localizacao.setPatio(patio);
        }

        return localizacao;
    }
}
