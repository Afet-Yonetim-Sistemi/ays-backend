package com.ays.assignment.repository;

import com.ays.assignment.model.entity.AssignmentEntity;
import com.ays.assignment.model.enums.AssignmentStatus;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Repository interface for performing CRUD operations on UserAssignmentEntity objects.
 */
public interface AssignmentRepository extends JpaRepository<AssignmentEntity, String>, JpaSpecificationExecutor<AssignmentEntity> {

    /**
     * Checks whether an assignment exists for a specific user ID and status.
     *
     * @param userId The ID of the user to check the assignment for.
     * @param status The status of the assignment to check.
     * @return {@code true} if an assignment exists for the specified user ID and status, otherwise {@code false}.
     */
    boolean existsByUserIdAndStatus(String userId, AssignmentStatus status);

    /**
     * Checks whether an assignment exists for a specific user ID.
     *
     * @param userId The ID of the user to check the assignment for.
     * @return {@code true} if an assignment exists for the specified user ID, otherwise {@code false}.
     */
    boolean existsByUserId(String userId);

    /**
     * Retrieves an optional AssignmentEntity based on the provided user ID and institution ID.
     *
     * @param id            The ID of the assignment to retrieve.
     * @param institutionId The ID of the institution associated with the user.
     * @return An optional AssignmentEntity that matches the specified ID and institution ID, or an empty optional if not found.
     */
    Optional<AssignmentEntity> findByIdAndInstitutionId(String id, String institutionId);

    /**
     * Retrieves an optional AssignmentEntity based on the provided user ID.
     *
     * @param userId The ID of the user to retrieve the assignment for.
     * @return An optional AssignmentEntity that matches the specified user ID, or an empty optional if not found.
     */
    Optional<AssignmentEntity> findByUserId(String userId);

    /**
     * Retrieves nearest optional AssignmentEntity based on the provided user location point and institution ID.
     *
     * @param point         The point of the assignment to retrieve.
     * @param institutionId The institution ID of the assignment to retrieve.
     * @return An Optional AssignmentEntity that nearest specified point and institution ID or an empty optional if not found.
     */
    @Query("""
            SELECT assignmentEntity
            FROM AssignmentEntity assignmentEntity
            WHERE assignmentEntity.institutionId = :institutionId AND assignmentEntity.status = 'AVAILABLE'
            ORDER BY st_distance(st_geomfromtext(:#{#point.toString()}, 4326), assignmentEntity.point)
            LIMIT 1"""
    )
    Optional<AssignmentEntity> findNearestAvailableAssignment(Point point, String institutionId);

}
