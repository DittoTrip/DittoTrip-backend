package site.dittotrip.ditto_trip.auth.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import site.dittotrip.ditto_trip.auth.domain.enums.TokenType;
import site.dittotrip.ditto_trip.utils.JwtProvider;

import java.io.IOException;
import java.net.URI;

@Component
@RequiredArgsConstructor
public class OAuth2MemberSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  private final JwtProvider jwtProvider;

  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
    MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();

    String accessToken = jwtProvider.generateToken(authentication, TokenType.ACCESS_TOKEN);
    String refreshToken = jwtProvider.generateToken(authentication, TokenType.REFRESH_TOKEN);
    jwtProvider.saveRefreshToken(authentication.getName(), refreshToken);

    queryParams.add("accessToken", accessToken);
    queryParams.add("refreshToken", refreshToken);

    URI uri = UriComponentsBuilder
        .fromUriString("http://localhost:3000/oauth2/redirect")
        .queryParams(queryParams)
        .build()
        .toUri();

    getRedirectStrategy().sendRedirect(request, response, uri.toString());
  }
}
