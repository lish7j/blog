package com.lssj.blog.service.impl;

import com.lssj.blog.dao.TagMapper;
import com.lssj.blog.repository.es.EsBlogRepository;
import com.lssj.blog.domain.Tag;
import com.lssj.blog.domain.User;
import com.lssj.blog.domain.es.EsBlog;
import com.lssj.blog.service.EsBlogService;
import com.lssj.blog.service.UserService;
import com.lssj.blog.vo.TagVO;

import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.BucketOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;

import org.springframework.stereotype.Service;

import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;

/**
 * EsBlog 服务.
 */
@Service("EsBlogService")
public class EsBlogServiceImpl implements EsBlogService {

	private final EsBlogRepository esBlogRepository;
	private final RestHighLevelClient elasticsearchClient;
	private final UserService userService;
	private final TagMapper tagMapper;
	private final ElasticsearchOperations elasticsearchOperations;

	private static final Pageable TOP_5_PAGEABLE = PageRequest.of(0, 5);
	private static final String EMPTY_KEYWORD = "";

	@Autowired
	public EsBlogServiceImpl(EsBlogRepository esBlogRepository, RestHighLevelClient elasticsearchClient, UserService userService, TagMapper tagMapper, ElasticsearchOperations elasticsearchOperations) {
		this.esBlogRepository = esBlogRepository;
		this.elasticsearchClient = elasticsearchClient;
		this.userService = userService;
		this.tagMapper = tagMapper;
		this.elasticsearchOperations = elasticsearchOperations;
	}
	@Override
	public void removeEsBlog(String id) {
		esBlogRepository.deleteById(id);
	}

	@Override
	public void updateEsBlog(EsBlog esBlog) {
        esBlogRepository.save(esBlog);
    }

	@Override
	public EsBlog getEsBlogByBlogId(Long blogId) {
		System.err.println(blogId);
		return esBlogRepository.findByBlogId(blogId);
	}

	@Override
	public Page<EsBlog> listNewestEsBlogs(String keyword, Pageable pageable) {
		Sort sort = Sort.by("createTime").descending();
		Page<EsBlog> res = esBlogRepository.findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(keyword, keyword, keyword, keyword, pageSort(pageable, sort));
		return res;
	}

	@Override
	public Page<EsBlog> listHotestEsBlogs(String keyword, Pageable pageable){
//		Sort sort = new Sort(Direction.DESC,"readSize","commentSize","voteSize","createTime");
		Sort createTimeSorter = Sort.by("createTime").descending();
		Sort voteSizeSorter = Sort.by("voteSize").descending();
		Sort commentSizeSorter = Sort.by("commentSize").descending();
		Sort readSizeSorter = Sort.by("readSize").descending().and(commentSizeSorter).and(voteSizeSorter).and(createTimeSorter);
		return esBlogRepository.findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(keyword, keyword, keyword, keyword, pageSort(pageable, readSizeSorter));
	}

	private Pageable pageSort(Pageable pageable, Sort sort) {
		// 原来的pageable.getSort() == null 是错的，永远都是false
		if (pageable.getSort().isUnsorted()) {
			return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
		} else {
			return pageable;
		}
	}

	@Override
	public Page<EsBlog> listEsBlogs(Pageable pageable) {
		return esBlogRepository.findAll(pageable);
	}


	/**
	 * 最新前5
	 */
	@Override
	public List<EsBlog> listTop5NewestEsBlogs() {
		Page<EsBlog> page = this.listNewestEsBlogs(EMPTY_KEYWORD, TOP_5_PAGEABLE);
		return page.getContent();
	}

	/**
	 * 最热前5
	 */
	@Override
	public List<EsBlog> listTop5HotestEsBlogs() {
		Page<EsBlog> page = this.listHotestEsBlogs(EMPTY_KEYWORD, TOP_5_PAGEABLE);
		return page.getContent();
	}

	@Override
	public List<TagVO> listTop30Tags() {

		List<TagVO> list = new ArrayList<>();
		List<Tag> all = tagMapper.findAll();
		Collections.sort(all, (a, b) -> {
			if (a.getCount() == b.getCount())
				return b.getName().compareTo(a.getName());
			return (int) (b.getCount() - a.getCount());
		});
		for (int i = 0; i < 5 && i < all.size(); i++) {
			Tag tag = all.get(i);
			list.add(new TagVO(tag.getName(), tag.getCount()));
		}
		return list;
	}
	/**
	 * 最热前12用户
	 */
	@Override
	public List<User> listTop12Users() {
		Set<String> usernamelist = new HashSet<>();
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
		return userService.listUsersByUsernames(usernamelist);
	}
}
