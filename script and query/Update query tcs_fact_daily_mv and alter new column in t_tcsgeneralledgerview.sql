CREATE materialized view tcs_fact_daily_mv AS
select 
fa.ad_client_id, 
fa.ad_org_id, 
ao.name as org_name, 
fa.c_acctschema_id, 
cas.name as acct_schema_name, 
fa.account_id, 
cev.value as account_no, 
cev.name as account_name, 
fa.dateacct, fa.c_period_id, 
cp.name as period_name, 
fa.postingtype, 
sum(fa.amtacctdr) as amtacctdr, 
sum(fa.amtacctcr) as amtacctcr, 
sum(fa.amtsourcedr) as amtsourcedr, 
sum(fa.amtsourcecr) as amtsourcecr, 
sum(fa.amtacctdr-fa.amtacctcr) as amtacctbalance, 
sum(fa.amtsourcedr-fa.amtsourcecr) as amtsourcebalance,
case when cev.accounttype IN ('A','E','M') then 1 else -1 end as multiplier, 
cc.iso_code, 
fa.description,
gc.name as gl_category_name,
fa.fact_acct_id
from fact_acct fa
join ad_org ao on ao.ad_org_id=fa.ad_org_id
join c_acctschema cas on cas.c_acctschema_id=fa.c_acctschema_id
join c_elementvalue cev on cev.c_elementvalue_id=fa.account_id
join c_period cp on cp.c_period_id=fa.c_period_id
join c_currency cc on fa.c_currency_id = cc.c_currency_id
join gl_category gc on gc.gl_category_id = fa.gl_category_id
group by fa.ad_client_id, fa.ad_org_id, ao.name, fa.c_acctschema_id, fa.account_id, cev.value, cev.name, fa.dateacct, fa.c_period_id, fa.postingtype, cev.accounttype, cas.name, cp.name, cc.iso_code, fa.description,gc.name,fa.fact_acct_id;

ALTER materialized view tcs_fact_daily_mv OWNER TO devina;
 

ALTER TABLE t_tcsgeneralledgerview
add column gl_category_name varchar(60)
