package com.app.muhammadgamal.swapy.SwapData;

public class User {

    private String mUsername, mCompany, mBranch, mAccount, mCurrentShift;
    private String mPhoneNumber;
    private String mProfilePhotoURL;
    private int mSentRequests, mReceivedRequests, mAcceptedRequests;

    public  User(){

    }

    public User(String username,
                String phoneNumber,
                String company,
                String branch,
                String account,
                String currentShift,
                String profilePhotoURL,
                int sentRequests,
                int receivedRequests,
                int acceptedRequests){
        mUsername = username;
        mPhoneNumber = phoneNumber;
        mCompany = company;
        mBranch = branch;
        mAccount = account;
        mCurrentShift = currentShift;
        mProfilePhotoURL = profilePhotoURL;
        mSentRequests = sentRequests;
        mReceivedRequests = receivedRequests;
        mAcceptedRequests = acceptedRequests;
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

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public void setmCompany(String mCompany) {
        this.mCompany = mCompany;
    }

    public void setmBranch(String mBranch) {
        this.mBranch = mBranch;
    }

    public void setmAccount(String mAccount) {
        this.mAccount = mAccount;
    }

    public void setmCurrentShift(String mCurrentShift) {
        this.mCurrentShift = mCurrentShift;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public void setmProfilePhotoURL(String mProfilePhotoURL) {
        this.mProfilePhotoURL = mProfilePhotoURL;
    }

    public int getmSentRequests() {
        return mSentRequests;
    }

    public void setmSentRequests(int mSentRequests) {
        this.mSentRequests = mSentRequests;
    }

    public int getmReceivedRequests() {
        return mReceivedRequests;
    }

    public void setmReceivedRequests(int mReceivedRequests) {
        this.mReceivedRequests = mReceivedRequests;
    }

    public int getmAcceptedRequests() {
        return mAcceptedRequests;
    }

    public void setmAcceptedRequests(int mAcceptedRequests) {
        this.mAcceptedRequests = mAcceptedRequests;
    }

}
