CREATE TABLE `config` (
                          `id` int NOT NULL AUTO_INCREMENT,
                          `key` varchar(255) DEFAULT NULL,
                          `value` text,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;