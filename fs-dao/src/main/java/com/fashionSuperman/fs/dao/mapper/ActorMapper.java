package com.fashionSuperman.fs.dao.mapper;

import com.fashionSuperman.fs.dao.entity.Actor;

public interface ActorMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table actor
     *
     * @mbggenerated Thu Feb 16 11:29:26 CST 2017
     */
    int deleteByPrimaryKey(Short actorId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table actor
     *
     * @mbggenerated Thu Feb 16 11:29:26 CST 2017
     */
    int insert(Actor record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table actor
     *
     * @mbggenerated Thu Feb 16 11:29:26 CST 2017
     */
    int insertSelective(Actor record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table actor
     *
     * @mbggenerated Thu Feb 16 11:29:26 CST 2017
     */
    Actor selectByPrimaryKey(Short actorId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table actor
     *
     * @mbggenerated Thu Feb 16 11:29:26 CST 2017
     */
    int updateByPrimaryKeySelective(Actor record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table actor
     *
     * @mbggenerated Thu Feb 16 11:29:26 CST 2017
     */
    int updateByPrimaryKey(Actor record);
}