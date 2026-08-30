package com.ayurvana.service;

import com.ayurvana.dto.request.LoginRequest;
import com.ayurvana.dto.request.RegisterRequest;
import com.ayurvana.dto.response.JwtAuthResponse;
import com.ayurvana.dto.response.UserResponse;
import com.ayurvana.security.UserPrincipal;

public interface AuthService {

    JwtAuthResponse register(RegisterRequest request);

    JwtAuthResponse login(LoginRequest request);

    UserResponse getCurrentUser(UserPrincipal currentUser);

    UserResponse updateProfile(UserPrincipal currentUser, com.ayurvana.dto.request.UpdateProfileRequest request);
}
