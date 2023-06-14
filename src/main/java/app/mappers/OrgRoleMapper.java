package app.mappers;

import app.domain.model.OrgRole;
import app.mappers.dto.OrgRoleDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class for the Organization Roles' Instances.
 *
 * @author SRC-Code-23
 */
public class OrgRoleMapper {
    /**
     * Method for creating a DTO object of a Organization Role object
     * @param role the object to become a DTO
     * @return a Organization Role DTO
     */
    public OrgRoleDTO toDTO(OrgRole role) {
        return new OrgRoleDTO(role.getDescription());
    }

    /**
     * Method for creating a list of Organization Roles Dto
     * @param roles list of Organization Role to become a list of Dtos
     * @return list of Organization Roles Dto
     */
    public List<OrgRoleDTO> toDTO(List<OrgRole> roles) {
        List<OrgRoleDTO> rolesDTOS = new ArrayList<>();
        for (OrgRole role : roles) {
            rolesDTOS.add(this.toDTO(role));
        }
        return rolesDTOS;
    }
}
