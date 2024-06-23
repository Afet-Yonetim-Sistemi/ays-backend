package org.ays.auth.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.ays.auth.model.enums.AysConfigurationParameter;
import org.ays.auth.util.AysKeyConverter;
import org.ays.parameter.model.AysParameter;
import org.ays.parameter.service.AysParameterService;
import org.springframework.context.annotation.Configuration;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Optional;
import java.util.Set;

/**
 * Configuration class for AYS token-related properties such as issuer, token expiration times and cryptographic keys.
 */
@Slf4j
@Getter
@Configuration
public class AysTokenConfigurationParameter {

    /**
     * The issuer value to be used in JWTs generated by the application.
     */
    private final String issuer;
    /**
     * The number of minutes until access tokens expire.
     */
    private final Integer accessTokenExpireMinute;
    /**
     * The number of days until refresh tokens expire.
     */
    private final Integer refreshTokenExpireDay;
    /**
     * The private key used for token signing and verification.
     */
    private final PrivateKey privateKey;
    /**
     * The public key used for token verification.
     */
    private final PublicKey publicKey;

    /**
     * Constructs a new AysTokenConfiguration instance using AysParameterService to retrieve relevant configuration parameters.
     *
     * @param parameterService the AysParameterService instance to use for parameter retrieval
     */
    public AysTokenConfigurationParameter(AysParameterService parameterService) {

        log.info("AYS Token Configuration is initializing with AYS Parameters...");

        final Set<AysParameter> configurationParameters = parameterService.findAll("AUTH_");

        this.issuer = AysConfigurationParameter.AYS.getDefaultValue();

        this.accessTokenExpireMinute = Optional
                .ofNullable(AysParameter.getDefinition(AysConfigurationParameter.AUTH_ACCESS_TOKEN_EXPIRE_MINUTE, configurationParameters))
                .map(Integer::valueOf)
                .orElse(Integer.valueOf(AysConfigurationParameter.AUTH_ACCESS_TOKEN_EXPIRE_MINUTE.getDefaultValue()));

        this.refreshTokenExpireDay = Optional
                .ofNullable(AysParameter.getDefinition(AysConfigurationParameter.AUTH_REFRESH_TOKEN_EXPIRE_DAY, configurationParameters))
                .map(Integer::valueOf)
                .orElse(Integer.valueOf(AysConfigurationParameter.AUTH_REFRESH_TOKEN_EXPIRE_DAY.getDefaultValue()));

        final String encryptedPrivateKeyPem = Optional
                .ofNullable(AysParameter.getDefinition(AysConfigurationParameter.AUTH_TOKEN_PRIVATE_KEY, configurationParameters))
                .orElse(AysConfigurationParameter.AUTH_TOKEN_PRIVATE_KEY.getDefaultValue());
        this.privateKey = AysKeyConverter.convertPrivateKey(encryptedPrivateKeyPem);

        final String encryptedPublicKeyPem = Optional
                .ofNullable(AysParameter.getDefinition(AysConfigurationParameter.AUTH_TOKEN_PUBLIC_KEY, configurationParameters))
                .orElse(AysConfigurationParameter.AUTH_TOKEN_PUBLIC_KEY.getDefaultValue());
        this.publicKey = AysKeyConverter.convertPublicKey(encryptedPublicKeyPem);

        log.info("AYS Token Configuration is initialized!");
    }

}
