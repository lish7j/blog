package com.tshaojl.blog.service;

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

	private final VoteRepository voteRepository;

	@Autowired
	public VoteServiceImpl(VoteRepository voteRepository) {
		this.voteRepository = voteRepository;
	}

	@Override
	@Transactional
	public void removeVote(Long id) {
		voteRepository.deleteById(id);
	}
	@Override
	public Vote getVoteById(Long id) {
		return voteRepository.findById(id).orElse(null);
	}

}
