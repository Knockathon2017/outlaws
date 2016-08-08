
package com.juster.data.api.database.user.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GuidesDetail implements Serializable {

    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Rating")
    @Expose
    private int rating;
    @SerializedName("RatingCount")
    @Expose
    private int ratingCount;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("Languages")
    @Expose
    private String languages;
    @SerializedName("DateOfBirth")
    @Expose
    private String dateOfBirth;
    @SerializedName("Mobile")
    @Expose
    private long mobile;

    /**
     * 
     * @return
     *     The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 
     * @param userId
     *     The UserId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The rating
     */
    public int getRating() {
        return rating;
    }

    /**
     * 
     * @param rating
     *     The Rating
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * 
     * @return
     *     The ratingCount
     */
    public int getRatingCount() {
        return ratingCount;
    }

    /**
     * 
     * @param ratingCount
     *     The RatingCount
     */
    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    /**
     * 
     * @return
     *     The address
     */
    public String getAddress() {
        return address;
    }

    /**
     * 
     * @param address
     *     The Address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 
     * @return
     *     The gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * 
     * @param gender
     *     The Gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * 
     * @return
     *     The languages
     */
    public String getLanguages() {
        return languages;
    }

    /**
     * 
     * @param languages
     *     The Languages
     */
    public void setLanguages(String languages) {
        this.languages = languages;
    }

    /**
     * 
     * @return
     *     The dateOfBirth
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * 
     * @param dateOfBirth
     *     The DateOfBirth
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * 
     * @return
     *     The mobile
     */
    public long getMobile() {
        return mobile;
    }

    /**
     * 
     * @param mobile
     *     The Mobile
     */
    public void setMobile(long mobile) {
        this.mobile = mobile;
    }

}
