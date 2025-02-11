package org.jjh.mallapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jjh.mallapi.dto.MemberDTO;
import org.jjh.mallapi.service.member.MemberService;
import org.jjh.mallapi.util.JWTUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SocialController {

    private final MemberService memberService;

    @GetMapping("api/member/kakao")
    public Map<String, Object> getMemberFromKakao(String accessToken) {

        log.info("access Token " + accessToken);

        MemberDTO memberDTO = memberService.getKakaoMember(accessToken);

        Map<String, Object> claims = memberDTO.getClaims();

        String jwtAccessToken = JWTUtil.generateToken(claims, 10);
        String jwtRefreshToken = JWTUtil.generateToken(claims, 60 * 24);
        claims.put("accessToken", jwtAccessToken);
        claims.put("refreshToken", jwtRefreshToken);

        return claims;

    }
}
