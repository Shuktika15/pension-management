package dev.shuktika.pensionerdetail.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PensionerCsv {

    @CsvBindByName(column = "Aadhar Number")
    private Long aadharNumber;

    @CsvBindByName(column = "Name")
    private String name;

    @CsvBindByName(column = "Date of Birth")
    @CsvDate(value = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @CsvBindByName(column = "PAN")
    private String pan;

    @CsvBindByName(column = "Salary Earned")
    private Integer salaryEarned;

    @CsvBindByName(column = "Allowance")
    private Integer allowances;

    @CsvBindByName(column = "Pension Type")
    private String pensionType;

    @CsvBindByName(column = "Bank Name")
    private String bankName;

    @CsvBindByName(column = "Account Number")
    private Long accountNumber;

    @CsvBindByName(column = "Bank Type")
    private String bankType;
}
