package com.tshaojl.blog.repository.es;

import com.tshaojl.blog.domain.es.EsBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Blog 存储库.
 */
public interface EsBlogRepository extends ElasticsearchRepository<EsBlog, String> {
 
	/**
	 * 模糊查询(去重)
	 */
	Page<EsBlog> findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(String title, String summary, String content, String tags, Pageable pageable);

	EsBlog findByBlogId(Long blogId);

	Page<EsBlog> findDistinctEsBlogByTitleContaining(String title, Pageable pageable);
}
