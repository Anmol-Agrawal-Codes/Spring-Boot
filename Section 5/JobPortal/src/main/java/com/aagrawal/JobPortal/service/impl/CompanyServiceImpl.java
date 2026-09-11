package com.aagrawal.JobPortal.service.impl;

import com.aagrawal.JobPortal.dto.CompanyDto;
import com.aagrawal.JobPortal.entity.Company;
import com.aagrawal.JobPortal.repository.CompanyRepository;
import com.aagrawal.JobPortal.service.ICompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements ICompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public List<CompanyDto> getAllCompanies() {
        List<Company> companyList = companyRepository.findAll();
        return companyList.stream().map(this::transformToData).collect(Collectors.toList());
    }

    private CompanyDto transformToData(Company company){
        return new CompanyDto(company.getId(), company.getName(), company.getLogo(),
                company.getIndustry(), company.getSize(), company.getRating(),
                company.getLocations(), company.getFounded(), company.getDescription(),
                company.getEmployees(), company.getWebsite(), company.getCreatedAt());
    }
}
