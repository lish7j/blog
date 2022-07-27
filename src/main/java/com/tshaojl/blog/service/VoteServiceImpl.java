package com.tshaojl.blog.service;

import com.tshaojl.blog.dao.VoteMapper;
import com.tshaojl.blog.domain.Vote;
import com.tshaojl.blog.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

}
