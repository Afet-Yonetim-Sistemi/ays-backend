package org.ays.auth.model.mapper;

import org.ays.auth.model.AysInvalidToken;
import org.ays.auth.model.entity.AysInvalidTokenEntity;
import org.ays.common.model.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * {@link AysInvalidTokenToEntityMapper} is an interface that defines the mapping between an {@link AysInvalidToken} and an {@link AysInvalidTokenEntity}.
 * This interface uses the MapStruct annotation @Mapper to generate an implementation of this interface at compile-time.
 * <p>The class provides a static method {@code initialize()} that returns an instance of the generated mapper implementation.
 * <p>The interface extends the MapStruct interface {@link BaseMapper}, which defines basic mapping methods.
 * The interface adds no additional mapping methods, but simply defines the types to be used in the mapping process.
 */
@Mapper
public interface AysInvalidTokenToEntityMapper extends BaseMapper<AysInvalidToken, AysInvalidTokenEntity> {

    /**
     * Initializes the mapper.
     *
     * @return the initialized mapper object.
     */
    static AysInvalidTokenToEntityMapper initialize() {
        return Mappers.getMapper(AysInvalidTokenToEntityMapper.class);
    }

}
