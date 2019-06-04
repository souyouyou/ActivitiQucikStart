#act_id_user >> user
CREATE OR REPLACE VIEW ACT_ID_USER
    (id_, rev_, first_, last_, email_, pwd_, picture_id_)
AS
SELECT u.username,1,u.nickname,null,u.email,u.password,null
FROM t_user u;

#act_id_group >> role
create or replace view act_id_group
    (id_, rev_, name_, type_)
as
select r.role , 1 as rev_,r.role ,'' as type_
from t_role r;

#act_id_membership
create or replace view act_id_membership
    (user_id_, group_id_)
as
select u.username,r.role
from  t_user u inner join t_user_role_relation ur
                                  on u.id=ur.user_id inner join t_role r on ur.role_id=r.id;