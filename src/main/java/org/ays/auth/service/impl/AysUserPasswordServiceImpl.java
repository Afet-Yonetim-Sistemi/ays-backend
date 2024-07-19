package org.ays.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.ays.auth.model.AysUser;
import org.ays.auth.model.enums.AysConfigurationParameter;
import org.ays.auth.model.request.AysForgotPasswordRequest;
import org.ays.auth.port.AysUserReadPort;
import org.ays.auth.port.AysUserSavePort;
import org.ays.auth.service.AysUserPasswordService;
import org.ays.auth.util.exception.AysEmailAddressNotValidException;
import org.ays.common.model.AysMail;
import org.ays.common.model.enums.AysMailTemplate;
import org.ays.common.service.AysMailService;
import org.ays.common.util.AysRandomUtil;
import org.ays.parameter.model.AysParameter;
import org.ays.parameter.port.AysParameterReadPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Service implementation for handling user password operations such as forgotten password.
 * This service handles the retrieval of user information and sending emails for password creation.
 */
@Service
@Transactional
@RequiredArgsConstructor
class AysUserPasswordServiceImpl implements AysUserPasswordService {

    private final AysUserReadPort userReadPort;
    private final AysUserSavePort userSavePort;
    private final AysMailService mailService;
    private final AysParameterReadPort parameterReadPort;

    /**
     * Handles the forgot password request by sending an email to the user
     * with instructions to create a new password.
     *
     * @param forgotPasswordRequest the request containing the user's email address.
     * @throws AysEmailAddressNotValidException if the email address is not associated with any user.
     */
    @Override
    public void forgotPassword(final AysForgotPasswordRequest forgotPasswordRequest) {

        final String emailAddress = forgotPasswordRequest.getEmailAddress();
        final AysUser user = userReadPort.findByEmailAddress(emailAddress)
                .orElseThrow(() -> new AysEmailAddressNotValidException(emailAddress));

        if (user.getPassword() != null) {
            this.sendPasswordCreateEmail(user);
            return;
        }

        final AysUser.Password password = AysUser.Password.builder()
                .value(AysRandomUtil.generateUUID())
                .build();
        user.setPassword(password);
        AysUser savedUser = userSavePort.save(user);

        this.sendPasswordCreateEmail(savedUser);
    }

    /**
     * Sends an email to the user with instructions to create a new password.
     *
     * @param user the user to whom the email should be sent.
     */
    private void sendPasswordCreateEmail(final AysUser user) {

        final Map<String, Object> parameters = Map.of(
                "userFullName", user.getFirstName() + " " + user.getLastName(),
                "url", this.findFeUrl().concat("/create-password/").concat(user.getPassword().getId())
        );

        final AysMail mail = AysMail.builder()
                .to(List.of(user.getEmailAddress()))
                .template(AysMailTemplate.CREATE_PASSWORD)
                .parameters(parameters)
                .build();

        mailService.send(mail);
    }

    /**
     * Retrieves the Front-End URL from the configuration parameters.
     *
     * @return the Front-End URL.
     */
    private String findFeUrl() {
        return parameterReadPort
                .findByName(AysConfigurationParameter.FE_URL.name())
                .orElse(AysParameter.from(AysConfigurationParameter.FE_URL))
                .getDefinition();
    }

}