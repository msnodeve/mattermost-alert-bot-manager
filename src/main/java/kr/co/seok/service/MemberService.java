package kr.co.seok.service;

import kr.co.seok.dto.Member;
import kr.co.seok.dto.request.MemberSaveRequestDto;
import kr.co.seok.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberService implements UserDetailsService {
    @Autowired
    private MemberRepository memberRepository;

    public Member join(MemberSaveRequestDto memberSaveRequestDto) {
        return memberRepository.save(memberSaveRequestDto.toEntity());
    }

    public UserDetails loadUserById(String memberId) throws UsernameNotFoundException {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        return memberRepository.findByMemberId(Long.parseLong(memberId))
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }
}
