package com.mode.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.mode.domain.Config;

/**
 * Created by chao on 1/19/16.
 */
public interface ConfigDAO {

    /**
     * Get a key-value pair from app config table.
     *
     * @param type
     * @param key
     * @return
     */
    @Select({"SELECT * FROM h$config ",
            "WHERE type = #{type} ",
            "AND key = #{key} ",
            "LIMIT 1"})
    public Config getConfig(@Param("type") String type,
                            @Param("key") String key);
}