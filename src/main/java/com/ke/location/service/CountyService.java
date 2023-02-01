package com.ke.location.service;



import com.ke.location.entity.SubCounty;
import com.ke.location.web.rest.dto.CountyDto;
import com.ke.location.web.rest.dto.ListResponse;
import com.ke.location.entity.County;
import com.ke.location.entity.QCounty;
import com.ke.location.repository.CountyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CountyService {
    @Autowired
    private CountyRepository countyRepository;

//    public County addCounty(County county) {
//        return countyRepository.save(county);
//    }
    public Optional<County> getCountyByCounty_name(String county_name) {
        Optional<County> county = countyRepository.getCountyByName(county_name);

        if (county.isPresent()) {
            return county;
        } else {
            throw new IllegalStateException("county with name " + county_name + " does not exist");
        }
    }
    public Optional<County> findById(Integer id) {
        Optional<County> county = countyRepository.findById(id);

        if (county.isPresent()) {
            return county;
        } else {
            throw new IllegalStateException("county with id " + id + " does not exist");
        }
    }

    public ListResponse getAllCounties(Integer page, Integer perPage, String search, Integer countyId) {

        page = page - 1;
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, perPage, sort);

        Page<CountyDto> countyPage;
        if (search != null && !search.isEmpty()) {
            QCounty qCounty = QCounty.county;

            countyPage = countyRepository.findBy(qCounty.id.eq(countyId).andAnyOf(qCounty.name.containsIgnoreCase(search)), q -> q.sortBy(sort).as(CountyDto.class).page(pageable));

            return new ListResponse(countyPage.getContent(), countyPage.getTotalPages(), countyPage.getNumberOfElements(), countyPage.getTotalElements());
        } else {

            Page<County> countyPage1 = countyRepository.findAll(pageable);

            return new ListResponse(countyPage1.getContent(), countyPage1.getTotalPages(), countyPage1.getNumberOfElements(), countyPage1.getTotalElements());
        }



    }


    public Page<County> getAllCounties(Pageable pageable) {
        return countyRepository.findAll(pageable);
    }
    public Page<County> getCountiesByName(String name, int page, int size) {
        if (name == null) {
            return countyRepository.findAll(PageRequest.of(page, size));
        } else {
            return countyRepository.findByNameContaining(name, PageRequest.of(page, size));
        }
    }

}
