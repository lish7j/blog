package com.lssj.blog.service;

import com.lssj.blog.dao.VoteMapper;
import com.lssj.blog.domain.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Vote 服务.
 */
@Service
public class VoteServiceImpl implements VoteService {

	private final VoteMapper voteMapper;

	@Autowired
	public VoteServiceImpl(VoteMapper voteMapper) {
		this.voteMapper = voteMapper;
	}

	@Override
	@Transactional
	public void removeVote(Long id) {
		voteMapper.deleteById(id);
	}
	@Override
	public Vote getVoteById(Long id) {
		return voteMapper.findById(id);
	}

	public List<Vote> getVoteByBlogId(Long blogId) {
		return voteMapper.findByBlogId(blogId);
	}

}
