package itx.iamservice.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;

public class Client {

    private final ClientCredentials credentials;
    private final Set<RoleId> roles;
    private final Long defaultAccessTokenDuration;
    private final Long defaultRefreshTokenDuration;

    @JsonCreator
    public Client(@JsonProperty("credentials") ClientCredentials credentials,
                  @JsonProperty("defaultAccessTokenDuration") Long defaultAccessTokenDuration,
                  @JsonProperty("defaultRefreshTokenDuration") Long defaultRefreshTokenDuration) {
        this.credentials = credentials;
        this.roles = new HashSet<>();
        this.defaultAccessTokenDuration = defaultAccessTokenDuration;
        this.defaultRefreshTokenDuration = defaultRefreshTokenDuration;
    }

    public ClientId getId() {
        return credentials.getId();
    }

    public ClientCredentials getCredentials() {
        return credentials;
    }

    public Set<RoleId> getRoles() {
        return roles;
    }

    public boolean addRole(RoleId roleId) {
        return roles.add(roleId);
    }

    public boolean removeRole(RoleId roleId) {
        return roles.remove(roleId);
    }

    public Long getDefaultAccessTokenDuration() {
        return defaultAccessTokenDuration;
    }

    public Long getDefaultRefreshTokenDuration() {
        return defaultRefreshTokenDuration;
    }

}
