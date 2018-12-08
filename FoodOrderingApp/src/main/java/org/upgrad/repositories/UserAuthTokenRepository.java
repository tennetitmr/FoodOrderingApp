package org.upgrad.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.upgrad.models.UserAuthToken;

// Controller  ------> Services Layer ---> Data Access Layer (Repository)  --> Database
//                                                                  UserAuthTokenRepository
// This UserAuthTokenRepository interface: User Auth Token Service ------------------------> user_auth_token table


@Repository
public interface UserAuthTokenRepository extends CrudRepository<UserAuthToken, Integer> {

    // To checks whether the user is Logged in
    @Query(nativeQuery = true,value = "SELECT * FROM USER_AUTH_TOKEN WHERE access_token=?1")
    UserAuthToken isUserLoggedIn(String accessToken);

    // To Returns userId  
    @Query(nativeQuery = true,value = "SELECT user_id FROM USER_AUTH_TOKEN WHERE access_token=?1")
    Integer getUserId(String accessToken);

    // To Remove accesstoken
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="UPDATE USER_AUTH_TOKEN SET logout_at=NOW() WHERE access_token=?1")
    void removeAuthToken( String accessToken);


}
