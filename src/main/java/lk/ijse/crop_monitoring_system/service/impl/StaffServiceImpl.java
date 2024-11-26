package lk.ijse.crop_monitoring_system.service.impl;

import lk.ijse.crop_monitoring_system.dto.StaffDTO;
import lk.ijse.crop_monitoring_system.entity.*;
import lk.ijse.crop_monitoring_system.exception.DataPersistFailedException;
import lk.ijse.crop_monitoring_system.exception.StaffNotFoundException;
import lk.ijse.crop_monitoring_system.repository.StaffRepository;
import lk.ijse.crop_monitoring_system.service.StaffService;
import lk.ijse.crop_monitoring_system.util.AppUtil;
import lk.ijse.crop_monitoring_system.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {
    private final StaffRepository staffRepository;
    private final Mapping mapping;

    @Override
    public void saveStaff(StaffDTO staffDTO) {
        staffDTO.setId(AppUtil.createStaffId());
        var staff = mapping.convertToStaff(staffDTO);
        var savedStaff = staffRepository.save(staff);
        if (savedStaff == null) {
            throw new DataPersistFailedException("Can't save the staff");
        }
    }

    @Override
    public void updateStaff(String staffId, StaffDTO incomestaffDTO) {
        Optional<Staff> tmpNoteEntity= staffRepository.findById(staffId);
        if(!tmpNoteEntity.isPresent()){
            throw new StaffNotFoundException("Staff not found");
        }else {
            tmpNoteEntity.get().setFirstName(incomestaffDTO.getFirstName());
            tmpNoteEntity.get().setLastName(incomestaffDTO.getLastName());
            tmpNoteEntity.get().setDesignation(Designation.valueOf(incomestaffDTO.getDesignation()));
            tmpNoteEntity.get().setGender(Gender.valueOf(incomestaffDTO.getGender()));
            tmpNoteEntity.get().setJoinedDate(incomestaffDTO.getJoinedDate());
            tmpNoteEntity.get().setDob(incomestaffDTO.getDob());
            tmpNoteEntity.get().setAddressLine01(incomestaffDTO.getAddressLine01());
            tmpNoteEntity.get().setAddressLine02(incomestaffDTO.getAddressLine02());
            tmpNoteEntity.get().setAddressLine03(incomestaffDTO.getAddressLine03());
            tmpNoteEntity.get().setAddressLine04(incomestaffDTO.getAddressLine04());
            tmpNoteEntity.get().setAddressLine05(incomestaffDTO.getAddressLine05());
            tmpNoteEntity.get().setContactNo(incomestaffDTO.getContactNo());
            tmpNoteEntity.get().setEmail(incomestaffDTO.getEmail());
            tmpNoteEntity.get().setRole(Role.valueOf(incomestaffDTO.getRole()));
        }
    }

    @Override
    public void deleteStaff(String staffId) {
        Optional<Staff> findId = staffRepository.findById(staffId);
        if(!findId.isPresent()){
            throw new StaffNotFoundException("Staff not found");
        }else {
            staffRepository.deleteById(staffId);
        }
    }

    @Override
    public StaffDTO getSelectedStaff(String staffId) {
        if (staffRepository.existsById(staffId)) {
            return mapping.convertToStaffDTO(staffRepository.getReferenceById(staffId));
        } else {
            throw new StaffNotFoundException("Staff not found");
        }
    }

    @Override
    public List<StaffDTO> getAllStaff() {
        return mapping.convertToStaffDTOList(staffRepository.findAll());
    }
}