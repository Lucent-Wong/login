package com.primedice.app.mapper;

import com.primedice.common.entity.UserAccount;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserAccountMapper {

    UserAccount findByUserId(Long id);
    UserAccount findByUserIdForUpdate(Long id);
    int createUserAccount(UserAccount account);
    int updateUserAccount(UserAccount account);
    int deleteByUserId(Long id);
}
