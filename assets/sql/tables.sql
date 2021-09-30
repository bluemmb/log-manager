
CREATE TABLE `alerts`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `ruleName`    varchar(50) NOT NULL ,
    `component`   varchar(50) NOT NULL ,
    `description` text NOT NULL ,
    `created_at`  datetime(3) NOT NULL ,

    PRIMARY KEY (`id`),
    INDEX (`ruleName`),
    INDEX (`component`),
    INDEX (`created_at`)
);
