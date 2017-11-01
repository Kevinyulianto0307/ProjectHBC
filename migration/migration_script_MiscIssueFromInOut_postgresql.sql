-- Update Asset Register Script_PartII
-- Mar 14, 2017 1:33:25 PM ICT
INSERT INTO AD_Process (AD_Process_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Name,IsReport,Value,IsDirectPrint,Classname,AccessLevel,EntityType,Statistic_Count,Statistic_Seconds,IsBetaFunctionality,IsServerProcess,ShowHelp,CopyFromProcess,AD_Process_UU) VALUES (300273,0,0,'Y',TO_TIMESTAMP('2017-03-14 13:33:25','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-14 13:33:25','YYYY-MM-DD HH24:MI:SS'),100,'Generate Misc Issue from Material Reciept','N','Generate Misc Issue from Material Reciep','N','org.toba.habco.process.TCS_GenerateMiscIssueFromInOut','3','TAOWI',0,0,'N','N','Y','N','56c311b7-5924-4254-9fbf-665296430da7')
;

-- Mar 14, 2017 1:35:40 PM ICT
INSERT INTO AD_Val_Rule (AD_Val_Rule_ID,Name,Type,Code,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,EntityType,AD_Val_Rule_UU) VALUES (300106,'Misc Issue Document Type','S','C_DocType.name = ''Misc Issue''',0,0,'Y',TO_TIMESTAMP('2017-03-14 13:35:39','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-14 13:35:39','YYYY-MM-DD HH24:MI:SS'),100,'U','37a9f5f6-6595-4b0a-9040-76952a686d72')
;

-- Mar 14, 2017 1:35:46 PM ICT
UPDATE AD_Val_Rule SET EntityType='TAOWI',Updated=TO_TIMESTAMP('2017-03-14 13:35:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=300106
;

-- Mar 14, 2017 1:36:06 PM ICT
INSERT INTO AD_Process_Para (AD_Process_Para_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Name,Description,Help,AD_Process_ID,SeqNo,AD_Reference_ID,IsRange,AD_Val_Rule_ID,FieldLength,IsMandatory,ColumnName,IsCentrallyMaintained,EntityType,AD_Element_ID,AD_Process_Para_UU,IsEncrypted) VALUES (300589,0,0,'Y',TO_TIMESTAMP('2017-03-14 13:36:06','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-14 13:36:06','YYYY-MM-DD HH24:MI:SS'),100,'Document Type','Document type or rules','The Document Type determines document sequence and processing rules',300273,10,19,'N',300106,0,'Y','C_DocType_ID','Y','U',196,'e848d2c4-56bd-44eb-acca-d0e742a5a570','N')
;

-- Mar 14, 2017 1:37:16 PM ICT
INSERT INTO AD_Process_Para (AD_Process_Para_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Name,Description,Help,AD_Process_ID,SeqNo,AD_Reference_ID,IsRange,FieldLength,IsMandatory,DefaultValue,ColumnName,IsCentrallyMaintained,EntityType,AD_Element_ID,AD_Process_Para_UU,IsEncrypted) VALUES (300591,0,0,'Y',TO_TIMESTAMP('2017-03-14 13:37:12','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-14 13:37:12','YYYY-MM-DD HH24:MI:SS'),100,'MovementDate','Movement Date','The Movement Date indicates the date of the misc issue.',300273,20,15,'N',0,'N','@#Date@','DateInvoiced','Y','U',267,'7f93292b-7e75-4b11-a1ec-b8373c0222cd','N')
;

-- Mar 14, 2017 1:40:34 PM ICT
INSERT INTO AD_Val_Rule (AD_Val_Rule_ID,Name,Type,Code,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,EntityType,AD_Val_Rule_UU) VALUES (300108,'Document Action PR CO','S','AD_Ref_List.AD_Reference_ID = 135 AND (AD_Ref_List.value = ''CO'' OR AD_Ref_List.value = ''PR'')',0,0,'Y',TO_TIMESTAMP('2017-03-14 13:40:33','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-14 13:40:33','YYYY-MM-DD HH24:MI:SS'),100,'TAOWI','d59ac4e7-bfdd-4d19-9ffb-17e3731cdd33')
;

-- Mar 14, 2017 1:41:10 PM ICT
INSERT INTO AD_Process_Para (AD_Process_Para_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Name,Description,Help,AD_Process_ID,SeqNo,AD_Reference_ID,AD_Reference_Value_ID,IsRange,AD_Val_Rule_ID,FieldLength,IsMandatory,DefaultValue,ColumnName,IsCentrallyMaintained,EntityType,AD_Element_ID,AD_Process_Para_UU,IsEncrypted) VALUES (300592,0,0,'Y',TO_TIMESTAMP('2017-03-14 13:41:07','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-14 13:41:07','YYYY-MM-DD HH24:MI:SS'),100,'Document Action','The targeted status of the document','You find the current status in the Document Status field. The options are listed in a popup',300273,30,17,135,'N',300108,0,'Y','''PR''','DocAction','Y','TAOWI',287,'f1142728-3739-4352-b02c-d966aff01eb7','N')
;

-- Mar 14, 2017 1:41:19 PM ICT
UPDATE AD_Process_Para SET IsMandatory='Y', EntityType='TAOWI',Updated=TO_TIMESTAMP('2017-03-14 13:41:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=300591
;

-- Mar 14, 2017 1:42:05 PM ICT
INSERT INTO AD_Process_Para (AD_Process_Para_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Name,Description,Help,AD_Process_ID,SeqNo,AD_Reference_ID,IsRange,FieldLength,IsMandatory,ColumnName,IsCentrallyMaintained,EntityType,AD_Element_ID,AD_Process_Para_UU,IsEncrypted) VALUES (300593,0,0,'Y',TO_TIMESTAMP('2017-03-14 13:42:04','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-14 13:42:04','YYYY-MM-DD HH24:MI:SS'),100,'Charge','Additional document charges','The Charge indicates a type of Charge (Handling, Shipping, Restocking)',300273,40,19,'N',0,'N','C_Charge_ID','Y','U',968,'28fa015b-8108-4490-916b-fca36b7467c7','N')
;

-- Mar 14, 2017 1:42:08 PM ICT
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2017-03-14 13:42:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=300593
;

-- Mar 14, 2017 1:42:16 PM ICT
UPDATE AD_Process_Para SET EntityType='TAOWI',Updated=TO_TIMESTAMP('2017-03-14 13:42:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=300593
;

-- Mar 14, 2017 1:42:24 PM ICT
UPDATE AD_Process_Para SET EntityType='TAOWI',Updated=TO_TIMESTAMP('2017-03-14 13:42:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=300589
;

-- Mar 14, 2017 1:43:58 PM ICT
INSERT INTO AD_ToolBarButton (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ComponentName,IsActive,AD_ToolBarButton_ID,Name,Updated,UpdatedBy,IsCustomization,AD_ToolBarButton_UU,"action",AD_Tab_ID,AD_Process_ID,SeqNo) VALUES (0,0,TO_TIMESTAMP('2017-03-14 13:43:57','YYYY-MM-DD HH24:MI:SS'),100,'Generate Misc Issue from Material Reciept','Y',300098,'Generate Misc Issue from Material Reciept',TO_TIMESTAMP('2017-03-14 13:43:57','YYYY-MM-DD HH24:MI:SS'),100,'N','c4527f37-ec52-4e98-9708-4dbe6ee23c9d','W',296,300273,40)
;

