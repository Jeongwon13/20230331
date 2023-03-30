package edu.kh.community.member.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Lombok 라이브러리
// - VO(Value Object) 또는 DTO(Data Transfer Object)
// 에 작성되는 공통코드(getter/setter/생성자)를 자동 추가해주는 라이브러리


@Getter // getter 자동추가
@Setter // setter 자동추가
@ToString // toString 자동추가
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드 초기화하는 매개변수 생성자

public class Member {
	private int MemberNo;
	private String MemberEmail;
	private String MemberPw;
	private String MemberNickname;
	private String memberTel;
	private String memberAddress;
	private String profilemage;
	private String enrollDate;
	private String secesstionFlag;
	public Member() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Member(int memberNo, String memberEmail, String memberPw, String memberNickname, String memberTel,
			String memberAddress, String profilemage, String enrollDate, String secesstionFlag) {
		super();
		MemberNo = memberNo;
		MemberEmail = memberEmail;
		MemberPw = memberPw;
		MemberNickname = memberNickname;
		this.memberTel = memberTel;
		this.memberAddress = memberAddress;
		this.profilemage = profilemage;
		this.enrollDate = enrollDate;
		this.secesstionFlag = secesstionFlag;
	}
	
	public Member(int memberNo, String memberEmail, String memberNickname) {
		MemberNo = memberNo;
		MemberEmail = memberEmail;
		MemberNickname = memberNickname;
	}
	
	
	
	public int getMemberNo() {
		return MemberNo;
	}
	public void setMemberNo(int memberNo) {
		MemberNo = memberNo;
	}
	public String getMemberEmail() {
		return MemberEmail;
	}
	public void setMemberEmail(String memberEmail) {
		MemberEmail = memberEmail;
	}
	public String getMemberPw() {
		return MemberPw;
	}
	public void setMemberPw(String memberPw) {
		MemberPw = memberPw;
	}
	public String getMemberNickname() {
		return MemberNickname;
	}
	public void setMemberNickname(String memberNickname) {
		MemberNickname = memberNickname;
	}
	public String getMemberTel() {
		return memberTel;
	}
	public void setMemberTel(String memberTel) {
		this.memberTel = memberTel;
	}
	public String getMemberAddress() {
		return memberAddress;
	}
	public void setMemberAddress(String memberAddress) {
		this.memberAddress = memberAddress;
	}
	public String getProfilemage() {
		return profilemage;
	}
	public void setProfilemage(String profilemage) {
		this.profilemage = profilemage;
	}
	public String getEnrollDate() {
		return enrollDate;
	}
	public void setEnrollDate(String enrollDate) {
		this.enrollDate = enrollDate;
	}
	public String getSecesstionFlag() {
		return secesstionFlag;
	}
	public void setSecesstionFlag(String secesstionFlag) {
		this.secesstionFlag = secesstionFlag;
	}
	@Override
	public String toString() {
		return "Member [MemberNo=" + MemberNo + ", MemberEmail=" + MemberEmail + ", MemberPw=" + MemberPw
				+ ", MemberNickname=" + MemberNickname + ", memberTel=" + memberTel + ", memberAddress=" + memberAddress
				+ ", profilemage=" + profilemage + ", enrollDate=" + enrollDate + ", secesstionFlag=" + secesstionFlag
				+ "]";
	}
	
 
}

