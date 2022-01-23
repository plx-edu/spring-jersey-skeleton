CREATE TABLE IF NOT EXISTS `user` (
  `id` char(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `name` varchar(25) NOT NULL,
  `handle` varchar(25) NOT NULL,
  `created` timestamp NOT NULL DEFAULT current_timestamp(),
  `password` varchar(64) NOT NULL,
  `salt` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `handle` (`handle`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci;


CREATE TABLE IF NOT EXISTS `quest` (
  `id` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `user_id` char(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `title` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `objective` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `created` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci;


ALTER TABLE `quest` 
	ADD CONSTRAINT `userid_fk` FOREIGN KEY IF NOT EXISTS (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE;


CREATE TABLE IF NOT EXISTS `reward` (
  `id` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `quest_id` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  
  FOREIGN KEY (`quest_id`)
  		REFERENCES `quest` (`id`)
  		ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci;











