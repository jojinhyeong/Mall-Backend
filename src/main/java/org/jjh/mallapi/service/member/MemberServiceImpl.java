package org.jjh.mallapi.service.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jjh.mallapi.domain.Member;
import org.jjh.mallapi.domain.MemberRole;
import org.jjh.mallapi.dto.MemberDTO;
import org.jjh.mallapi.dto.MemberModifyDTO;
import org.jjh.mallapi.repository.MemberRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.LinkedHashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberDTO getKakaoMember(String accessToken) {

        log.info("----------------------------");
        log.info("-----------getKakaoMember log-----------------");
        //카카오 연동 닉네임 -- 이메일 주소에 해당
        String nickname = getEmailFromKakaoAccessToken(accessToken);
        //log.info(nickname);
        //데이터베이스에 있는지 확인
        Optional<Member> result = memberRepository.findById(nickname);
        //log.info(String.valueOf(result));

        //기존의 회원
        if (result.isPresent()) {
            MemberDTO memberDTO = entityToDTO(result.get());
            //log.info("-----기존회원-------");
            //log.info(String.valueOf(memberDTO));
            return memberDTO;
        }

        //회원이 아니었다면 닉네임은 '소셜회원’으로 패스워드는 임의로 생성
        Member socialMember = makeSocialMember(nickname);
        memberRepository.save(socialMember);

        MemberDTO memberDTO = entityToDTO(socialMember);
        //log.info("-----뉴회원-------");
        //log.info(String.valueOf(memberDTO));
        return memberDTO;
    }

    @Override
    public void modifyMember(MemberModifyDTO memberModifyDTO) {

        Optional<Member> result =
                memberRepository.findById(memberModifyDTO.getEmail());

        Member member = result.orElseThrow();

        member.changePw(passwordEncoder.encode(memberModifyDTO.getPw()));

        member.changeSocial(false);

        member.changeNickname(memberModifyDTO.getNickname());

        memberRepository.save(member);

    }

    private Member makeSocialMember(String nickname) {
        String tempPassword = makeTempPassword();

        log.info("tempPassword: {}", tempPassword);

        Member member = Member.builder()
                .email(nickname) //닉네임
                .pw(passwordEncoder.encode(tempPassword))
                .nickname("Social Member")
                .social(true)
                .build();

        member.addRole(MemberRole.USER);

        return member;

    }

    private String getEmailFromKakaoAccessToken(String accessToken) {

        String kakaoGetUserURL = "https://kapi.kakao.com/v2/user/me";

        if (accessToken == null) {
            throw new RuntimeException("Access Token is null");
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(kakaoGetUserURL).build();

        ResponseEntity<LinkedHashMap> response
                = restTemplate.exchange(uriBuilder.toString(), HttpMethod.GET, entity, LinkedHashMap.class);


        log.info("------------------------------------");
        log.info(String.valueOf(response));

        LinkedHashMap<String, LinkedHashMap> bodyMap = response.getBody();

        LinkedHashMap<String, String> kakaoAccount = bodyMap.get("properties");

        log.info("kakaoAccount: {}", kakaoAccount);

        String nickname = kakaoAccount.get("nickname");

        log.info("nickname: {}", nickname);

        return nickname;
    }

    //랜덤 패스워드
    private String makeTempPassword() {
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < 10; i++) {
            buffer.append((char) ((int) (Math.random() * 55) + 65));
        }
        return buffer.toString();
    }

}

