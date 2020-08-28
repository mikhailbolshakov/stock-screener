create table us_acc.user_account_hst (
	id uuid primary key,
	account_id uuid not null,
	operation varchar(100) not null,
	systimestamp timestamp not null
);

alter table us_acc.user_account_hst add constraint c_fk_hst_account foreign key(account_id) references us_acc.user_account(id);

create index idx_hst_account_id on us_acc.user_account_hst(account_id);