package ua.com.alevel.facade.elastic;

import java.util.List;

public interface SearchPatientFacade {

    List<String> fetchSuggestions(String query);
}
