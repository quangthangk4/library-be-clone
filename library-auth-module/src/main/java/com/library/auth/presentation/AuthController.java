package com.library.auth.presentation;


import com.library.auth.application.AuthService;
import com.library.auth.application.LoginUseCase;
import com.library.auth.application.LogoutUseCase;
import com.library.auth.application.OnboardingProfileUseCase;
import com.library.auth.application.RefreshAccessTokenUseCase;
import com.library.auth.dto.response.TokenResponse;
import com.library.auth.infrastructure.config.Oauth2UrlBuilder;
import com.library.shared.dto.ApiResponseApp;
import com.library.shared.util.RequiresAuthentication;
import com.library.shared.util.SecurityEvaluator;
import com.library.user.application.dto.request.ChangePasswordRequest;
import com.library.user.application.dto.request.LoginRequest;
import com.library.user.application.dto.request.OnboardingProfileRequest;
import com.library.user.application.dto.request.RegisterUserCommand;
import com.library.user.application.port.PasswordHasher;
import com.library.user.application.usecase.user.ChangePasswordUseCase;
import com.library.user.application.usecase.user.SignUpUseCase;
import com.library.user.domain.valueobject.UserId;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final LoginUseCase loginUseCase;
  private final PasswordHasher passwordHasher;
  private final Oauth2UrlBuilder oauth2UrlBuilder;
  private final AuthService authService;
  private final LogoutUseCase logoutUseCase;
  private final RefreshAccessTokenUseCase refreshAccessTokenUseCase;
  private final ChangePasswordUseCase changePasswordUseCase;
  private final SecurityEvaluator securityEvaluator;
  private final OnboardingProfileUseCase onboardingProfileUseCase;
  private final SignUpUseCase signUpUseCase;

  @PostMapping("/login")
  public ApiResponseApp<TokenResponse> login(@RequestBody LoginRequest request) {
    return ApiResponseApp.success("Login successful", loginUseCase.execute(request));
  }

  @PostMapping("/logout")
  public ApiResponseApp<Void> logout(@RequestHeader("re-token") String refreshToken) {
    logoutUseCase.execute(refreshToken);
    return ApiResponseApp.success("Logout successful", null);
  }

  // not finish
  @Operation(summary = "Register a new user account")
  @PostMapping("/register")
  public ApiResponseApp<Void> register(@Valid @RequestBody RegisterUserCommand request) {
    signUpUseCase.execute(request);
    return ApiResponseApp.success("Register successful");
  }

  // not finish
  @PostMapping("/onboarding-profile")
  @RequiresAuthentication()
  public ApiResponseApp<Void> onboardingProfile(
      @Valid @RequestBody OnboardingProfileRequest request) {
    Long userId = securityEvaluator.getCurrentUserId();
    // update profile -> sau khi tạo tk với google xong thì bắt nhập studentId và faculty
    onboardingProfileUseCase.execute(UserId.of(userId), request);
    return ApiResponseApp.success("Onboarding profile successful");
  }

  @PostMapping("/change-password")
  @RequiresAuthentication()
  public ApiResponseApp<Void> changePassword(@RequestBody ChangePasswordRequest request) {
    Long userId = securityEvaluator.getCurrentUserId();
    changePasswordUseCase.execute(request, userId);
    return ApiResponseApp.success("Change password successful", null);

  }

  @PostMapping("/refresh-accesstoken")
  public ApiResponseApp<TokenResponse> refreshToken(
      @RequestHeader("re-token") String refreshToken) {
    return ApiResponseApp.success(
        "Refresh access Token successful",
        refreshAccessTokenUseCase.execute(refreshToken)
    );
  }

  @PostMapping("/hash")
  public String hash(@RequestParam("hash") String hash) {
    return passwordHasher.hash(hash);
  }

  @GetMapping("/login-with-social")
  public ApiResponseApp<String> socialLogin(@RequestParam("loginType") String loginType,
      HttpServletRequest request) {
    return ApiResponseApp.success(
        "Social login successful",
        oauth2UrlBuilder.buildAuthorizationUrl(request, loginType.toLowerCase())
    );
  }

  @PostMapping("/social-callback/{registrationId}")
  public ApiResponseApp<TokenResponse> oauth2CallBack(
      @PathVariable("registrationId") String loginType,
      @RequestParam("code") String code
//                                                     @RequestParam ("deviceId") String deviceId
  ) {
    return ApiResponseApp.success(
        "OAuth2 login successful",
        authService.oauth2CallBack(loginType.toLowerCase(), code, "deviceId")
    );
  }
}
