package ua.com.alevel.facade.elastic.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.facade.elastic.SearchDoctorFacade;
import ua.com.alevel.service.elastic.SearchDoctorService;

import java.util.List;

@Service
public class SearchDoctorFacadeImpl implements SearchDoctorFacade {

    private final SearchDoctorService searchDoctorService;

    public SearchDoctorFacadeImpl(SearchDoctorService searchDoctorService) {
        this.searchDoctorService = searchDoctorService;
    }

    @Override
    public List<String> fetchSuggestions(String query) {
        return searchDoctorService.fetchSuggestions(query);
    }
}
