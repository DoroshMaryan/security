INSERT IGNORE INTO `spring_security`.`user` (`id`, `username`, `password`, `algorithm`) VALUES ('1', 'john', '$2a$10$Ev8DTnRoA4DfuAn7tbge5OvTjtFYIv8pc7IEL26WRJfEiHDjxVc56', 'BCRYPT');

INSERT IGNORE INTO `spring_security`.`authority` (`id`, `name`, `user`) VALUES ('1', 'READ', '1');
INSERT IGNORE INTO `spring_security`.`authority` (`id`, `name`, `user`) VALUES ('2', 'WRITE', '1');

INSERT IGNORE INTO `spring_security`.`product` (`id`, `name`, `price`, `currency`) VALUES ('1', 'Chocolate', '10', 'USD');
