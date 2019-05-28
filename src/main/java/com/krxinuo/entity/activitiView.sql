#act_id_user >> user
CREATE OR REPLACE VIEW ACT_ID_USER
    (id_, rev_, first_, last_, email_, pwd_, picture_id_)
AS
SELECT u.username,1,u.nickname,null,null,u.password,null
FROM user u

#act_id_group >> role
create or replace view act_id_group1
    (id_, rev_, name_, type_)
as
select r.role , 1 as rev_,r.role ,'' as type_
from role r;

#act_id_membership
create or replace view act_id_membership1
    (user_id_, group_id_)
as
select u.username,r.role
from  user u inner join user_role ur
                                  on u.id=ur.user_id inner join role r on ur.role_id=r.id;