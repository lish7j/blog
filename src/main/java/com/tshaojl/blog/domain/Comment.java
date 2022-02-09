package com.tshaojl.blog.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Comment 实体
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // 实体
public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id // 主键
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
	private Long id; // 用户的唯一标识

	@NotEmpty(message = "评论内容不能为空")
	@Size(min=2, max=500)
	@Column(nullable = false) // 映射为字段，值不能为空
	private String content;
 
	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;

	@Column(name="vote_size")
	private Integer voteSize = 0;  // 点赞量

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "commnet_vo", joinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "commentVo_id", referencedColumnName = "id"))
	private List<CommentVote> votes;
	
	@Column(nullable = false) // 映射为字段，值不能为空
	@org.hibernate.annotations.CreationTimestamp  // 由数据库自动创建时间
	private Timestamp createTime;

	public Comment(User user, String content) {
		this.content = content;
		this.user = user;
	}
 
}
