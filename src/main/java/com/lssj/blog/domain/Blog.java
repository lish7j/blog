package com.lssj.blog.domain;

import com.github.rjeschke.txtmark.Processor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Blog 实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id; // 博客的唯一标识
	
	// 博客标题
	private String title;
	
	// 博客摘要
	private String summary;

	// 博客内容, 大对象，映射 MySQL 的 Long Text 类型
	private String content;
	
	// 大对象，映射 MySQL 的 Long Text 类型
	// 将 md 转为 html
	private String htmlContent;
 	
	// 用户id
	private Long userId;
	
	// 由数据库自动创建时间
	private Timestamp createTime;

	// 更新时间
	private Timestamp updateTime;

	// 访问量、阅读量
	private Integer readSize = 0;

	// 评论量
	private Integer commentSize = 0;

	// 点赞量
	private Integer voteSize = 0;

	private List<Comment> comments;

	private List<Vote> votes;

	private Long catalogId;
	
	// 分类id
	private Catalog catalog;

	// 标签 如 "java, springboot, springcloud"
	private String tags;
	

	public Blog(String title, String summary,String content) {
		this.title = title;
		this.summary = summary;
		this.content = content;
	}


	public void setContent(String content) {
		this.content = content;
		this.htmlContent = Processor.process(content);
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
		this.commentSize = this.comments.size();
	}
 
	/**
	 * 添加评论
	 */
	public void addComment(Comment comment) {
		this.comments.add(comment);
		this.commentSize = this.comments.size();
	}
	/**
	 * 删除评论
	 */
	public void removeComment(Long commentId) {
		for (int index=0; index < this.comments.size(); index ++ ) {
			if (comments.get(index).getId().equals(commentId)) {
				this.comments.remove(index);
				break;
			}
		}
		this.commentSize = this.comments.size();
	}
 
	/**
	 * 点赞
	 */
	public boolean addVote(Vote vote) {
		boolean isExist = false;
		// 判断重复
		for (Vote vote1 : this.votes) {
			if (vote1.getUserId().equals(vote.getUserId())) {
				isExist = true;
				break;
			}
		}
		
		if (!isExist) {
			this.votes.add(vote);
			this.voteSize = this.votes.size();
		}

		return isExist;
	}
	/**
	 * 取消点赞
	 * @param voteId
	 */
	public void removeVote(Long voteId) {
		for (int index=0; index < this.votes.size(); index ++ ) {
			if (this.votes.get(index).getId().equals(voteId) ) {
				this.votes.remove(index);
				break;
			}
		}
		
		this.voteSize = this.votes.size();
	}
	public List<Vote> getVotes() {
		return votes;
	}
	public void setVotes(List<Vote> votes) {
		this.votes = votes;
		this.voteSize = this.votes.size();
	}
}
