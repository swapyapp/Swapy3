package com.app.muhammadgamal.swapy;

public class User {

    private String mUsername, mCompany, mBranch, mAccount, mCurrentShift;
    private String mPhoneNumber;
    private String mProfilePhotoURL;

    public  User(){

    }

    public User(String username,
                String phoneNumber,
                String company,
                String branch,
                String account,
                String currentShift,
                String profilePhotoURL){
        mUsername = username;
        mPhoneNumber = phoneNumber;
        mCompany = company;
        mBranch = branch;
        mAccount = account;
        mCurrentShift = currentShift;
        mProfilePhotoURL = profilePhotoURL;
    }

    public String getmUsername() {
        return mUsername;
    }

    public String getmCompany() {
        return mCompany;
    }

    public String getmBranch() {
        return mBranch;
    }

    public String getmAccount() {
        return mAccount;
    }

    public String getmCurrentShift() {
        return mCurrentShift;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public String getmProfilePhotoURL() {
        return mProfilePhotoURL;
    }

}
