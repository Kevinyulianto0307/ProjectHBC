-- process update tax amount
-- Dec 29, 2016 3:57:06 PM WIB
INSERT INTO AD_Process (AD_Process_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Name,IsReport,Value,IsDirectPrint,Classname,AccessLevel,EntityType,Statistic_Count,Statistic_Seconds,IsBetaFunctionality,IsServerProcess,ShowHelp,CopyFromProcess,AD_Process_UU) VALUES (1100172,0,0,'Y',TO_TIMESTAMP('2016-12-29 15:57:06','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2016-12-29 15:57:06','YYYY-MM-DD HH24:MI:SS'),100,'Set Tax Amount','N','HBC_SetTaxAmount','N','org.toba.habco.process.HBC_SetTaxAmount','3','H',0,0,'N','N','Y','N','d9d4aef4-a9e5-4808-b2eb-cff7ff7df9ae')
;

-- Dec 29, 2016 4:04:07 PM WIB
INSERT INTO AD_Reference (AD_Reference_ID,Name,ValidationType,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,EntityType,IsOrderByValue,AD_Reference_UU) VALUES (1100085,'Tax in Invoice Tax','T',0,0,'Y',TO_TIMESTAMP('2016-12-29 16:04:07','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2016-12-29 16:04:07','YYYY-MM-DD HH24:MI:SS'),100,'H','N','dd7bcba2-a0ac-4e56-ae4a-d6002e822d2f')
;

-- Dec 29, 2016 4:05:08 PM WIB
INSERT INTO AD_Ref_Table (AD_Reference_ID,AD_Table_ID,AD_Key,AD_Display,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,IsValueDisplayed,EntityType,AD_Ref_Table_UU) VALUES (1100085,334,3850,3860,0,0,'Y',TO_TIMESTAMP('2016-12-29 16:05:08','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2016-12-29 16:05:08','YYYY-MM-DD HH24:MI:SS'),100,'N','H','fe87548b-9e21-4107-bc2a-1f8c38e8b3a1')
;

-- Dec 29, 2016 4:05:15 PM WIB
INSERT INTO AD_Process_Para (AD_Process_Para_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Name,AD_Process_ID,SeqNo,AD_Reference_ID,AD_Reference_Value_ID,IsRange,FieldLength,IsMandatory,ColumnName,IsCentrallyMaintained,EntityType,AD_Process_Para_UU,IsEncrypted) VALUES (1100328,0,0,'Y',TO_TIMESTAMP('2016-12-29 16:05:15','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2016-12-29 16:05:15','YYYY-MM-DD HH24:MI:SS'),100,'C_Tax_ID',1100172,10,18,1100085,'N',0,'N','C_Tax_ID','N','H','8523a2a0-4153-4e2e-bd2f-7e84d0bac3d3','N')
;

-- Dec 29, 2016 4:05:37 PM WIB
INSERT INTO AD_Process_Para (AD_Process_Para_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Name,AD_Process_ID,SeqNo,AD_Reference_ID,IsRange,FieldLength,IsMandatory,ColumnName,IsCentrallyMaintained,EntityType,AD_Process_Para_UU,IsEncrypted) VALUES (1100329,0,0,'Y',TO_TIMESTAMP('2016-12-29 16:05:37','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2016-12-29 16:05:37','YYYY-MM-DD HH24:MI:SS'),100,'TaxAmt',1100172,20,37,'N',0,'N','TaxAmt','N','H','fbb9ccca-6a9a-4af4-b922-19227ad6a9b2','N')
;

-- Dec 29, 2016 4:06:42 PM WIB
INSERT INTO AD_ToolBarButton (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ComponentName,IsActive,AD_ToolBarButton_ID,Name,Updated,UpdatedBy,IsCustomization,AD_ToolBarButton_UU,"action",AD_Tab_ID,AD_Process_ID,DisplayLogic,SeqNo) VALUES (0,0,TO_TIMESTAMP('2016-12-29 16:06:42','YYYY-MM-DD HH24:MI:SS'),100,'Set Tax Amount','Y',1000046,'Set Tax Amount',TO_TIMESTAMP('2016-12-29 16:06:42','YYYY-MM-DD HH24:MI:SS'),100,'N','5aa2977d-612c-404b-bcdb-43fd4061b978','W',290,1100172,'@DocStatus@=''CO''',50)
;

-- Dec 29, 2016 4:07:30 PM WIB
INSERT INTO AD_ToolBarButton (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ComponentName,IsActive,AD_ToolBarButton_ID,Name,Updated,UpdatedBy,IsCustomization,AD_ToolBarButton_UU,"action",AD_Tab_ID,AD_Process_ID,DisplayLogic,SeqNo) VALUES (0,0,TO_TIMESTAMP('2016-12-29 16:07:30','YYYY-MM-DD HH24:MI:SS'),100,'Set Tax Amount','Y',1000047,'Set Tax Amount',TO_TIMESTAMP('2016-12-29 16:07:30','YYYY-MM-DD HH24:MI:SS'),100,'N','1e33e67a-94bc-4fff-a4ba-2aab3af3e9d7','W',263,1100172,'@DocStatus@=''CO''',100)
;

-- Dec 29, 2016 4:08:47 PM WIB
UPDATE AD_Ref_Table SET WhereClause='C_Invoice_ID=@C_Invoice_ID@',Updated=TO_TIMESTAMP('2016-12-29 16:08:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=1100085
;

-- Dec 29, 2016 4:10:15 PM WIB
UPDATE AD_Process_Para SET AD_Element_ID=213,Updated=TO_TIMESTAMP('2016-12-29 16:10:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=1100328
;
