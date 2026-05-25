package com.library.service;

import java.util.List;
import java.util.Optional;

import com.library.entity.Parabike;

public interface ParabikeService {
    List<Parabike> getAllParabikes();
    Optional<Parabike> getParabikeById(Long id);
    Parabike saveParabike(Parabike parabike);
    Parabike updateParabike(Long id, Parabike parabikeDetails);
    void deleteParabike(Long id);
}