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
fa.fact_acct_id
from fact_acct fa
join ad_org ao on ao.ad_org_id=fa.ad_org_id
join c_acctschema cas on cas.c_acctschema_id=fa.c_acctschema_id
join c_elementvalue cev on cev.c_elementvalue_id=fa.account_id
join c_period cp on cp.c_period_id=fa.c_period_id
join c_currency cc on fa.c_currency_id = cc.c_currency_id
group by fa.ad_client_id, fa.ad_org_id, ao.name, fa.c_acctschema_id, fa.account_id, cev.value, cev.name, fa.dateacct, fa.c_period_id, fa.postingtype, cev.accounttype, cas.name, cp.name, cc.iso_code, fa.description,fa.fact_acct_id;

ALTER materialized view tcs_fact_daily_mv OWNER TO devina;








-- Create target database
CREATE TABLE adempiere.t_tcsgeneralledger
(
  ad_client_id numeric(10,0) NOT NULL,
  ad_org_id numeric(10,0) NOT NULL,
  org_name character(60) NOT NULL,
  c_acctschema_id numeric(10,0),
  acct_schema_name character(60),
  account_id numeric(10,0),
  account_no character(40),
  account_name character(120),
  dateacct timestamp without time zone,
  c_period_id numeric(10,0),
  period_name character(60),
  postingType character(1),
  amtacctdr numeric, 
  amtacctcr numeric,
  amtsourcecr numeric,
  amtsourcedr numeric,
  amtacctbalance numeric, 
  iso_code character(3),
  description character(255),
  AD_PInstance_ID numeric(10,0),
  sequence numeric(10,0)
)









-- Create target database
CREATE TABLE adempiere.t_tcsgeneralledgerview
(
  ad_client_id numeric(10,0) NOT NULL,
  ad_org_id numeric(10,0) NOT NULL,
  org_name character(60) NOT NULL,
  c_acctschema_id numeric(10,0),
  acct_schema_name character(60),
  account_id numeric(10,0),
  account_no character(40),
  account_name character(120),
  dateacct timestamp without time zone,
  c_period_id numeric(10,0),
  period_name character(60),
  postingType character(1),
  amtacctdr numeric, 
  amtacctcr numeric,
  amtsourcecr numeric,
  amtsourcedr numeric,
  amtacctbalance numeric, 
  iso_code character(3),
  description character(255),
  AD_PInstance_ID numeric(10,0),
  sequence numeric(10,0)
)

CREATE TABLE adempiere.t_tcsgeneralledger
(
  ad_client_id numeric(10,0) NOT NULL,
  ad_org_id numeric(10,0) NOT NULL,
  org_name character(60) NOT NULL,
  c_acctschema_id numeric(10,0),
  acct_schema_name character(60),
  account_id numeric(10,0),
  account_no character(40),
  account_name character(120),
  dateacct timestamp without time zone,
  c_period_id numeric(10,0),
  period_name character(60),
  postingType character(1),
  amtacctdr numeric, 
  amtacctcr numeric,
  amtsourcecr numeric,
  amtsourcedr numeric,
  amtacctbalance numeric, 
  iso_code character(3),
  description character(255),
  AD_PInstance_ID numeric(10,0),
  sequence numeric(10,0)
)



CREATE TABLE adempiere.t_tcsgeneralledgerdaily
(
  ad_client_id numeric(10,0) NOT NULL,
  ad_org_id numeric(10,0) NOT NULL,
  org_name character(60) NOT NULL,
  c_acctschema_id numeric(10,0),
  acct_schema_name character(60),
  account_id numeric(10,0),
  account_no character(40),
  account_name character(120),
  dateacct timestamp without time zone,
  c_period_id numeric(10,0),
  period_name character(60),
  amtacctdr numeric, 
  amtacctcr numeric,
  amtsourcecr numeric,
  amtsourcedr numeric,
  amtacctbalance numeric, 
  AD_PInstance_ID numeric(10,0),
  sequence numeric(10,0)
)




CREATE TABLE adempiere.t_tcsgeneralledgerdailyview
(
  ad_client_id numeric(10,0) NOT NULL,
  ad_org_id numeric(10,0) NOT NULL,
  org_name character(60) NOT NULL,
  c_acctschema_id numeric(10,0),
  acct_schema_name character(60),
  account_id numeric(10,0),
  account_no character(40),
  account_name character(120),
  dateacct timestamp without time zone,
  c_period_id numeric(10,0),
  period_name character(60),
  amtacctdr numeric, 
  amtacctcr numeric,
  amtsourcecr numeric,
  amtsourcedr numeric,
  amtacctbalance numeric, 
  AD_PInstance_ID numeric(10,0),
  sequence numeric(10,0)
)





