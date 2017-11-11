package com.wy.app.mapper.SqlProvider;

import com.wy.app.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Arrays;

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

    public String correlationRoles(final @Param("user_id") Long userid, final Long... roleIds) {
        return new SQL() {{
            INSERT_INTO("sys_users_roles");
            if (roleIds != null) {
                Arrays.stream(roleIds).forEach(role_id -> {
                    VALUES("user_id", "#{user_id}");
                    VALUES("role_id", role_id.toString());
                });
            }
        }}.toString();
    }

    public String uncorrelationRoles(final @Param("user_id") Long userid, final Long... roleIds) {
        return new SQL() {{
        DELETE_FROM("sys_users_roles");
        if (roleIds != null) {
            Arrays.stream(roleIds).forEach(role_id -> {
                WHERE("user_id = #{user_id} and role_id");
            });
        }
    }}.toString();
}
}
