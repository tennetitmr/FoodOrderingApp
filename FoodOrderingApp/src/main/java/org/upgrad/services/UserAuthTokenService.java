package org.upgrad.services;

import org.upgrad.models.UserAuthToken;

/*               interface
/*  Controller ------------------> Service Methods
 *  UserAuthTokenService Interface : List of all the service methods that exist in the userAuthToken service implementation class.
 */

public interface UserAuthTokenService {

    void addAccessToken(Integer userId, String accessToken);

    void removeAccessToken(String accessToken);

    UserAuthToken isUserLoggedIn(String accessToken);

    int getUserId (String accessToken);
}
