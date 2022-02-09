1.把标签存在一个MySQL表里面，然后通过计数来找到热门标签。
create table `tag`
(`id` bigint(20) NOT NULL AUTO_INCREMENT,
`name` varchar(30) DEFAULT NULL,
`count` bigint(20) NOT NULL,
PRIMARY KEY (`id`),
KEY `name_index` (`name`),) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

create table `tag` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`name` varchar(30) DEFAULT NULL,
`count` bigint(20) NOT NULL,
PRIMARY KEY (`id`),
KEY `name_index` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

1.给评论添加点赞功能
一条评论包含点赞数，可以取消点赞，同一个人不能点赞两次，所以一条评论必须包含所有点赞的用户实体。


1.添加精选评论


catalog表包含字段（id, name, user_id) 表明一个用户可以有多个catalog, 而不同的用户可以有相同名字的catalog;
select
c.id, c.name, u.id, u.avatar, u.email, u.name, u.password, u.username
from catalog c, user u
where c.user_id = 3 and u.id = 3;