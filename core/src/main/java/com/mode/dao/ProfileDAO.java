package com.mode.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.mode.domain.Profile;

/**
 * Created by chao on 1/18/16.
 */
public interface ProfileDAO {

    /**
     * Get the profile information of the user
     *
     * @param userId
     * @return
     */
    @Select("SELECT * FROM h$profile WHERE user_id = #{userId}")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "avatar", column = "avatar"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "ctime", column = "ctime"),
            @Result(property = "utime", column = "utime")
    })
    public Profile getProfile(@Param("userId") Integer userId);

    /**
     * Create a new profile for the user
     *
     * @param profile
     * @return
     */
    @Insert("INSERT INTO h$profile (user_id, name, avatar, gender, ctime, utime) " +
            "VALUES (#{userId}, #{name}, #{avatar}, #{gender}, #{ctime}, #{utime})")
    public int createProfile(Profile profile);
}
