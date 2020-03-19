package itx.iamservice.core.model;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class User {

    private final UserId id;
    private final ProjectId projectId;
    private final String name;
    private final Map<Class<? extends CredentialsType>, Credentials> credentials;
    private final Set<RoleId> roles;
    private final KeyPairData keyPairData;
    private final Long defaultAccessTokenDuration;
    private final Long defaultRefreshTokenDuration;

    public User(UserId id, String name, ProjectId projectId, Long defaultAccessTokenDuration, Long defaultRefreshTokenDuration, PrivateKey projectPrivateKey) throws PKIException {
        this.id = id;
        this.name = name;
        this.credentials = new ConcurrentHashMap<>();
        this.roles = new CopyOnWriteArraySet<>();
        this.projectId = projectId;
        this.keyPairData = TokenUtils.createSignedKeyPairData(projectId.getId(), id.getId(), 365L, TimeUnit.DAYS, projectPrivateKey);
        this.defaultAccessTokenDuration = defaultAccessTokenDuration;
        this.defaultRefreshTokenDuration = defaultRefreshTokenDuration;
    }

    public UserId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ProjectId getProjectId() {
        return projectId;
    }

    public void addRole(RoleId roleId) {
        this.roles.add(roleId);
    }

    public void addCredentials(Credentials credentials) {
        this.credentials.put(credentials.getType().getClass(), credentials);
    }

    public Optional<Credentials> getCredentials(Class<? extends CredentialsType> type) {
        return Optional.ofNullable(credentials.get(type));
    }

    public PrivateKey getPrivateKey() {
        return keyPairData.getPrivateKey();
    }

    public X509Certificate getCertificate() {
        return keyPairData.getX509Certificate();
    }

    public Long getDefaultAccessTokenDuration() {
        return defaultAccessTokenDuration;
    }

    public Long getDefaultRefreshTokenDuration() {
        return defaultRefreshTokenDuration;
    }

    public Set<RoleId> getRoles() {
        return this.roles.stream().collect(Collectors.toSet());
    }

    public boolean removeRole(RoleId roleId) {
        return roles.remove(roleId);
    }

}
