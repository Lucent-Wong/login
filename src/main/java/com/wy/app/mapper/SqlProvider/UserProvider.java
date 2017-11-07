package com.wy.app.mapper.SqlProvider;

import com.wy.app.entity.User;
import org.apache.ibatis.jdbc.SQL;

public class UserProvider {
    public String updateUser(final User user)
    {
        return new SQL() {{
            UPDATE("sys_users");
            if (user.getUsername() != null) {
                SET("username = #{username}");
            }
            if (user.getLocked() != null) {
                SET("locked = #{locked}");
            }
            WHERE("id = #{id}");
        }}.toString();
    }
}
