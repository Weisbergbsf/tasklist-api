CREATE TABLE IF NOT EXISTS tasks (
        id integer not null auto_increment,
        title varchar(200) not null,
	status varchar(255),
        description varchar(255),
	create_at datetime(6),
	update_at datetime(6),
        done_at datetime(6),
        primary key (id)
    ) engine=InnoDB
