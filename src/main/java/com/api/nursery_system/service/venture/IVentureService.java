package com.api.nursery_system.service.venture;

import java.util.List;

import com.api.nursery_system.entity.Venture;

public interface IVentureService {
    Venture createVenture(Venture venture);

    Venture updateVenture(Long ventureId, Venture venture);

    void deleteVenture(Long ventureId);

    Venture getVentureById(Long ventureId);

    List<Venture> getAllVentures();
}
