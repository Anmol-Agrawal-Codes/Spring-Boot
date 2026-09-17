package com.aagrawal.JobPortal.service;

import com.aagrawal.JobPortal.dto.CompanyDto;
import com.aagrawal.JobPortal.entity.Company;

import java.util.List;

public interface ICompanyService {

    List<CompanyDto> getAllCompanies();
}
