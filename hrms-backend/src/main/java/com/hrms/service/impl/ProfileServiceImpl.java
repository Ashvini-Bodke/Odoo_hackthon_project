package com.hrms.service.impl;

import com.hrms.dto.ProfileDTO;
import com.hrms.entity.EmployeeProfile;
import com.hrms.entity.User;
import com.hrms.repository.EmployeeProfileRepository;
import com.hrms.repository.UserRepository;
import com.hrms.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final EmployeeProfileRepository employeeProfileRepository;

    @Override
    public ProfileDTO getMyProfile(User user) {
        return convertToDTO(user);
    }

    @Override
    @Transactional
    public ProfileDTO updateMyProfile(User user, ProfileDTO profileDTO) {
        EmployeeProfile profile = employeeProfileRepository.findByUser(user)
                .orElse(EmployeeProfile.builder()
                        .user(user)
                        .build());

        if (profileDTO.getPhone() != null) profile.setPhone(profileDTO.getPhone());
        if (profileDTO.getAddress() != null) profile.setAddress(profileDTO.getAddress());
        if (profileDTO.getProfilePicture() != null) profile.setProfilePicture(profileDTO.getProfilePicture());
        if (profileDTO.getPersonalEmail() != null) profile.setPersonalEmail(profileDTO.getPersonalEmail());

        employeeProfileRepository.save(profile);
        User updatedUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDTO(updatedUser);
    }

    @Override
    public ProfileDTO getProfileById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return convertToDTO(user);
    }

    @Override
    @Transactional
    public ProfileDTO updateProfileById(Long userId, ProfileDTO profileDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        EmployeeProfile profile = employeeProfileRepository.findByUser(user)
                .orElse(EmployeeProfile.builder()
                        .user(user)
                        .build());

        if (profileDTO.getFirstName() != null) profile.setFirstName(profileDTO.getFirstName());
        if (profileDTO.getLastName() != null) profile.setLastName(profileDTO.getLastName());
        if (profileDTO.getPhone() != null) profile.setPhone(profileDTO.getPhone());
        if (profileDTO.getAddress() != null) profile.setAddress(profileDTO.getAddress());
        if (profileDTO.getProfilePicture() != null) profile.setProfilePicture(profileDTO.getProfilePicture());
        if (profileDTO.getDateOfBirth() != null) profile.setDateOfBirth(profileDTO.getDateOfBirth());
        if (profileDTO.getNationality() != null) profile.setNationality(profileDTO.getNationality());
        if (profileDTO.getPersonalEmail() != null) profile.setPersonalEmail(profileDTO.getPersonalEmail());
        if (profileDTO.getGender() != null) profile.setGender(profileDTO.getGender());
        if (profileDTO.getMaritalStatus() != null) profile.setMaritalStatus(profileDTO.getMaritalStatus());
        if (profileDTO.getDateOfJoining() != null) profile.setDateOfJoining(profileDTO.getDateOfJoining());
        if (profileDTO.getAccountNumber() != null) profile.setAccountNumber(profileDTO.getAccountNumber());
        if (profileDTO.getBankName() != null) profile.setBankName(profileDTO.getBankName());
        if (profileDTO.getIfscCode() != null) profile.setIfscCode(profileDTO.getIfscCode());
        if (profileDTO.getPanNumber() != null) profile.setPanNumber(profileDTO.getPanNumber());
        if (profileDTO.getUanNumber() != null) profile.setUanNumber(profileDTO.getUanNumber());
        if (profileDTO.getSkills() != null) profile.setSkills(profileDTO.getSkills());

        employeeProfileRepository.save(profile);
        User updatedUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDTO(updatedUser);
    }

    private ProfileDTO convertToDTO(User user) {
        EmployeeProfile profile = user.getProfile();
        ProfileDTO.ProfileDTOBuilder builder = ProfileDTO.builder()
                .email(user.getEmail())
                .employeeId(user.getEmployeeId());

        if (profile != null) {
            builder.firstName(profile.getFirstName())
                    .lastName(profile.getLastName())
                    .phone(profile.getPhone())
                    .address(profile.getAddress())
                    .profilePicture(profile.getProfilePicture())
                    .dateOfBirth(profile.getDateOfBirth())
                    .nationality(profile.getNationality())
                    .personalEmail(profile.getPersonalEmail())
                    .gender(profile.getGender())
                    .maritalStatus(profile.getMaritalStatus())
                    .dateOfJoining(profile.getDateOfJoining())
                    .accountNumber(profile.getAccountNumber())
                    .bankName(profile.getBankName())
                    .ifscCode(profile.getIfscCode())
                    .panNumber(profile.getPanNumber())
                    .uanNumber(profile.getUanNumber())
                    .skills(profile.getSkills());
        }
        return builder.build();
    }
}
