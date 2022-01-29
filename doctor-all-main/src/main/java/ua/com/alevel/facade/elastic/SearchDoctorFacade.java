package ua.com.alevel.facade.elastic;

import java.util.List;

public interface SearchDoctorFacade {

    List<String> fetchSuggestions(String query);
}
