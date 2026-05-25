package com.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.entity.Parabike;

@Repository
public interface ParabikeRepository extends JpaRepository<Parabike, Long> {
    // လိုအပ်ပါက အမည်ဖြင့်ရှာဖွေရန် findByName စသည်တို့ကို ဤနေရာတွင် ထပ်တိုးနိုင်ပါသည်
}
