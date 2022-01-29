package ua.com.alevel.service.elastic.impl;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import ua.com.alevel.elastic.index.DoctorIndex;
import ua.com.alevel.service.elastic.SearchDoctorService;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchDoctorServiceImpl implements SearchDoctorService {

    private static final String DOCTOR_INDEX = "doctorindex";

    private final ElasticsearchOperations elasticsearchOperations;

    public SearchDoctorServiceImpl(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public List<String> fetchSuggestions(String query) {
        QueryBuilder queryBuilder = QueryBuilders
                .wildcardQuery("name", query + "*");
        Query searchQuery = new NativeSearchQueryBuilder()
                .withFilter(queryBuilder)
                .withPageable(PageRequest.of(0, 5))
                .build();
        SearchHits<DoctorIndex> searchSuggestions =
                elasticsearchOperations.search(searchQuery,
                        DoctorIndex.class,
                        IndexCoordinates.of(DOCTOR_INDEX));
        final List<String> suggestions = new ArrayList<>();
        searchSuggestions.getSearchHits().forEach(searchHit-> suggestions.add(searchHit.getContent().getName()));
        return suggestions;
    }
}
