package com.lssj.blog.service;

import com.lssj.blog.domain.Vote;

import java.util.List;

/**
 * Vote 服务接口.
 */
public interface VoteService {
	/**
	 * 根据id获取 Vote
	 */
	Vote getVoteById(Long id);
	/**
	 * 删除Vote
	 */
	void removeVote(Long id);

	List<Vote> getVoteByBlogId(Long blogId);

	Vote getVoteByUserIdAndBlogId(Long userId, Long blogId);

	void insert(Vote vote);
}
