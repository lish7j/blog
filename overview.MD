
#### 项目架构
- 1.实体划分
    - 点赞实体 Vote (与博客和用户关联，一篇博客可以有多个用户的点赞，但是每个用户只能点赞一次)
        - 包含字段 (id, 时间戳，用户id, 博客id)
    - 评论点赞实体 CommentVote (与评论和用户关联，一条评论可以有多个用户的点赞，但是每个用户只能点赞一次)
        - 包含字段 (id, 时间戳, 用户id, 评论id)
    - 标签实体 Tag
        - 包含字段 (id, 标签名name, 标签数量count)
    - 评论实体 Comment (与博客和用户管理，一篇博客可以有多个用户的多条评论)
        - 包含字段 (id, 评论内容content, 评论用户id, 评论时间createTime, 评论的博客id)
    - 分类实体 Catalog (用户发布博客之前，必须给博客一个分类，与博客是一对一的关系)
        - 包含字段 (id, 分类名name, 用户id)
    - 用户实体 User
        - 包含字段 (id, 昵称name可以修改, 邮箱email, 账号名字username不能修改, 密码password, 头像图片地址avatar, 角色列表authorities)
    - 博客实体 Blog
        - 包含字段 (id, 用户id, 标题title, 摘要summary, 内容content, htmContent, 阅读量readSize, 点赞量voteSize, 博客分类id, 创建时间createTime, 更新时间updateTime, 标签tag)
    - 关注实体 Follow
        - 包含字段 (id, followId, followedId)