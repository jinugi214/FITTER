package com.mk.fitter.api.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mk.fitter.api.common.oauth.SocialType;
import com.mk.fitter.api.user.repository.dto.UserDto;

@Repository
public interface UserRepository extends JpaRepository<UserDto, Integer> {
	Optional<UserDto> findByEmail(String email);

	Optional<UserDto> findByNickname(String nickname);

	Optional<UserDto> findByRefreshToken(String refreshToken);

	Optional<UserDto> findBySocialTypeAndSocialId(SocialType socialType, String socialId);
}
