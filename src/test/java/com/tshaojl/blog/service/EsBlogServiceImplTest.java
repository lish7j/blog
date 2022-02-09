package com.tshaojl.blog.service;


import com.tshaojl.blog.domain.es.EsBlog;
import com.tshaojl.blog.vo.TagVO;
import com.tshaojl.blog.repository.es.EsBlogRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;

@RunWith(SpringRunner.class)
@SpringBootTest
@Component
@Slf4j
class EsBlogServiceImplTest {

    @Autowired
    private EsBlogRepository esBlogRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Test
    void getEsBlogByBlogId() {
        EsBlog blog = esBlogRepository.findByBlogId(5L);
        log.info(blog.toString());
    }

    @Test
    void get() {
        Sort sort = Sort.by("createTime").descending();
        Pageable pageable = PageRequest.of(0, 10, sort);
        Page<EsBlog> blogs = esBlogRepository.findDistinctEsBlogByTitleContaining("", pageable);
        log.info(blogs.getContent().toString());
    }

    @Test
    void get2() {
        Sort sort = Sort.by("createTime").descending();
        Pageable pageable = PageRequest.of(0, 10, sort);

        Page<EsBlog> blogs = esBlogRepository.findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining("","","","", pageable);
        log.info(blogs.getContent().toString());
    }

    @Test
    void get3() {
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();

        searchQueryBuilder.withQuery(QueryBuilders.queryStringQuery("*7").field("title"));
        searchQueryBuilder.withSorts(SortBuilders.fieldSort("createTime").order(SortOrder.DESC));
        searchQueryBuilder.withPageable(PageRequest.of(0, 10));
        SearchHits<EsBlog> blogs = elasticsearchOperations.search(searchQueryBuilder.build(), EsBlog.class, IndexCoordinates.of("blog"));
        log.info("查询到 " + blogs.getSearchHits().size());
        Iterator<SearchHit<EsBlog>> iterator = blogs.iterator();

        while (iterator.hasNext()) {
            log.info("tt: " + iterator.next().getContent().toString());

        }
    }

    @Test
    void get4() {
        Pageable pageable = PageRequest.of(0, 4);
        log.info(String.valueOf(pageable.getSort().isUnsorted()));
        log.info(String.valueOf(pageable.getSort().isEmpty()));
        pageable = PageRequest.of(0, 4, Sort.by("cr").descending());
        log.info(String.valueOf(pageable.getSort().isUnsorted()));
        log.info(String.valueOf(pageable.getSort().isEmpty()));
    }

    @Test
    void listTop12UsersTest() {
        List<String> usernamelist = new ArrayList<>();
		// given
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withSearchType(SearchType.QUERY_THEN_FETCH)
                .addAggregation(terms("users").field("username").order(BucketOrder.count(false)).size(12))
                .build();
		// when
		SearchHits<EsBlog> searchHits = elasticsearchOperations.search(searchQuery, EsBlog.class, IndexCoordinates.of("blog"));
		Iterator<SearchHit<EsBlog>> iterator = searchHits.iterator();
		while (iterator.hasNext()) {
			SearchHit<EsBlog> now = iterator.next();
			usernamelist.add(now.getContent().getUsername());
		}
		log.info(usernamelist.toString());
    }

    @Test
    void listTop30Tags() {
        List<TagVO> list = new ArrayList<>();

    }
}