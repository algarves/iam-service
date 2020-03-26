package itx.iamservice.core.services.admin;

import itx.iamservice.core.model.Organization;
import itx.iamservice.core.model.OrganizationId;
import itx.iamservice.core.model.PKIException;
import itx.iamservice.core.services.dto.CreateOrganizationRequest;
import itx.iamservice.core.services.dto.OrganizationInfo;

import java.security.cert.CertificateEncodingException;
import java.util.Collection;
import java.util.Optional;

public interface OrganizationManagerService {

    boolean create(OrganizationId id, CreateOrganizationRequest createOrganizationRequest) throws PKIException;

    Optional<OrganizationId> create(CreateOrganizationRequest createOrganizationRequest) throws PKIException;

    Collection<Organization> getAll();

    Collection<OrganizationInfo> getAllInfo() throws CertificateEncodingException;

    Optional<Organization> get(OrganizationId id);

    Optional<OrganizationInfo> getInfo(OrganizationId id) throws CertificateEncodingException;

    boolean remove(OrganizationId id);

}
