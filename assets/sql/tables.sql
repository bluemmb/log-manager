
CREATE TABLE `logs`
(
    `key`        varchar(40) NOT NULL ,
    `component`  varchar(20) NOT NULL ,
    `logdatetime`   datetime(3) NULL ,
    `type`       varchar(30) NULL ,
    `threadName` varchar(20) NULL ,
    `className`  varchar(200) NULL ,
    `message`    text NULL ,
    `created_at`  datetime(3) NOT NULL ,

    PRIMARY KEY (`key`, `component`)
);

CREATE TABLE `alerts`
(
    `id`          bigint NOT NULL ,
    `component`   varchar(20) NOT NULL ,
    `ruleName`    varchar(50) NOT NULL ,
    `rate`        double NOT NULL ,
    `message`     varchar(200) NOT NULL ,
    `description` text NOT NULL ,
    `created_at`  datetime(3) NOT NULL ,

    PRIMARY KEY (`id`)
);
