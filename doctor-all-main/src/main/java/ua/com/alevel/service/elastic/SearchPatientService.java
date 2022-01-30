package ua.com.alevel.service.elastic;

import java.util.List;

public interface SearchPatientService {

    List<String> fetchSuggestions(String query);
}
