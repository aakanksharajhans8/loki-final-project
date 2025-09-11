package com.cams.notification.register.repo;
import com.cams.notification.register.model.RegisteredUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import java.util.Map;
@Repository
public class RegistrationRepository {
  private final SimpleJdbcInsert insert;
  public RegistrationRepository(JdbcTemplate jdbc){
    this.insert=new SimpleJdbcInsert(jdbc).withTableName("registered_users").usingGeneratedKeyColumns("id");
  }
  public long save(RegisteredUser u){
    SqlParameterSource p=new MapSqlParameterSource(Map.of(
      "username",u.getUsername(),
      "email",u.getEmail(),
      "phone_number",u.getPhoneNumber(),
      "password_hash",u.getPasswordHash()
    ));
    Number key=insert.executeAndReturnKey(p);
    return key.longValue();
  }
}
