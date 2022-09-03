package dev.shuktika.pensionerdetail.repository;

import dev.shuktika.pensionerdetail.entity.Pensioner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PensionerRepository extends JpaRepository<Pensioner, Long> {
    Optional<Pensioner> findByAadharNumber(Long aadharNumber);
}
