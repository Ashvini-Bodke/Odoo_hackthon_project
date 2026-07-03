package com.hrms.service;

import com.hrms.dto.ProfileDTO;
import com.hrms.entity.User;

public interface ProfileService {
    ProfileDTO getMyProfile(User user);
    ProfileDTO updateMyProfile(User user, ProfileDTO profileDTO);
    ProfileDTO getProfileById(Long userId);
    ProfileDTO updateProfileById(Long userId, ProfileDTO profileDTO);
}
