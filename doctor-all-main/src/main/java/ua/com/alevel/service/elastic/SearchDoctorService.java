package ua.com.alevel.service.elastic;

import java.util.List;

public interface SearchDoctorService {

    List<String> fetchSuggestions(String query);
}
