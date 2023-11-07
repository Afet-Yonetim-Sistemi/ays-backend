package com.ays.assignment.model.mapper;

import com.ays.assignment.model.Assignment;
import com.ays.assignment.model.dto.response.AssignmentUserResponse;
import com.ays.common.model.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * AssignmentToAssignmentUserResponseMapper is an interface that defines the mapping between an {@link Assignment} and an {@link AssignmentUserResponse}.
 * This interface uses the MapStruct annotation @Mapper to generate an implementation of this interface at compile-time.
 * <p>The class provides a static method {@code initialize()} that returns an instance of the generated mapper implementation.
 * <p>The interface extends the MapStruct interface {@link BaseMapper}, which defines basic mapping methods.
 * The interface adds no additional mapping methods, but simply defines the types to be used in the mapping process.
 */
@Mapper
public interface AssignmentToAssignmentUserResponseMapper extends BaseMapper<Assignment, AssignmentUserResponse> {

    @Override
    @Mapping(target = "location.longitude", source = "point.x")
    @Mapping(target = "location.latitude", source = "point.y")
    AssignmentUserResponse map(Assignment source);

    /**
     * Initializes the mapper.
     *
     * @return the initialized mapper object.
     */
    static AssignmentToAssignmentUserResponseMapper initialize() {
        return Mappers.getMapper(AssignmentToAssignmentUserResponseMapper.class);
    }

}