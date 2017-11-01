-- Update Asset Register Script_PartII
-- Mar 14, 2017 1:35:05 PM ICT
INSERT INTO AD_Process (AD_Process_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Name,IsReport,Value,IsDirectPrint,AccessLevel,EntityType,Statistic_Count,Statistic_Seconds,IsBetaFunctionality,IsServerProcess,ShowHelp,CopyFromProcess,AD_Process_UU) VALUES (300274,0,0,'Y',TO_TIMESTAMP('2017-03-14 13:35:05','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-14 13:35:05','YYYY-MM-DD HH24:MI:SS'),100,'Generate Invoice','N','Generate Invoice','N','3','U',0,0,'N','N','Y','N','d22ca4d2-65e9-4edc-9119-146dc833c048')
;

-- Mar 14, 2017 1:35:53 PM ICT
UPDATE AD_Process SET Classname='org.toba.habco.process.TCS_GenerateInvoiceFromInOut',Updated=TO_TIMESTAMP('2017-03-14 13:35:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=300274
;

-- Mar 14, 2017 1:37:09 PM ICT
INSERT INTO AD_Process_Para (AD_Process_Para_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Name,Description,Help,AD_Process_ID,SeqNo,AD_Reference_ID,IsRange,FieldLength,IsMandatory,ColumnName,IsCentrallyMaintained,EntityType,AD_Element_ID,AD_Process_Para_UU,IsEncrypted) VALUES (300590,0,0,'Y',TO_TIMESTAMP('2017-03-14 13:37:09','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-14 13:37:09','YYYY-MM-DD HH24:MI:SS'),100,'Document Type','Document type or rules','The Document Type determines document sequence and processing rules',300274,10,19,'N',0,'N','C_DocType_ID','Y','U',196,'872f7a47-98a2-4528-a82b-c9c818d0561f','N')
;

-- Mar 14, 2017 1:38:02 PM ICT
INSERT INTO AD_Val_Rule (AD_Val_Rule_ID,Name,Description,Type,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,EntityType,AD_Val_Rule_UU) VALUES (300107,'DocumentType','Only AP Invoice','S',0,0,'Y',TO_TIMESTAMP('2017-03-14 13:38:02','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-14 13:38:02','YYYY-MM-DD HH24:MI:SS'),100,'U','47e69234-d259-407b-b6e2-5d1ca78ee9fe')
;

-- Mar 14, 2017 1:39:49 PM ICT
UPDATE AD_Val_Rule SET Code='c_doctype.name=''AP Invoice''',Updated=TO_TIMESTAMP('2017-03-14 13:39:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=300107
;

-- Mar 14, 2017 1:40:29 PM ICT
UPDATE AD_Process_Para SET AD_Val_Rule_ID=300107,Updated=TO_TIMESTAMP('2017-03-14 13:40:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=300590
;

-- Mar 14, 2017 1:42:34 PM ICT
INSERT INTO AD_Process_Para (AD_Process_Para_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Name,Description,Help,AD_Process_ID,SeqNo,AD_Reference_ID,IsRange,FieldLength,IsMandatory,DefaultValue,ColumnName,IsCentrallyMaintained,EntityType,AD_Element_ID,AD_Process_Para_UU,IsEncrypted) VALUES (300594,0,0,'Y',TO_TIMESTAMP('2017-03-14 13:42:33','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-14 13:42:33','YYYY-MM-DD HH24:MI:SS'),100,'Date Invoiced','Date printed on Invoice','The Date Invoice indicates the date printed on the invoice.',300274,20,15,'N',0,'N','@#Date@','DateInvoiced','Y','U',267,'90c7a66c-cc6a-4fdc-a9c3-317f99b30e06','N')
;

-- Mar 14, 2017 1:43:06 PM ICT
INSERT INTO AD_Process_Para (AD_Process_Para_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Name,Description,Help,AD_Process_ID,SeqNo,AD_Reference_ID,AD_Reference_Value_ID,IsRange,FieldLength,IsMandatory,ColumnName,IsCentrallyMaintained,EntityType,AD_Element_ID,AD_Process_Para_UU,IsEncrypted) VALUES (300595,0,0,'Y',TO_TIMESTAMP('2017-03-14 13:43:05','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-14 13:43:05','YYYY-MM-DD HH24:MI:SS'),100,'Document Action','The targeted status of the document','You find the current status in the Document Status field. The options are listed in a popup',300274,30,17,135,'N',0,'N','DocAction','Y','U',287,'e8da2df9-7967-4d8f-a70a-ec47a4ce8afe','N')
;

-- Mar 14, 2017 1:43:46 PM ICT
INSERT INTO AD_Val_Rule (AD_Val_Rule_ID,Name,Description,Type,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,EntityType,AD_Val_Rule_UU) VALUES (300109,'DocumentAction','Only show Prepare and Complete','S',0,0,'Y',TO_TIMESTAMP('2017-03-14 13:43:46','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-14 13:43:46','YYYY-MM-DD HH24:MI:SS'),100,'U','fbc60f69-7de1-447d-8fc9-bc2bc811de55')
;

-- Mar 14, 2017 1:46:28 PM ICT
UPDATE AD_Val_Rule SET Code='AD_Ref_List.AD_Reference_ID = 135 AND (AD_Ref_List.value = ''CO'' OR AD_Ref_List.value = ''PR'')',Updated=TO_TIMESTAMP('2017-03-14 13:46:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=300109
;

-- Mar 14, 2017 1:46:36 PM ICT
UPDATE AD_Process_Para SET AD_Val_Rule_ID=300109,Updated=TO_TIMESTAMP('2017-03-14 13:46:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=300595
;

-- Mar 14, 2017 1:46:42 PM ICT
UPDATE AD_Process_Para SET DefaultValue='''PR''',Updated=TO_TIMESTAMP('2017-03-14 13:46:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=300595
;

-- Mar 14, 2017 2:01:08 PM ICT
UPDATE AD_Process_Para SET IsMandatory='N',Updated=TO_TIMESTAMP('2017-03-14 14:01:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=300590
;

-- Mar 14, 2017 2:01:10 PM ICT
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2017-03-14 14:01:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=300590
;

-- Mar 14, 2017 2:01:16 PM ICT
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2017-03-14 14:01:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=300594
;

-- Mar 14, 2017 2:01:20 PM ICT
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2017-03-14 14:01:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=300595
;

