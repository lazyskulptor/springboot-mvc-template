package user.lazyskulptor.ecommerce.security.dto;

import lombok.Builder;

@Builder
public record LoginVM(String username, String password, boolean rememberMe) {}
