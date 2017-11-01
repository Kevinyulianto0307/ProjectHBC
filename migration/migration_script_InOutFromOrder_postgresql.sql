-- Update Asset Register Script_PartII
-- Mar 14, 2017 2:19:35 PM ICT
INSERT INTO AD_Process (AD_Process_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Name,Description,IsReport,Value,IsDirectPrint,AccessLevel,EntityType,Statistic_Count,Statistic_Seconds,IsBetaFunctionality,IsServerProcess,ShowHelp,CopyFromProcess,AD_Process_UU) VALUES (300275,0,0,'Y',TO_TIMESTAMP('2017-03-14 14:19:34','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-14 14:19:34','YYYY-MM-DD HH24:MI:SS'),100,'Generate In Out','Generate In Out From Order ','N','GenerateInOutFromOrder ','N','3','U',0,0,'N','N','Y','N','787ab3aa-295a-4ff2-8f30-f49d80fff836')
;

-- Mar 14, 2017 2:21:36 PM ICT
INSERT INTO AD_Val_Rule (AD_Val_Rule_ID,Name,Type,Code,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,EntityType,AD_Val_Rule_UU) VALUES (300110,'DocumentType Material Receipt','S','C_DocType.name =''MM Receipt''',0,0,'Y',TO_TIMESTAMP('2017-03-14 14:21:35','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-14 14:21:35','YYYY-MM-DD HH24:MI:SS'),100,'U','2db9fa63-eab3-4256-8183-1a2445d5b880')
;

-- Mar 14, 2017 2:22:21 PM ICT
INSERT INTO AD_Process_Para (AD_Process_Para_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Name,Description,Help,AD_Process_ID,SeqNo,AD_Reference_ID,IsRange,AD_Val_Rule_ID,FieldLength,IsMandatory,ColumnName,IsCentrallyMaintained,EntityType,AD_Element_ID,AD_Process_Para_UU,IsEncrypted) VALUES (300596,0,0,'Y',TO_TIMESTAMP('2017-03-14 14:22:21','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-14 14:22:21','YYYY-MM-DD HH24:MI:SS'),100,'Document Type','Document type or rules','The Document Type determines document sequence and processing rules',300275,10,19,'N',300110,0,'Y','C_DocType_ID','Y','U',196,'c5f39452-eaa1-48c7-a9a0-e20cafceb5d4','N')
;

-- Mar 14, 2017 2:23:10 PM ICT
UPDATE AD_Val_Rule SET Code='M_Warehouse.AD_Org_ID=@AD_Org_ID@',Updated=TO_TIMESTAMP('2017-03-14 14:23:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=300032
;

-- Mar 14, 2017 2:23:18 PM ICT
INSERT INTO AD_Process_Para (AD_Process_Para_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Name,Description,Help,AD_Process_ID,SeqNo,AD_Reference_ID,IsRange,AD_Val_Rule_ID,FieldLength,IsMandatory,ColumnName,IsCentrallyMaintained,EntityType,AD_Element_ID,AD_Process_Para_UU,IsEncrypted) VALUES (300597,0,0,'Y',TO_TIMESTAMP('2017-03-14 14:23:18','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-14 14:23:18','YYYY-MM-DD HH24:MI:SS'),100,'Warehouse','Storage Warehouse and Service Point','The Warehouse identifies a unique Warehouse where products are stored or Services are provided.',300275,20,19,'N',300032,0,'Y','M_Warehouse_ID','Y','U',459,'2927ecfe-9dba-4452-a20f-292e0f8d8312','N')
;

-- Mar 14, 2017 2:23:59 PM ICT
INSERT INTO AD_Process_Para (AD_Process_Para_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Name,Description,Help,AD_Process_ID,SeqNo,AD_Reference_ID,IsRange,AD_Val_Rule_ID,FieldLength,IsMandatory,ColumnName,IsCentrallyMaintained,EntityType,AD_Element_ID,AD_Process_Para_UU,IsEncrypted) VALUES (300598,0,0,'Y',TO_TIMESTAMP('2017-03-14 14:23:59','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-14 14:23:59','YYYY-MM-DD HH24:MI:SS'),100,'Locator','Warehouse Locator','The Locator indicates where in a Warehouse a product is located.',300275,30,19,'N',127,0,'Y','M_Locator_ID','Y','U',448,'b47320ef-e375-4b02-bba2-38b1d8e55898','N')
;

-- Mar 14, 2017 2:24:38 PM ICT
INSERT INTO AD_Process_Para (AD_Process_Para_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Name,Description,Help,AD_Process_ID,SeqNo,AD_Reference_ID,IsRange,FieldLength,IsMandatory,DefaultValue,ColumnName,IsCentrallyMaintained,EntityType,AD_Element_ID,AD_Process_Para_UU,IsEncrypted) VALUES (300599,0,0,'Y',TO_TIMESTAMP('2017-03-14 14:24:38','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-14 14:24:38','YYYY-MM-DD HH24:MI:SS'),100,'Movement Date','Date a product was moved in or out of inventory','The Movement Date indicates the date that a product moved in or out of inventory.  This is the result of a shipment, receipt or inventory movement.',300275,40,15,'N',0,'Y','@#Date@','MovementDate','Y','U',1037,'f12d96da-011b-49a6-90c6-57526e44b1fa','N')
;

-- Mar 14, 2017 2:25:47 PM ICT
INSERT INTO AD_Val_Rule (AD_Val_Rule_ID,Name,Type,Code,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,EntityType,AD_Val_Rule_UU) VALUES (300111,'Document Action','S','AD_Ref_List.AD_Reference_ID = 135 AND (AD_Ref_List.value = ''CO'' OR AD_Ref_List.value = ''PR'')',0,0,'Y',TO_TIMESTAMP('2017-03-14 14:25:47','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-14 14:25:47','YYYY-MM-DD HH24:MI:SS'),100,'U','afb84433-a4c9-485d-96fb-c4e3201a513f')
;

-- Mar 14, 2017 2:25:58 PM ICT
INSERT INTO AD_Process_Para (AD_Process_Para_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Name,Description,Help,AD_Process_ID,SeqNo,AD_Reference_ID,AD_Reference_Value_ID,IsRange,AD_Val_Rule_ID,FieldLength,IsMandatory,ColumnName,IsCentrallyMaintained,EntityType,AD_Element_ID,AD_Process_Para_UU,IsEncrypted) VALUES (300600,0,0,'Y',TO_TIMESTAMP('2017-03-14 14:25:58','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-03-14 14:25:58','YYYY-MM-DD HH24:MI:SS'),100,'Document Action','The targeted status of the document','You find the current status in the Document Status field. The options are listed in a popup',300275,50,17,135,'N',300111,0,'N','DocAction','Y','U',287,'80fb6c4e-baa9-48c0-99c0-594f87ba3617','N')
;

-- Mar 14, 2017 2:26:09 PM ICT
UPDATE AD_Process_Para SET DefaultValue='''PR''',Updated=TO_TIMESTAMP('2017-03-14 14:26:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=300600
;

-- Mar 14, 2017 2:28:34 PM ICT
INSERT INTO AD_ToolBarButton (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ComponentName,IsActive,AD_ToolBarButton_ID,Name,Updated,UpdatedBy,IsCustomization,AD_ToolBarButton_UU,"action",AD_Tab_ID,AD_Process_ID,SeqNo) VALUES (0,0,TO_TIMESTAMP('2017-03-14 14:28:34','YYYY-MM-DD HH24:MI:SS'),100,'Generate In Out','Y',300099,'Generate In Out',TO_TIMESTAMP('2017-03-14 14:28:34','YYYY-MM-DD HH24:MI:SS'),100,'N','4ef04056-27fc-4fe1-a56f-7e17033f3e64','W',294,300275,80)
;

