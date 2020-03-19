package itx.iamservice.core.model;

import itx.iamservice.core.services.dto.JWToken;

public class Tokens {

    private final JWToken accessToken;
    private final JWToken refreshToken;
    private final TokenType tokenType;
    private final Long expiresIn;
    private final Long refreshExpiresIn;

    public Tokens(JWToken accessToken, JWToken refreshToken, TokenType tokenType, Long expiresIn, Long refreshExpiresIn) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.refreshExpiresIn = refreshExpiresIn;
    }

    public JWToken getAccessToken() {
        return accessToken;
    }

    public JWToken getRefreshToken() {
        return refreshToken;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public Long getRefreshExpiresIn() {
        return refreshExpiresIn;
    }

}
