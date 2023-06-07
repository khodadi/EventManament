package com.notification.repo.irepo;

import com.notification.entity.PSP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IPSPRepository extends JpaRepository<PSP,Long> {
}
