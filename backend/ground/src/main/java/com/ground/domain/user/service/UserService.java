package com.ground.domain.user.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.ground.domain.follow.repository.FollowRepository;
import com.ground.domain.jwt.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ground.domain.user.dto.UserLoginDto;
import com.ground.domain.user.dto.UserModifyPassDto;
import com.ground.domain.user.dto.UserProfileDto;
import com.ground.domain.user.dto.UserRegisterDto;
import com.ground.domain.user.dto.UserUpdateDto;
import com.ground.domain.user.entity.User;
import com.ground.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {
	
	@Autowired 
	private final UserRepository userRepository;
	@Autowired
	private final FollowRepository followRepository;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Transactional
	//회원가입
	public boolean saveUser(UserRegisterDto params) {
		try {
			userRepository.save(params.toEntity());
			return true;
		}
		catch(Exception e){
			return false;
		}
	}
	
	@Transactional
	//아이디 중복 확인
	public boolean checkUsername(String username) {
		Optional<User> result = userRepository.findByUsername(username);
		if(result.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Transactional
	//닉네임 중복 확인
	public boolean checkNickname(String nickname) {
		Optional<User> result = userRepository.findByNickname(nickname);
		if(result.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Transactional
	//이메일 중복 확인
	public boolean checkEmail(String email) {
		Optional<User> result = userRepository.findByEmail(email);
		if(result.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Transactional
	//유저 계정 삭제 처리
	public boolean deleteUser(Long id) {
		User user = userRepository.findById(id).orElseThrow();
		try {
			user.deleteUser();
			return true;
		}
		catch(Exception e){
			return false;
		}
		
	}
	
	@Transactional
	//아이디 찾기
	public String findId(String email) {
		User user = userRepository.findByEmail(email).orElseThrow();
		return user.getUsername();
	}
	
	@Transactional
	//비밀번호 변경
	public boolean modifyPass(UserModifyPassDto params) {
		User user = userRepository.findByEmail(params.getEmail()).orElseThrow(()
				-> new IllegalArgumentException("해당 유저는 존재하지 않습니다."));
		try {
			user.modifyPass(params.getPass());
			return true;
		}
		catch(Exception e){
			return false;
		}
	}
	
//	@Transactional
//	//일반 로그인
//	public String login(UserLoginDto params) {
//		Optional<User> user = userRepository.findByUsernameAndPass(params.getUsername(), params.getPass());
//		if(user.isEmpty()) {
//			return "아이디나 비밀번호가 틀렸습니다.";
//		}
//		else {
//			try {
//				MakeJwtToken maketoken = new MakeJwtToken();
//				String ftoken = maketoken.makeJwtToken(user.get().getUsername(), user.get().getEmail());
//				user.get().saveFtoken(ftoken);
//				return user.get().getFtoken();
//			}
//			catch(Exception e){
//				return "오류가 발생했습니다.";
//			}		
//		}
//	}
	
	public String createToken(UserLoginDto params) {
	    User user = userRepository.findByUsernameAndPass(params.getUsername(), params.getPass())
	            .orElseThrow(IllegalArgumentException::new);
	      //비밀번호 확인 등의 유효성 검사 진행
	    return jwtTokenProvider.createToken(user.getUsername());
	}


	@Transactional
	public List<User> findFirstByUsernameLikeOrderByIdDesc(String username){
//		return userRepository.findFirstByUsernameLikeOrderByIdDesc(username);
		return userRepository.findAll();
	}
	
	
	@Transactional
	public User save(User user) {
		userRepository.save(user);
		
		return user;
	}
	
	@Transactional
    public UserProfileDto getUserProfile(Long id) {
		UserProfileDto userProfileDto = new UserProfileDto();

        User user = userRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 유저는 존재하지 않습니다."));

		userProfileDto.setUser(user);
		userProfileDto.setUserFollowerCount(followRepository.findFollowerCountById(id));
		userProfileDto.setUserFollowingCount(followRepository.findFollowingCountById(id));

        return userProfileDto;
    }

    @Transactional
    public void getModifyUser(UserUpdateDto userUpdateDto) {

    }
	@Transactional
    public Long profileUpdate(Long id, UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 유저는 존재하지 않습니다."));

        user.profileUpdate(userUpdateDto.getNickname(),
                userUpdateDto.isPrivateYN(),
                userUpdateDto.getAge(),
                userUpdateDto.getGender(),
                userUpdateDto.getIntroduce());

        return id;
    }

}
