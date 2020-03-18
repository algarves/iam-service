package itx.iamservice.core.services.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.impl.DefaultClaims;
import itx.iamservice.core.model.User;
import itx.iamservice.core.model.UserId;
import itx.iamservice.core.model.Model;
import itx.iamservice.core.model.Organization;
import itx.iamservice.core.model.OrganizationId;
import itx.iamservice.core.model.Project;
import itx.iamservice.core.model.ProjectId;
import itx.iamservice.core.model.TokenCache;
import itx.iamservice.core.model.TokenUtils;
import itx.iamservice.core.services.ResourceServerService;
import itx.iamservice.core.services.dto.UserInfo;
import itx.iamservice.core.services.dto.JWToken;
import itx.iamservice.core.services.dto.ProjectInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class ResourceServerServiceImpl implements ResourceServerService {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceServerServiceImpl.class);

    private final Model model;
    private final TokenCache tokenCache;

    public ResourceServerServiceImpl(Model model, TokenCache tokenCache) {
        this.model = model;
        this.tokenCache = tokenCache;
    }

    @Override
    public boolean verify(OrganizationId organizationId, ProjectId projectId, JWToken token) {
        boolean isRevoked = this.tokenCache.isRevoked(token);
        if (!isRevoked) {
            DefaultClaims defaultClaims = TokenUtils.extractClaims(token);
            UserId userId = UserId.from(defaultClaims.getSubject());
            Optional<User> userOptional = this.model.getUser(organizationId, projectId, userId);
            if (userOptional.isPresent()) {
                Optional<Jws<Claims>> claimsJws = TokenUtils.verify(token, userOptional.get().getCertificate().getPublicKey());
                LOG.info("JWT verified={}", claimsJws.isPresent());
                return claimsJws.isPresent();
            } else {
                LOG.info("JWT subject {} not found", userId);
            }
        } else {
            LOG.info("JWT is revoked: {}", token);
        }
        return false;
    }

    @Override
    public Optional<ProjectInfo> getProjectInfo(OrganizationId organizationId, ProjectId projectId) {
        Optional<Organization> organizationOptional = model.getOrganization(organizationId);
        if (organizationOptional.isPresent()) {
            Optional<Project> projectOptional = organizationOptional.get().getProject(projectId);
            if (projectOptional.isPresent()) {
                Project project = projectOptional.get();
                ProjectInfo projectInfo = new ProjectInfo(project.getId(), project.getOrganizationId(),
                        project.getName(), organizationOptional.get().getCertificate(), project.getCertificate());
                return Optional.of(projectInfo);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserInfo> getUserInfo(OrganizationId organizationId, ProjectId projectId, UserId userId) {
        Optional<Organization> organizationOptional = model.getOrganization(organizationId);
        if (organizationOptional.isPresent()) {
            Optional<Project> projectOptional = organizationOptional.get().getProject(projectId);
            if (projectOptional.isPresent()) {
                Optional<User> userOptional = projectOptional.get().getUser(userId);
                if (userOptional.isPresent()) {
                    UserInfo userInfo = new UserInfo(userId, projectId, organizationId,
                            userOptional.get().getName(), userOptional.get().getCertificate(),
                            userOptional.get().getRoles());
                    return Optional.of(userInfo);
                }
            }
        }
        return Optional.empty();
    }

}
