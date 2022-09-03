package dev.shuktika.pensionerdetail.util;

import dev.shuktika.pensionerdetail.entity.Pensioner;
import dev.shuktika.pensionerdetail.repository.PensionerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReadPensionerRunner implements CommandLineRunner {
    private final CsvUtil csvUtil;
    private final PensionerRepository pensionerRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Pensioner> pensioners = csvUtil.readPensionerFromCsv();
        pensioners.forEach(pensioner -> log.info(pensioner.toString()));
        pensionerRepository.saveAll(pensioners);
    }
}
