package itx.iamservice.core.model;

import itx.iamservice.core.model.extensions.authentication.up.UPCredentials;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public final class ModelUtils {

    private ModelUtils() {
    }

    public static String createId() {
        return UUID.randomUUID().toString();
    }

    public static OrganizationId createOrganizationId() {
        return OrganizationId.from(createId());
    }

    public static ProjectId createProjectId() {
        return ProjectId.from(createId());
    }

    public static ClientId createClientId() {
        return ClientId.from(createId());
    }

    public static RoleId createRoleId() {
        return RoleId.from(createId());
    }

    public static Model createDefaultModel() throws NoSuchAlgorithmException {
        ModelImpl model = new ModelImpl();
        Organization organization = new Organization(createOrganizationId(), "iam-admins", model);
        Project project = new Project(createProjectId(), "iam-admins", organization.getId(), model);
        Client client = new Client(ClientId.from("iam-admin-id"), "iam-admin", project.getId(), TokenUtils.generateKeyPair(), 3600*1000L);
        UPCredentials upCredentials = new UPCredentials(client.getId(), "iam-secret-77");
        organization.add(project);
        project.add(client);
        client.addCredentials(upCredentials);
        return model;
    }

}