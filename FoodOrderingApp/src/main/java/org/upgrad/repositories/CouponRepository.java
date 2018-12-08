package org.upgrad.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.upgrad.models.Coupon;

@Repository
public interface CouponRepository extends CrudRepository<Coupon, String> {

    // Read Coupon Details by Name.
    @Query(nativeQuery = true,value = "SELECT * FROM COUPON WHERE COUPON_NAME=?1")
    Coupon getCouponByName(String couponName);
}
