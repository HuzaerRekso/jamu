package com.swn.jamu.service;

import com.swn.jamu.dto.BaseJamuDTO;
import com.swn.jamu.dto.DoseDTO;
import com.swn.jamu.dto.JamuDTO;
import com.swn.jamu.mapper.DoseMapper;
import com.swn.jamu.mapper.JamuMapper;
import com.swn.jamu.model.Dose;
import com.swn.jamu.model.Jamu;
import com.swn.jamu.repository.DoseRepository;
import com.swn.jamu.repository.JamuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JamuService {

    private final JamuRepository jamuRepository;
    private final DoseRepository doseRepository;
    private final BaseJamuService baseJamuService;
    private final JamuMapper jamuMapper;
    private final DoseMapper doseMapper;

    @Autowired
    public JamuService(JamuRepository jamuRepository,
                       DoseRepository doseRepository,
                       BaseJamuService baseJamuService,
                       JamuMapper jamuMapper,
                       DoseMapper doseMapper) {
        this.jamuRepository = jamuRepository;
        this.doseRepository = doseRepository;
        this.baseJamuService = baseJamuService;
        this.jamuMapper = jamuMapper;
        this.doseMapper = doseMapper;
    }

    public Page<JamuDTO> findPaginated(Pageable pageable, String name) {
        Page<Jamu> jamuPage;
        if (StringUtils.hasLength(name)) {
            jamuPage = jamuRepository.findByActiveAndNameContaining(true, name, pageable);
        } else {
            jamuPage = jamuRepository.findByActive(true, pageable);
        }

        List<JamuDTO> dtoList = jamuPage.getContent().stream().map(jamuMapper::toJamuDTO).toList();
        return new PageImpl<>(dtoList, pageable, jamuPage.getTotalElements());
    }

    public void saveJamu(JamuDTO jamuDTO) {
        if (jamuRepository.existsByCodeAndActive(jamuDTO.getCode(), true)) {
            throw new IllegalArgumentException("Code already exist");
        }
        if (jamuDTO.getDose() == null || jamuDTO.getDose().size() == 0) {
            throw new IllegalArgumentException("Dose is required");
        }

        Jamu jamu = jamuMapper.toJamu(jamuDTO);

        List<Dose> doses = new ArrayList<>();
        jamuDTO.getDose().forEach(doseDTO -> {
            if (doseDTO.getSelected()) {
                Dose dose = doseMapper.toDose(doseDTO);
                dose.setJamu(jamu);
                dose.setBaseJamu(baseJamuService.findBaseJamu(doseDTO.getBaseJamuId()));
                doses.add(dose);
            }
        });

        jamu.setCode(generateJamuCode());
        jamu.setDose(doses);
        jamu.setActive(true);
        jamuRepository.save(jamu);
    }

    public void editJamu(JamuDTO jamuDTO, long id) {
        Jamu jamu = jamuRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(" Jamu not found"));
        if (jamuDTO.getDose() == null || jamuDTO.getDose().size() == 0) {
            throw new IllegalArgumentException("Dose is required");
        }
        jamu.setName(jamuDTO.getName());
        jamu.setPrice(jamuDTO.getPrice());

        deleteDose(jamu.getId());

        List<Dose> doses = new ArrayList<>();
        jamuDTO.getDose().forEach(doseDTO -> {
            if (doseDTO.getSelected()) {
                Dose dose = doseMapper.toDose(doseDTO);
                dose.setJamu(jamu);
                dose.setBaseJamu(baseJamuService.findBaseJamu(doseDTO.getBaseJamuId()));
                doses.add(dose);
            }
        });

        jamu.setDose(doses);
        jamuRepository.save(jamu);
    }

    public JamuDTO findById(long id) {
        return jamuMapper.toJamuDTO(jamuRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(" Jamu not found")));
    }

    public Jamu findJamu(long id) {
        return jamuRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(" Jamu not found"));
    }

    public void deactivate(long id) {
        Jamu jamu = jamuRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Jamu not found"));
        if (!jamu.getActive()) {
            throw new IllegalArgumentException("This Jamu already inactive");
        }
        jamu.setActive(false);
        jamuRepository.save(jamu);
    }

    public List<BaseJamuDTO> getAllBaseJamu() {
        return baseJamuService.findAllBaseJamu();
    }

    public void deleteDose(long jamuId) {
        List<Dose> jamus = doseRepository.findByJamuId(jamuId);
        if (jamus != null && jamus.size() > 0) {
            doseRepository.deleteAll(jamus);
        }
    }

    public List<JamuDTO> getAllJamu() {
        return jamuRepository.findByActive(true).stream().map(jamuMapper::toJamuDTO).toList();
    }

    public JamuDTO findEdit(long id) {
        JamuDTO jamuDTO = jamuMapper.toJamuDTO(jamuRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(" Jamu not found")));
        List<BaseJamuDTO> baseJamuDTOS = getAllBaseJamu();
        Map<Long, DoseDTO> map = jamuDTO.getDose().stream().collect(Collectors.toMap(DoseDTO::getBaseJamuId, Function.identity()));
        List<DoseDTO> doseNewList = new ArrayList<>();
        for (BaseJamuDTO baseJamuDTO : baseJamuDTOS) {
            if (map.containsKey(baseJamuDTO.getId())) {
                DoseDTO dto = map.get(baseJamuDTO.getId());
                dto.setSelected(true);
                doseNewList.add(dto);
            } else {
                DoseDTO dto = new DoseDTO();
                dto.setBaseJamuId(baseJamuDTO.getId());
                dto.setJamuId(jamuDTO.getId());
                dto.setSelected(false);
                doseNewList.add(dto);
            }
        }
        jamuDTO.setDose(doseNewList);
        return jamuDTO;
    }

    private String generateJamuCode() {
        Jamu jamu = jamuRepository.findLatest();
        long latestCodeSeq = Long.parseLong(jamu.getCode().substring(jamu.getCode().length()-3));
        latestCodeSeq++;
        String seq = ("000" + latestCodeSeq).substring(String.valueOf(latestCodeSeq).length());
        return "J"+seq;
    }
}
