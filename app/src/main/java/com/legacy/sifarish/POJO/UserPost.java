package com.legacy.sifarish.POJO;

/**
 * Created by shivang on 11/23/15.
 */
public class UserPost {
    public long userId;
    public String userName;
    public String userDob;
    public String userEmail;
    public String userGender;
    public String userStreet;
    public String userCity;
    public String userState;
    public String userCountry;
    public String userZip;

    public String userProfession;
    public String userClothingProfessional;
    public String userClothingPersonal;
    public String userHobbies;
    public String userSpendingStyle;

    public UserPost(long userId, String userName, String userDob, String userEmail, String userGender, String userStreet, String userCity, String userState, String userCountry, String userZip, String userProfession, String userClothingProfessional, String userClothingPersonal, String userHobbies, String userSpendingStyle) {
        this.userId = userId;
        this.userName = userName;
        this.userDob = userDob;
        this.userEmail = userEmail;
        this.userGender = userGender;
        this.userStreet = userStreet;
        this.userCity = userCity;
        this.userState = userState;
        this.userCountry = userCountry;
        this.userZip = userZip;
        this.userProfession = userProfession;
        this.userClothingProfessional = userClothingProfessional;
        this.userClothingPersonal = userClothingPersonal;
        this.userHobbies = userHobbies;
        this.userSpendingStyle = userSpendingStyle;
    }

    @Override
    public String toString() {
        return "UserPost{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userDob='" + userDob + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userGender='" + userGender + '\'' +
                ", userStreet='" + userStreet + '\'' +
                ", userCity='" + userCity + '\'' +
                ", userState='" + userState + '\'' +
                ", userCountry='" + userCountry + '\'' +
                ", userZip='" + userZip + '\'' +
                ", userProfession='" + userProfession + '\'' +
                ", userClothingProfessional='" + userClothingProfessional + '\'' +
                ", userClothingPersonal='" + userClothingPersonal + '\'' +
                ", userHobbies='" + userHobbies + '\'' +
                ", userSpendingStyle='" + userSpendingStyle + '\'' +
                '}';
    }
}
