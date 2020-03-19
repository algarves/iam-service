package itx.iamservice.core.model;

import java.util.Objects;

public final class ClientCredentials {

    private final ClientId id;
    private final String secret;

    public ClientCredentials(ClientId id, String secret) {
        this.id = id;
        this.secret = secret;
    }

    public ClientId getId() {
        return id;
    }

    public String getSecret() {
        return secret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientCredentials clientCredentials = (ClientCredentials) o;
        return Objects.equals(id, clientCredentials.id) &&
                Objects.equals(secret, clientCredentials.secret);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, secret);
    }

    @Override
    public String toString() {
        return id.getId();
    }

}
