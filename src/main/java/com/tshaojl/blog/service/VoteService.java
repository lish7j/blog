package com.tshaojl.blog.service;

import com.tshaojl.blog.domain.Vote;

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
}
