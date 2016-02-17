package com.mode.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.mode.domain.Account;

public interface AccountDAO {

    /**
     * Create a new account
     *
     * @param account
     * @return
     */
    @Insert("INSERT INTO h$account " +
            "(login, password, roles, activated, access_token, ctime, utime) " +
            "VALUES " +
            "(#{login}, #{password}, #{roles}, #{activated}, #{accessToken}, #{ctime}, #{utime})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", keyColumn = "id",
            before = false, resultType = Integer.class)
    public int createAccount(Account account);

    /**
     * Get a user account by unique login or id.
     *
     * @param login
     * @param id
     * @return
     */
    @Select({"<script>",
            "SELECT * FROM h$account ",
            "<where>",
            "<if test='login != null'> login = #{login} </if>",
            "<if test='id != null'> id = #{id} </if>",
            "</where>",
            "</script>"})
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "login", column = "login"),
            @Result(property = "password", column = "password"),
            @Result(property = "roles", column = "roles"),
            @Result(property = "activated", column = "activated"),
            @Result(property = "accessToken", column = "access_token"),
            @Result(property = "ctime", column = "ctime"),
            @Result(property = "utime", column = "utime"),
    })
    public Account getAccount(@Param("login") String login,
                              @Param("id") Integer id);

    /**
     * Update account information
     *
     * @param account
     * @return
     */
    @Update({"<script>",
            "UPDATE h$account ",
            "<set>",
            "<if test='password != null'> password = #{password}, </if>",
            "<if test='roles != null'> roles = #{roles}, </if>",
            "<if test='activated != null'> activated = #{activated}, </if>",
            "<if test='accessToken != null'> access_token = #{accessToken}, </if>",
            "<if test='utime != null'> utime = #{utime} </if>",
            "</set>",
            "</script>"})
    public int updateAccount(Account account);
}

