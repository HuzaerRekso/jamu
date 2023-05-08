package com.swn.jamu.service;

import com.swn.jamu.dto.BaseJamuDTO;
import com.swn.jamu.mapper.BaseJamuMapper;
import com.swn.jamu.model.BaseJamu;
import com.swn.jamu.repository.BaseJamuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class BaseJamuService {

    private final BaseJamuRepository baseJamuRepository;
    private final BaseJamuMapper baseJamuMapper;

    @Autowired
    public BaseJamuService(BaseJamuRepository baseJamuRepository, BaseJamuMapper baseJamuMapper) {
        this.baseJamuRepository = baseJamuRepository;
        this.baseJamuMapper = baseJamuMapper;
    }

    public Page<BaseJamuDTO> findPaginated(Pageable pageable, String name) {
        Page<BaseJamu> baseJamuPage;
        if (StringUtils.hasLength(name)) {
            baseJamuPage = baseJamuRepository.findByActiveAndNameContaining(true, name, pageable);
        } else {
            baseJamuPage = baseJamuRepository.findByActive(true, pageable);
        }

        List<BaseJamuDTO> dtoList = baseJamuPage.getContent().stream().map(baseJamuMapper::toBaseJamuDTO).toList();
        return new PageImpl<>(dtoList, pageable, baseJamuPage.getTotalElements());
    }

    public void saveBaseJamu(BaseJamuDTO baseJamuDTO) {
        if (baseJamuRepository.existsByCodeAndActive(baseJamuDTO.getCode(), true)) {
            throw new IllegalArgumentException("Code already exist");
        }

        BaseJamu baseJamu = baseJamuMapper.toBaseJamu(baseJamuDTO);
        baseJamu.setCode(generateBaseJamuCode());
        baseJamu.setActive(true);
        baseJamuRepository.save(baseJamu);
    }

    public void editBaseJamu(BaseJamuDTO baseJamuDTO, long id) {
        BaseJamu baseJamu = baseJamuRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Base Jamu not found"));
        baseJamu.setName(baseJamuDTO.getName());
        baseJamu.setPrice(baseJamuDTO.getPrice());
        baseJamuRepository.save(baseJamu);
    }

    public BaseJamuDTO findById(long id) {
        return baseJamuMapper.toBaseJamuDTO(baseJamuRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Base Jamu not found")));
    }

    public void deactivate(long id) {
        BaseJamu baseJamu = baseJamuRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Base Jamu not found"));
        if (!baseJamu.getActive()) {
            throw new IllegalArgumentException("This Base Jamu already inactive");
        }
        baseJamu.setActive(false);
        baseJamuRepository.save(baseJamu);
    }

    public List<BaseJamuDTO> findAllBaseJamu() {
        return baseJamuRepository.findByActive(true).stream().map(baseJamuMapper::toBaseJamuDTO).toList();
    }

    public BaseJamu findBaseJamu(long id) {
        return baseJamuRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Base Jamu not found"));
    }

    private String generateBaseJamuCode() {
        BaseJamu baseJamu = baseJamuRepository.findLatest();
        long latestCodeSeq = Long.parseLong(baseJamu.getCode().substring(baseJamu.getCode().length()-3));
        latestCodeSeq++;
        String seq = ("000" + latestCodeSeq).substring(String.valueOf(latestCodeSeq).length());
        return "BJ"+seq;
    }
}
