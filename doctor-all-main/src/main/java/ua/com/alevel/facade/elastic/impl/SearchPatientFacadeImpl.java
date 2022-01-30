package ua.com.alevel.facade.elastic.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.facade.elastic.SearchPatientFacade;
import ua.com.alevel.service.elastic.SearchPatientService;

import java.util.List;

@Service
public class SearchPatientFacadeImpl implements SearchPatientFacade {

    private final SearchPatientService searchPatientService;

    public SearchPatientFacadeImpl(SearchPatientService searchPatientService) {
        this.searchPatientService = searchPatientService;
    }

    @Override
    public List<String> fetchSuggestions(String query) {
        return searchPatientService.fetchSuggestions(query);
    }
}
