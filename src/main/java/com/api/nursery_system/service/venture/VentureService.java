package com.api.nursery_system.service.venture;

import java.util.List;

import org.springframework.stereotype.Service;

import com.api.nursery_system.entity.Venture;
import com.api.nursery_system.exception.ResourceNotFoundException;
import com.api.nursery_system.repository.UserRepository;
import com.api.nursery_system.repository.VentureRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VentureService implements IVentureService {

    private final VentureRepository ventureRepository;
    private final UserRepository userRepository;

    @Override
    public Venture createVenture(Venture venture) {
        return ventureRepository.save(venture);
    }

    @Override
    public Venture updateVenture(Long ventureId, Venture venture) {
        Venture existingVenture = ventureRepository.findById(ventureId)
                .orElseThrow(() -> new ResourceNotFoundException("Venture not found " + ventureId));

        existingVenture.setVentureName(venture.getVentureName());
        existingVenture.setAddress(venture.getAddress());
        existingVenture.setVillage(venture.getVillage());
        existingVenture.setTaluka(venture.getTaluka());
        existingVenture.setDistrict(venture.getDistrict());
        existingVenture.setState(venture.getState());
        existingVenture.setGstinNo(venture.getGstinNo());
        existingVenture.setGstPer(venture.getGstPer());
        existingVenture.setOwnerName(venture.getOwnerName());
        existingVenture.setContactNo(venture.getContactNo());
        existingVenture.setBankName(venture.getBankName());
        existingVenture.setAccountHolderName(venture.getAccountHolderName());
        existingVenture.setAccountNumber(venture.getAccountNumber());
        existingVenture.setIfscCode(venture.getIfscCode());
        existingVenture.setBranchName(venture.getBranchName());
        existingVenture.setUpiId(venture.getUpiId());

        return ventureRepository.save(existingVenture);
    }

    @Transactional
    @Override
    public void deleteVenture(Long ventureId) {
        Venture existingVenture = ventureRepository.findById(ventureId)
                .orElseThrow(() -> new ResourceNotFoundException("Venture not found with id: " + ventureId));

        userRepository.deleteById(existingVenture.getUserId());
        ventureRepository.delete(existingVenture);
    }

    @Override
    public Venture getVentureById(Long ventureId) {
        return ventureRepository.findById(ventureId)
                .orElseThrow(() -> new ResourceNotFoundException("Venture not found with id: " + ventureId));
    }

    @Override
    public List<Venture> getAllVentures() {
        return ventureRepository.findAll();
    }
}
