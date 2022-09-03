package dev.shuktika.authorization.repository;

import dev.shuktika.authorization.entity.Pensioner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PensionerRepository extends JpaRepository<Pensioner, Long> {
}
