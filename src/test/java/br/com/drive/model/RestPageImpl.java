package br.com.drive.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RestPageImpl<T> extends PageImpl<T> {
    private boolean first;
    private boolean last;
    private int totalPages;
    private int numberOfElements;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES )
    public RestPageImpl(@JsonProperty("content") List<T> content,
                            @JsonProperty("number") int number,
                            @JsonProperty("size") int size,
                            @JsonProperty("totalElements") int totalElements,
                            @JsonProperty("last") boolean last,
                            @JsonProperty("first") boolean first,
                            @JsonProperty("totalPages") int totalPages,
                            @JsonProperty("numberOfElements") int numberOfElements,
                            @JsonProperty("pageable") JsonNode pageable,
                            @JsonProperty("sort") JsonNode sort) {
        super(content, PageRequest.of(number, size), totalElements);

        this.last = last;
        this.first = first;
        this.totalPages = totalPages;
        this.numberOfElements = numberOfElements;

    }

//    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
//    public RestPageImpl(@JsonProperty("content") List<T> content,
//                        @JsonProperty("number") int number,
//                        @JsonProperty("size") int size,
//                        @JsonProperty("totalElements") Long totalElements,
//                        @JsonProperty("pageable") JsonNode pageable,
//                        @JsonProperty("last") boolean last,
//                        @JsonProperty("totalPages") int totalPages,
//                        @JsonProperty("sort") JsonNode sort,
//                        @JsonProperty("first") boolean first,
//                        @JsonProperty("numberOfElements") int numberOfElements) {
//
//        super(content, PageRequest.of(number, size), totalElements);
//    }
//
//    public RestPageImpl(List<T> content, Pageable pageable, long total) {
//        super(content, pageable, total);
//    }
//
//    public RestPageImpl(List<T> content) {
//        super(content);
//    }
//
//    public RestPageImpl() {
//        super(new ArrayList<>());
//    }
}