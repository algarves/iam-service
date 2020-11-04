package one.microproject.iamservice.core.services.impl.caches;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.impl.DefaultClaims;
import one.microproject.iamservice.core.model.OrganizationId;
import one.microproject.iamservice.core.model.ProjectId;
import one.microproject.iamservice.core.model.User;
import one.microproject.iamservice.core.model.UserId;
import one.microproject.iamservice.core.model.utils.TokenUtils;
import one.microproject.iamservice.core.services.caches.ModelCache;
import one.microproject.iamservice.core.services.caches.TokenCache;
import one.microproject.iamservice.core.model.JWToken;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class TokenCacheImpl implements TokenCache {

    private final ModelCache modelCache;
    private Set<JWToken> revokedJWTokens;

    public TokenCacheImpl(ModelCache modelCache) {
        this.revokedJWTokens = new HashSet<>();
        this.modelCache = modelCache;
    }

    @Override
    public void addRevokedToken(JWToken jwToken) {
        this.revokedJWTokens.add(jwToken);
    }

    @Override
    public int purgeRevokedTokens() {
        int size = this.revokedJWTokens.size();
        this.revokedJWTokens = this.revokedJWTokens.stream()
                .filter(this::validateToken)
                .collect(Collectors.toSet());
        return size - this.revokedJWTokens.size();
    }

    @Override
    public boolean isRevoked(JWToken jwToken) {
        return this.revokedJWTokens.contains(jwToken);
    }

    @Override
    public int size() {
        return this.revokedJWTokens.size();
    }

    private boolean validateToken(JWToken jwToken) {
        DefaultClaims defaultClaims = TokenUtils.extractClaims(jwToken);
        OrganizationId organizationId = OrganizationId.from(defaultClaims.getIssuer());
        ProjectId projectId = ProjectId.from(defaultClaims.getAudience());
        UserId userId = UserId.from(defaultClaims.getSubject());
        Optional<User> userOptional = this.modelCache.getUser(organizationId, projectId, userId);
        if (userOptional.isPresent()) {
            Optional<Jws<Claims>> verify = TokenUtils.verify(jwToken, userOptional.get().getCertificate().getPublicKey());
            return verify.isPresent();
        }
        return false;
    }

}