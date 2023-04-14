package com.ays.user.service.impl;

import com.ays.auth.controller.dto.request.AysLoginRequest;
import com.ays.auth.model.AysToken;
import com.ays.auth.model.enums.AysTokenClaims;
import com.ays.auth.service.AysTokenService;
import com.ays.auth.util.exception.PasswordNotValidException;
import com.ays.user.model.entity.UserEntity;
import com.ays.user.repository.UserRepository;
import com.ays.user.service.UserAuthService;
import com.ays.user.util.exception.AysUserNotActiveException;
import com.ays.user.util.exception.AysUserNotExistByUsernameException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class UserAuthServiceImpl implements UserAuthService {

    private final UserRepository userRepository;

    private final AysTokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public AysToken authenticate(AysLoginRequest loginRequest) {

        final UserEntity userEntity = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new AysUserNotExistByUsernameException(loginRequest.getUsername()));

        if (!userEntity.isActive()) {
            throw new AysUserNotActiveException(loginRequest.getUsername());
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), userEntity.getPassword())) {
            throw new PasswordNotValidException();
        }

        return tokenService.generate(userEntity.getClaims());

    }

    public AysToken refreshAccessToken(final String refreshToken) {

        tokenService.verifyAndValidate(refreshToken);
        final String username = tokenService
                .getClaims(refreshToken)
                .get(AysTokenClaims.USERNAME.getValue()).toString();

        final UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new AysUserNotExistByUsernameException(username));

        return tokenService.generate(userEntity.getClaims(), refreshToken);
    }
}
