package com.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.library.entity.Parabike;
import com.library.repository.ParabikeRepository;
import com.library.service.ParabikeService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParabikeServiceImpl implements ParabikeService {

    private final ParabikeRepository parabikeRepository;

    @Override
    public List<Parabike> getAllParabikes() {
        return parabikeRepository.findAll();
    }

    @Override
    public Optional<Parabike> getParabikeById(Long id) {
        return parabikeRepository.findById(id);
    }

    @Override
    public Parabike saveParabike(Parabike parabike) {
        return parabikeRepository.save(parabike);
    }

    @Override
    public Parabike updateParabike(Long id, Parabike details) {
        return parabikeRepository.findById(id).map(parabike -> {
            parabike.setNo(details.getNo());
            parabike.setLibOwnNo(details.getLibOwnNo());
            parabike.setName(details.getName());
            parabike.setBegin(details.getBegin());
            parabike.setEnd(details.getEnd());
            parabike.setPlainInkBorderedLeaf(details.getPlainInkBorderedLeaf());
            parabike.setGoldLeafSugarCame(details.getGoldLeafSugarCame());
            parabike.setRemark(details.getRemark());
            return parabikeRepository.save(parabike);
        }).orElseThrow(() -> new RuntimeException("Parabike not found with id: " + id));
    }

    @Override
    public void deleteParabike(Long id) {
        parabikeRepository.deleteById(id);
    }
}